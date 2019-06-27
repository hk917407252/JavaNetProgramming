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
	 * ��ȡ����ip��ַ
	 */
	public static void getLocalIpAdd() {
		InetAddress iaddr;
		try {
			iaddr = InetAddress.getLocalHost();//��ȡһ��������Ip��ַ����
			String hostname = iaddr.getHostName();//��ȡ����������
			String host_ip = iaddr.getHostAddress();//��ȡ��������IP��ַ
			System.out.println("����������Ϊ��" + hostname + "    ip��ַΪ: " + host_ip);
		
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	/**
	 * ��ȡ����������mac��ַ
	 */
	public static void getMACAddress() {
		String mac_s;
		InetAddress iaddr;
		try {
			iaddr = InetAddress.getLocalHost();//��ȡһ��������Ip��ַ����
			byte[] mac = NetworkInterface.getByInetAddress(iaddr).getHardwareAddress();
			StringBuffer b = new StringBuffer("");
			for(int i = 0;i<mac.length;i++) {
				b.append("-");
				String temp = Integer.toHexString(mac[i]);
				b.append(temp);
			}
			System.out.println("�����ַΪ�� " + b);			
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
}
