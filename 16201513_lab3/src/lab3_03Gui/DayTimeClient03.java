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
		
		//����������������
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
	
	//����ʱ��
	public void time_update(byte hour ,byte minute,byte second) {
		this.hour = hour;
		this.minute = minute;
		this.second = second;
		
	}
	
	/**
	 * ��ʼ��ʱ��
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
		//�趨���ӵ�
		pane.setPadding(new Insets(15,15,15,15)); //���ñ߽���
		System.out.println("ʱ��Ϊ:" + dtc.hour+ ":" + dtc.minute+":" + dtc.second);
		Clock_pane clock = new Clock_pane(dtc.hour,dtc.minute,dtc.second);
		pane.getChildren().add(clock);
		
		//���ö���
		EventHandler<ActionEvent> eventHandler = e ->{//�����������Ҫ���еĶ���
			//�ӷ�������ȡʱ�䣬����������ǰ����
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
			//���»���ʱ��
			clock.paint_clock(dtc.hour, dtc.minute, dtc.second);
		};
		Timeline animation = new Timeline(new KeyFrame(Duration.millis(1000), eventHandler));//���ƶ�����֡������ˢ���ٶ�
		animation.setCycleCount(Timeline.INDEFINITE);//���ƶ����������ʱ�䣬��ǰ����Ϊ����
		//��������
		animation.play();
		Scene scene = new Scene(pane);
		primaryStage.setTitle("ʱ��");
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
	
}
