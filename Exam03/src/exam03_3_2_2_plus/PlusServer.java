package exam03_3_2_2_plus;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

/**
 * 求和的服务端
 * @author Administrator
 *
 */
public class PlusServer {
	
	
	
	/**服务器练习*/
	public void server() {
		try {
			//定义服务器套接字，指明端口
			ServerSocket socket = new ServerSocket(8888);
			while(true) {
				Socket s = socket.accept();
				//定义输入流，接受客户端数据；
				DataInputStream dataInputStream = new DataInputStream(s.getInputStream());
				//读取服务端发送过来的数据；
				int data1 = dataInputStream.readInt();
				
				//定义输出流，发送数据至客户端
				DataOutputStream dataOutputStream = new DataOutputStream(s.getOutputStream());
				dataOutputStream.writeInt(000001);
				
				//关闭链接
				socket.close();
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public static void main(String[] args) {
		PlusServer plusServer = new PlusServer();
		
		try {
			//定义服务器套接字
			ServerSocket ss = new ServerSocket(6666);
			while(true) {
				//调用服务器套接字对象中的方法accept()获取客户端套接字对象
				Socket s=ss.accept();				//接受客户端请求
				MyThread t = plusServer.new MyThread(s);
				t.start();
			}
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 自定义线程类
	 * @author Administrator
	 *
	 */
	class MyThread extends Thread{
		private Socket socket;
		
		public MyThread() {
			
		}
		
		public MyThread(Socket s){
			this.socket = s;
		}
		
		@Override
		public void run() {
			int len;
			int[] rec;
			int sum = 0;
			try {
				//定义输入流接收服务端发送过来数据
				DataInputStream dis = new DataInputStream(this.socket.getInputStream()) ;
				//读取第一个传送过来的数，即为客户端所需要传送的数字的个数
				len = dis.readInt();
				System.out.println("获取的数的个数为："+len);
				
				//初始化接受数组
				rec = new int[len];
				//接受数字信息数据,同时统计总和
				for(int i = 0;i<len;i++) {
					rec[i] = dis.readInt();
					System.out.println("当前接受的第"+i+"个数为："+rec[i]);
					sum+=rec[i];
				}
				
				Thread.sleep(50);
				//定义输出流将计算结果发送至客户端
				DataOutputStream out=new DataOutputStream(
						this.socket.getOutputStream());		  
				out.writeInt(sum);
				/*dis.close();
				out.close();*/
				this.socket.close();
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

}
