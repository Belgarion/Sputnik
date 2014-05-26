package main;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Hashtable;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.jme3.asset.AssetManager;

import Entities.Player;

public class Meny extends JFrame implements ActionListener, ChangeListener {
	
	// TODO: join ska öppna textfält där man skriver in ip på servern man ska joina
	// Host ska starta servern och sätta ip man ska joina till localhost.

	JPanel mypanel;
	JButton hostb, joinb;
	JLabel mylabel;
	InetAddress host, client;
	ArrayList<Player> players;
	JFrame Mframe;
	String IP = "";

	private static final long serialVersionUID = 1L;

	public Meny() {
		Mframe = new JFrame("Sputnik Meny");
		Mframe.setSize(200, 200);
		Mframe.setVisible(true);
		JPanel mypanel = new JPanel();
		mypanel.setLayout(new BoxLayout(mypanel, BoxLayout.Y_AXIS));

		hostb = new JButton("Host");
		joinb = new JButton("Join");

		hostb.addActionListener(this);
		hostb.setPreferredSize(new Dimension(20, 20));
		joinb.addActionListener(this);
		joinb.setPreferredSize(new Dimension(20, 20));

		mypanel.add(hostb);
		mypanel.add(joinb);

		Mframe.add(mypanel);

	}

	@Override
	public void actionPerformed(ActionEvent e) {

		String kp = e.getActionCommand();
		switch (kp) {
		case "Host":
			System.out.println("Host Game");
			InetAddress address = null;
			try {
				address = InetAddress.getLocalHost();
			} catch (UnknownHostException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} 
		    IP = "::1";
			break;
		case "Join":
			JFrame frame = new JFrame("IP input");
		    IP = JOptionPane.showInputDialog(frame, "Enter IP");
			System.out.println("Join Game");
			break;
		}
	}

	@Override
	public void stateChanged(ChangeEvent e) {

	}
	public void createPlayer(String name) {
		
		//players.add(new Player(new sharedstate.Player(name), assetmanager, name));
		//Main.updatePlayers(players);
	}
	
	public String getIP(){
		return IP;
	}
	
	public boolean started(){
		if(IP == ""){
			return false;
		}else{
			return true;
		}
	}
	
}
