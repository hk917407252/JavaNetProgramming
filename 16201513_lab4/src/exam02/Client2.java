package exam02;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Random;

public class Client2 {
	private static Random random = new Random();
	static DatagramSocket ds;
	static DatagramPacket dp;
	private static int port  = 2008;
	private static String ip_add="127.0.0.1";
	
	public static void main(String[] args) {
		//�������Ͷ˵�socket����
		try {
			while(true) {
				ds = new DatagramSocket();
				//�������ݲ������ݴ��
				String temp =  String.valueOf((random.nextInt(70)-30));
				byte[] by = temp.getBytes();
				InetAddress addr = InetAddress.getByName(ip_add);		
				dp = new DatagramPacket(by, by.length,addr,port);
				
				//����socket����ķ��ͷ����������ݰ�
				ds.send(dp);
				
				//�ͷ���Դ
				ds.close();
				Thread.sleep(1000);
			}
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
}
