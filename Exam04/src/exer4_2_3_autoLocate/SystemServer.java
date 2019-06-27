package exer4_2_3_autoLocate;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

import exer4_2_2_randomSum.Server;


public class SystemServer {
	
	private static DatagramSocket datagramSocket;
	private static DatagramPacket datagramPacket;
	private static int port1 = 8887;
	private static int port2 = 8888;
	private static String addr_address = "127.0.0.1";
	
	/**
	 * 主函数
	 * @param args
	 */
	public static  void main(String[] args) {
		SystemServer systemServer = new SystemServer();
		
		try {
			while(true) {
				datagramSocket  = new DatagramSocket(port2);			//定义系统服务端的接收端口为port2	
				MyThread t = systemServer.new MyThread(datagramSocket);
				t.start();
				
				//确保线程已经结束，避免出现端口冲突
				while(t.isAlive()) {
					Thread.sleep(1000);
				}
			}
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	/**
	 * 判断距离是否符合要求函数，此处定义距离不能超过3000（单位m）
	 * @param s
	 * @return
	 */
	public boolean get_distance(String s) {
		String[] ss = s.trim().split(",");
		double sum = 0;
		for(int i = 0 ;i<ss.length;i++) {
			/*System.out.println(ss[i]);*/
			sum+= Math.pow(Integer.parseInt(ss[i]), 2);
		}
		sum = Math.sqrt(sum);
		return (sum<3000)? true:false;
	}
	
	/**
	 * 线程类
	 * @author Administrator
	 *
	 */
	class MyThread extends Thread{
		private DatagramSocket ds;
		
		//构造方法
		public MyThread(DatagramSocket ds) {
			this.ds = ds;
		}
		
		
		@Override
		public void run() {
			try {	
				SystemServer server = new SystemServer();
				//创建一个数据包，接受容器
				byte[] by = new byte[1024];
				DatagramPacket dp = new DatagramPacket(by, by.length);
							
				//调用socket的receive方法接受数据（以数据包的形式）
				ds.receive(dp);
				String s = new String(dp.getData(),0,dp.getData().length);
				
				if(!server.get_distance(s)) {		//当计算距离不满足要求
					//创建数据并把数据打包,将所求的总和发送到客户端				
					byte[] bytes = "警告！警告！您已超出定位范围中心".getBytes() ;
					InetAddress addr = InetAddress.getByName(addr_address);		
					dp = new DatagramPacket(bytes, bytes.length,addr,port1);		//此处端口port1为目标端口，即想要对方收到数据的端口
							
					//调用socket对象的发送方法发送数据包
					ds.send(dp);
				}else {				//当计算记录满足要求是，发送空字符串
					//创建数据并把数据打包,将所求的总和发送到客户端				
					byte[] bytes = "距离合法".getBytes() ;
					InetAddress addr = InetAddress.getByName(addr_address);		
					dp = new DatagramPacket(bytes, bytes.length,addr,port1);		//此处端口port1为目标端口，即想要对方收到数据的端口
							
					//调用socket对象的发送方法发送数据包
					ds.send(dp);
				}
				/*System.out.println("从客户端接收的数据为： " + s);*/						
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
