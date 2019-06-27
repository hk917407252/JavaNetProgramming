package exam03_3_2_3_variance_plus_ave;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

import exam03_3_2_2_plus.PlusClient;

/**
 * �󷽲ƽ�������ۺϵĿͻ���
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
			//������Ϣ��ʼ��
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
			//���Ƚ�����Ԫ�ظ������͹�ȥ
			out.writeInt(dig[dig.length-1]);
			//Ȼ����������Ϣ��ȥ,���һ��������Ϣ����Ҫ�ظ����͹�ȥ
			for(int i = 0;i<dig.length-1;i++) {
				out.writeInt(dig[i]);
			}
			Thread.sleep(10);
			DataInputStream dis = new DataInputStream(s.getInputStream()) ;
			System.out.println("��ȡ�����ĺ�Ϊ��"+dis.readInt());
			System.out.println("��ȡ������ƽ����Ϊ��"+dis.readDouble());
			System.out.println("��ȡ�����ķ���Ϊ��"+dis.readDouble());
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
		System.out.println("��������Ҫ��������ֵĸ�����");
		nums = scan.nextInt();
		int[] dig = new int[nums+1];
		
		System.out.println("������"+nums+"�����֣�");
		while(count < nums) {
			dig[count++] = scan.nextInt();
		}
		dig[count] = nums;
		return dig;
	}

}
