package exam03_3_2_2_plus;

import java.io.DataInput;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

/**
 * 求和的客户端
 * @author Administrator
 *
 */
public class PlusClient {
	private static int dig_nums=2;
	private static int[] dig;
	
	public void client() {
		try {
			Socket s = new Socket("127.0.0.1", 8888);
			//想服务端发送数据
			DataOutputStream dataOutputStream = new DataOutputStream(s.getOutputStream());
			dataOutputStream.writeInt(100001);
			//接受服务器返回的数据
			DataInputStream dataInputStream = new DataInputStream(s.getInputStream());
			int re = dataInputStream.readInt();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		PlusClient client = new PlusClient();	
		//数字信息初始化
		dig = client.input_digit(dig_nums);
		try {
			//定义套接字与服务端建立连接，客户端访问服务端需要指明服务端的主机名或IP
			Socket s  = new Socket("127.0.0.1",6666);
			//定义输出流将数据发送至服务端
			DataOutputStream out=new DataOutputStream(
					s.getOutputStream());
			//首先将数组个数传送过去
			out.writeInt(dig_nums);
			//然后传送数字信息过去
			for(int i = 0;i<dig_nums;i++) {
				out.writeInt(dig[i]);
			}
			Thread.sleep(10);
			//定义输入流接收服务端返回的计算结果
			DataInputStream dis = new DataInputStream(s.getInputStream()) ;
			System.out.println("获取的数的和为："+dis.readInt());
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
	
	public int[] input_digit(int nums) {
		int count = 0;
		int[] dig = new int[nums];
		Scanner scan = new Scanner(System.in);
		System.out.println("请输入"+nums+"个数字：");
		while(count < nums) {
			dig[count++] = scan.nextInt();
		}
		return dig;
	}

}
