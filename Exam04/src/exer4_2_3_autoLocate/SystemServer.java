package exer4_2_3_autoLocate;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

import exer4_2_2_randomSum.Server;


public class SystemServer {
	
	private static DatagramSocket datagramSocket;
	private static DatagramPacket datagramPacket;
	private static int port1 = 8887;
	private static int port2 = 8888;
	private static String addr_address = "127.0.0.1";
	
	/**
	 * ������
	 * @param args
	 */
	public static  void main(String[] args) {
		SystemServer systemServer = new SystemServer();
		
		try {
			while(true) {
				datagramSocket  = new DatagramSocket(port2);			//����ϵͳ����˵Ľ��ն˿�Ϊport2	
				MyThread t = systemServer.new MyThread(datagramSocket);
				t.start();
				
				//ȷ���߳��Ѿ�������������ֶ˿ڳ�ͻ
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
	 * �жϾ����Ƿ����Ҫ�������˴�������벻�ܳ���3000����λm��
	 * @param s
	 * @return
	 */
	public boolean get_distance(String s) {
		String[] ss = s.trim().split(",");
		double sum = 0;
		for(int i = 0 ;i<ss.length;i++) {
			/*System.out.println(ss[i]);*/
			sum+= Math.pow(Integer.parseInt(ss[i]), 2);
		}
		sum = Math.sqrt(sum);
		return (sum<3000)? true:false;
	}
	
	/**
	 * �߳���
	 * @author Administrator
	 *
	 */
	class MyThread extends Thread{
		private DatagramSocket ds;
		
		//���췽��
		public MyThread(DatagramSocket ds) {
			this.ds = ds;
		}
		
		
		@Override
		public void run() {
			try {	
				SystemServer server = new SystemServer();
				//����һ�����ݰ�����������
				byte[] by = new byte[1024];
				DatagramPacket dp = new DatagramPacket(by, by.length);
							
				//����socket��receive�����������ݣ������ݰ�����ʽ��
				ds.receive(dp);
				String s = new String(dp.getData(),0,dp.getData().length);
				
				if(!server.get_distance(s)) {		//��������벻����Ҫ��
					//�������ݲ������ݴ��,��������ܺͷ��͵��ͻ���				
					byte[] bytes = "���棡���棡���ѳ�����λ��Χ����".getBytes() ;
					InetAddress addr = InetAddress.getByName(addr_address);		
					dp = new DatagramPacket(bytes, bytes.length,addr,port1);		//�˴��˿�port1ΪĿ��˿ڣ�����Ҫ�Է��յ����ݵĶ˿�
							
					//����socket����ķ��ͷ����������ݰ�
					ds.send(dp);
				}else {				//�������¼����Ҫ���ǣ����Ϳ��ַ���
					//�������ݲ������ݴ��,��������ܺͷ��͵��ͻ���				
					byte[] bytes = "����Ϸ�".getBytes() ;
					InetAddress addr = InetAddress.getByName(addr_address);		
					dp = new DatagramPacket(bytes, bytes.length,addr,port1);		//�˴��˿�port1ΪĿ��˿ڣ�����Ҫ�Է��յ����ݵĶ˿�
							
					//����socket����ķ��ͷ����������ݰ�
					ds.send(dp);
				}
				/*System.out.println("�ӿͻ��˽��յ�����Ϊ�� " + s);*/						
			} catch (SocketException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
			} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
			}
			
			//�ر���Դ
			ds.close();
			
		}
	}
}
