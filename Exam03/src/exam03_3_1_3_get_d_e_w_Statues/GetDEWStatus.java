package exam03_3_1_3_get_d_e_w_Statues;

import java.io.IOException;
import java.net.ConnectException;
import java.net.Socket;
import java.net.UnknownHostException;

public class GetDEWStatus {
	private static int port1 = 13;
	private static int port2 = 7;
	private static int port3 = 80;
	
	public void scanPost(int port) {
		Socket socket;
		
		try {
			socket = new Socket("127.0.0.1",port);					
			socket.close();
		} catch (UnknownHostException e) {
			System.out.println("端口-"+port+":连接失败");
		} catch (IOException e) {
			System.out.println("端口-"+port+":连接失败");
		}
	}
	
	public static void main(String[] args) {
		GetDEWStatus dewStatus = new GetDEWStatus();
		dewStatus.scanPost(port1);
		dewStatus.scanPost(port2);
		dewStatus.scanPost(port3);
		
	}

}
