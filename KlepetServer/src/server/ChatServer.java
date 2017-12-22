package server;
import java.rmi.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.InputStream;
import java.rmi.server.*;
import java.util.*;
import interfaces.*;

public class ChatServer extends UnicastRemoteObject implements ChatServerIntBeforeLogin, ChatServerIntAfterLogin {
	private static final long serialVersionUID = 1L;
	final public static int BUF_SIZE = 1024 * 64;
	
	private HashMap<String, ChatClientInt> userOnlineList;
	StartServer ss;
	public ChatServer(StartServer ss) throws RemoteException {
		super();
		this.ss = ss; 
		userOnlineList = new HashMap<String, ChatClientInt>();
	}
	public boolean login(ChatClientInt a) throws RemoteException {
		ss.tx.append("\n"+a.getName()+ " got connected...");
		userOnlineList.put(a.getName(), a);
		return true;
	}
	
	public boolean logout(ChatClientInt a) throws RemoteException {
		ss.tx.append("\n"+a.getName()+ " logout..");
		userOnlineList.remove(a.getName());
		return true;
	}
	
	public void send(ChatClientInt client,String username, String message)throws RemoteException {
		if(userOnlineList.containsKey(username)) {
			userOnlineList.get(username).tell(client.getName(), message);
		}
	}
	
	public OutputStream getOutputStream(File f) throws IOException {
	    return new RMIOutputStream(new RMIOutputStreamImpl(new FileOutputStream(f)));
	}

	public InputStream getInputStream(File f) throws IOException {
	    return new RMIInputStream(new RMIInputStreamImpl(new FileInputStream(f)));
	}
	
	public HashMap<String, ChatClientInt> getConnected() throws RemoteException {
		return userOnlineList;
	}
	
	public void copy(InputStream in, OutputStream out) 
	          throws IOException {
	  	
	  	byte[] b = new byte[BUF_SIZE];
	      int len;
	      while ((len = in.read(b)) >= 0) {
	      	out.write(b, 0, len);
	      }
	      in.close();
	      out.close();
	  }
	  
	  public void upload(ChatClientInt server,  File src, File dest) throws IOException {
	      copy(new FileInputStream(src), server.getOutputStream(dest));
	  }
	
	  public void download(ChatClientInt server, File src, File dest) throws IOException {
	      copy (server.getInputStream(src), new FileOutputStream(dest));
	  }
}
