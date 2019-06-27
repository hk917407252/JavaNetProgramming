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
		//初始化shape列表
		shapeList = shape_list_initial();
		//序列化存储
		serializal_write(shapeList,target_path);
		
		//反序列化读取
		shapes = serializal_read(target_path);
		
		//打印所读取出来的列表对象
		print_all_info(shapes);
		
	}
	
	/**
	 * 输出列表中所有的图形信息
	 * @param list
	 */
	public static void print_all_info(List<MyCircle> list) {
		for(MyCircle c:list) {
			System.out.println("x: " + c.getCenterX()+ "  y : "+c.getCenterY() + "  radius: " + c.getRadius() + "   areas :" +c.getArea());
		}
		
	}
	
	
	/**
	 * 反序列化读取shapeList对象
	 * @param path
	 * @return
	 */
	public static List<MyCircle> serializal_read(String path) {
		List<MyCircle> list = new ArrayList<MyCircle>();
		//初始化对象输入流
		FileInputStream fis = null;
		ObjectInputStream ois = null;
		try {
			fis = new FileInputStream(path);
			ois= new ObjectInputStream(fis);
					
			//将所有的对象保存到文件中
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
	 * 序列化存储，将列表中所有的对象存储到文件中
	 * @param shapeList
	 * @param target_path
	 */
	public static void serializal_write(List<MyCircle> shapeList,String target_path) {
		//判断目标路径是否为空
		File file = new File(target_path);
		if(!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		//初始化对象输入流
		FileOutputStream fos = null;
		ObjectOutputStream oos = null;
		try {
			fos = new FileOutputStream(target_path);
			oos= new ObjectOutputStream(fos);
			
			//将所有的对象保存到文件中
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
	 * 初始化shape列表
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
