package exam03_3_1_1_getIpAddress;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class GetIpAddress {

	public static void main(String[] args) throws UnknownHostException{
		String hostName = "www.baidu.com";
		get_Ip_AddressByName(hostName);
	}
	/**ͨ������������ȡ������ַ*/
	public static void  get_Ip_AddressByName(String hostName) {
		InetAddress addr;
		try {
			addr = InetAddress.getByName(hostName);
			String hostAddr=addr.getHostAddress();//��ȡ����IP��ַ
			System.out.println("����Ϊ��"+hostName+"������IP��ַΪ��"+hostAddr);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
