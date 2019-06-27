package exam01_exe03;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Scanner;

import exam01_exe01.Read_Write;

public class Random_Access_File {
	private static RandomAccessFile ras;
	
	public static void main(String[] args) {
		String path = "score.data";
		boolean flag = true;
		int option = 0;
		Scanner scan = new Scanner(System.in);
		//判断文件是否存在，如果不存在，则创建该文件
		File file = new File(path);
		if(!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		//定义随机读取文件对象
		try {
			ras = new RandomAccessFile(path,"rw");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		do {
			//接收输入的操作选项
			System.out.println("请输入需要进行的操作序号：1（增加）、2（查找）、3（删除）、4（修改）、5（退出系统）");
			option = scan.nextInt();
			switch(option){
			case 1:
				System.out.println("operation-1：增加学生信息");
				add_info(ras,scan);
				break;				
			case 2:
				String[] info = new String[4];
				System.out.println("operation-2：根据学好查找学生信息");
				
				info = seek_info(ras,scan);
				System.out.println("学号: "+ info[0] +" 姓名: "+ info[1]+" 成绩: " + info[2]);
				break;	
				
			case 3:
				System.out.println("operation-3：根基学号删除学生信息");
				int locate = 0;
				//首先查找到需要进行修改的数据位置
				info = seek_info(ras,scan);
				locate = Integer.parseInt(info[3].toString());
				System.out.println("位置处于:"+locate);
				
				//进行学生信息删除
				info_delete(ras,scan,locate);
				break;	
				
			case 4:
				System.out.println("operation-4：根据学生学号来修改学生信息");
				locate = 0;				
				//首先查找到需要进行修改的数据位置
				/*String[] info = new String[4];*/
				info = seek_info(ras,scan);
				locate = Integer.parseInt(info[3].toString());
				
				//进行学生成绩修改
				info_alter(ras,scan,locate,info);
				
				break;
			case 5:
				try {
					ras.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				System.out.println("退出系统成功");
				flag = false;
				break;
			default:
				System.out.println("输入非法，请从新输入");
				break;
			}
		}while(flag);	
	}
	
	/**
	 * 信息删除
	 * @param ras
	 * @param scan
	 * @param locate
	 */
	public static void info_delete(RandomAccessFile ras,Scanner scan,int locate) {
		//将修改后学生信息写入文件
		try {
			//根据位置信息找到目标学生信息
			ras.seek(locate);
			//转化字符类型并写入到文件中（解决了中文乱码问题...）
			//得到需要删除这一行的长度
			String tem = ras.readLine();
			//将位置归档
			ras.seek(locate);
			byte[] b = new byte[tem.length()];
			ras.write(b);
			//提示用户信息删除成功
			System.out.println("学生信息删除成功");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 学生信息修改
	 * @param ras
	 * @param scan
	 * @param locate
	 * @param info
	 */
	public static void info_alter(RandomAccessFile ras,Scanner scan,int locate,String[] info) {
		//控制台输入学生信息
		String name,id,grade;
		id = info[0];
		name = info[1];
		System.out.println("请输入成绩（以回车结束当前输入）：");
		grade = scan.next();
		
		
		//将修改后学生信息写入文件
		try {
			String altered_info = "学号:"+ id +"姓名:"+ name+"成绩:" + grade + "\r\n";
			//根据位置信息找到目标学生信息
			ras.seek(locate);
			//转化字符类型并写入到文件中（解决了中文乱码问题...）
			ras.writeBytes("");
			ras.writeBytes(new String(altered_info.getBytes("utf-8"),"ISO-8859-1"));
			//提示用户信息修改成功
			System.out.println(id+"-" + name + " 学生信息修改成功");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	/**
	 * 
	 * 学生信息查找
	 * @param ras
	 * @param scan
	 * @return
	 */
	public static String[] seek_info(RandomAccessFile ras,Scanner scan) {
		String[] info = new String[4];
		boolean flag = false;
		//提示用户输入所需要查找的学生信息
		System.out.println("请输入需要查找的学生学号信息（八位数字）：");
		info[0] = scan.next();
		String b;
		try {
			ras.seek(0);
			while( (b=new String(ras.readLine().getBytes("ISO-8859-1"),"utf-8") )!=null) {
				if(b.indexOf(info[0])!=-1) {
					System.out.println("B的长度为："+ b.getBytes().length);
					info[3] = ""+(ras.getFilePointer()-b.getBytes().length-2);
					/*System.out.println("test" + b);*/
					flag = true;
					//获取学生姓名
					info[1] = b.substring(14,b.indexOf("成绩"));
					/*System.out.println("name:"+info[1]);*/
					//获取学生成绩
					info[2] = b.substring(14+info[1].length()+3);
					/*System.out.println("grade:"+info[2]);*/
					break;
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(flag) {
			return info;
		}
		return null;
	}
	
	/**
	 * 信息存储
	 * @param ras
	 */
	public static void add_info(RandomAccessFile ras,Scanner scan) {
		//控制台输入学生信息
		String name,id,grade;
		System.out.println("请输入学号（八位数字，统一以回车结束当前输入，以下输入类同）：");
		id = scan.next();
		System.out.println("请输入姓名：");
		name = scan.next();
		System.out.println("请输入成绩：");
		grade = scan.next();
		
		//将学生信息写入文件
		try {
			ras.seek(ras.length());
			String info = "学号:"+ id +"姓名:"+ name+"成绩:" + grade+"\r\n";
			//转化字符类型并写入到文件中（解决了中文乱码问题...）
			/*new String(info.getBytes("utf-8"),"ISO-8859-1")*/
			ras.writeBytes(new String(info.getBytes("utf-8"),"ISO-8859-1"));
			//提示用户信息存储成功
			System.out.println(id+"-" + name + " 学生信息添加成功");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
