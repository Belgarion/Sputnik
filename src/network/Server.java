package network;

import java.io.IOException;
import java.net.*;
import java.util.HashMap;
import java.util.UUID;

public class Server {
	private HashMap<UUID, ClientData> clients;
	private HashMap<UUID, String> state; // store received data in a hashmap with uuid as key and netstring as value (this makes the server independent of the data types, meaning that the server should never have to be updated --> infinite uptime)
	private byte[] recvBuffer = new byte[1024];
	private byte[] sendBuffer = new byte[1024];
	private byte[] sendBuffer2 = new byte[1024];
	DatagramSocket sock;
	
	
	
	// TODO: Handle client disconnects, remove player from hashmap, even if timeout (add client id to all objects owned by that client)
	public static void main(String args[]) throws Exception {
		Server s = new Server();
		s.run();
	}
	
	public Server() throws Exception {
		state = new HashMap<UUID, String>();
		clients = new HashMap<UUID, ClientData>();
		sock = new DatagramSocket(12345);
	}
	
	public void run() {
		ServerThread sendToClients = new ServerThread(this);
		sendToClients.start();
		
		while (true) {
			try {
				DatagramPacket recvPacket = new DatagramPacket(recvBuffer, recvBuffer.length);
				sock.receive(recvPacket);
				String data = new String(recvPacket.getData());
				InetAddress ip  = recvPacket.getAddress();
				int port = recvPacket.getPort();

				System.out.println("Received from " + ip + " , port " + port + ": " + data);
				
				String type = Utils.parseType(data);
				if (type == null) continue;
				if (type.equals("connect")) {
					UUID id = Utils.parseId(data);
					clients.put(id, new ClientData(ip, port));
					continue;
				} else if (type.equals("disconnect")) {
					UUID id = Utils.parseId(data);
					// TODO: Remove objects owned by player
					continue;
				} else if (type.equals("chat")) {
					for(ClientData client : clients.values()) {
						InetAddress ip1 = client.getIp();
						int port1 = client.getPort();

						sendBuffer2 = data.getBytes();
						DatagramPacket sendPacket2 = new DatagramPacket(sendBuffer2, sendBuffer2.length, ip, port);
						try {
							sock.send(sendPacket2);
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
					continue;
				}
				// Send ack
				UUID id = Utils.parseId(data);
				state.put(id, data);
				sendBuffer = ("type:ACK " + id + "\n").getBytes();
				DatagramPacket sendPacket = new DatagramPacket(sendBuffer, sendBuffer.length, ip, port);
				sock.send(sendPacket);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	//Skicka uppdateringar till alla klienter. G�r detta fr�n en ny tr�d.
	public void sendToClients(){
		for(ClientData client : clients.values()){
			InetAddress ip = client.getIp();
			int port = client.getPort();
			
			for (UUID id2 : state.keySet()) {
				sendBuffer2 = state.get(id2).getBytes();
				DatagramPacket sendPacket2 = new DatagramPacket(sendBuffer2, sendBuffer2.length, ip, port);
				try {
					sock.send(sendPacket2);
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
	}}
	
	

	

}
