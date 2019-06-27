package exam03_3_2_3_variance_plus_ave;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

import exam03_3_2_2_plus.PlusClient;

/**
 * 求方差、平均数、综合的客户端
 * @author Administrator
 *
 */
public class VPAClient {
	private static int port = 8881;
	private static int[] dig;
	private static int inputNums = 3;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int count = 0;
		VPAClient vpaclient = new VPAClient();
		while(count<inputNums) {
			//数字信息初始化
			dig = vpaclient.input_digit();
			try {
				
				Socket s  = new Socket("127.0.0.1",port);
				vpaclient.get_info(s);
				count++;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
			
	}
	
	public void get_info(Socket s) {
		try {
			DataOutputStream out=new DataOutputStream(
					s.getOutputStream());
			//首先将数组元素个数传送过去
			out.writeInt(dig[dig.length-1]);
			//然后传送数字信息过去,最后一个长度信息不需要重复传送过去
			for(int i = 0;i<dig.length-1;i++) {
				out.writeInt(dig[i]);
			}
			Thread.sleep(10);
			DataInputStream dis = new DataInputStream(s.getInputStream()) ;
			System.out.println("获取的数的和为："+dis.readInt());
			System.out.println("获取的数的平均数为："+dis.readDouble());
			System.out.println("获取的数的方差为："+dis.readDouble());
			/*out.close();
			dis.close();*/
			s.close();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public int[] input_digit() {
		Scanner scan = new Scanner(System.in);
		int count = 0;
		int nums = 0;
		System.out.println("请输入需要输入的数字的个数：");
		nums = scan.nextInt();
		int[] dig = new int[nums+1];
		
		System.out.println("请输入"+nums+"个数字：");
		while(count < nums) {
			dig[count++] = scan.nextInt();
		}
		dig[count] = nums;
		return dig;
	}

}
