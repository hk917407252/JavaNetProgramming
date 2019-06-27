package exer5_1_2_URLInfo;

import java.io.IOException;
import java.net.URL;
import java.util.Scanner;

public class URLGetInfo {

	public static void main(String [] args)
	   {
			Scanner scan = new Scanner(System.in);
			do {
				System.out.println("请输入URL：");
				String url_string = scan.nextLine().trim();
				try
			      {
			         URL url = new URL(url_string);
			         System.out.println("协议为：" + url.getProtocol());
			         System.out.println("主机名：" + url.getHost());
			         System.out.println("路径：" + url.getPath());
			         System.out.println("端口：" + url.getPort());
			         System.out.println("默认端口：" + url.getDefaultPort());
			         System.out.println("请求参数：" + url.getQuery());
			         System.out.println("定位位置：" + url.getRef());
			      }catch(IOException e)
			      {
			         e.printStackTrace();
			      }
			}while(true);
	      
	   }

}
