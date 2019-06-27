package exer4_3_1_ExamProcedure;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.Scanner;

public class ExamClient{
	static DatagramSocket ds;
	static DatagramPacket dp;
	private static int port1  = 6651;
	private static int port2  = 6652;
	private static String ip_add="127.0.0.1";
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ExamClient examClient = new ExamClient();
		try {	
			/*while(true) {*/
				//��ʼ�����ݱ��׽���
				ds = new DatagramSocket(port1);		//�˴��˿����ڽ�������
				
				MyThread myThread = examClient.new MyThread(ds);
				myThread.start();
				
				//�����һ���߳�û�н������򲻼��������̣߳���������еȴ������޷���������δ�ͷŵĶ˿�
			/*	while(myThread.isAlive()) {
					//��������ʱ��
					Thread.sleep(1000);
				}
			}*/
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} /*catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
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
			boolean flag = true;
			Scanner scan = new Scanner(System.in);
			do {
				try {	
					ExamClient examClient = new ExamClient();
					
					//���Ϳ����󣬽�������
					byte[] bytes = "".getBytes() ;						//���𰸴��
					InetAddress addr = InetAddress.getByName(ip_add);		
					dp = new DatagramPacket(bytes, bytes.length,addr,port2);		//�˴��˿�port2ΪĿ��˿ڣ�����Ҫ�Է��յ����ݵĶ˿�	
					//����socket����ķ��ͷ����������ݰ�
					ds.send(dp);
					
					
					//������Ŀ
					//����һ�����ݰ�����������
					byte[] by = new byte[1024];
					DatagramPacket dp = new DatagramPacket(by, by.length);
					ds.receive(dp);	//����socket��receive�����������ݣ������ݰ�����ʽ��
					String s = new String(dp.getData(),0,dp.getData().length);
					
					//���𰸷�����������
					//�������ݲ������ݴ��,��������ܺͷ��͵��ͻ���
					System.out.println(s);		//��ʾ
					
					System.out.println("������𰸣�");
					int ans = scan.nextInt();				//�����
					System.out.println("�������Ƿ�������⣨1�ǡ�0�񣩣�");
					int choose = scan.nextInt();
					
					bytes = (ans+","+choose).getBytes() ;					//���𰸴��
					addr = InetAddress.getByName(ip_add);		
					dp = new DatagramPacket(bytes, bytes.length,addr,port2);		//�˴��˿�port2ΪĿ��˿ڣ�����Ҫ�Է��յ����ݵĶ˿�	
					//����socket����ķ��ͷ����������ݰ�
					ds.send(dp);
													
					//����������⣬��������д��⣬����������⣬��ӷ����������Ѵ�Ĵ�
					if("1".equals((choose+"").trim())) {
						continue;
					}else {
						byte[] by_grade = new byte[1024];
						dp = new DatagramPacket(by_grade, by_grade.length);		
						//����socket��receive�����������ݣ������ݰ�����ʽ��
						ds.receive(dp);
						s = new String(dp.getData(),0,dp.getData().length);
						System.out.println("�������⣬�����ε���ȷ��Ϊ��" + s);
						flag = false;
					}
							
				} catch (SocketException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
				} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
				}
			}while(flag);
			//�ر���Դ
			ds.close();
		}
	}
}
