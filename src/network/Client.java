package network;

import java.net.*;
import java.util.Vector;

import sharedstate.GameObject;

public class Client {
	private DatagramSocket sock;
	private InetAddress ip;
	private int port;
	
	private byte[] sendBuffer = new byte[1024];
	private byte[] recvBuffer = new byte[1024];
	
	public Client(String hostname, int port) throws Exception {
		sock = new DatagramSocket();
		ip = InetAddress.getByName(hostname);
		this.port = port;
		System.out.println("Hostname: " + hostname + " resolved to " + ip.toString());
		sock.setSoTimeout(1000); // 1 second timeout, makes it possible for recvthread to exit
	}
	
	public void sendState(sharedstate.SharedState state) throws Exception {
		Vector<GameObject> objs = state.getMyObjects();
		for (GameObject obj : objs) {
			sendBuffer = obj.toNetString().getBytes();
			DatagramPacket sendPacket = new DatagramPacket(sendBuffer, sendBuffer.length, ip, port);
			sock.send(sendPacket);
		}
	}
	
	public void recvState(sharedstate.SharedState state) throws Exception {
		DatagramPacket recvPacket = new DatagramPacket(recvBuffer, recvBuffer.length);
		sock.receive(recvPacket);
		String data = new String(recvPacket.getData());
		System.out.println("Received data: " + data);
		
		// TODO: Update state
	}
	
	public void close() {
		sock.close();
	}
}
