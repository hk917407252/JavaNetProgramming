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
				
				/*//发送请求
				byte[] by1 = new byte[1];
				InetAddress addr = InetAddress.getByName(ip_add);		
				dp = new DatagramPacket(by1, by1.length,addr,port);
				ds.send(dp);*/
				
				//接受验证码
				byte[] by2 = new byte[1024];					//创建接受容器
				dp = new DatagramPacket(by2, by2.length);		//创建数据报
				ds.receive(dp);									//使用数据报接受数据
				String check_info = new String(dp.getData(),0,dp.getLength());		//将所接受的字节数据转化为字符串数据
				
				//输出结果
				System.out.println("从服务器获取的验证码为："+ check_info);
				
				//关闭资源
				ds.close();
				
				//设置休眠时间
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
