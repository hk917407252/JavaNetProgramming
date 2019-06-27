package serialize;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;


public class Serial_Test{

	public static void main(String[] args) {
		String target_path = "serial_wirte.txt";
		List<MyCircle> shapeList = null;
		List<MyCircle> shapes = null;
		//��ʼ��shape�б�
		shapeList = shape_list_initial();
		//���л��洢
		serializal_write(shapeList,target_path);
		
		//�����л���ȡ
		shapes = serializal_read(target_path);
		
		//��ӡ����ȡ�������б����
		print_all_info(shapes);
		
	}
	
	/**
	 * ����б������е�ͼ����Ϣ
	 * @param list
	 */
	public static void print_all_info(List<MyCircle> list) {
		for(MyCircle c:list) {
			System.out.println("x: " + c.getCenterX()+ "  y : "+c.getCenterY() + "  radius: " + c.getRadius() + "   areas :" +c.getArea());
		}
		
	}
	
	
	/**
	 * �����л���ȡshapeList����
	 * @param path
	 * @return
	 */
	public static List<MyCircle> serializal_read(String path) {
		List<MyCircle> list = new ArrayList<MyCircle>();
		//��ʼ������������
		FileInputStream fis = null;
		ObjectInputStream ois = null;
		try {
			fis = new FileInputStream(path);
			ois= new ObjectInputStream(fis);
					
			//�����еĶ��󱣴浽�ļ���
			list = (List<MyCircle>)ois.readObject(); 
			ois.close();
			fis.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return list;
	}
	
	/**
	 * ���л��洢�����б������еĶ���洢���ļ���
	 * @param shapeList
	 * @param target_path
	 */
	public static void serializal_write(List<MyCircle> shapeList,String target_path) {
		//�ж�Ŀ��·���Ƿ�Ϊ��
		File file = new File(target_path);
		if(!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		//��ʼ������������
		FileOutputStream fos = null;
		ObjectOutputStream oos = null;
		try {
			fos = new FileOutputStream(target_path);
			oos= new ObjectOutputStream(fos);
			
			//�����еĶ��󱣴浽�ļ���
			oos.writeObject(shapeList);
			oos.close();
			fos.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	     
	}
	
	
	/**
	 * ��ʼ��shape�б�
	 * @return
	 */
	public static List<MyCircle> shape_list_initial(){
		List<MyCircle> shapeList = new ArrayList<MyCircle>();
		for(int i = 0;i<10;i++) {
			MyCircle c = new MyCircle();
			shapeList.add(c);
		}
		return shapeList;	
	}
	
}
