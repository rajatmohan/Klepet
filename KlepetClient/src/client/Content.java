package client;
import java.awt.*;
import javax.swing.*;

abstract class Content extends JPanel {
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Image bg;
    
    public Content(Image bg) {
    	this.bg = bg;
    }

    @Override
    public void paintComponent(Graphics g) {
        g.drawImage(bg, 0, 0, getWidth(), getHeight(), this);
    }
    
    public abstract void setGUI();
	public Chatting getChatObj(String friend) {
		return null;
	}
	public abstract void setClient(ChatClient client);
	public abstract ChatClient getClient();
	public void openNewChatInterface(String a) {
		return;
	}
}