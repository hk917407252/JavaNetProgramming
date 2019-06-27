package exer5_1_2_URLInfo;

import java.io.IOException;
import java.net.URL;
import java.util.Scanner;

public class URLGetInfo {

	public static void main(String [] args)
	   {
			Scanner scan = new Scanner(System.in);
			do {
				System.out.println("������URL��");
				String url_string = scan.nextLine().trim();
				try
			      {
			         URL url = new URL(url_string);
			         System.out.println("Э��Ϊ��" + url.getProtocol());
			         System.out.println("��������" + url.getHost());
			         System.out.println("·����" + url.getPath());
			         System.out.println("�˿ڣ�" + url.getPort());
			         System.out.println("Ĭ�϶˿ڣ�" + url.getDefaultPort());
			         System.out.println("���������" + url.getQuery());
			         System.out.println("��λλ�ã�" + url.getRef());
			      }catch(IOException e)
			      {
			         e.printStackTrace();
			      }
			}while(true);
	      
	   }

}
