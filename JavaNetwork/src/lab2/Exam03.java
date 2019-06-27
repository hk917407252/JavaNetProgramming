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
		//����3���ļ������߳�,��ȷ��ԭ�ļ����ݲ�Ϊ��
		MyThread  m1 = e3.new MyThread("D:\\temp\\source\\test.txt","D:\\temp\\target1");
		MyThread  m2 = e3.new MyThread("D:\\temp\\source\\test.txt","D:\\temp\\target2");
		MyThread  m3 = e3.new MyThread("D:\\temp\\source\\test.txt","D:\\temp\\target3");
		
		//�����߳�
		m1.start();
		m2.start();
		m3.start();
	}
	
	class MyThread extends Thread {
		File source_file;
		File target_file; 
		//���췽������������ԭ·����Ŀ��·������ʼ��ԭ�ļ���Ŀ��Ŀ¼
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
		
		//��д
		@Override
		public void run() {
			FileInputStream fis;
			FileOutputStream fos;
			
			try {
				fis = new FileInputStream(this.source_file);
				fos = new FileOutputStream(this.target_file.getPath()+"\\"+source_file.getName());
				System.out.println("****test_path: "+this.target_file.getPath()+"\\"+source_file.getName());
				
				//����ԭ�ļ�������Ŀ���ļ�
				/*File new_file = new File(this.target_file.getPath()+"\\"+source_file.getName());
				if(!new_file.exists()) {
					try {
						new_file.createNewFile();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}*/
				
				//�����ļ��������
				BufferedReader br = new BufferedReader(new InputStreamReader(fis));
				BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
				
				//�ļ����ݶ�ȡ��д��Ŀ���ļ�
				String str = null;
				try {
					while((str = br.readLine())!=null) {
						bw.newLine();
						bw.write(str);
						System.out.println(str);
					}
					
					//�ر��������
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
