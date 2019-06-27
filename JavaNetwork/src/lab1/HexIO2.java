package lab1;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class HexIO2 {

	public static void main(String[] args) {
		
		//源文件夹目录路径
		String source_path = "D:\\temp\\source";
		//目标文件夹目录路径
		String target_path = "D:\\temp\\target";
		
		//判断该该文件夹是否存在，不存在的话创建该文件夹
		File source_files = new File(source_path);
		if(!source_files.exists()) {
			source_files.mkdirs();
		}
		//接收源目录下的所有文件（此处包含所有文件）
		File[] files = source_files.listFiles();
		
		//调用copy_files方法将源文件夹下所有文件的复制到目标文件
		copy_files(files,target_path);
		
	}
	
	
	/**
	 * 复制文件夹下的所有文件到目标文件夹
	 * @param files
	 * @param target_path
	 */
	public static void copy_files(File[] files,String target_path) {
		//遍历源文件夹中的所有文件
		for(File f:files) {
			if(f.exists() ) {
				//控制台输出文件夹下所有文件的名称
				System.out.println(f.getName() +"\n");
				//调用单个文件复制函数，每个文件都调用该方法，参数包括源单个文件，以及该文件的目标路径（文件夹路径加上文件名）
				copy_file(f,target_path+"\\"+f.getName());
			}
		}
	}
	
	/**
	 * 将单个文件复制到指定目录下，如果已经存在则不进行复制
	 * @param file
	 * @param targetfile_path
	 */
	public static void copy_file(File file,String targetfile_path) {
		
		File targetfile = new File(targetfile_path);
		//如果文件不存在，则进行复制；若相同文件已经存在，则不再执行文件复制操作
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
