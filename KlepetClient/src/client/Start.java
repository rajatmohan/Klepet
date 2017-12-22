package client;


public class Start {
	private State state;
	public Start() {
		
	}
	
	public void setState(State state) {
		this.state = state;
	}
	
	public State getState() {
		return this.state;
	}
	
	public static void main(String args[]) {
		Start a = new Start();
		a.setState(new StateBeforeLogin(a));
	}
}
