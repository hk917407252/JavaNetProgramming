package remote_fun;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * Rmi����ӿڵ�ʵ�� 
 * ʵ��Զ�̽ӿڼ�Զ�̷������̳�UnicastRemoteObject�� 
 * RMI ʵ��UnicastRemoteObject�����ʵ����صĿչ��캯�����׳�RemoteException 
 * @author Administrator
 *
 */
public class RmiMonitorServiceImpl extends UnicastRemoteObject implements RMservice {

	protected RmiMonitorServiceImpl() throws RemoteException {
		super();
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * ��������Ƶ�ʺͼ����Ϣ 
	 */
	@Override
	public int interactive(int funindex, String param) throws RemoteException {
		// TODO Auto-generated method stub
		return funindex;
	}

}
