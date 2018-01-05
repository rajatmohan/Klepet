package interfaces;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.rmi.RemoteException;
import java.util.HashMap;

public interface ChatServerIntAfterLogin extends ChatServerInt {
	public boolean logout(ChatClientInt client) throws RemoteException;
	public void send(ChatClientInt client,String username, String message)throws RemoteException ;
	public HashMap<String, ChatClientInt> getConnected() throws RemoteException ;
	public OutputStream getOutputStream(File f) throws IOException, RemoteException;
	public InputStream getInputStream(File f) throws IOException, RemoteException;
	public OutputStream getOutputStream(String friend, File f) throws IOException, RemoteException;
	public InputStream getInputStream(String friend, File f) throws IOException, RemoteException;
}
