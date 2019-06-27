package exer4_2_2_randomSum;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;


public class Server {
	static DatagramSocket ds;
	static DatagramPacket dp;
	private static int port1  = 6661;
	private static int port2  = 6662;
	private static String ip_add="127.0.0.1";
	
	/**
	 * �������ӿͻ��˽��յ��ַ�����������ͺ���
	 * @param s
	 * @return
	 */
	public String sum_num(String s) {
		int sum = 0;
		String[] ss = s.trim().split(",");
		
		for(int i = 0;i < ss.length;i++) {
			sum+=Integer.parseInt(ss[i]);
		}
		/*System.out.println("��������ܺ�Ϊ�� "+ sum);*/
		return sum+"";
	}
	
	/**
	 * ������
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Server server = new Server();
		
		try {	
			while(true) {
				//��ʼ�����ݱ��׽���
				ds = new DatagramSocket(port1);		//�˴��˿����ڽ�������
				
				MyThread myThread = server.new MyThread(ds);
				myThread.start();
				
				//�����һ���߳�û�н������򲻼��������̣߳���������еȴ������޷���������δ�ͷŵĶ˿�
				while(myThread.isAlive()) {
					//��������ʱ��
					Thread.sleep(1000);
				}
			}
		} catch (SocketException e) {
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
	
	/**
	 * UDP�������ϰ
	 */
	public void sevver_practice() {
		try {
			//�˴�ָ���Ķ˿�Ϊ����˵� ���ն˿�
			DatagramSocket datagramSocket = new DatagramSocket(8888);
			//����������ݵ��������������ݽ���
			byte[] container = new byte[24];
			DatagramPacket dp = new DatagramPacket(container, container.length);
			
			//ָ�����ݱ����������ݽ���
			datagramSocket.receive(dp);
			//��������,�����ܵ��ֽ�����װ��δ�ַ���
			String re = new String(dp.getData(),0,dp.getData().length);
			
			String back_content = "this is server";
			byte[] back = back_content.getBytes();
			
			//������������ȡInet�����û���ʼ�����ݱ����������ݷ���
			InetAddress addr = InetAddress.getByName("localhost");
			//��ʼ�����ݱ����˴��˿�Ϊ�Է����ڽ����Ҷ����ݵĵĶ˿ں�
			dp = new DatagramPacket(back, back.length,addr,5555);
			//�����ݷ������ͻ���
			datagramSocket.send(dp);
			
			//�ر�����
			datagramSocket.close();
			
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	/**
	 * �����߳���
	 * @author Administrator
	 *
	 */
	class MyThread extends Thread{
		private DatagramSocket ds;
		
		
		public MyThread(DatagramSocket ds) {
			this.ds = ds;
		}
		
		
		@Override
		public void run() {
			try {	
				Server server = new Server();
				//����һ�����ݰ�����������
				byte[] by = new byte[1024];
				DatagramPacket dp = new DatagramPacket(by, by.length);
							
				//����socket��receive�����������ݣ������ݰ�����ʽ��,������ݻ�δ��������ڴ˵ȴ�����
				ds.receive(dp);
				String s = new String(dp.getData(),0,dp.getData().length);
				/*System.out.println("�ӿͻ��˽��յ�����Ϊ�� " + s);*/
						
				//�������ݲ������ݴ��,��������ܺͷ��͵��ͻ���				
				byte[] bytes = server.sum_num(s).getBytes();
				InetAddress addr = InetAddress.getByName(ip_add);				//����IP��ַ��ȡInet����	
				dp = new DatagramPacket(bytes, bytes.length,addr,port2);		//�˴��˿�port2ΪĿ��˿ڣ�����Ҫ�Է��յ����ݵĶ˿�
						
				//����socket����ķ��ͷ����������ݰ�
				ds.send(dp);
				
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
