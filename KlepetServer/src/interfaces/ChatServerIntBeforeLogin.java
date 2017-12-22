package interfaces;
import java.rmi.RemoteException;

public interface ChatServerIntBeforeLogin extends ChatServerInt {
	public boolean login (ChatClientInt a) throws RemoteException ;
}
