package client;

import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.rmi.Naming;
import java.rmi.RemoteException;

import interfaces.*;
public class ContentBeforeLogin extends Content {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ChatServerIntBeforeLogin server;
	private ChatClient client;
	private Start start;
	
	public ContentBeforeLogin(Start start, Image img) {
		super(img);
		this.start = start;
		String serverIp = start.getProperty().getProperty("serverIp");
		if(serverIp == null) {
			serverIp = "127.0.0.1";
		}
		try {
			server = (ChatServerIntBeforeLogin)Naming.lookup("rmi://"+serverIp+"/ServerBeforeLogin");
		} catch(Exception e) {
			e.printStackTrace();
		}
		setGUI();
	}
	
	
	public void setGUI() {
        panel = new JPanel();
        username = new JLabel("Username");
        username.setForeground(Color.red);
        login = new JButton("login");
        login.setForeground(Color.red);
        usernameField = new JTextField(10);
        usernameField.setForeground(Color.red);
        panel.add(username);
        panel.add(usernameField);
        panel.add(login);
        panel.setOpaque(false);
        login.setOpaque(false);
		login.setContentAreaFilled(false);
		usernameField.setOpaque(false);
        super.add(panel, BorderLayout.CENTER);
        
        login.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) { 
                login();
            }  
        });
        
        
        start.getRootPane().setDefaultButton(login);
        
        start.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
            	e.getWindow().dispose();
            }
        });
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
				start.setVisible(false);	
				start.setContent(new ContentAfterLogin(start, client , new ImageIcon(start.getClass().getResource("/images/sun.png")).getImage()));
				start.setVisible(true);
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
	
	
	JPanel panel;
	JLabel username;
	JTextField usernameField;
	JButton login;
}
