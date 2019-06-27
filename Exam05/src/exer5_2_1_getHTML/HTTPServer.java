package exer5_2_1_getHTML;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URLDecoder;

public class HTTPServer {
    public static void main(String[] args) {
        try {
           
        	ServerSocket ss=new ServerSocket(8888);
            
        	/**��������*/
            while(true){
                Socket socket=ss.accept();
                
                BufferedReader bd = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                
                /*BufferedReader bd=new BufferedReader(new InputStreamReader(socket.getInputStream()));*/
                
                //����HTTP����
                String requestHeader;
                int contentLength=0;
                //��ȡ����ͷ��Ϣ������ӡ���е�get��post����
                while((requestHeader=bd.readLine())!=null&&!requestHeader.isEmpty()){
                    System.out.println(requestHeader);
                    
                    //���GET����
                    if(requestHeader.startsWith("GET")){
                        int begin = requestHeader.indexOf("/?")+2;
                        int end = requestHeader.indexOf("HTTP/");
                        String condition=requestHeader.substring(begin, end);
                        System.out.println("GET�����ǣ�"+condition);
                    }
                    //���POST����
                    if(requestHeader.startsWith("Content-Length")){
                        int begin=requestHeader.indexOf("Content-Lengh:")+"Content-Length:".length();
                        String postParamterLength=requestHeader.substring(begin).trim();
                        contentLength=Integer.parseInt(postParamterLength);
                        System.out.println("POST���������ǣ�"+Integer.parseInt(postParamterLength));
                    }
                }
                StringBuffer sb=new StringBuffer();
                if(contentLength>0){
                    for (int i = 0; i < contentLength; i++) {
                        sb.append((char)bd.read());
                    }
                    System.out.println("POST�����ǣ�"+sb.toString());
                }
                
                //���ͻ�ִ
                PrintWriter pw=new PrintWriter(socket.getOutputStream());
                
                pw.println("HTTP/1.1 200 OK");
                pw.println("Content-type:text/html");
                pw.println();
                pw.println("<h1>���ʳɹ�!��ȡһֻС��</h1>");
                pw.println("<img src=\"tsy3.jpg\"  alt=\"С��\" width=\"400px;\"/>");
                pw.flush();
                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}