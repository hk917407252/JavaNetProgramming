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
	
	//主函数
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			
			/*while(true) {*/	
				//定义数据报套接字
				ds = new DatagramSocket(port);
				
				//创建数据并把数据打包				
				byte[] bytes = new NtpMessage().toByteArray();  
				InetAddress addr = InetAddress.getByName(ip_add);		
				dp = new DatagramPacket(bytes, bytes.length,addr,port);
				
				//调用socket对象的发送方法发送数据包
				ds.send(dp);
				
				//创建一个数据包，接受容器
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
				//释放资源
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
