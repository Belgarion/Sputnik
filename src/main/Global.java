package main;

public class Global {
	private static Global instance = null;
	public boolean quit = false;
	
	protected Global() {
		
	}
	public static Global getInstance() {
		if (instance == null) {
			instance = new Global();
		}
		return instance;
	}
}
