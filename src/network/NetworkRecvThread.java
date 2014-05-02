package network;

import java.net.SocketTimeoutException;

import main.Global;
import sharedstate.SharedState;

public class NetworkRecvThread extends Thread {
	private SharedState state;
	private Client client;
	Global global = Global.getInstance();
	
	public NetworkRecvThread(SharedState state, Client client) {
		super();
		this.state = state;
		this.client = client;
	}
	
	public void run() {
		while (!global.quit) {
			try {
				client.recvState(state);
			} catch (SocketTimeoutException e) {
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
		}
	}
}
