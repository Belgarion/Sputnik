package network;

public class HostGameServerThread extends Thread{
	Server server;
	/*
	 * Den här klassen är bara påbörjad. Den ska användas för att få in nya spelare i gamet när de tryckt join.
	 * De skriver in serverns ip och klickar på join.
	 */
	public HostGameServerThread(){
		try {
			server = new Server();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
