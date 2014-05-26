package network;

public class HostGameServerThread extends Thread{
	Server server;
	/*
	 * Den h�r klassen �r bara p�b�rjad. Den ska anv�ndas f�r att f� in nya spelare i gamet n�r de tryckt join.
	 * De skriver in serverns ip och klickar p� join.
	 */
	public HostGameServerThread(){
		try {
			server = new Server();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
