package client;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.Naming;
import java.rmi.RemoteException;
import interfaces.*;

public class StateBeforeLogin implements State {
	
	private ChatServerIntBeforeLogin server;
	private ChatClient client;
	private Start start;
	
	public StateBeforeLogin(Start start) {
		this.start = start;
		try {
			server = (ChatServerIntBeforeLogin)Naming.lookup("rmi://127.0.0.1/ServerBeforeLogin");
		} catch(Exception e) {
			e.printStackTrace();
		}
		setGUI();
	}
	
	
	public void setGUI() {
		frame = new JFrame();
		panel = new JPanel();
		
 		username = new JLabel("Username");
 		login = new JButton("login");
		usernameField = new JTextField(10);
		panel.add(username);
		panel.add(usernameField);
		panel.add(login);
		login.addActionListener(new ActionListener(){
		    public void actionPerformed(ActionEvent e) { 
		    	login();
		    }  
		});
		frame.setContentPane(panel);
	    frame.setSize(200,70);
	    frame.setLocationRelativeTo(null);
	    frame.setVisible(true);  
	}
	
	public void setClient(ChatClient client) {
		this.client = client;
	}
	
	public ChatClient getClient() {
		return this.client;
	}
	
	public void login() {
		setChatClient();
		try {
			if(server.login(client)) {
				frame.setVisible(false);	
				start.setState(new StateAfterLogin(start, client));
			}
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
	
	public void setChatClient() {
		try {
			setClient(new ChatClient(usernameField.getText()));
			client.setGUI(start);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void logout() {
		
	}
	
	public Chatting getChatObj(String friend) {
		return null;
	}
	
	
	JFrame frame;
	JPanel panel;
	JLabel username;
	JTextField usernameField;
	JButton login;
}
