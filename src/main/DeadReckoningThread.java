package main;

import sharedstate.SharedState;

public class DeadReckoningThread extends Thread {
	private SharedState state;
	Global global = Global.getInstance();
	
	public DeadReckoningThread(SharedState state) {
		super();
		this.state = state;
	}
	
	public void run() {
		while (!global.quit) {
			state.update();
			try {
				Thread.sleep(5); // update every 5ms
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
	}

}
