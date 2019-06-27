package remote_fun;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Map;

import javax.management.monitor.MonitorSettingException;

/**
 * rmi监控的客户端服务
 * 
 * 客户端查找远程对象
 * @author Administrator
 *
 */
public class RMClient {
	//用户缓存使用的示例
	public Map<Class,Object> serviceMap = new HashMap<Class,Object>();
	public RMservice moniterService;
	public String ip="localhost";
	public int port=8888;
	public int interactive(int funindex,String param) {
		try {
			if(moniterService==null) {
				moniterService = getMonitorService(RMservice.class);
			}
			return moniterService.interactive(funindex, param);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}
	
	
	public RMservice getMonitorService(Class clazz) {
		Object object = serviceMap.get(clazz);
		
		try {
			if(object ==null) {
			moniterService = (RMservice)Naming.lookup("rmi://"+ip+":"+port+"/monitor");
			serviceMap.put(RMservice.class, moniterService);
			}else {
				moniterService = (RMservice)serviceMap.get(clazz);
			}
			return moniterService;
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return moniterService;
	}
	
	public static void main(String[] args) {
		RMClient client = new RMClient();
		int result = client.interactive(9, "fishc.com");
		System.out.println("result:" + result);
	}
	
}
