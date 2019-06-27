package remote_fun;

import java.net.MalformedURLException;
import java.rmi.AlreadyBoundException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

/**
 * rmi远程监控的服务端
 * 启动rmi注册服务，并注册远程对象
 * @param args
 */
public class RMServer {
	
	public String host = "localhost";
	public int port = 8888;
	
	
	/**
	 * 初始化远程服务的方法
	 * 
	 */
	public void init() {
		try {
			//注册本地端口
			LocateRegistry.createRegistry(port);
			RMservice monitor = new RmiMonitorServiceImpl();
			
			//rmi绑定本地目录和命名服务
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
	 * 主函数
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		RMServer rmi = new RMServer();
		System.out.println("rmi服务初始化：");
		rmi.init();
	}

}
