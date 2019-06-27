package exam01_exe01;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

public class Write {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String path_name = "D:\\temp\\random.txt";
		write_ByLine(path_name);
	}
	
	/**
	 * 按行写入文件
	 * @param path_name
	 */
	public static void write_ByLine(String path_name) {
		//声明文件写入以及写入缓存
		FileWriter writer = null;
		BufferedWriter bw = null;
		
		try {
			//判断文件是否存在
			File file = new File(path_name);
			if(!file.exists()) {
				file.createNewFile();
			}
			//定义以追加的方式写入字符串
			writer = new FileWriter(path_name,true);
			bw = new BufferedWriter(writer);
			
			//生成随机数,并按行写入目标文件
			for(int i = 0 ; i<10;i++) {
				int tem_num = (int)(Math.random()*100);
				String str;
				str = "Number "+ (i+1)+": "+ tem_num;
				//按行写入目标文件
				bw.newLine();
				bw.write(str);
				/*System.out.println("Number "+ (i+1)+": "+ tem_num +"\n");	*/
			}
			bw.close();
			writer.close();
			
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

}
