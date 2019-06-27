package lab3_03Gui;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;

public class Clock_pane extends Pane{
	final double PI = 3.1415926;
	int height = 500,width = 500;
	int centerX = 250;
	int centerY = 250;
	double r = 250;
	byte hour;
	byte minute;
	byte second;
	
	
	
	public Clock_pane(byte hour,byte minute,byte second) {
		this.hour = hour;
		this.minute = minute;
		this.second = second;
		paint_clock(hour,minute,second);
	}
	
	public void paint_clock(byte hour,byte minute,byte second) {
		int x1,y1,x2,y2,t,xt,yt;
		int count = 1;
		
		//首先清空所有子元素
		getChildren().clear();
		
		//绘制圆圈
		Circle circle= new Circle(centerX,centerY,r);//外圈大圆
		circle.setFill(Color.WHITE);
		circle.setStroke(Color.BLACK);
		getChildren().add(circle);
		//绘制时钟刻度
		for(t = 0;t<60;t++) {
			x1 = centerX+(int)(r*Math.cos(t*PI/30));
			y1 = centerY+(int)(r*Math.sin(t*PI/30));
			
			if(t%5==0) {
				//计算刻度指针末端位置
				x2 = centerX+(int)(r*Math.cos(t*PI/30)*18/20);
				y2 = centerY+(int)(r*Math.sin(t*PI/30)*18/20);
				
				//计算刻度数字的位置
				xt = centerX+(int)(r*Math.cos(t*PI/30)*0.8);
				yt = centerY+(int)(r*Math.sin(t*PI/30)*0.8);
				
				Text text = new Text(xt,yt, ""+(((count+2)%12==0)? 12 :(count+2)%12));
				count++;
				text.setStroke(Color.BLACK);
				getChildren().add(text);
			}
			else {
				x2 = centerX+(int)(r*Math.cos(t*PI/30)*19/20);
				y2 = centerY+(int)(r*Math.sin(t*PI/30)*19/20);
			}
			Line line = new Line(x1,y1,x2,y2);
			line.setStroke(Color.BLACK);
			getChildren().add(line);
		}
		
		//绘制时针
		if(hour<12) {
			t = 60*hour + minute;
		}else {
			t = 60*(hour%12) + minute;
		}
		
		x1 = centerX+(int)(r*Math.cos(t*PI/360 - PI/2)*0.4);
		y1 = centerY+(int)(r*Math.sin(t*PI/360 - PI/2)*0.4);
		x2 = centerX+(int)(r*Math.cos(t*PI/360 - PI/2)*0.01);
		y2 = centerY+(int)(r*Math.sin(t*PI/360 - PI/2)*0.01);
		Line hour_line = new Line(x1,y1,x2,y2);
		hour_line.setStroke(Color.BLACK);
		getChildren().add(hour_line);
		
		//绘制分针
		t = 60*minute + second;
		x1 = centerX+(int)(r*Math.cos(t*PI/1800 - PI/2)*0.6);
		y1 = centerY+(int)(r*Math.sin(t*PI/1800 - PI/2)*0.6);
		x2 = centerX+(int)(r*Math.cos(t*PI/1800 - PI/2)*0.01);
		y2 = centerY+(int)(r*Math.sin(t*PI/1800 - PI/2)*0.01);
		Line minute_line = new Line(x1,y1,x2,y2);
		minute_line.setStroke(Color.BLUE);
		getChildren().add(minute_line);
		
		//绘制秒针
		t = second;
		x1 = centerX+(int)(r*Math.cos(t*PI/30 - PI/2)*0.8);
		y1 = centerY+(int)(r*Math.sin(t*PI/30 - PI/2)*0.8);
		x2 = centerX+(int)(r*Math.cos(t*PI/30 - PI/2)*0.01);
		y2 = centerY+(int)(r*Math.sin(t*PI/30 - PI/2)*0.01);
		Line second_line = new Line(x1,y1,x2,y2);
		second_line.setStroke(Color.RED);
		getChildren().add(second_line);
		
	}
}
