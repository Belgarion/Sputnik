package network;

import java.net.*;

public class Server {
	private byte[] recvBuffer = new byte[1024];
	private byte[] sendBuffer = new byte[1024];
	DatagramSocket sock;
	
	public static void main(String args[]) throws Exception {
		Server s = new Server();
		s.run();
	}
	
	public Server() throws Exception {
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
				// TODO: parse id and send that back in ack
				sendBuffer = "ACK\n".getBytes();
				DatagramPacket sendPacket = new DatagramPacket(sendBuffer, sendBuffer.length, ip, port);
				sock.send(sendPacket);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
