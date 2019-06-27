package lab3_03Gui;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Calendar;

public class DayTimeServer {
	public static void main(String[] args) {
		DayTimeServer  dts = new DayTimeServer();
		try {
			ServerSocket ss=new ServerSocket(2007);
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
			Calendar current=Calendar.getInstance();
			try {
				DataOutputStream out=new DataOutputStream(
						this.socket.getOutputStream());
				out.writeInt(current.get(Calendar.YEAR));
				out.writeByte(current.get(Calendar.MONTH));
				out.writeByte(current.get(Calendar.DAY_OF_MONTH));
				out.writeByte(current.get(Calendar.HOUR_OF_DAY));
				out.writeByte(current.get(Calendar.MINUTE));
				out.writeByte(current.get(Calendar.SECOND));
				out.close();
				socket.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			System.out.println("这是服务器");
		}
	}
}