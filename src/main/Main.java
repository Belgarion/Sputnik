package main;

import java.util.Vector;

import network.Client;
import network.NetworkRecvThread;
import network.NetworkSendThread;
import planets.Earth;
import Entities.Player;

import com.jme3.app.SimpleApplication;
import com.jme3.input.ChaseCamera;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.AnalogListener;
import com.jme3.input.controls.InputListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.light.DirectionalLight;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;

public class Main extends SimpleApplication {

	ChaseCamera Cam;
	Player player;
	Earth earth;
	Client client;
	Global global = Global.getInstance();
	
	sharedstate.Player playerData;
	sharedstate.SharedState state;
	
	Vector<Thread> threads;
	
	boolean initialized = false;

	public static void main(String[] args) {
		Main app = new Main();
		app.start();
	}
	
	public void destroy() {
		super.destroy();
		global.quit = true;
	}
	
	@Override
	public void simpleUpdate(float tpf){
		if (!initialized) return; // don't run updates before everything is initialized
		
		player.update();
	}
	

	@Override
	public void simpleInitApp() {
		initLights();
		initObjects();
		initKeys();
		initCam();
		
		threads = new Vector<Thread>();
		try {
			client = new Client("::1", 12345);
			NetworkRecvThread recvThread = new NetworkRecvThread(state, client);
			NetworkSendThread sendThread = new NetworkSendThread(state, client);
			threads.add(recvThread);
			threads.add(sendThread);
		} catch (Exception e) {
			System.out.println("Exception caugh for client: " + e.toString());
			e.printStackTrace();
		}
		
		DeadReckoningThread drt = new DeadReckoningThread(state);
		threads.add(drt);
		initialized = true;
		
		for (Thread t : threads) {
			t.start(); // start all threads after everything is initialized
		}

	}

	private void initKeys() {
		inputManager.addMapping("down", new KeyTrigger(KeyInput.KEY_W));
		inputManager.addMapping("up", new KeyTrigger(KeyInput.KEY_S));
		inputManager.addMapping("left", new KeyTrigger(KeyInput.KEY_A));
		inputManager.addMapping("right", new KeyTrigger(KeyInput.KEY_D));

		inputManager.addListener(analogListener, "left", "right", "down",
				"up");

	}

	private void initCam() {
		flyCam.setEnabled(false);
		Cam = new ChaseCamera(cam, player.pnode, inputManager);
		Cam.setInvertVerticalAxis(true);
		Cam.setMaxDistance(4000);
		Cam.setMinDistance(2);
		Cam.setDefaultDistance(50);
		cam.setFrustumFar(5000); // increase view distance

		Cam.setDefaultHorizontalRotation((float) (-Math.PI / 2));
		Cam.setDefaultVerticalRotation((float) (Math.PI / 8));
		Cam.setMaxVerticalRotation(360);
		Cam.setMinVerticalRotation(-360);

	}

	private void initObjects() {
		//cam.setLocation(new Vector3f(0f, 0f, 50f));
		playerData = new sharedstate.Player();
		state = new sharedstate.SharedState(playerData);
		
		
		earth = new Earth(150, assetManager);
		player = new Player(playerData, assetManager);
		rootNode.attachChild(player.pnode);
		rootNode.attachChild(earth.enode);
		earth.enode.setLocalTranslation(0, 0, 400);

	}

	private void initLights() {
		DirectionalLight sun = new DirectionalLight();
		sun.setDirection(new Vector3f(0, 0, 1.0f));
		rootNode.addLight(sun);

	}

	private AnalogListener analogListener = new AnalogListener() {
		public void onAnalog(String name, float value, float tpf) {

			Quaternion rotation = cam.getRotation();

			if (name.equals("right")) {
				//player.pnode.rotate(0, 0, player.rotateSpeed); //TODO: rotations should update the playerData object.
				playerData.setPosition(playerData.getPosition().add(0, -playerData.getSpeed(), 0));
			}
			if (name.equals("left")) {
				//player.pnode.rotate(0, 0, -player.rotateSpeed);
				playerData.setPosition(playerData.getPosition().add(0, playerData.getSpeed(), 0));
			}
			if (name.equals("down")) {
				//player.pnode.rotate(player.rotateSpeed, 0, 0);

				playerData.setPosition(playerData.getPosition().add(-playerData.getSpeed(), 0, 0));
			}
			if (name.equals("up")) {
				//player.pnode.rotate(-player.rotateSpeed, 0, 0);

				playerData.setPosition(playerData.getPosition().add(playerData.getSpeed(), 0, 0));
			}
		}
	};

}
