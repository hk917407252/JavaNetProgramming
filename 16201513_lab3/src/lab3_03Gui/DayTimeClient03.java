package lab3_03Gui;


import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;

public class DayTimeClient03 extends Application{
	private int year;
	private byte month;
	private byte day;
	private byte hour;
	private byte minute;
	private byte second;
	Pane pane = new GridPane();
	static DayTimeClient03 dtc = new DayTimeClient03();
	static Socket s;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		//给服务器发送请求
		try {
			s = new Socket("127.0.0.1",2007);
			DataInputStream dis = new DataInputStream(s.getInputStream()) ;			
			dtc.time_init(dis);
			s.close();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		launch();
		
	}
	
	//更新时间
	public void time_update(byte hour ,byte minute,byte second) {
		this.hour = hour;
		this.minute = minute;
		this.second = second;
		
	}
	
	/**
	 * 初始化时间
	 * @param dis
	 */
	public void time_init(DataInputStream dis) {
		try {
			this.year = dis.readInt();
			this.month = dis.readByte();
			this.day = dis.readByte();
			this.hour = dis.readByte();
			this.minute = dis.readByte();
			this.second = dis.readByte();
			/*System.out.println(year+"-"+ month+"-"+day);
			System.out.println(hour+"-"+ minute+"-"+second);*/
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		//设定闹钟的
		pane.setPadding(new Insets(15,15,15,15)); //设置边界间距
		System.out.println("时间为:" + dtc.hour+ ":" + dtc.minute+":" + dtc.second);
		Clock_pane clock = new Clock_pane(dtc.hour,dtc.minute,dtc.second);
		pane.getChildren().add(clock);
		
		//设置动画
		EventHandler<ActionEvent> eventHandler = e ->{//花括号里就是要进行的动画
			//从服务器获取时间，并更行至当前对象
			try {
				s = new Socket("127.0.0.1",2007);
				DataInputStream dis = new DataInputStream(s.getInputStream()) ;			
				dtc.time_init(dis);
				s.close();
			} catch (UnknownHostException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			//重新绘制时钟
			clock.paint_clock(dtc.hour, dtc.minute, dtc.second);
		};
		Timeline animation = new Timeline(new KeyFrame(Duration.millis(1000), eventHandler));//控制动画的帧数，即刷新速度
		animation.setCycleCount(Timeline.INDEFINITE);//控制动画启动后的时间，当前设置为无限
		//启动动画
		animation.play();
		Scene scene = new Scene(pane);
		primaryStage.setTitle("时钟");
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
	
}
