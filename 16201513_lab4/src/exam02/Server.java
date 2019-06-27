package exam02;

import java.io.IOException;
import java.net.*;
import java.util.ArrayList;
import java.util.List;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Server extends Application{

	private static int port1 = 2007,port2 = 2008;
	private static int x_index = 0;
	private static List<XYChart.Series> series_list;
	
	//����<�б�<int>>�洢�û����͹������¶���Ϣ�����ں�������ƽ���¶�
	private static List<Integer>[] tem_list =new List[2];
	
	//xy�����Լ�lineChart����ͼ
	private static NumberAxis xAxis ;
	private static NumberAxis yAxis;
	private static LineChart linechart;
	
	
	//��������߳�
	class MyThread extends Thread{
		private int port;
		private int series_number = 0;
		
		//���췽��
		public MyThread(int port,int series_number) {
			this.port = port;
			this.series_number = series_number;
		}
		
		//�����߳�run����
		@Override
		public void run() {
			while(true) {
				String s;
				int t = 0;
				//�������ն�socket����
				DatagramSocket ds;
				try {
					ds = new DatagramSocket(this.port);
					//����һ�����ݰ�����������
					byte[] by = new byte[1024];
					DatagramPacket dp = new DatagramPacket(by, by.length);
						
					//����socket��receive�����������ݣ������ݰ�����ʽ��
					ds.receive(dp);
							
					//�������ݣ������
					InetAddress addr = dp.getAddress();
					String ip = addr.getHostAddress();
					//���ֽ�������ת��ΪString����
					s = new String(dp.getData(),0,dp.getLength());
					/*System.out.println(s+"..............");*/
					t = Integer.parseInt(s);
					/*//���
					System.out.println(ip+"�����͹�����������:" + s);*/
							
					//�ر���Դ
					ds.close();
				} catch (SocketException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				/*System.out.println(this.getName()+"-��ȡ���¶�Ϊ��" + t);*/
				//����Ӧ���¶����ߴ洢�ڶ�Ӧ�б���
				tem_list[series_number].add(t);
				//�����ݼ��ص�ͼ��
				series_list.get(series_number).getData().add(new XYChart.Data(x_index, t));
				/*if (x_index > 25) {
					series_list.get(series_number).getData().remove(x_index - 25);
					xAxis.setLowerBound(xAxis.getLowerBound() + 1);
		            xAxis.setUpperBound(xAxis.getUpperBound() + 1);
				}*/
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		}
	}
	
	
	/**
	 * ��ʼ������ͼ
	 */
	public void graph_init() {
		//��ʼ����(ʱ��)��(�¶�)�����±�
		xAxis = new NumberAxis(0,25,1);
		xAxis.setLabel("time");
		yAxis = new NumberAxis(-30,45,5);
		yAxis.setLabel("temperature");
				
		//ʵ����lineChart������������ͼ
		linechart = new LineChart(xAxis,yAxis);
		
		//��ʼ���¶�������Ϣ�б�<�б�>
		for(int i =0;i<2;i++) {
			tem_list[i] = new ArrayList<Integer>();
		}
		
		//��ʼ��XYchart.series������
		 series_list = new ArrayList<XYChart.Series>();
		 
		 XYChart.Series[] slist = new XYChart.Series[3];
		 
		for(int i = 0;i<3;i++) {
			slist[i] = new XYChart.Series();
			series_list.add(slist[i]);
			if(i==2) {
				slist[i].setName("ave");
			}else{
				slist[i].setName("user-"+i);
			}
		}
		
		
	}
	
	/**
	 * ����ƽ���¶�����
	 */
	public static void draw_line() {
			if(x_index < tem_list[0].size()&& x_index < tem_list[1].size()) {
				System.out.println("*****T****__"+ x_index  +"*******U******__"+tem_list[0].size() +"*******U1******__"+tem_list[1].size());
				series_list.get(2).getData().add(new XYChart.Data(x_index, ((tem_list[0].get(x_index)) + tem_list[1].get(x_index))/2.0 ));
				if (x_index > 25) {
					series_list.get(2).getData().remove(x_index - 25);
					xAxis.setLowerBound(xAxis.getLowerBound() + 1);
		            xAxis.setUpperBound(xAxis.getUpperBound() + 1);
				}
				x_index++;
			}
	}
	
	
	
	/**
	 * ������
	 * @param args
	 */
	public static void main(String[] args) {
		Server server = new Server();
		server.graph_init();
		launch();
		
	}
	
	
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		MyThread thread1 = new MyThread(port1,0);
		MyThread thread2 = new MyThread(port2,1);
		thread1.start();
		thread2.start();
		
		//System.out.println("���ô��ڱ���");
		primaryStage.setTitle("ClientTemperature");
		
		//��������
		GridPane pane = new GridPane();
		pane.setPadding(new Insets(15,15,15,15));
		
		
		//���ö���
		EventHandler<ActionEvent> eventHandler = e ->{//�����������Ҫ���еĶ���
			draw_line();			
		};
		Timeline animation = new Timeline(new KeyFrame(Duration.millis(1000), eventHandler));//���ƶ�����֡������ˢ���ٶ�
		animation.setCycleCount(Timeline.INDEFINITE);//���ƶ����������ʱ�䣬��ǰ����Ϊ����
		//��������
		animation.play();
		
		
		//������ͼ������������
		pane.getChildren().add(linechart);
		
		//���������ֵ�������ӵ�����ͼ
		for(int i = 0;i<3;i++) {
			linechart.getData().add(series_list.get(i));
		}
		
		//��������
		Scene scene = new Scene(pane);
		primaryStage.setScene(scene);
		primaryStage.show();
		
	}
	
}
