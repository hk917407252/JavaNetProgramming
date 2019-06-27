package lab3_01;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Calendar;

public class DaytimeClient {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			Socket s  = new Socket("127.0.0.1",7);
			DataInputStream dis = new DataInputStream(s.getInputStream()) ;			
			System.out.println("获取的当前时间为： "+dis.readInt()+ "-" + dis.readByte()+ "-"+ dis.readByte()+ "-"
			+ dis.readByte()+ ":"+ dis.readByte()+ ":"+ dis.readByte());

		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
