package lab1;
import java.io.*;

public class HexIO1 {
	public static void main(String[] args) {
		try {
			//定义文件读取与文件写入流；
			FileInputStream fis = new FileInputStream( "Test2.txt" );
			FileWriter fw = new FileWriter("TestOut.txt");
			File file = new File("Test2.txt");
			//判断原文件是否存在
			if(!file.exists()) {
				file.createNewFile();
			}
			int b,n=0;
			//读取文件并将文件内容写入到目标文件中
			while ((b=fis.read())!=-1){
				fw.write(b);
				if (((++n)%10)==0) fw.write("\n");
			}
			//关闭输入输出流
			fw.close();
			fis.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
