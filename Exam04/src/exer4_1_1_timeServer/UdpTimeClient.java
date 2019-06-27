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
	
	//主函数
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			
			/*while(true) {*/	
				//定义数据报套接字
				ds = new DatagramSocket(port);
				
				//创建数据并把数据打包				
				byte[] bytes = new byte[1];
				InetAddress addr = InetAddress.getByName(ip_add);		
				dp = new DatagramPacket(bytes, bytes.length,addr,port);
				
				//调用socket对象的发送方法发送数据包
				ds.send(dp);
				
				//创建一个数据包，接受容器
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
				
				System.out.println("获取的信息为："+time);
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
