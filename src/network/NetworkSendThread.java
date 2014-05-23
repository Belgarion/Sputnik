package network;

import java.lang.Thread;

import main.Global;
import sharedstate.SharedState;

public class NetworkSendThread extends Thread {
	private SharedState state;
	private Client client;

	Global global = Global.getInstance();
	
	public NetworkSendThread(SharedState state, Client client) {
		super();
		this.state = state;
		this.client = client;
	}

	public void run() {
		while (!global.quit) {
			try {
				client.sendState(state);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			/*try {
				Thread.sleep(10); // TODO: should send when needed instead of sending everything every second
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}*/
		}
	}
}
