package exer4_1_2_NTP;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Date;

public class UdpNTPClient {
	static DatagramSocket ds;
	static DatagramPacket dp;
	private static int port  = 123;
	private static String ip_add="ntp1.aliyun.com";
	
	//������
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			
			/*while(true) {*/	
				//�������ݱ��׽���
				ds = new DatagramSocket(port);
				
				//�������ݲ������ݴ��				
				byte[] bytes = new NtpMessage().toByteArray();  
				InetAddress addr = InetAddress.getByName(ip_add);		
				dp = new DatagramPacket(bytes, bytes.length,addr,port);
				
				//����socket����ķ��ͷ����������ݰ�
				ds.send(dp);
				
				//����һ�����ݰ�����������
				byte[] by = new byte[1024];
				DatagramPacket dp = new DatagramPacket(by, by.length);
				ds.receive(dp);
				
				double destinationTimestamp = (System.currentTimeMillis() / 1000.0) + 2208988800.0;  
				
				// Validate NTP Response  
                // IOException thrown if packet does not decode as expected.  
                NtpMessage msg = new NtpMessage(dp.getData());  
                double localClockOffset = ((msg.receiveTimestamp - msg.originateTimestamp) + (msg.transmitTimestamp - destinationTimestamp)) / 2;  
				
				Date time = new Date();
				
				System.out.println("NTP message : " + msg.toString());  
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
