package exer4_1_1_timeServer;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Date;

public class UdpTimeClient {
	static DatagramSocket ds;
	static DatagramPacket dp;
	private static int port  = 37;
	private static String ip_add="time.nist.gov";
	
	//������
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			
			/*while(true) {*/	
				//�������ݱ��׽���
				ds = new DatagramSocket(port);
				
				//�������ݲ������ݴ��				
				byte[] bytes = new byte[1];
				InetAddress addr = InetAddress.getByName(ip_add);		
				dp = new DatagramPacket(bytes, bytes.length,addr,port);
				
				//����socket����ķ��ͷ����������ݰ�
				ds.send(dp);
				
				//����һ�����ݰ�����������
				byte[] by = new byte[1024];
				DatagramPacket dp = new DatagramPacket(by, by.length);
				ds.receive(dp);
				byte[] get =  dp.getData();
				
				long differ = 2208988800L;
				
				long secondsSince1900 = 0;
				for(int i =0; i<4;i++) {
					secondsSince1900 = (secondsSince1900<<8) | (get[i]&0x000000FF);
					
				}
				long secondsSince1970 = secondsSince1900 - differ;
				long msSince1970 = secondsSince1970 * 1000;
				Date time = new Date(msSince1970);
				
				System.out.println("��ȡ����ϢΪ��"+time);
				//�ͷ���Դ
				ds.close();
				Thread.sleep(999);
			/*}*/
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
