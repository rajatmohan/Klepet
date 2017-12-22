package client;

public interface State {
	public void setGUI();
	public Chatting getChatObj(String friend);
	public void setClient(ChatClient client);
	public ChatClient getClient();
}
