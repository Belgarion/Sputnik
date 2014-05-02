package network;

import java.net.*;
import java.util.HashMap;
import java.util.UUID;

public class Server {
	private HashMap<UUID, String> state; // store received data in a hashmap with uuid as key and netstring as value (this makes the server independent of the data types, meaning that the server should never have to be updated --> infinite uptime)
	private byte[] recvBuffer = new byte[1024];
	private byte[] sendBuffer = new byte[1024];
	DatagramSocket sock;
	
	
	// TODO: Handle client disconnects, remove player from hashmap, even if timeout (add client id to all objects owned by that client)
	public static void main(String args[]) throws Exception {
		Server s = new Server();
		s.run();
	}
	
	public Server() throws Exception {
		state = new HashMap<UUID, String>();
		sock = new DatagramSocket(12345);
	}
	
	public void run() {
		while (true) {
			try {
				DatagramPacket recvPacket = new DatagramPacket(recvBuffer, recvBuffer.length);
				sock.receive(recvPacket);
				String data = new String(recvPacket.getData());
				InetAddress ip  = recvPacket.getAddress();
				int port = recvPacket.getPort();

				System.out.println("Received from " + ip + " , port " + port + ": " + data);
				
				// Send ack
				UUID id = parseId(data);
				state.put(id, data);
				sendBuffer = ("ACK " + id + "\n").getBytes();
				DatagramPacket sendPacket = new DatagramPacket(sendBuffer, sendBuffer.length, ip, port);
				sock.send(sendPacket);
				
				// Send all data in hashmap to client
				// TODO: Keep list of all clients, send periodically (every 5 seconds or so) and when objects change.
				// TODO: Move sending data to clients to thread?
				for (UUID id2 : state.keySet()) {
					sendBuffer = state.get(id2).getBytes();
					sendPacket = new DatagramPacket(sendBuffer, sendBuffer.length, ip, port);
					sock.send(sendPacket);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public UUID parseId(String data) {
		String[] lines = data.split("\n");
		for (String line : lines) {
			String[] parts = line.split(":", 2);
			System.out.println("Line: " + line);
			System.out.println("parts.length: " + parts.length);
			System.out.println("Key: " + parts[0] + " value: " + parts[1] + "\n");
			if (parts[0].equals("id")) {
				return UUID.fromString(parts[1]);
			}
		}
		return null;
	}
}
