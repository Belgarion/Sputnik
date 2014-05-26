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
	
	public void run(){
		while(!global.quit){
			server.sendToClients();
			try{
				Thread.sleep(1000);
			}
			catch(InterruptedException e){
				e.printStackTrace();
			}
		}
	}
}
