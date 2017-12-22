package client;

import java.awt.BorderLayout;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.awt.event.*;
import java.rmi.Naming;
import java.rmi.RemoteException;
import javax.swing.*;
import javax.swing.event.*;
import java.util.*;
import interfaces.*;

public class StateAfterLogin implements State{
	
	private ChatServerIntAfterLogin server;
	private ChatClient client;
	private Start start;
	private HashMap<String, Chatting> chatList; 
	private GraphicsEnvironment ge;
    private GraphicsDevice defaultScreen;
    private Rectangle rect;
	public StateAfterLogin(Start start, ChatClient client) {
		this.start = start;
		this.client = client;
		chatList = new HashMap<String, Chatting>();
		try {
			server = (ChatServerIntAfterLogin)Naming.lookup("rmi://"+"127.0.0.1"+"/"+"ServerAfterLogin");
		} catch(Exception e) {
			e.printStackTrace();
		}
		ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        defaultScreen = ge.getDefaultScreenDevice();
        rect = defaultScreen.getDefaultConfiguration().getBounds();
		setGUI();
	}
	
	public void setGUI() {
		frame = new JFrame();
		panel = new JPanel();
		logout = new JButton("Logout");
		panel.setLayout(new BorderLayout());
		friends = new JList<String>();
		getFriendsAsList();
		friends.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				if (!e.getValueIsAdjusting()) {
					ListSelectionPerformed();
				}
			}
		});
		panel.add(friends, BorderLayout.CENTER);
		panel.add(logout, BorderLayout.NORTH);
		logout.addActionListener(new ActionListener(){
		    public void actionPerformed(ActionEvent e) { 
		    	logout();
		    }  
		});
		frame.setContentPane(panel);
	    frame.setSize(500,500);
	    frame.setLocationRelativeTo(null);
	    frame.setVisible(true); 
	}
	
	public void ListSelectionPerformed() {
		String a = friends.getSelectedValue();
		if(a != null && !chatList.containsKey(a)) {
			Chatting temp = new Chatting(this, a);
			setLocation(temp, chatList.size());
			chatList.put(a, temp);
			temp.setVisible(true);
		}
		friends.clearSelection();
	}
	
	public void setLocation(Chatting chat, int size) {
		int x = (int) rect.getMaxX() - (size+1)*chat.getWidth();
        int y = (int) rect.getMaxY() - chat.getHeight();
        if(x >= 0 && y >= 0) {
        	chat.setLocation(x,  y);
        }
        else {
        	chat.setLocationRelativeTo(null);
        }
	}
	
	public void getFriendsAsList() {
		 try {
			 listModel = new DefaultListModel<String>();
			 if(client.getName().equals("rajat")) {
				 listModel.addElement("mohan");
				 listModel.addElement("yadav");
			 }
			 else if(client.getName().equals("mohan")) {
				 listModel.addElement("rajat");
				 listModel.addElement("yadav");
			 }
			 else {
				 listModel.addElement("rajat");
				 listModel.addElement("mohan");
				 listModel.addElement("raman");
			 }
			 friends.setModel(listModel);
		 } catch(Exception e) {
			 e.printStackTrace();
		 } 
	}
	
	public void setClient(ChatClient client) {
		this.client = client;
	}
	
	public ChatClient getClient() {
		return this.client;
	}
	
	public ChatServerIntAfterLogin getServer() {
		return this.server;
	}
	
	public void removeFromChatList(String friend) {
		chatList.remove(friend);
		int size = 0;
		for (Map.Entry<String, Chatting> entry : chatList.entrySet()) {
			entry.getValue().setVisible(false);
			setLocation(entry.getValue(), size);
			entry.getValue().setVisible(true);
			size++;
		}
		
	}
	
	public Chatting getChatObj(String friend) {
		if(chatList.containsKey(friend)) {
			return chatList.get(friend);
		}
		return null;
	}
	
	public void login() {
	}
	
	public void logout() {
		try {
			if(server.logout(client)) {
				start.setState(new StateBeforeLogin(start));
				frame.setVisible(false);		
			}
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
	
	
	JFrame frame;
	JPanel panel;
	JButton logout; 
	JList<String> friends;
	DefaultListModel<String> listModel;
}
