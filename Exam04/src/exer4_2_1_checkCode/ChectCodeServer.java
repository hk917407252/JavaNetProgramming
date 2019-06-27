package exer4_2_1_checkCode;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.Random;

public class ChectCodeServer {
	static DatagramSocket ds;
	static DatagramPacket dp;
	private static int port  = 6666;
	private static String ip_add="127.0.0.1";
	
	
	/**
	 * 定义一个获取随机验证码的方法：getCode();
	 * @param n
	 * @return
	 */
	public static String getCode(int n) {
		String string = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";//保存数字0-9 和 大小写字母
		StringBuffer sb = new StringBuffer(); 	//声明一个StringBuffer对象sb 保存 验证码
		for (int i = 0; i < n; i++) {
			Random random = new Random();		//创建一个新的随机数生成器
			int index = random.nextInt(string.length());//返回[0,string.length)范围的int值    作用：保存下标
			char ch = string.charAt(index);		//charAt() : 返回指定索引处的 char 值   -->赋值给char字符对象ch
			sb.append(ch);						
		}
		return sb.toString();					
	}
	
	
	//主函数
	public static void main(String[] args) {
		ChectCodeServer chectCodeServer = new ChectCodeServer();
		try {	
			while(true) {
				//初始化数据报套接字
				ds = new DatagramSocket();
				MyThread t = chectCodeServer.new MyThread(ds);
				t.start();
				
				//设置休眠时间
				Thread.sleep(1000);
				
				/*//创建一个数据包，接受容器
				byte[] by = new byte[1024];
				DatagramPacket dp = new DatagramPacket(by, by.length);
					
				//调用socket的receive方法接受数据（以数据包的形式）
				ds.receive(dp);*/
				
				/*//创建数据并把数据打包				
				byte[] bytes = getCode(5).getBytes();
				InetAddress addr = InetAddress.getByName(ip_add);		
				dp = new DatagramPacket(bytes, bytes.length,addr,port);
				
				//调用socket对象的发送方法发送数据包
				ds.send(dp);
			
				//关闭资源
				ds.close();*/
			}
		} catch (SocketException e) {
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
	
	
	/**
	 * 定义线程类,线程类负责将验证码发送到客户端
	 * @author Administrator
	 *
	 */
	class MyThread extends Thread{
		private DatagramSocket ds;
		
		public MyThread(DatagramSocket ds) {
			this.ds = ds;
		}
		
		@Override
		public void run() {
			try {
				//创建数据并把数据打包				
				byte[] bytes = getCode(5).getBytes();
				InetAddress addr = InetAddress.getByName(ip_add);		
				dp = new DatagramPacket(bytes, bytes.length,addr,port);
				
				//调用socket对象的发送方法发送数据包
				ds.send(dp);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
			//关闭资源
			ds.close();
		}
	}

}
