package exam01_exe02;

import java.io.Serializable;

/**
 * 创建shape类实现序列化接口
 * @author Administrator
 *
 */
public abstract class Shape implements Serializable{
	private static final long serialVersionUID = 1L;

	public abstract double getArea();
	
	//打印该图形的基本信息
	public void printInfo() {
	    System.out.println(getClass().getSimpleName() + " has area :" + getArea());
	  }
}
