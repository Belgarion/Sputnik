package network;

import java.io.IOException;

import main.Global;

public class ServerThread extends Thread {

	private Server server;
	Global global = Global.getInstance();
	
	public ServerThread(Server s){
		super();
		this.server = s;
	}
	
	// Send updates every 0.2 seconds
	public void run(){
		while(!global.quit){
			server.sendToClients();
			try{
				Thread.sleep(200);
			}
			catch(InterruptedException e){
				e.printStackTrace();
			}
		}
	}
}
