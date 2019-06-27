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
	private static String[][] issues;			//题库
	private static List<String> real_solution = new ArrayList<String>();
	
	private static List<String> answer = new ArrayList<String>();		//记录用户答案
	
	/**
	 * 题目加载，涉及读取后台题库
	 * @return
	 */
	public String[][] issues_init(){
		StringBuffer get_initial_issues = new StringBuffer();
		String path_name = "D:\\雨黍云涵\\校内文件\\Java网络编程\\超星作业\\作业四\\issues.txt";
		FileReader reader = null;
		BufferedReader br = null;
		try {
			String str = null;
			reader = new FileReader(path_name);
			br = new BufferedReader(reader);
			while((str = br.readLine())!= null) {		//将所有的题库预读取
				get_initial_issues.append(str.trim()+",");			//此处在末尾会加入一个空字符串!!!!!,记得后面需要隔离掉
			}
		}catch (Exception e) {
			// TODO: handle exception
		}
		
		String[] issues_includeAns = get_initial_issues.toString().split(",");
		String[][] issues_withAndDevided = new String[issues_includeAns.length][];		//切记，需要将空字符串除去
		
		for(int i = 0;i<issues_includeAns.length ;i++) {							//将答案与题目分离，存入二维数组
			issues_withAndDevided[i] = issues_includeAns[i].split(":");				//因题库中题目与答案是以 冒号 隔开的，所以需要将题目与答案先分片存储
			real_solution.add( issues_withAndDevided[i][1] );						//将答案按顺序保存至列表中
		}
		
		return issues_withAndDevided;
	}
	
	
	/**
	 * 主函数
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
				datagramSocket = new DatagramSocket(port2);			//此处定义的为接收端口
				MyThread t = examServer.new MyThread(datagramSocket);
				t.start();
						
				//如果上一个线程没有结束，则不继续生成线程,如果不进行等待，则无法继续绑定尚未释放的端口
				while(t.isAlive()) {
					//设置休眠时间
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
	 * 定义线程类
	 * @author Administrator
	 *
	 */
	
	class MyThread extends Thread{
		private DatagramSocket ds;
		private int count = 0;
		
		//构造方法
		public MyThread(DatagramSocket ds) {
			this.ds = ds;
		}
		
		@Override
		public void run() {
			boolean flag = true;
			do {
				try {
					ExamServer examServer = new ExamServer();
					
					//接收客户端请求，等待客户端发送请求
					byte[] by2 = new byte[1024];					//创建接受容器
					datagramPacket = new DatagramPacket(by2, by2.length);		//创建数据报
					this.ds.receive(datagramPacket);				//
					
					//发送请求,将随机数发送至服务器
					byte[] by1 = issues[count++][0].getBytes();		//此处现将StringBuffer转化为String,再讲String类转化为byte[]类型
					InetAddress addr;
					addr = InetAddress.getByName(ip_add);
					datagramPacket = new DatagramPacket(by1, by1.length,addr,port1);	//此处port1未目标端口
					this.ds.send(datagramPacket);
					
					//接受答案
					by2 = new byte[1024];					//创建接受容器
					datagramPacket = new DatagramPacket(by2, by2.length);		//创建数据报
					this.ds.receive(datagramPacket);							//使用数据报接受数据
					String[] rece = new String(datagramPacket.getData(),0,datagramPacket.getLength()).split(",");
					answer.add(rece[0]);					//将用户输入答案加入列表
					
					/*//String check_info = new String(datagramPacket.getData(),0,datagramPacket.getLength());		//将所接受的字节数据转化为字符串数据
					//接收用户选择，是继续答题（1）还是结束答题（0）
					byte[] by_chosen = new byte[32];										//创建接受容器
					DatagramPacket datagramPacket1 = new DatagramPacket(by_chosen, by_chosen.length);		//创建数据报
					this.ds.receive(datagramPacket1);										//使用数据报接受数据
					String ans = new String(datagramPacket1.getData(),0,datagramPacket1.getLength());*/
					
					/*System.out.println(rece[1]);*/
					if("1".equals(rece[1])) {		//如果选择继续答题
						continue;
					}else {						//将答题分数返回至服务端
						float sum = 0;
						for(int i = 0; i<answer.size();i++) {
							if(answer.get(i).equals(real_solution.get(i))) {
								sum +=1;
							}
						}
						String grade = ((int)((sum/answer.size())*100))/100.0+"";
						
						//将用户成功率发送过去
						byte[] by_grade = grade.getBytes();
						datagramPacket = new DatagramPacket(by_grade, by_grade.length,addr,port1);		//此处port1未目标端口
						this.ds.send(datagramPacket);		
						flag = false;
					}
					/*//输出结果
					System.out.println("从服务器获取的总和为："+ check_info);*/
					
				} catch (UnknownHostException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}while(flag);
		//关闭资源
		this.ds.close();
		}
	}
}
