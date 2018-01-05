package interfaces;

import java.io.File;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.rmi.*;

import javax.swing.JProgressBar;

public interface ChatClientInt extends Remote{	
	public void tell (String friend, String message)throws RemoteException ;
	public String getName()throws RemoteException;
	public OutputStream getOutputStream(File f) throws IOException;
	public InputStream getInputStream(File f) throws IOException;
	public void copy(JProgressBar pb, InputStream in, OutputStream out) throws IOException;
}