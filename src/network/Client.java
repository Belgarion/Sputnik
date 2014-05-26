	package network;

import java.net.*;
import java.util.UUID;
import java.util.Vector;
import java.util.concurrent.CopyOnWriteArrayList;

import sharedstate.GameObject;

public class Client {
	private DatagramSocket sock;
	private InetAddress ip;
	private int port;
	private double lastSentUpdate;
	
	private byte[] recvBuffer = new byte[1024];
	
	private boolean connected = false;
	
	public Client(String hostname, int port) throws Exception {
		sock = new DatagramSocket();
		ip = InetAddress.getByName(hostname);
		this.port = port;
		System.out.println("Hostname: " + hostname + " resolved to " + ip.toString());
		sock.setSoTimeout(1000); // 1 second timeout, makes it possible for recvthread to exit
	}
	
	public void sendState(sharedstate.SharedState state) throws Exception {
		System.out.println("Sending state");
		if (!connected) {
			byte[] sendBuffer = ("type:connect\nid:" + state.getMyId() + "\n").getBytes();
			DatagramPacket sendPacket = new DatagramPacket(sendBuffer, sendBuffer.length, ip, port);
			sock.send(sendPacket);
			connected = true;
		}
		CopyOnWriteArrayList<GameObject> objs = state.getMyObjects();
		for (GameObject obj : objs) {
			//Skicka endast objekt som uppdaterats sedan sist.
			if(obj.getLastTimeStamp() > lastSentUpdate){
				byte[] sendBuffer = obj.toNetString().getBytes();//G�r om all info om objektet till en str�ng.
				DatagramPacket sendPacket = new DatagramPacket(sendBuffer, sendBuffer.length, ip, port);
				sock.send(sendPacket);
			}
		}
	}
	
	public void recvState(sharedstate.SharedState state) throws Exception {
		DatagramPacket recvPacket = new DatagramPacket(recvBuffer, recvBuffer.length);
		sock.receive(recvPacket);
		String data = new String(recvPacket.getData());
		System.out.println("Received data: " + data);
		
		String type = Utils.parseType(data);
		if (type == null) return;
		if (type.equals("chat")) {
			state.chatMessage = Utils.parseMsg(data);
		} else if (type.equals("Player")) {
			sharedstate.Player p = new sharedstate.Player();
			p.fromNetString(data);
			addOrUpdate(state, p, data);
		} else if(type.equals("BeamD")){
			sharedstate.BeamD b = new sharedstate.BeamD();
			b.fromNetString(data);
			addOrUpdate(state, b, data);
		}
	}
	
	public void addOrUpdate(sharedstate.SharedState state, GameObject g, String data) {
		UUID id = g.getId();

		boolean found = false;
		for (GameObject g2 : state.getObjects()) {
			if (id.equals(g2.getId())) {
				found = true;
				if (g.getLastTimeStamp() <= g2.getLastTimeStamp()) {
					break; // old data dont use
				}
				g2.fromNetString(data);
				break;
			}
		}
		if (!found) state.getObjects().add(g);
	}
	
	public void close() {
		sock.close();
	}
	
	public void sendChat(String message) {
		try {
		byte[] sendBuffer = ("type:chat\nmsg:" + message + "\n").getBytes();
		DatagramPacket sendPacket = new DatagramPacket(sendBuffer, sendBuffer.length, ip, port);
		sock.send(sendPacket);
		} catch (Exception e) {
			
		}
	}
}
