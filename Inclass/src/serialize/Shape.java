package serialize;

import java.io.Serializable;

public abstract class Shape implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public abstract double getArea();
	
	//��ӡ��ͼ�εĻ�����Ϣ
	public void printInfo() {
	    System.out.println(getClass().getSimpleName() + " has area :" + getArea());
	  }
}
