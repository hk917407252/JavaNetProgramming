package lab3_02;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Calendar;

public class DayTimeClient {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			Socket s  = new Socket("127.0.0.1",2007);
			DataInputStream dis = new DataInputStream(s.getInputStream()) ;			
			System.out.println("获取的当前时间为： "+dis.readInt()+ "-" + dis.readByte()+ "-"+ dis.readByte()+ "-"
			+ dis.readByte()+ ":"+ dis.readByte()+ ":"+ dis.readByte());

			s.close();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
