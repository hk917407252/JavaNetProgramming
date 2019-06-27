package exam01;

import java.io.IOException;
import java.net.*;

import javax.sound.midi.Soundbank;

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
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.Duration;


public class Server extends Application{
	private static int port = 200;
	private static int x_index;
	private static XYChart.Series series;
	private static NumberAxis xAxis ;
	private static NumberAxis yAxis;
	private static LineChart linechart;
	
	/**
	 * 从客户端接受温度数据
	 */
	public static int get_temperature() {
		String s;
		int t=0;
		try {
			//创建接收端socket对象
			DatagramSocket ds = new DatagramSocket(port);
			
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
		
		return t;
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
		
		//初始化XYchart.series类
		series = new XYChart.Series();
		series.setName("温度折线图");
		
	}
	
	/**
	 * 绘制折线
	 */
	public static void draw_line() {
		series.getData().add(new XYChart.Data(x_index, get_temperature()));
		if (x_index > 25) {
			series.getData().remove(x_index - 25);
			xAxis.setLowerBound(xAxis.getLowerBound() + 1);
            xAxis.setUpperBound(xAxis.getUpperBound() + 1);
		}
		x_index++;
	}
	
	
	public static void main(String[] args) {
		Server server = new Server();
		server.graph_init();
		launch();
		
	}
	
	
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		//System.out.println("设置窗口标题");
		primaryStage.setTitle("ClientTemperature");
		
		//创建容器
		GridPane pane = new GridPane();
		pane.setPadding(new Insets(15,15,15,15));
		
		//将折线图加入容器容器
		pane.getChildren().add(linechart);
		
		//设置动画
		EventHandler<ActionEvent> eventHandler = e ->{//花括号里就是要进行的动画
			draw_line();			
		};
		Timeline animation = new Timeline(new KeyFrame(Duration.millis(1000), eventHandler));//控制动画的帧数，即刷新速度
		animation.setCycleCount(Timeline.INDEFINITE);//控制动画启动后的时间，当前设置为无限
		//启动动画
		animation.play();
		
		//将数据添加到折线图
		linechart.getData().add(series);
		
		//创建场景
		Scene scene = new Scene(pane);
		primaryStage.setScene(scene);
		primaryStage.show();
		
	}
	
}
