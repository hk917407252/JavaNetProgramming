package exer4_2_2_randomSum;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;


public class Server {
	static DatagramSocket ds;
	static DatagramPacket dp;
	private static int port1  = 6661;
	private static int port2  = 6662;
	private static String ip_add="127.0.0.1";
	
	/**
	 * 根据所从客户端接收的字符串来进行求和函数
	 * @param s
	 * @return
	 */
	public String sum_num(String s) {
		int sum = 0;
		String[] ss = s.trim().split(",");
		
		for(int i = 0;i < ss.length;i++) {
			sum+=Integer.parseInt(ss[i]);
		}
		/*System.out.println("所计算的总和为： "+ sum);*/
		return sum+"";
	}
	
	/**
	 * 主函数
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Server server = new Server();
		
		try {	
			while(true) {
				//初始化数据报套接字
				ds = new DatagramSocket(port1);		//此处端口用于接受数据
				
				MyThread myThread = server.new MyThread(ds);
				myThread.start();
				
				//如果上一个线程没有结束，则不继续生成线程，如果不进行等待，则无法继续绑定尚未释放的端口
				while(myThread.isAlive()) {
					//设置休眠时间
					Thread.sleep(1000);
				}
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
	 * UDP服务端练习
	 */
	public void sevver_practice() {
		try {
			//此处指明的端口为服务端的 接收端口
			DatagramSocket datagramSocket = new DatagramSocket(8888);
			//定义接收数据的容器，用于数据接受
			byte[] container = new byte[24];
			DatagramPacket dp = new DatagramPacket(container, container.length);
			
			//指定数据报，进行数据接受
			datagramSocket.receive(dp);
			//解析数据,将接受的字节数组装化未字符串
			String re = new String(dp.getData(),0,dp.getData().length);
			
			String back_content = "this is server";
			byte[] back = back_content.getBytes();
			
			//根据主机名获取Inet对象用户初始化数据报，进行数据发送
			InetAddress addr = InetAddress.getByName("localhost");
			//初始化数据报，此处端口为对方用于接受我端数据的的端口号
			dp = new DatagramPacket(back, back.length,addr,5555);
			//将数据发送至客户端
			datagramSocket.send(dp);
			
			//关闭链接
			datagramSocket.close();
			
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	/**
	 * 定义线程类
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
				Server server = new Server();
				//创建一个数据包，接受容器
				byte[] by = new byte[1024];
				DatagramPacket dp = new DatagramPacket(by, by.length);
							
				//调用socket的receive方法接受数据（以数据包的形式）,如果数据还未到，则会在此等待接收
				ds.receive(dp);
				String s = new String(dp.getData(),0,dp.getData().length);
				/*System.out.println("从客户端接收的数据为： " + s);*/
						
				//创建数据并把数据打包,将所求的总和发送到客户端				
				byte[] bytes = server.sum_num(s).getBytes();
				InetAddress addr = InetAddress.getByName(ip_add);				//根据IP地址获取Inet对象	
				dp = new DatagramPacket(bytes, bytes.length,addr,port2);		//此处端口port2为目标端口，即想要对方收到数据的端口
						
				//调用socket对象的发送方法发送数据包
				ds.send(dp);
				
			} catch (SocketException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
			} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
			}
			
			//关闭资源
			ds.close();
		}
	}
}
