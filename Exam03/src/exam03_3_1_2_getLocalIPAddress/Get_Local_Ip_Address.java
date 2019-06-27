package exam03_3_1_2_getLocalIPAddress;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;

public class Get_Local_Ip_Address{

	public static void main(String[] args) {
		getLocalIpAdd();
		getMACAddress();
	}
	
	
	public static void getLocalIpadd() {
		InetAddress addr;
		try {
			addr = InetAddress.getLocalHost();
			String hostname = addr.getHostName();
			String hostip = addr.getHostAddress();
			byte[] mac = NetworkInterface.getByInetAddress(addr).getHardwareAddress();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 获取本机ip地址
	 */
	public static void getLocalIpAdd() {
		InetAddress iaddr;
		try {
			iaddr = InetAddress.getLocalHost();//获取一个本机的Ip地址对象
			String hostname = iaddr.getHostName();//获取本地主机名
			String host_ip = iaddr.getHostAddress();//获取本机主机IP地址
			System.out.println("本地主机名为：" + hostname + "    ip地址为: " + host_ip);
		
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	/**
	 * 获取本地主机的mac地址
	 */
	public static void getMACAddress() {
		String mac_s;
		InetAddress iaddr;
		try {
			iaddr = InetAddress.getLocalHost();//获取一个本机的Ip地址对象
			byte[] mac = NetworkInterface.getByInetAddress(iaddr).getHardwareAddress();
			StringBuffer b = new StringBuffer("");
			for(int i = 0;i<mac.length;i++) {
				b.append("-");
				String temp = Integer.toHexString(mac[i]);
				b.append(temp);
			}
			System.out.println("物理地址为： " + b);			
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
}
