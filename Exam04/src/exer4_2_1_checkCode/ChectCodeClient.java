package exer4_2_1_checkCode;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public class ChectCodeClient {
	static DatagramSocket ds;
	static DatagramPacket dp;
	private static int port  = 6666;
	private static String ip_add="127.0.0.1";
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			while(true) {
				ds = new DatagramSocket(port);
				
				/*//��������
				byte[] by1 = new byte[1];
				InetAddress addr = InetAddress.getByName(ip_add);		
				dp = new DatagramPacket(by1, by1.length,addr,port);
				ds.send(dp);*/
				
				//������֤��
				byte[] by2 = new byte[1024];					//������������
				dp = new DatagramPacket(by2, by2.length);		//�������ݱ�
				ds.receive(dp);									//ʹ�����ݱ���������
				String check_info = new String(dp.getData(),0,dp.getLength());		//�������ܵ��ֽ�����ת��Ϊ�ַ�������
				
				//������
				System.out.println("�ӷ�������ȡ����֤��Ϊ��"+ check_info);
				
				//�ر���Դ
				ds.close();
				
				//��������ʱ��
				Thread.sleep(1000);
			}
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
