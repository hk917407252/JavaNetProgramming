package exe2_3Exam01_ball_rebound;

import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

public class BallRebound  extends Application{
	private static int length = 1000;
	private static int height = 600;
	private int circle_num = 10;
	private Pane circle_pane = new Pane();
	private Pane integrate_pane;
	
	/**
	 * 定义主函数
	 * @param args
	 */
	public static void main(String[] args) {
		launch();
	}
	
	
	/**
	 * 
	 */
	@Override
	public void start(Stage primaryStage) throws Exception {
		//定义容器
		Pane pane = new Pane();
		//定义定时器
		Timer timer = new Timer();
		
		//初始化定时器
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				for(int i = 0;i<circle_num;i++) {
					new MyCircle().start();
				}
			}
		}, Calendar.getInstance().getTime());
		
		primaryStage.setTitle("变色弹球");
		Scene scene = new Scene(pane,length,height);
		primaryStage.setScene(scene);
		pane.getChildren().add(circle_pane);
		primaryStage.show();
	}
	
	
	/**
	 * 定义随机圆
	 * @author Administrator
	 *
	 */
	class MyCircle extends Thread {
		Random ran = new Random();
		private int x,y,radius;
		private int x_move;
		private int y_move;
		private Circle circle;
		
		//构造方法
		public MyCircle() {
			radius = ran.nextInt(30)+20;
			x = ran.nextInt(length-2*radius)+radius;
			y = ran.nextInt(height-2*radius)+radius;
			x_move = ran.nextInt(2)==1 ? 1:-1;
			y_move = ran.nextInt(2)==1 ? 1:-1;
			Stop[] stops = new Stop[] { new Stop(Math.random(), Color.BLACK), new Stop(Math.random(), Color.RED)
					, new Stop(Math.random(), Color.PINK), new Stop(Math.random(), Color.GRAY), new Stop(Math.random(), Color.LIGHTPINK)
					, new Stop(Math.random(), Color.LIGHTGRAY), new Stop(Math.random(), Color.LIGHTBLUE), new Stop(Math.random(), Color.LIGHTCYAN)};
	        LinearGradient lg1 = new LinearGradient(0, 0, 1, 0, true, CycleMethod.NO_CYCLE, stops);
	        //
			circle = new Circle(x,y,radius);
			integrate_pane = new Pane(
					new Group(new Rectangle(length, height, Color.BLACK), circle_pane),circle);
			circle.setFill(lg1);
			circle_pane.getChildren().add(circle);
		}
		
		/**
		 * 更新圆的原点坐标
		 */
		public void update_circle_locate() {
			//检测是否到达边界,若达到边界，则需要改变移动方向
			if(x<radius||x>length-radius) {
				x_move *= -1;
			}
			if(y<radius||y>height-radius) {
				y_move *= -1;
			}
			x += x_move;
			y += y_move;
			
			circle.setCenterX(x);
			circle.setCenterY(y);
		}
		
		@Override
		public void run() {
			/*while(true) {
				try {
					update_circle_locate();
					Thread.sleep(10);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}*/
			Platform.runLater(() -> {
				//设置动画
				EventHandler<ActionEvent> eventHandler = e ->{//花括号里就是要进行的动画
					update_circle_locate();
				};
				Timeline animation = new Timeline(new KeyFrame(Duration.millis(10), eventHandler));//控制动画的帧数，即刷新速度
				animation.setCycleCount(Timeline.INDEFINITE);//控制动画启动后的时间，当前设置为无限
				//启动动画
				animation.play();
			});
			
		}
		
	}
	
}
