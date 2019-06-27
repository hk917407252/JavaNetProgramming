package exam03_3_2_2_plus;

import java.io.DataInput;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

/**
 * ��͵Ŀͻ���
 * @author Administrator
 *
 */
public class PlusClient {
	private static int dig_nums=2;
	private static int[] dig;
	
	public void client() {
		try {
			Socket s = new Socket("127.0.0.1", 8888);
			//�����˷�������
			DataOutputStream dataOutputStream = new DataOutputStream(s.getOutputStream());
			dataOutputStream.writeInt(100001);
			//���ܷ��������ص�����
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
		//������Ϣ��ʼ��
		dig = client.input_digit(dig_nums);
		try {
			//�����׽��������˽������ӣ��ͻ��˷��ʷ������Ҫָ������˵���������IP
			Socket s  = new Socket("127.0.0.1",6666);
			//��������������ݷ����������
			DataOutputStream out=new DataOutputStream(
					s.getOutputStream());
			//���Ƚ�����������͹�ȥ
			out.writeInt(dig_nums);
			//Ȼ����������Ϣ��ȥ
			for(int i = 0;i<dig_nums;i++) {
				out.writeInt(dig[i]);
			}
			Thread.sleep(10);
			//�������������շ���˷��صļ�����
			DataInputStream dis = new DataInputStream(s.getInputStream()) ;
			System.out.println("��ȡ�����ĺ�Ϊ��"+dis.readInt());
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
		System.out.println("������"+nums+"�����֣�");
		while(count < nums) {
			dig[count++] = scan.nextInt();
		}
		return dig;
	}

}
