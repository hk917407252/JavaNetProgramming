package exam01_exe01;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class Read_Write{

	public static void main(String[] args) throws FileNotFoundException {
		// TODO Auto-generated method stub
		String souce_path = "D:/temp/random.txt";
		String target_path = "D:/temp/test.txt";
		write_accOfRead(souce_path, target_path);
	}
	
	/**
	 * 将读取出来的内容存到另一个文件中
	 * @param souce_path
	 * @param target_path
	 * @throws FileNotFoundException
	 */
	public static void write_accOfRead(String souce_path,String target_path) throws FileNotFoundException {
		//定义文件输入流，用于读取文件
		FileInputStream fis = null;
		//定义文件输出流，用于写入文件
		FileOutputStream fos = null;
		
		/*FileWriter fw = null;
		FileReader fr = null;*/
		//定义缓冲过滤流
		BufferedWriter bw = null;
		BufferedReader br = null;
		
		try {
			//判断目标文件是否存在，不存在的话创建该文件（此处不需要考虑源文件，源文件必须存在才有读写）
			File target_file = new File(target_path);
			if(!target_file.exists()) {
				target_file.createNewFile();
			}
			//初始化输入输出流
			fis = new FileInputStream(souce_path);
			fos = new FileOutputStream(target_path,true);
			bw = new BufferedWriter(new OutputStreamWriter(fos)); 
			br = new BufferedReader(new InputStreamReader(fis));
			String str = null;
			
			while((str = br.readLine())!=null) {
				//如果当前行出现了:，则说明有随机数
				if(str.indexOf(":")!=-1) {
					//按照其冒号的下标找到随机数并判断大小
					String tem_str = str.substring(str.indexOf(":")+2);
					if(Integer.parseInt(tem_str)>50) {
						bw.newLine();
						bw.write(tem_str);
						System.out.println(tem_str);
					}
				}
			}
			//先关缓冲过滤流，先关写
			bw.close();
			br.close();
			fos.close();
			fis.close();
			
			
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

}
