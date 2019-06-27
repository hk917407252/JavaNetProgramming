package exam01_exe02;

import exam01_exe02.Shape;

/**
 * 创建MyCircle继承自shape类
 * @author Administrator
 *
 */

public class MyCircle extends Shape{
	private int radius;
	private int centerX;
	private int centerY;
	
	private static final long serialVersionUID = 1L;

	public MyCircle() {
		this.setCenterX((int)(500*Math.random()));
		this.setCenterY((int)(600*Math.random()));
		this.setRadius((int)(50*Math.random()));
	}

	private void setRadius(int i) {
		this.radius = i;
	}

	private void setCenterY(int i) {
		this.centerY = i;	
	}

	private void setCenterX(int i) {
		this.centerX = i;		
	}

	public int getRadius() {
		return radius;
	}

	public int getCenterY() {
		return centerY;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public double getArea() {
		
		return ((int)(Math.PI*this.radius*this.radius*100))/100;
	}

	public int getCenterX() {
		// TODO Auto-generated method stub
		return this.centerX;
	}

}
