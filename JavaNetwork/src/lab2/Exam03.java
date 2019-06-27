package lab2;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class Exam03 {

	public static void main(String[] args) {
		Exam03 e3 = new Exam03();
		//创建3个文件复制线程,请确保原文件内容不为空
		MyThread  m1 = e3.new MyThread("D:\\temp\\source\\test.txt","D:\\temp\\target1");
		MyThread  m2 = e3.new MyThread("D:\\temp\\source\\test.txt","D:\\temp\\target2");
		MyThread  m3 = e3.new MyThread("D:\\temp\\source\\test.txt","D:\\temp\\target3");
		
		//启动线程
		m1.start();
		m2.start();
		m3.start();
	}
	
	class MyThread extends Thread {
		File source_file;
		File target_file; 
		//构造方法根据所给的原路径与目标路径，初始化原文件及目标目录
		public MyThread(String source,String target) {
			this.source_file = new File(source);
			this.target_file = new File(target);
			if(!this.source_file.exists()) {
				try {
					this.source_file.createNewFile();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(!this.target_file.exists()) {
				this.target_file.mkdir();
			}
		}
		
		//重写
		@Override
		public void run() {
			FileInputStream fis;
			FileOutputStream fos;
			
			try {
				fis = new FileInputStream(this.source_file);
				fos = new FileOutputStream(this.target_file.getPath()+"\\"+source_file.getName());
				System.out.println("****test_path: "+this.target_file.getPath()+"\\"+source_file.getName());
				
				//根据原文件名创建目标文件
				/*File new_file = new File(this.target_file.getPath()+"\\"+source_file.getName());
				if(!new_file.exists()) {
					try {
						new_file.createNewFile();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}*/
				
				//创建文件读入读出
				BufferedReader br = new BufferedReader(new InputStreamReader(fis));
				BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
				
				//文件内容读取并写入目标文件
				String str = null;
				try {
					while((str = br.readLine())!=null) {
						bw.newLine();
						bw.write(str);
						System.out.println(str);
					}
					
					//关闭输入输出
					bw.close();
					br.close();
					fos.close();
					fis.close();	
				} catch (NumberFormatException | IOException e) {
					e.printStackTrace();
				}
				
				
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
	}
}
