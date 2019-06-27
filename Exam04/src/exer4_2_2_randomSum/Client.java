package exer4_2_2_randomSum;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Random;

public class Client {
	static DatagramSocket datagramSocket;
	static DatagramPacket datagramPacket;
	private static int port1  = 6661;		
	private static int port2  = 6662;
	private static String ip_add="127.0.0.1";
	
	/**
	 * 初始化随机数 ,存放于可变长字符串当中，每个数据以英文逗号隔开，便于服务器进行数据拆分
	 * @return
	 */
	public StringBuffer randomNum_init() {
		StringBuffer sb = new StringBuffer();
		Random ran = new Random();
		int nums = ran.nextInt(20)+10;		//将数字综述设置为 10 到 30个之间的随机数
		
		for(int i = 1; i<nums; i++) {
			sb.append(ran.nextInt(1001)+",");	//将前nums-1 个树存入可变字符中，以英文逗号隔开
		}
		sb.append(ran.nextInt(1001));			//将第nums个数追加，最后一个不需要加英文逗号
		
		System.out.println("所产生的随机数为：" + sb);
		return sb;
	}
	
	/**
	 * 客户端练习
	 */
	public void client_pra() {
		try {
			
			//定义数据报套接字，此处端口用于接受数据的端口
			DatagramSocket datagramSocket = new DatagramSocket(5555);
			//将需要发送的数据封装进数据报
			byte[] send = "Hi,this is client!".getBytes();
			InetAddress addr = InetAddress.getByName("localhost");
			//此处指明的端口为，对方用于接受数据的端口
			DatagramPacket datagramPacket = new DatagramPacket(send, send.length,addr,8888);
			//将数据发送出去
			datagramSocket.send(datagramPacket);
			
			//定义接受容器
			byte[] re = new byte[24];
			//定义数据报
			datagramPacket = new DatagramPacket(re, re.length);
			//接受数据
			datagramSocket.receive(datagramPacket);
			//解析数据
			String rece = new String(datagramPacket.getData(),0,datagramPacket.getData().length);
			
			//关闭连接
			datagramSocket.close();	
			
		} catch (SocketException e) {
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
				Client client  = new Client();
				
				//发送请求,将随机数发送至服务器
				//此处现将StringBuffer转化为String,再讲String类转化为byte[]类型
				byte[] by1 = client.randomNum_init().toString().getBytes();		
				InetAddress addr;
				addr = InetAddress.getByName(ip_add);
				//此处port1未目标端口
				datagramPacket = new DatagramPacket(by1, by1.length,addr,port1);	
				this.ds.send(datagramPacket);
				
				//接受服务器传回的计算结果
				//创建接受容器
				byte[] by2 = new byte[1024];					
				//创建数据报
				datagramPacket = new DatagramPacket(by2, by2.length);		
				//使用数据报接受数据
				this.ds.receive(datagramPacket);									
				//将所接受的字节数据转化为字符串数据
				String check_info = new String(datagramPacket.getData(),0,datagramPacket.getLength());		
				//输出结果
				System.out.println("从服务器获取的总和为："+ check_info);
				
			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			//关闭资源
			this.ds.close();
		}
	}
	
	//主函数
	public static void main(String[] args) {		
		Client client  = new Client();
		// TODO Auto-generated method stub
		try {
			while(true) {
				datagramSocket = new DatagramSocket(port2);			//此处定义的为接收端口
				MyThread t = client.new MyThread(datagramSocket);
				t.start();
				//如果上一个线程没有结束，则不继续生成线程,如果不进行等待，则无法继续绑定尚未释放的端口
				while(t.isAlive()) {
					//设置休眠时间
					Thread.sleep(1000);
				}
			}
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
