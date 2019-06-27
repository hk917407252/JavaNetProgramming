package exer4_2_3_autoLocate;

import java.awt.color.ICC_ColorSpace;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Random;

import javafx.scene.chart.PieChart.Data;

public class AutoClient {
	private static DatagramSocket datagramSocket;
	private static DatagramPacket datagramPacket;
	private static int port1 = 8887;
	private static int port2 = 8888;
	private static String addr_address = "127.0.0.1";
	
	/*static int x = 2140;
	static int y = 2140;*/
	
	public static  void main(String[] args) {
		AutoClient autoClient = new AutoClient();
		
		try {
			while(true) {
				datagramSocket  = new DatagramSocket(port1);		//定义汽车客户端的接收端口为port1
				MyThread t = autoClient.new MyThread(datagramSocket);
				t.start();
				
				//确保线程已经结束，避免出现端口冲突
				while(t.isAlive()) {
					Thread.sleep(1000);
				}
			}
			
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 初始化坐标，坐标均为随机生成
	 * @return
	 */
	public String location_init(){
		Random ran = new Random();
		int x = ran.nextInt(3500);
		int y = ran.nextInt(3500);
		/*x -= 2;
		y -= 2;*/
		
		System.out.println("当前的坐标信息为："+(x+","+y).trim());
		return (x+","+y).trim();
	}
	
	class MyThread extends Thread{
		private DatagramSocket ds;
		
		public MyThread(DatagramSocket datagramSocket) {
			this.ds = datagramSocket;
		}
		
		@Override
		public void run() {
			try {
				//将位置信息发送时系统服务器
				byte[] by_location = location_init().getBytes();
				InetAddress addr;
				addr = InetAddress.getByName(addr_address);
				datagramPacket = new DatagramPacket(by_location, by_location.length,addr,port2);
				ds.send(datagramPacket);
				
				//从系统段接收警告
				byte[] by_container = new byte[1024];
				datagramPacket = new DatagramPacket(by_container, by_container.length);
				ds.receive(datagramPacket);
				String s = new String(datagramPacket.getData(),0,datagramPacket.getData().length);
				System.out.println(s+"\n");
				
			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			this.ds.close();
		}
	}
}
