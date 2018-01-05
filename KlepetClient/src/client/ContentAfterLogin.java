package client;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.*;
import java.rmi.Naming;
import java.rmi.RemoteException;
import javax.swing.*;
import javax.swing.event.*;
import java.util.*;
import interfaces.*;
public class ContentAfterLogin extends Content {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ChatServerIntAfterLogin server;
	private ChatClient client;
	private Start start;
	private HashMap<String, Chatting> chatList; 
	private GraphicsEnvironment ge;
    private GraphicsDevice defaultScreen;
    private Rectangle rect;
    
	public ContentAfterLogin(Start start, ChatClient client, Image img) {
		super(img);
		this.start = start;
		this.client = client;
		chatList = new HashMap<String, Chatting>();
		String serverIp = start.getProperty().getProperty("serverIp");
		if(serverIp == null) {
			serverIp = "127.0.0.1";
		}
		try {
			server = (ChatServerIntAfterLogin)Naming.lookup("rmi://"+serverIp+"/"+"ServerAfterLogin");
		} catch(Exception e) {
			e.printStackTrace();
		}
		ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        defaultScreen = ge.getDefaultScreenDevice();
        rect = defaultScreen.getDefaultConfiguration().getBounds();
		setGUI();
	}
	
	public void setGUI() {
		logout = new JButton("Logout");
		addFriend = new JButton("Add friend");
		friends = new JList<String>();
		getFriendsAsList();
		sp = new JScrollPane(friends);
		panel = new JPanel();
		topPanel = new JPanel();
		panel.setLayout(new BorderLayout());
		panel.setSize(start.getWidth(), start.getHeight());
		JPanel topLeft = new JPanel();
		JPanel topRight = new JPanel();
		topLeft.setLayout(new FlowLayout(FlowLayout.LEFT));
		topLeft.setLayout(new FlowLayout(FlowLayout.RIGHT));
		topLeft.add(addFriend);
		topRight.add(logout);
		topPanel.add(topLeft);
		topPanel.add(topRight);
		panel.add(sp, BorderLayout.CENTER);
		panel.add(topPanel, BorderLayout.NORTH);
		panel.setOpaque(false);
		topPanel.setOpaque(false);
		topLeft.setOpaque(false);
		topRight.setOpaque(false);
		friends.setCellRenderer(new DefaultListCellRenderer() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
	        public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
	            super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
	            setForeground(Color.WHITE);
	            setOpaque(isSelected);
	            return this;
	        }
		});
		
        sp.setOpaque(false);
        sp.getViewport().setOpaque(false);
		friends.setOpaque(false);
		addFriend.setOpaque(false);
		addFriend.setContentAreaFilled(false);
		logout.setOpaque(false);
		logout.setContentAreaFilled(false);
		super.add(panel, BorderLayout.CENTER);
		
		friends.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				if (!e.getValueIsAdjusting()) {
					ListSelectionPerformed();
				}
			}
		});
		
		logout.addActionListener(new ActionListener(){
		    public void actionPerformed(ActionEvent e) { 
		    	logout();
		    }  
		}); 
		
		addFriend.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				addFriendActionPerformed();
			}
			
		});
		
		start.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
            	logout();
            	if(addFriendFrame != null) {
            		addFriendFrame.dispose();
            	}
            	e.getWindow().dispose();
            }
        });
		
	}
	
	public void addFriendActionPerformed() {
		if(addFriendFrame != null) {
			addFriendFrame.setVisible(true);
			return;
		}
		addFriendFrame = new JFrame();
		addFriendFrame.setTitle("Add friend");
		
		JPanel jp = new JPanel();
		
		final JTextField jtf = new JTextField(15);
		JButton addF = new JButton("Add Friend");
		
		jp.add(jtf);
		jp.add(addF);
		
		addF.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				String newFriend = jtf.getText();
				if(newFriend.length() >= 3) {
					((DefaultListModel<String>)friends.getModel()).addElement(newFriend);
					addFriendFrame.dispose();
				}
			}
			
		});
		
		addFriendFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
            	e.getWindow().dispose();
            }
        });
		
		addFriendFrame.setContentPane(jp);
		addFriendFrame.pack();
		addFriendFrame.setLocationRelativeTo(addFriend);
		addFriendFrame.setVisible(true);
	}
	
	public void ListSelectionPerformed() {
		String a = friends.getSelectedValue();
		if(a != null && !chatList.containsKey(a)) {
			openNewChatInterface(a);
		}
		friends.clearSelection();
	}
	
	public void openNewChatInterface(String a) {
		Chatting temp = new Chatting(this, a);
		setLocation(temp, chatList.size());
		chatList.put(a, temp);
		temp.setVisible(true);
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
			 }
			 else if(client.getName().equals("mohan")) {
				 listModel.addElement("rajat");
			 };
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
	
	public void logout() {
		try {
			if(server.logout(client)) {
				start.setVisible(false);
				start.setContent(new ContentBeforeLogin(start, new ImageIcon(getClass().getResource("/images/sun.png")).getImage()));	
				start.setVisible(true);
			}
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		for (Map.Entry<String, Chatting> entry : chatList.entrySet()) {
			entry.getValue().dispose();
		}
	}
	JPanel panel;
	JPanel topPanel;
	JButton logout; 
	JButton addFriend;
	JScrollPane sp;
	JList<String> friends;
	DefaultListModel<String> listModel;
	JFrame addFriendFrame;
}
