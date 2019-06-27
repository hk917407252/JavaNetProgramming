package exam03_3_2_1_telnet;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		while(true) {
			try {
				Socket s  = new Socket("127.0.0.1",23);
				DataInputStream dis = new DataInputStream(s.getInputStream()) ;
				System.out.println("获取的数为："+dis.readInt());
				Thread.sleep(1000);
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
