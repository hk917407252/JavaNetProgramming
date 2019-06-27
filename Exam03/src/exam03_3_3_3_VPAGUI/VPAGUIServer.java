package exam03_3_3_3_VPAGUI;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * 图形化求总和，平均数、方差服务端
 * @author Administrator
 *
 */
public class VPAGUIServer {

	private static int port  = 8811;
	private static int count = 0;	//定义计数器
	private static int resNums = 3;
	/**
	 * 主函数
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		VPAGUIServer Server = new VPAGUIServer();
		try {
			
			//定义服务器套接字
			ServerSocket ss = new ServerSocket(port);
			while(count<resNums) {
				//调用服务器套接字对象中的方法accept()获取客户端套接字对象
				Socket s=ss.accept();	//接受客户端请求
				MyThread t = Server.new MyThread(s);
				t.start();
			}
			ss.close();
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
		private Socket socket;
		
		public MyThread() {
			
		}
		
		public MyThread(Socket s){
			this.socket = s;
		}
		
		/**
		 * 求方差
		 * @param elements
		 * @param ave
		 * @return
		 */
		public double variance(int[] elements,double ave) {
			double sum = 0.0;
			double var;
			//求差的平方和
			for(int i = 0; i<elements.length;i++) {
				sum += Math.pow(elements[i]-ave, 2);
			}
			var = (elements.length==0)? 0:sum/elements.length;
			return var;
		}
		
		@Override
		public void run() {
			int len = 0;
			int[] rec;	
			double ave;	//平均数
			double var;	//方差
			int sum = 0;
			try {
				//定义流读取数据
				DataInputStream dis = new DataInputStream(this.socket.getInputStream()) ;
				len = dis.readInt();
				System.out.println("获取的数的个数为："+len);
				if(len!=0) {
					count ++;
				}
				//初始化接受数组
				rec = new int[len];
				//接受数字信息数据,同时统计总和
				for(int i = 0;i<len;i++) {
					rec[i] = dis.readInt();
					/*System.out.println("当前接受的第"+(i+1)+"个数为："+rec[i]);*/
					sum+=rec[i];
				}
				//计算平均数
				ave = (len==0) ? 0:sum/len;
				//计算方差
				var = variance(rec, ave);
				/*Thread.sleep(50);*/
				DataOutputStream out=new DataOutputStream(
						this.socket.getOutputStream());
				/*System.out.println(" 总和："+sum+" ave: " + ave + "var: " + var);*/
				out.writeInt(sum);
				out.writeDouble(ave);
				out.writeDouble(var);
				/*dis.close();
				out.close();*/
				this.socket.close();
			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
