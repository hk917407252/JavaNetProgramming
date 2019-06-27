package exer4_3_1_ExamProcedure;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.Scanner;

public class ExamClient{
	static DatagramSocket ds;
	static DatagramPacket dp;
	private static int port1  = 6651;
	private static int port2  = 6652;
	private static String ip_add="127.0.0.1";
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ExamClient examClient = new ExamClient();
		try {	
			/*while(true) {*/
				//初始化数据报套接字
				ds = new DatagramSocket(port1);		//此处端口用于接受数据
				
				MyThread myThread = examClient.new MyThread(ds);
				myThread.start();
				
				//如果上一个线程没有结束，则不继续生成线程，如果不进行等待，则无法继续绑定尚未释放的端口
			/*	while(myThread.isAlive()) {
					//设置休眠时间
					Thread.sleep(1000);
				}
			}*/
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} /*catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
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
			boolean flag = true;
			Scanner scan = new Scanner(System.in);
			do {
				try {	
					ExamClient examClient = new ExamClient();
					
					//发送空请求，建立连接
					byte[] bytes = "".getBytes() ;						//将答案打包
					InetAddress addr = InetAddress.getByName(ip_add);		
					dp = new DatagramPacket(bytes, bytes.length,addr,port2);		//此处端口port2为目标端口，即想要对方收到数据的端口	
					//调用socket对象的发送方法发送数据包
					ds.send(dp);
					
					
					//接收题目
					//创建一个数据包，接受容器
					byte[] by = new byte[1024];
					DatagramPacket dp = new DatagramPacket(by, by.length);
					ds.receive(dp);	//调用socket的receive方法接受数据（以数据包的形式）
					String s = new String(dp.getData(),0,dp.getData().length);
					
					//将答案发送至服务器
					//创建数据并把数据打包,将所求的总和发送到客户端
					System.out.println(s);		//提示
					
					System.out.println("请输入答案：");
					int ans = scan.nextInt();				//输入答案
					System.out.println("请输入是否继续答题（1是、0否）：");
					int choose = scan.nextInt();
					
					bytes = (ans+","+choose).getBytes() ;					//将答案打包
					addr = InetAddress.getByName(ip_add);		
					dp = new DatagramPacket(bytes, bytes.length,addr,port2);		//此处端口port2为目标端口，即想要对方收到数据的端口	
					//调用socket对象的发送方法发送数据包
					ds.send(dp);
													
					//如果继续答题，则继续进行答题，如果结束答题，则从服务器接收已答的答案
					if("1".equals((choose+"").trim())) {
						continue;
					}else {
						byte[] by_grade = new byte[1024];
						dp = new DatagramPacket(by_grade, by_grade.length);		
						//调用socket的receive方法接受数据（以数据包的形式）
						ds.receive(dp);
						s = new String(dp.getData(),0,dp.getData().length);
						System.out.println("结束答题，您本次的正确率为：" + s);
						flag = false;
					}
							
				} catch (SocketException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
				} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
				}
			}while(flag);
			//关闭资源
			ds.close();
		}
	}
}
