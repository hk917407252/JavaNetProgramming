package remote_fun;

import java.net.MalformedURLException;
import java.rmi.AlreadyBoundException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

/**
 * rmiԶ�̼�صķ����
 * ����rmiע����񣬲�ע��Զ�̶���
 * @param args
 */
public class RMServer {
	
	public String host = "localhost";
	public int port = 8888;
	
	
	/**
	 * ��ʼ��Զ�̷���ķ���
	 * 
	 */
	public void init() {
		try {
			//ע�᱾�ض˿�
			LocateRegistry.createRegistry(port);
			RMservice monitor = new RmiMonitorServiceImpl();
			
			//rmi�󶨱���Ŀ¼����������
			Naming.bind("//"+host+":"+port+"/monitor",monitor);
		
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (AlreadyBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
	/**
	 * ������
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		RMServer rmi = new RMServer();
		System.out.println("rmi�����ʼ����");
		rmi.init();
	}

}
