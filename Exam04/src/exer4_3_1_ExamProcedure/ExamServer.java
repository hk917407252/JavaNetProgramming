package exer4_3_1_ExamProcedure;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

public class ExamServer {
	static DatagramSocket datagramSocket;
	static DatagramPacket datagramPacket;
	private static int port1  = 6651;		
	private static int port2  = 6652;
	private static String ip_add="127.0.0.1";
	private static String[][] issues;			//���
	private static List<String> real_solution = new ArrayList<String>();
	
	private static List<String> answer = new ArrayList<String>();		//��¼�û���
	
	/**
	 * ��Ŀ���أ��漰��ȡ��̨���
	 * @return
	 */
	public String[][] issues_init(){
		StringBuffer get_initial_issues = new StringBuffer();
		String path_name = "D:\\�����ƺ�\\У���ļ�\\Java������\\������ҵ\\��ҵ��\\issues.txt";
		FileReader reader = null;
		BufferedReader br = null;
		try {
			String str = null;
			reader = new FileReader(path_name);
			br = new BufferedReader(reader);
			while((str = br.readLine())!= null) {		//�����е����Ԥ��ȡ
				get_initial_issues.append(str.trim()+",");			//�˴���ĩβ�����һ�����ַ���!!!!!,�ǵú�����Ҫ�����
			}
		}catch (Exception e) {
			// TODO: handle exception
		}
		
		String[] issues_includeAns = get_initial_issues.toString().split(",");
		String[][] issues_withAndDevided = new String[issues_includeAns.length][];		//�мǣ���Ҫ�����ַ�����ȥ
		
		for(int i = 0;i<issues_includeAns.length ;i++) {							//��������Ŀ���룬�����ά����
			issues_withAndDevided[i] = issues_includeAns[i].split(":");				//���������Ŀ������� ð�� �����ģ�������Ҫ����Ŀ����ȷ�Ƭ�洢
			real_solution.add( issues_withAndDevided[i][1] );						//���𰸰�˳�򱣴����б���
		}
		
		return issues_withAndDevided;
	}
	
	
	/**
	 * ������
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		ExamServer examServer = new ExamServer();
		issues = examServer.issues_init();
		
		/*for(String[] each : issues) {
				System.out.println(each[0]);System.out.println(each[1]);
		}*/
		
		try {
			
			while(true) {
				datagramSocket = new DatagramSocket(port2);			//�˴������Ϊ���ն˿�
				MyThread t = examServer.new MyThread(datagramSocket);
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
	
	/**
	 * �����߳���
	 * @author Administrator
	 *
	 */
	
	class MyThread extends Thread{
		private DatagramSocket ds;
		private int count = 0;
		
		//���췽��
		public MyThread(DatagramSocket ds) {
			this.ds = ds;
		}
		
		@Override
		public void run() {
			boolean flag = true;
			do {
				try {
					ExamServer examServer = new ExamServer();
					
					//���տͻ������󣬵ȴ��ͻ��˷�������
					byte[] by2 = new byte[1024];					//������������
					datagramPacket = new DatagramPacket(by2, by2.length);		//�������ݱ�
					this.ds.receive(datagramPacket);				//
					
					//��������,�������������������
					byte[] by1 = issues[count++][0].getBytes();		//�˴��ֽ�StringBufferת��ΪString,�ٽ�String��ת��Ϊbyte[]����
					InetAddress addr;
					addr = InetAddress.getByName(ip_add);
					datagramPacket = new DatagramPacket(by1, by1.length,addr,port1);	//�˴�port1δĿ��˿�
					this.ds.send(datagramPacket);
					
					//���ܴ�
					by2 = new byte[1024];					//������������
					datagramPacket = new DatagramPacket(by2, by2.length);		//�������ݱ�
					this.ds.receive(datagramPacket);							//ʹ�����ݱ���������
					String[] rece = new String(datagramPacket.getData(),0,datagramPacket.getLength()).split(",");
					answer.add(rece[0]);					//���û�����𰸼����б�
					
					/*//String check_info = new String(datagramPacket.getData(),0,datagramPacket.getLength());		//�������ܵ��ֽ�����ת��Ϊ�ַ�������
					//�����û�ѡ���Ǽ������⣨1�����ǽ������⣨0��
					byte[] by_chosen = new byte[32];										//������������
					DatagramPacket datagramPacket1 = new DatagramPacket(by_chosen, by_chosen.length);		//�������ݱ�
					this.ds.receive(datagramPacket1);										//ʹ�����ݱ���������
					String ans = new String(datagramPacket1.getData(),0,datagramPacket1.getLength());*/
					
					/*System.out.println(rece[1]);*/
					if("1".equals(rece[1])) {		//���ѡ���������
						continue;
					}else {						//��������������������
						float sum = 0;
						for(int i = 0; i<answer.size();i++) {
							if(answer.get(i).equals(real_solution.get(i))) {
								sum +=1;
							}
						}
						String grade = ((int)((sum/answer.size())*100))/100.0+"";
						
						//���û��ɹ��ʷ��͹�ȥ
						byte[] by_grade = grade.getBytes();
						datagramPacket = new DatagramPacket(by_grade, by_grade.length,addr,port1);		//�˴�port1δĿ��˿�
						this.ds.send(datagramPacket);		
						flag = false;
					}
					/*//������
					System.out.println("�ӷ�������ȡ���ܺ�Ϊ��"+ check_info);*/
					
				} catch (UnknownHostException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}while(flag);
		//�ر���Դ
		this.ds.close();
		}
	}
}
