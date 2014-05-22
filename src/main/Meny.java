package main;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Hashtable;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.jme3.asset.AssetManager;

import Entities.Player;

public class Meny extends JFrame implements ActionListener, ChangeListener {

	JPanel mypanel;
	JButton hostb, joinb;
	JLabel mylabel;
	InetAddress host, client;
	ArrayList<Player> players;
	AssetManager assetmanager;

	private static final long serialVersionUID = 1L;

	public Meny(AssetManager assetmanager) {
		this.assetmanager = assetmanager;
		JFrame Mframe = new JFrame("Sputnik Meny");
		Mframe.setSize(200, 200);
		Mframe.setVisible(true);
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

		hostb = new JButton("Host");
		joinb = new JButton("Join");

		hostb.addActionListener(this);
		hostb.setPreferredSize(new Dimension(20, 20));
		joinb.addActionListener(this);
		joinb.setPreferredSize(new Dimension(20, 20));

		mainPanel.add(hostb);
		mainPanel.add(joinb);

		Mframe.add(mainPanel);

	}

	@Override
	public void actionPerformed(ActionEvent e) {

		String kp = e.getActionCommand();
		String name = "";
		switch (kp) {
		case "Host":
			System.out.println("Host Game");
			createPlayer(name);
			break;
		case "Join":
			System.out.println("Join Game");
			createPlayer(name);
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
}
