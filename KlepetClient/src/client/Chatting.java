package client;

import javax.swing.*;
import interfaces.*;
import java.awt.*;
import java.awt.event.*;

import javax.swing.text.Style;
import javax.swing.text.StyledDocument;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import java.util.*;

class Chatting extends JFrame implements ChattingInt {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private StateAfterLogin state;
	private ChatServerIntAfterLogin server;
	private ChatClientInt client;
	private HashMap<String, String> map;
	private String friend;
	private SimpleAttributeSet keyword;
	SimpleAttributeSet key;
	public Chatting(StateAfterLogin state, String friend) {
		this.state = state;
		this.friend = friend;
		this.server = this.state.getServer();
		this.client = this.state.getClient();
		map = Emoticon.getEmoticonMap();
		keyword = new SimpleAttributeSet();
		key = new SimpleAttributeSet();
		StyleConstants.setFontSize(keyword,26);
		StyleConstants.setBold(keyword, true);
		setGUI();
	}

	public void setGUI() {
		panel = new JPanel();
		label = new JLabel("Type here:");
		friendLabel = new JLabel(friend);
		try {
			myLabel = new JLabel(client.getName()+" is chatting to ----> ");
		} catch(Exception e) {
			e.printStackTrace();
		}
		tfield = new JTextField(15);
		tpane = new JTextPane();
		tpane.setEditable(false);
		panel.setLayout(new BorderLayout());
		Panel pp = new Panel();
		pp.add(myLabel);
		pp.add(friendLabel);
		panel.add(pp, BorderLayout.NORTH);
		panel.add(tpane, BorderLayout.CENTER);
		Panel southp = new Panel();
		panel.add(southp, BorderLayout.SOUTH);
		southp.add(label, BorderLayout.SOUTH);
		southp.add(tfield, BorderLayout.SOUTH);
		tfield.addActionListener(new ActionListener(){
	      	public void actionPerformed(ActionEvent e) { 
	      		sendMessage();  
	      	}  
	    });
		setContentPane(panel);
		setSize(310,370);
		addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                state.removeFromChatList(friend);
            	e.getWindow().dispose();
            }
        });
	}

	public void sendMessage() {
		String message = tfield.getText();
		try {
			server.send(client, friend, message);
		} catch(Exception e) {
			e.printStackTrace();
		}
	    displayMessage(message, true);
		tfield.setText("");
	}
	
	public void displayMessage(String message, boolean sending) {
		int prevLength;
		try {
	     	StyledDocument doc = tpane.getStyledDocument();
	     	prevLength = doc.getLength();
			String[] words = message.split(" ");
			if(sending) {
				StyleConstants.setBackground(key, Color.GREEN);
			}
			else {
				StyleConstants.setBackground(key, Color.RED);
			}
			for (String word : words) {
				if(word.startsWith(":") && word.endsWith(":") && map.containsKey(word)) {
					Style style = doc.addStyle("StyleName", null);
		            StyleConstants.setIcon(style, new ImageIcon( getClass().getResource(map.get(word)) ) );
		            doc.insertString(doc.getLength(), "ignored text", style);
				}
				else {
					doc.insertString(doc.getLength(), word+" ", key);
				}
			}	
			if(sending) {
				StyleConstants.setAlignment(keyword, StyleConstants.ALIGN_RIGHT);
			}
			else {
				StyleConstants.setAlignment(keyword, StyleConstants.ALIGN_LEFT);
			}
			doc.setParagraphAttributes(prevLength, doc.getLength(), keyword, false);
			doc.insertString(doc.getLength(), "\n", keyword);
	    } catch(Exception exc) {
	        exc.printStackTrace();
    	}
	}
	
	JPanel panel;
	JTextPane tpane;
	JLabel label;
	JTextField tfield;
	JLabel friendLabel;
	JLabel myLabel;
}