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
	 * �ӿͻ��˽����¶�����
	 */
	public static int get_temperature() {
		String s;
		int t=0;
		try {
			//�������ն�socket����
			DatagramSocket ds = new DatagramSocket(port);
			
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
		
		return t;
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
		
		//��ʼ��XYchart.series��
		series = new XYChart.Series();
		series.setName("�¶�����ͼ");
		
	}
	
	/**
	 * ��������
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
		//System.out.println("���ô��ڱ���");
		primaryStage.setTitle("ClientTemperature");
		
		//��������
		GridPane pane = new GridPane();
		pane.setPadding(new Insets(15,15,15,15));
		
		//������ͼ������������
		pane.getChildren().add(linechart);
		
		//���ö���
		EventHandler<ActionEvent> eventHandler = e ->{//�����������Ҫ���еĶ���
			draw_line();			
		};
		Timeline animation = new Timeline(new KeyFrame(Duration.millis(1000), eventHandler));//���ƶ�����֡������ˢ���ٶ�
		animation.setCycleCount(Timeline.INDEFINITE);//���ƶ����������ʱ�䣬��ǰ����Ϊ����
		//��������
		animation.play();
		
		//��������ӵ�����ͼ
		linechart.getData().add(series);
		
		//��������
		Scene scene = new Scene(pane);
		primaryStage.setScene(scene);
		primaryStage.show();
		
	}
	
}
