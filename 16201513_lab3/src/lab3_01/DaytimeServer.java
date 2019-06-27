package lab3_01;

import java.io.*;
import java.net.*;
import java.util.*;
public class DaytimeServer {
	public static void main(String[] args) {
		try {
			//定义服务器套接字
			ServerSocket ss=new ServerSocket(7);
			while(true){
				//调用服务器套接字对象中的方法accept()获取客户端套接字对象
				Socket s=ss.accept();		//接受客户端请求
				DataOutputStream out=new DataOutputStream(
						s.getOutputStream());		  
				writeTime(out);
				out.close();
				s.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}			
	}
	
	public static void writeTime(DataOutputStream out) throws IOException{
		Calendar current=Calendar.getInstance();
		out.writeInt(current.get(Calendar.YEAR));
		out.writeByte(current.get(Calendar.MONTH));
		out.writeByte(current.get(Calendar.DAY_OF_MONTH));
		out.writeByte(current.get(Calendar.HOUR_OF_DAY));
		out.writeByte(current.get(Calendar.MINUTE));
		out.writeByte(current.get(Calendar.SECOND));
		System.out.println("这是服务器端");
	}	
}
