package exam01_exe01;

import java.io.BufferedReader;
import java.io.FileReader;

public class TestFind {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String path_name = "D:\\temp\\test.txt";
		read_ByLine(path_name);
	}
	
	/**
	 * 根据行类读取文件
	 * @param path_name
	 */
	public static void read_ByLine(String path_name) {
		FileReader reader = null;
		BufferedReader br = null;
		try {
			String str = null;
			reader = new FileReader(path_name);
			br = new BufferedReader(reader);
			while((str = br.readLine())!= null) {
				if(str.indexOf("test")!= -1) {
					System.out.println(str);
				}
			}
		}catch (Exception e) {
			// TODO: handle exception
		}
	}

}
