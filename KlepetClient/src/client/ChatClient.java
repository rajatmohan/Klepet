package client;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.rmi.*;
import java.rmi.server.UnicastRemoteObject;
import interfaces.*;

public class ChatClient extends UnicastRemoteObject implements ChatClientInt {
	
	final public static int BUF_SIZE = 1024 * 64;
	private static final long serialVersionUID = 1L;
	private String name;
	private Start ui;
	
	public ChatClient (String n) throws RemoteException {
		name = n;
	}
	
	public void tell(String friend, String message) throws RemoteException{
		ChattingInt chat = ui.getState().getChatObj(friend);
		if(chat != null) {
			chat.displayMessage(message, false);
		}
	}
	public String getName() throws RemoteException{
		return name;
	}
	
	public void setGUI(Start t){ 
		ui = t ; 
	} 
	
	public Start getGUI(){ 
		return ui;
	}

	
	public OutputStream getOutputStream(File f) throws IOException {
	    return new RMIOutputStream(new RMIOutputStreamImpl(new FileOutputStream(f)));
	}

	public InputStream getInputStream(File f) throws IOException {
	    return new RMIInputStream(new RMIInputStreamImpl(new FileInputStream(f)));
	}
	
	public void copy(InputStream in, OutputStream out) throws IOException {
	  	byte[] b = new byte[BUF_SIZE];
	    int len;
	    while ((len = in.read(b)) >= 0) {
	    	out.write(b, 0, len);
	    }
	    in.close();
	    out.close();
	}
	
	public void upload(ChatServerIntAfterLogin server, File src, File dest) throws IOException {
	    copy(new FileInputStream(src), server.getOutputStream(dest));
	}
	
	public void download(ChatServerIntAfterLogin server, File src, File dest) throws IOException {
	    copy (server.getInputStream(src), new FileOutputStream(dest));
	}
}
