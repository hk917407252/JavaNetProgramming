package lab3_01;

import java.io.*;
import java.net.*;
import java.util.*;
public class DaytimeServer {
	public static void main(String[] args) {
		try {
			//����������׽���
			ServerSocket ss=new ServerSocket(7);
			while(true){
				//���÷������׽��ֶ����еķ���accept()��ȡ�ͻ����׽��ֶ���
				Socket s=ss.accept();		//���ܿͻ�������
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
		System.out.println("���Ƿ�������");
	}	
}
