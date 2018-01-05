package client;
import javax.swing.*;
import interfaces.*;
import java.awt.*;
import java.awt.event.*;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
//import javax.swing.text.Style;
import javax.swing.text.StyledDocument;
//import javax.swing.text.SimpleAttributeSet;
//import javax.swing.text.StyleConstants;
import java.util.*;
class Chatting extends JFrame implements ChattingInt {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ContentAfterLogin content;
	private ChatServerIntAfterLogin server;
	private ChatClientInt client;
	private Emoticon emoticon;
	private String friend;
	//private SimpleAttributeSet keyword;
	//SimpleAttributeSet key;
	public Chatting(ContentAfterLogin content, String friend) {
		this.content = content;
		this.friend = friend;
		emoticon = Emoticon.getInstance();
		this.server = this.content.getServer();
		this.client = this.content.getClient();
		//keyword = new SimpleAttributeSet();
		//key = new SimpleAttributeSet();
		//StyleConstants.setFontSize(keyword,26);
		//StyleConstants.setBold(keyword, true);
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

		JButton b=new JButton(" :) ");  
	    b.setBounds(10,10,20,30);  
		tfield = new JTextField(10);
		tpane = new JTextPane();
		tpane.setPreferredSize(new Dimension(310, 350));
		jsp = new JScrollPane(tpane,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		tpane.setEditable(false);
		panel.setLayout(new BorderLayout());
		Panel pp = new Panel();
		pp.add(myLabel);
		pp.add(friendLabel);
		panel.add(pp, BorderLayout.NORTH);
		panel.add(jsp, BorderLayout.CENTER);
		Panel southp = new Panel();

		southp.setLayout(new FlowLayout());
		panel.add(southp, BorderLayout.SOUTH);
		southp.add(label, BorderLayout.SOUTH);
		southp.add(tfield);
		southp.add(b);
		
		b.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispalynewframe();
			}
		});
		
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
                content.removeFromChatList(friend);
                if(emojiFrame != null) {
                	emojiFrame.dispose();
                }
            	e.getWindow().dispose();
            }
        });
	}

	public void append(String s) {
		String message = tfield.getText();	
		message += s;
		message += ' ';
		tfield.setText(message);
		tfield.requestFocus();
	}
	 
	public void dispalynewframe() {
		if(emojiFrame != null) {
			emojiFrame.setVisible(true);
			return;
		}
		emojiFrame = new JFrame("EMOJI SELECT"); 
		emojiFrame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);     
		final JTabbedPane tab = new JTabbedPane();  
		final GridBagConstraints gbc = new GridBagConstraints();
		ArrayList<String> categoryList= emoticon.getCategoryList();
		for(String category:categoryList) {
			tab.addTab(category,null);
		}
		tab.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent changeEvent) {
		        JTabbedPane sourceTabbedPane = (JTabbedPane) changeEvent.getSource();
		        int index = sourceTabbedPane.getSelectedIndex();
		        if(sourceTabbedPane.getComponentAt(index) == null) {
		        	JPanel panel = new JPanel();
			        panel.setLayout(new GridBagLayout());
			        String category = sourceTabbedPane.getTitleAt(index);
			        int k = 0;
					ArrayList<Emoji> emojiList = emoticon.getEmojiList(category);
					for(final Emoji emoji: emojiList) {
						int x1 = k % 5;
				        int y1 = k / 5;
				        gbc.gridy = y1;
					    gbc.gridx = x1;
					    JButton b = null;
					    try {
					    	emoji.setImageIcon();
					    	b = new JButton(emoji.getImageIcon());
					    	panel.add(b,gbc);
					    	
					    	b.addActionListener(new ActionListener() {	
								public void actionPerformed(ActionEvent arg0) {
									append(emoji.getShortName());
								}		    	
					    	});	
					    	
					    	b.setToolTipText("Description : "+emoji.getDescription()+"|| ShortName: "+ emoji.getShortName());
					    	
					    } catch(Exception e) {
					    	System.out.println(emoji.getUnicode());
					    }            
				        k++;
					}
					JScrollPane sp = new JScrollPane(panel);
					sp.setWheelScrollingEnabled(true);
					sp.getVerticalScrollBar().setUnitIncrement(16);
					tab.setComponentAt(index, sp);
		        }
		    }
		});
		emojiFrame.add(tab);    
		emojiFrame.setSize(380, 300);
		emojiFrame.setVisible(true);
		tab.setSelectedIndex(1);
		//tab.setSelectedIndex(0);
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
		//int prevLength;
		int font_size = 24;
		try {
	     	StyledDocument doc = tpane.getStyledDocument();
	     	//prevLength = doc.getLength();
			String[] words = message.split(" ");
			/*if(sending) {
				StyleConstants.setBackground(key, Color.GREEN);
			}
			else {
				StyleConstants.setBackground(key, Color.RED);
			}*/
			//JPanel aa = new JPanel();

			for (String word : words) {
				JLabel jl;
				if(word.startsWith(":") && word.endsWith(":") && emoticon.getShortNameToLocationMap().containsKey(word)) {
					//Style style = doc.addStyle("StyleName", null);
		            //StyleConstants.setIcon(style, new ImageIcon( getClass().getResource(emoticon.getShortNameToLocationMap().get(word)) ) );
		            //doc.insertString(doc.getLength(), "ignored text", style);
		            ImageIcon imageIcon = new ImageIcon(getClass().getResource(emoticon.getShortNameToLocationMap().get(word))); // load the image to a imageIcon
					Image image = imageIcon.getImage(); // transform it 
					Image newimg = image.getScaledInstance(font_size+5, font_size+5,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
					imageIcon = new ImageIcon(newimg);
		            jl = new JLabel();
		            jl.setIcon(imageIcon);
				}			
				else {
					//doc.insertString(doc.getLength(), word+" ", key);
					jl = new JLabel(word+" ");
					jl.setFont(new Font("SansSerif",Font.PLAIN, font_size));
					//aa.add(jl);
				}
				if(sending) {
					jsp.setAlignmentX(Component.RIGHT_ALIGNMENT);
					jl.setBackground(new Color(80, 200, 80));
				}
				else {
					jsp.setAlignmentX(Component.LEFT_ALIGNMENT);
					jl.setBackground(new Color(200, 200, 150));
				}
				jl.setOpaque(true);
				jl.setBorder(BorderFactory.createMatteBorder(2, 0, 2, 0, Color.black));
				tpane.setCaretPosition(doc.getLength());
				tpane.insertComponent(jl);
			}	
			//tpane.setCaretPosition(doc.getLength());
			//tpane.insertComponent(aa);
			/*if(sending) {
				StyleConstants.setAlignment(keyword, StyleConstants.ALIGN_RIGHT);
			}
			else {
				StyleConstants.setAlignment(keyword, StyleConstants.ALIGN_LEFT);
			}*/
			//doc.setParagraphAttributes(prevLength, doc.getLength(), keyword, false);
			doc.insertString(doc.getLength(), "\n", null);
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
	JFrame emojiFrame;
	JScrollPane jsp;
}
