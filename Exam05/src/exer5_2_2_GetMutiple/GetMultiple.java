package exer5_2_2_GetMutiple;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 获取传送过来参数的乘积
 * @author Administrator
 *
 */
public class GetMultiple {
	
	/**
	 * 计算乘积
	 * @param parametersString
	 * @return
	 */
	public static double multiple(String parametersString) {
		double result = 1.0;
		String[] divided_Patameter = parametersString.split("&");
		for(String e:divided_Patameter) {
			System.out.println("formulate:"+e);
			String[] a = e.split("=");
			result*=Double.parseDouble(a[1]);
			/*System.out.println("a[1]:"+a[1]);*/
		}
		return result;
	}
	
	/**
	 * 主函数
	 * @param args
	 */
	public static void main(String[] args) {
		Double result = 0.0;
        try {
           
        	ServerSocket ss=new ServerSocket(8888);
            
            while(true){
                Socket socket=ss.accept();
                BufferedReader bd=new BufferedReader(new InputStreamReader(socket.getInputStream()));
                
                //接受HTTP请求
                String requestHeader;
                int contentLength=0;
                String parameterString = "";
                //读取请求头星系，并打印其中的get与post参数
                while((requestHeader=bd.readLine())!=null&&!requestHeader.isEmpty()){
                    /*System.out.println(requestHeader);*/
                    
                    //获得GET参数
                    if(requestHeader.startsWith("GET") && requestHeader.contains("?")){
                        int begin = requestHeader.indexOf("/?")+2;
                        int end = requestHeader.indexOf("HTTP/");
                        String condition=requestHeader.substring(begin, end);
                        System.out.println("GET参数是："+condition);
                        result = multiple(condition);
                    }
                    
                }
                //发送回执
                PrintWriter pw=new PrintWriter(socket.getOutputStream());
                
                pw.println("HTTP/1.1 200 OK");
                pw.println("Content-type:text/html");
                pw.println();
                pw.println("<h1>计算成功!获取的乘积为：" +result+   "</h1>");
                pw.flush();
                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
