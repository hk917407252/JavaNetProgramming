package exam03_3_3_3_VPAGUI;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * ͼ�λ����ܺͣ�ƽ��������������
 * @author Administrator
 *
 */
public class VPAGUIServer {

	private static int port  = 8811;
	private static int count = 0;	//���������
	private static int resNums = 3;
	/**
	 * ������
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		VPAGUIServer Server = new VPAGUIServer();
		try {
			
			//����������׽���
			ServerSocket ss = new ServerSocket(port);
			while(count<resNums) {
				//���÷������׽��ֶ����еķ���accept()��ȡ�ͻ����׽��ֶ���
				Socket s=ss.accept();	//���ܿͻ�������
				MyThread t = Server.new MyThread(s);
				t.start();
			}
			ss.close();
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
		private Socket socket;
		
		public MyThread() {
			
		}
		
		public MyThread(Socket s){
			this.socket = s;
		}
		
		/**
		 * �󷽲�
		 * @param elements
		 * @param ave
		 * @return
		 */
		public double variance(int[] elements,double ave) {
			double sum = 0.0;
			double var;
			//����ƽ����
			for(int i = 0; i<elements.length;i++) {
				sum += Math.pow(elements[i]-ave, 2);
			}
			var = (elements.length==0)? 0:sum/elements.length;
			return var;
		}
		
		@Override
		public void run() {
			int len = 0;
			int[] rec;	
			double ave;	//ƽ����
			double var;	//����
			int sum = 0;
			try {
				//��������ȡ����
				DataInputStream dis = new DataInputStream(this.socket.getInputStream()) ;
				len = dis.readInt();
				System.out.println("��ȡ�����ĸ���Ϊ��"+len);
				if(len!=0) {
					count ++;
				}
				//��ʼ����������
				rec = new int[len];
				//����������Ϣ����,ͬʱͳ���ܺ�
				for(int i = 0;i<len;i++) {
					rec[i] = dis.readInt();
					/*System.out.println("��ǰ���ܵĵ�"+(i+1)+"����Ϊ��"+rec[i]);*/
					sum+=rec[i];
				}
				//����ƽ����
				ave = (len==0) ? 0:sum/len;
				//���㷽��
				var = variance(rec, ave);
				/*Thread.sleep(50);*/
				DataOutputStream out=new DataOutputStream(
						this.socket.getOutputStream());
				/*System.out.println(" �ܺͣ�"+sum+" ave: " + ave + "var: " + var);*/
				out.writeInt(sum);
				out.writeDouble(ave);
				out.writeDouble(var);
				/*dis.close();
				out.close();*/
				this.socket.close();
			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
