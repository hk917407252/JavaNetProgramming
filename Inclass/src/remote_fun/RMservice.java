package remote_fun;

import java.rmi.Remote;
import java.rmi.RemoteException;
/**
 * Rmi����ӿ� 
 * ����Զ�̽ӿڼ�����Զ�̷��� 
 * Զ�̷���ӿ�ʵ��java.rmi.Remote �Ľӿ�
 * @author Administrator
 *
 */
public interface RMservice extends Remote {
	 public int interactive(int funindex ,String param)throws RemoteException;
	 /*public int emerge(int funindex ,String param)throws RemoteException;
	 public int muiti(int funindex ,String param)throws RemoteException;*/
}
