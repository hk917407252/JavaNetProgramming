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
		//创建发送端的socket对象
		try {
			while(true) {
				ds = new DatagramSocket();
				//创建数据并把数据打包
				String temp =  String.valueOf((random.nextInt(70)-30));
				byte[] by = temp.getBytes();
				InetAddress addr = InetAddress.getByName(ip_add);		
				dp = new DatagramPacket(by, by.length,addr,port);
				
				//调用socket对象的发送方法发送数据包
				ds.send(dp);
				
				//释放资源
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
