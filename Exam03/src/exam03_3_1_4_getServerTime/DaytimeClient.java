package exam03_3_1_4_getServerTime;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class DaytimeClient {
	public static void main(String[] args) throws IOException{
		
        //����Socket�������ӷ�����
        Socket socket=new Socket("127.0.0.1",13);
        //ͨ���ͻ��˵��׽��ֶ���Socket��������ȡ�ֽ��������������д�������
        OutputStream out=socket.getOutputStream();
        out.write("��������ã�".getBytes());
        
        //��ȡ���������ص����ݣ�ʹ��socket�׽��ֶ����е��ֽ�������
        InputStream in=socket.getInputStream();
        byte[] data=new byte[1024];
        int len=in.read(data);
        System.out.println(new String(data,0,len));
        socket.close();
    }
}
