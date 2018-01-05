package interfaces;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.rmi.*;

public interface ChatClientInt extends Remote{	
	public void tell (String friend, String message)throws RemoteException ;
	public String getName()throws RemoteException;
	public OutputStream getOutputStream(File f) throws IOException;
	public InputStream getInputStream(File f) throws IOException;
	public void copy(InputStream in, OutputStream out) throws IOException;
}