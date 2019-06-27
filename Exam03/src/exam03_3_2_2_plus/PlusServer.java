package exam03_3_2_2_plus;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

/**
 * ��͵ķ����
 * @author Administrator
 *
 */
public class PlusServer {
	
	
	
	/**��������ϰ*/
	public void server() {
		try {
			//����������׽��֣�ָ���˿�
			ServerSocket socket = new ServerSocket(8888);
			while(true) {
				Socket s = socket.accept();
				//���������������ܿͻ������ݣ�
				DataInputStream dataInputStream = new DataInputStream(s.getInputStream());
				//��ȡ����˷��͹��������ݣ�
				int data1 = dataInputStream.readInt();
				
				//����������������������ͻ���
				DataOutputStream dataOutputStream = new DataOutputStream(s.getOutputStream());
				dataOutputStream.writeInt(000001);
				
				//�ر�����
				socket.close();
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public static void main(String[] args) {
		PlusServer plusServer = new PlusServer();
		
		try {
			//����������׽���
			ServerSocket ss = new ServerSocket(6666);
			while(true) {
				//���÷������׽��ֶ����еķ���accept()��ȡ�ͻ����׽��ֶ���
				Socket s=ss.accept();				//���ܿͻ�������
				MyThread t = plusServer.new MyThread(s);
				t.start();
			}
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * �Զ����߳���
	 * @author Administrator
	 *
	 */
	class MyThread extends Thread{
		private Socket socket;
		
		public MyThread() {
			
		}
		
		public MyThread(Socket s){
			this.socket = s;
		}
		
		@Override
		public void run() {
			int len;
			int[] rec;
			int sum = 0;
			try {
				//�������������շ���˷��͹�������
				DataInputStream dis = new DataInputStream(this.socket.getInputStream()) ;
				//��ȡ��һ�����͹�����������Ϊ�ͻ�������Ҫ���͵����ֵĸ���
				len = dis.readInt();
				System.out.println("��ȡ�����ĸ���Ϊ��"+len);
				
				//��ʼ����������
				rec = new int[len];
				//����������Ϣ����,ͬʱͳ���ܺ�
				for(int i = 0;i<len;i++) {
					rec[i] = dis.readInt();
					System.out.println("��ǰ���ܵĵ�"+i+"����Ϊ��"+rec[i]);
					sum+=rec[i];
				}
				
				Thread.sleep(50);
				//������������������������ͻ���
				DataOutputStream out=new DataOutputStream(
						this.socket.getOutputStream());		  
				out.writeInt(sum);
				/*dis.close();
				out.close();*/
				this.socket.close();
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
