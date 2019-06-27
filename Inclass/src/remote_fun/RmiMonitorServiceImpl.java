package remote_fun;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * Rmi服务接口的实现 
 * 实现远程接口及远程方法（继承UnicastRemoteObject） 
 * RMI 实现UnicastRemoteObject类必须实现相关的空构造函数并抛出RemoteException 
 * @author Administrator
 *
 */
public class RmiMonitorServiceImpl extends UnicastRemoteObject implements RMservice {

	protected RmiMonitorServiceImpl() throws RemoteException {
		super();
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * 服务请求频率和监控信息 
	 */
	@Override
	public int interactive(int funindex, String param) throws RemoteException {
		// TODO Auto-generated method stub
		return funindex;
	}

}
