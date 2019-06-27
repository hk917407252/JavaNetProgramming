package lab1;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class HexIO2 {

	public static void main(String[] args) {
		
		//Դ�ļ���Ŀ¼·��
		String source_path = "D:\\temp\\source";
		//Ŀ���ļ���Ŀ¼·��
		String target_path = "D:\\temp\\target";
		
		//�жϸø��ļ����Ƿ���ڣ������ڵĻ��������ļ���
		File source_files = new File(source_path);
		if(!source_files.exists()) {
			source_files.mkdirs();
		}
		//����ԴĿ¼�µ������ļ����˴����������ļ���
		File[] files = source_files.listFiles();
		
		//����copy_files������Դ�ļ����������ļ��ĸ��Ƶ�Ŀ���ļ�
		copy_files(files,target_path);
		
	}
	
	
	/**
	 * �����ļ����µ������ļ���Ŀ���ļ���
	 * @param files
	 * @param target_path
	 */
	public static void copy_files(File[] files,String target_path) {
		//����Դ�ļ����е������ļ�
		for(File f:files) {
			if(f.exists() ) {
				//����̨����ļ����������ļ�������
				System.out.println(f.getName() +"\n");
				//���õ����ļ����ƺ�����ÿ���ļ������ø÷�������������Դ�����ļ����Լ����ļ���Ŀ��·�����ļ���·�������ļ�����
				copy_file(f,target_path+"\\"+f.getName());
			}
		}
	}
	
	/**
	 * �������ļ����Ƶ�ָ��Ŀ¼�£�����Ѿ������򲻽��и���
	 * @param file
	 * @param targetfile_path
	 */
	public static void copy_file(File file,String targetfile_path) {
		
		File targetfile = new File(targetfile_path);
		//����ļ������ڣ�����и��ƣ�����ͬ�ļ��Ѿ����ڣ�����ִ���ļ����Ʋ���
		if(!targetfile.exists()) {
			try {
				targetfile.createNewFile();
				FileReader fr = new FileReader(file);
				FileWriter fw = new FileWriter(targetfile_path);
				int b;
				while ((b=fr.read())!=-1){
					fw.write(b);
				}
				fw.close();
				fr.close();
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
