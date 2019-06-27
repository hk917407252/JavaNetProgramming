package exer4_2_2_randomSum;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Random;

public class Client {
	static DatagramSocket datagramSocket;
	static DatagramPacket datagramPacket;
	private static int port1  = 6661;		
	private static int port2  = 6662;
	private static String ip_add="127.0.0.1";
	
	/**
	 * ��ʼ������� ,����ڿɱ䳤�ַ������У�ÿ��������Ӣ�Ķ��Ÿ��������ڷ������������ݲ��
	 * @return
	 */
	public StringBuffer randomNum_init() {
		StringBuffer sb = new StringBuffer();
		Random ran = new Random();
		int nums = ran.nextInt(20)+10;		//��������������Ϊ 10 �� 30��֮��������
		
		for(int i = 1; i<nums; i++) {
			sb.append(ran.nextInt(1001)+",");	//��ǰnums-1 ��������ɱ��ַ��У���Ӣ�Ķ��Ÿ���
		}
		sb.append(ran.nextInt(1001));			//����nums����׷�ӣ����һ������Ҫ��Ӣ�Ķ���
		
		System.out.println("�������������Ϊ��" + sb);
		return sb;
	}
	
	/**
	 * �ͻ�����ϰ
	 */
	public void client_pra() {
		try {
			
			//�������ݱ��׽��֣��˴��˿����ڽ������ݵĶ˿�
			DatagramSocket datagramSocket = new DatagramSocket(5555);
			//����Ҫ���͵����ݷ�װ�����ݱ�
			byte[] send = "Hi,this is client!".getBytes();
			InetAddress addr = InetAddress.getByName("localhost");
			//�˴�ָ���Ķ˿�Ϊ���Է����ڽ������ݵĶ˿�
			DatagramPacket datagramPacket = new DatagramPacket(send, send.length,addr,8888);
			//�����ݷ��ͳ�ȥ
			datagramSocket.send(datagramPacket);
			
			//�����������
			byte[] re = new byte[24];
			//�������ݱ�
			datagramPacket = new DatagramPacket(re, re.length);
			//��������
			datagramSocket.receive(datagramPacket);
			//��������
			String rece = new String(datagramPacket.getData(),0,datagramPacket.getData().length);
			
			//�ر�����
			datagramSocket.close();	
			
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnknownHostException e) {
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
				Client client  = new Client();
				
				//��������,�������������������
				//�˴��ֽ�StringBufferת��ΪString,�ٽ�String��ת��Ϊbyte[]����
				byte[] by1 = client.randomNum_init().toString().getBytes();		
				InetAddress addr;
				addr = InetAddress.getByName(ip_add);
				//�˴�port1δĿ��˿�
				datagramPacket = new DatagramPacket(by1, by1.length,addr,port1);	
				this.ds.send(datagramPacket);
				
				//���ܷ��������صļ�����
				//������������
				byte[] by2 = new byte[1024];					
				//�������ݱ�
				datagramPacket = new DatagramPacket(by2, by2.length);		
				//ʹ�����ݱ���������
				this.ds.receive(datagramPacket);									
				//�������ܵ��ֽ�����ת��Ϊ�ַ�������
				String check_info = new String(datagramPacket.getData(),0,datagramPacket.getLength());		
				//������
				System.out.println("�ӷ�������ȡ���ܺ�Ϊ��"+ check_info);
				
			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			//�ر���Դ
			this.ds.close();
		}
	}
	
	//������
	public static void main(String[] args) {		
		Client client  = new Client();
		// TODO Auto-generated method stub
		try {
			while(true) {
				datagramSocket = new DatagramSocket(port2);			//�˴������Ϊ���ն˿�
				MyThread t = client.new MyThread(datagramSocket);
				t.start();
				//�����һ���߳�û�н������򲻼��������߳�,��������еȴ������޷���������δ�ͷŵĶ˿�
				while(t.isAlive()) {
					//��������ʱ��
					Thread.sleep(1000);
				}
			}
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
