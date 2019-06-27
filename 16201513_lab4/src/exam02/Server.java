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
	
	//数组<列表<int>>存储用户发送过来的温度信息，用于后续计算平均温度
	private static List<Integer>[] tem_list =new List[2];
	
	//xy坐标以及lineChart折线图
	private static NumberAxis xAxis ;
	private static NumberAxis yAxis;
	private static LineChart linechart;
	
	
	//定义接受线程
	class MyThread extends Thread{
		private int port;
		private int series_number = 0;
		
		//构造方法
		public MyThread(int port,int series_number) {
			this.port = port;
			this.series_number = series_number;
		}
		
		//定义线程run方法
		@Override
		public void run() {
			while(true) {
				String s;
				int t = 0;
				//创建接收端socket对象
				DatagramSocket ds;
				try {
					ds = new DatagramSocket(this.port);
					//创建一个数据包，接受容器
					byte[] by = new byte[1024];
					DatagramPacket dp = new DatagramPacket(by, by.length);
						
					//调用socket的receive方法接受数据（以数据包的形式）
					ds.receive(dp);
							
					//解析数据，并输出
					InetAddress addr = dp.getAddress();
					String ip = addr.getHostAddress();
					//将字节型数据转化为String类型
					s = new String(dp.getData(),0,dp.getLength());
					/*System.out.println(s+"..............");*/
					t = Integer.parseInt(s);
					/*//输出
					System.out.println(ip+"所发送过来的数据是:" + s);*/
							
					//关闭资源
					ds.close();
				} catch (SocketException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				/*System.out.println(this.getName()+"-获取的温度为：" + t);*/
				//将对应的温度曲线存储在对应列表中
				tem_list[series_number].add(t);
				//将数据加载到图上
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
	 * 初始化折线图
	 */
	public void graph_init() {
		//初始化行(时间)列(温度)属性下标
		xAxis = new NumberAxis(0,25,1);
		xAxis.setLabel("time");
		yAxis = new NumberAxis(-30,45,5);
		yAxis.setLabel("temperature");
				
		//实例化lineChart类来创建折线图
		linechart = new LineChart(xAxis,yAxis);
		
		//初始化温度曲线信息列表<列表>
		for(int i =0;i<2;i++) {
			tem_list[i] = new ArrayList<Integer>();
		}
		
		//初始化XYchart.series的链表
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
	 * 绘制平均温度曲线
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
	 * 主函数
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
		
		//System.out.println("设置窗口标题");
		primaryStage.setTitle("ClientTemperature");
		
		//创建容器
		GridPane pane = new GridPane();
		pane.setPadding(new Insets(15,15,15,15));
		
		
		//设置动画
		EventHandler<ActionEvent> eventHandler = e ->{//花括号里就是要进行的动画
			draw_line();			
		};
		Timeline animation = new Timeline(new KeyFrame(Duration.millis(1000), eventHandler));//控制动画的帧数，即刷新速度
		animation.setCycleCount(Timeline.INDEFINITE);//控制动画启动后的时间，当前设置为无限
		//启动动画
		animation.play();
		
		
		//将折线图加入容器容器
		pane.getChildren().add(linechart);
		
		//将三条折现的数据添加到折线图
		for(int i = 0;i<3;i++) {
			linechart.getData().add(series_list.get(i));
		}
		
		//创建场景
		Scene scene = new Scene(pane);
		primaryStage.setScene(scene);
		primaryStage.show();
		
	}
	
}
