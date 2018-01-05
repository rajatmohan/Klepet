package client;

import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

public class Start extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Content content;
	private Properties config;
	public Start() {
		InputStream in = null;
		try {
			in = getClass().getResourceAsStream("config.properties");
			config = new Properties();
			config.load(in);
		} catch (Exception e1) {;
			e1.printStackTrace();
		} finally {
	        if (in != null) {
	            try {
	                in.close();
	            } catch (Exception e) {
	                e.printStackTrace();
	            }
	        }
	    }
		setGUI();
	}
	
	public void setGUI() {
		menuBar = new JMenuBar();
        help = new JMenu("Help");
        file = new JMenu("File");
        help.setMnemonic(KeyEvent.VK_H);
        file.setMnemonic(KeyEvent.VK_F);
        menuBar.add(file);;
        menuBar.add(help);
        
        openFileFolder = new JMenuItem("Open file folder...", null);
        openFileFolder.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L, ActionEvent.CTRL_MASK));
        openFileFolder.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		openfilefolderActionPerformed();
        	}
        });
        file.add(openFileFolder);
        
        preferences = new JMenuItem("Preferences", null);
        preferences.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, ActionEvent.CTRL_MASK));
        preferences.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		preferencesActionPerformed();
        	}
        });
        file.add(preferences);
        
        quit = new JMenuItem("Quit", null);
        quit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, ActionEvent.CTRL_MASK));
        quit.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		quitActionPerformed();
        	}
        });
        file.add(quit);
        
        aboutKlepet = new JMenuItem("About Klepet", null);
        aboutKlepet.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		aboutKlepetActionPerformed();
        	}
        });
        help.add(aboutKlepet);
        setJMenuBar(menuBar);
        setSize(300, 400);
        setLocationRelativeTo(null);
        setResizable(false);
        
	}
	
	public void preferencesActionPerformed() {
		
	}
	
	public void openfilefolderActionPerformed() {
		if (Desktop.isDesktopSupported()) {
	        try {
				Desktop.getDesktop().open(new File("/home/rajat/Downloads"));
			} catch (IOException e) {
				e.printStackTrace();
			}
	    }
	}
	
	public void quitActionPerformed() {
		System.exit(0);
	}
	
	public void aboutKlepetActionPerformed() {
		
	}
	
	public void setContent(Content content) {
		this.content = content;
		setContentPane(content);
	}
	
	public Content getContent() {
		return this.content;
	}
	
	public Properties getProperty() {
		return config;
	}
	
	public static void main(String args[]) {
		Start a = new Start();
		a.setContent(new ContentBeforeLogin(a, new ImageIcon(a.getClass().getResource("/images/sun.png")).getImage()));
		a.setVisible(true);
	}
	
	JMenuBar menuBar;
	JMenu help;
	JMenu file;
	JMenuItem preferences;
	JMenuItem quit;
	JMenuItem openFileFolder;
	JMenuItem aboutKlepet;
	
}
