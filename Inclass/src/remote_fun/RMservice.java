package remote_fun;

import java.rmi.Remote;
import java.rmi.RemoteException;
/**
 * Rmi服务接口 
 * 创建远程接口及声明远程方法 
 * 远程服务接口实现java.rmi.Remote 的接口
 * @author Administrator
 *
 */
public interface RMservice extends Remote {
	 public int interactive(int funindex ,String param)throws RemoteException;
	 /*public int emerge(int funindex ,String param)throws RemoteException;
	 public int muiti(int funindex ,String param)throws RemoteException;*/
}
