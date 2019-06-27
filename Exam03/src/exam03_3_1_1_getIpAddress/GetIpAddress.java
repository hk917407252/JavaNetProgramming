package exam03_3_1_1_getIpAddress;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class GetIpAddress {

	public static void main(String[] args) throws UnknownHostException{
		String hostName = "www.baidu.com";
		get_Ip_AddressByName(hostName);
	}
	/**通过主机名来获取主机地址*/
	public static void  get_Ip_AddressByName(String hostName) {
		InetAddress addr;
		try {
			addr = InetAddress.getByName(hostName);
			String hostAddr=addr.getHostAddress();//获取主机IP地址
			System.out.println("域名为："+hostName+"的主机IP地址为："+hostAddr);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
