package exer4_2_1_checkCode;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.Random;

public class ChectCodeServer {
	static DatagramSocket ds;
	static DatagramPacket dp;
	private static int port  = 6666;
	private static String ip_add="127.0.0.1";
	
	
	/**
	 * ����һ����ȡ�����֤��ķ�����getCode();
	 * @param n
	 * @return
	 */
	public static String getCode(int n) {
		String string = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";//��������0-9 �� ��Сд��ĸ
		StringBuffer sb = new StringBuffer(); 	//����һ��StringBuffer����sb ���� ��֤��
		for (int i = 0; i < n; i++) {
			Random random = new Random();		//����һ���µ������������
			int index = random.nextInt(string.length());//����[0,string.length)��Χ��intֵ    ���ã������±�
			char ch = string.charAt(index);		//charAt() : ����ָ���������� char ֵ   -->��ֵ��char�ַ�����ch
			sb.append(ch);						
		}
		return sb.toString();					
	}
	
	
	//������
	public static void main(String[] args) {
		ChectCodeServer chectCodeServer = new ChectCodeServer();
		try {	
			while(true) {
				//��ʼ�����ݱ��׽���
				ds = new DatagramSocket();
				MyThread t = chectCodeServer.new MyThread(ds);
				t.start();
				
				//��������ʱ��
				Thread.sleep(1000);
				
				/*//����һ�����ݰ�����������
				byte[] by = new byte[1024];
				DatagramPacket dp = new DatagramPacket(by, by.length);
					
				//����socket��receive�����������ݣ������ݰ�����ʽ��
				ds.receive(dp);*/
				
				/*//�������ݲ������ݴ��				
				byte[] bytes = getCode(5).getBytes();
				InetAddress addr = InetAddress.getByName(ip_add);		
				dp = new DatagramPacket(bytes, bytes.length,addr,port);
				
				//����socket����ķ��ͷ����������ݰ�
				ds.send(dp);
			
				//�ر���Դ
				ds.close();*/
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
	 * �����߳���,�߳��ฺ����֤�뷢�͵��ͻ���
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
				//�������ݲ������ݴ��				
				byte[] bytes = getCode(5).getBytes();
				InetAddress addr = InetAddress.getByName(ip_add);		
				dp = new DatagramPacket(bytes, bytes.length,addr,port);
				
				//����socket����ķ��ͷ����������ݰ�
				ds.send(dp);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
			//�ر���Դ
			ds.close();
		}
	}

}
