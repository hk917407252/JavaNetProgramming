package exam03_3_2_1_telnet;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;


public class Server {
	public static void main(String[] args) {
		Server  dts = new Server();
		try {
			ServerSocket ss=new ServerSocket(23);
			while(true){
				Socket s=ss.accept();	//接受客户端请求		  
				ServerThread st = dts.new ServerThread(s);
				st.start();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}			
	}
	
	/**
	 * 定义线程类
	 * @author Administrator
	 *
	 */
	class ServerThread extends Thread{
		private Socket socket;
		
		//定义构造函数
		public ServerThread(Socket s) {
			this.socket = s;

		}
		
		@Override
		public void run() {
			Random ran = new Random();
				try {
					DataOutputStream out=new DataOutputStream(
							this.socket.getOutputStream());
					out.writeInt(ran.nextInt(100)+1);
					out.close();
					socket.close();
					Thread.sleep(1000);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			/*System.out.println("这是服务器端");*/
		}
	}
}
