package lab1;
import java.io.*;

public class HexIO1 {
	public static void main(String[] args) {
		try {
			//�����ļ���ȡ���ļ�д������
			FileInputStream fis = new FileInputStream( "Test2.txt" );
			FileWriter fw = new FileWriter("TestOut.txt");
			File file = new File("Test2.txt");
			//�ж�ԭ�ļ��Ƿ����
			if(!file.exists()) {
				file.createNewFile();
			}
			int b,n=0;
			//��ȡ�ļ������ļ�����д�뵽Ŀ���ļ���
			while ((b=fis.read())!=-1){
				fw.write(b);
				if (((++n)%10)==0) fw.write("\n");
			}
			//�ر����������
			fw.close();
			fis.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
