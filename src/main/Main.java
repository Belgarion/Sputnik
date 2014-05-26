package main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;

import javax.swing.text.html.HTMLDocument.HTMLReader.IsindexAction;

import network.Client;
import network.NetworkRecvThread;
import network.NetworkSendThread;
import planets.Earth;
import planets.Moon;
import planets.Planet;
import sharedstate.BeamD;
import sharedstate.WarD;
import weapons.Beam;
import Entities.Player;

import com.jme3.app.SimpleApplication;
import com.jme3.asset.plugins.FileLocator;
import com.jme3.asset.plugins.ZipLocator;
import com.jme3.audio.AudioNode;
import com.jme3.collision.CollisionResults;
import com.jme3.font.BitmapText;
import com.jme3.input.ChaseCamera;
import com.jme3.input.KeyInput;
import com.jme3.input.MouseInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.AnalogListener;
import com.jme3.input.controls.InputListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.input.controls.MouseButtonTrigger;
import com.jme3.light.DirectionalLight;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Quaternion;
import com.jme3.math.Ray;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.ui.Picture;
import com.jme3.util.SkyFactory;

public class Main extends SimpleApplication {
	ConcurrentHashMap<sharedstate.GameObject, GraphicsObject> objs = new ConcurrentHashMap<sharedstate.GameObject, GraphicsObject>();

	ChaseCamera Cam;
	Player player;
	Earth earth;
	AudioNode audio_beam;
	Moon moon;
	DirectionalLight sun;
	float sunrot = 0;
	Client client;
	Global global = Global.getInstance();
	static Meny meny;
	War war;
	WarD ward;
	boolean warzone = false;
	int counter = 0;
	static String IP;
	static int n = 0;

	BitmapText GREEN;
	BitmapText RED;
	BitmapText chat;

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

	

	private void warChecker() {
		war.checker(playerData);
		if (war.warzone) {
			ward.setAttackers(player.team);
			ward.update();
			//war.checker(playerData);
		}

	}

	@Override
	public void simpleUpdate(float tpf) {
		if (!initialized)
			return; // don't run updates before everything is initialized

		// Check that all GameObjects have GrahpicsObjects
		for (sharedstate.GameObject obj : state.getObjects()) {
			if (!objs.containsKey(obj)) {
				if (obj instanceof sharedstate.Player) {
					Entities.Player p = new Player((sharedstate.Player)obj, assetManager);
					rootNode.attachChild(p.pnode);
					objs.put(obj,  p);
				} else if (obj instanceof sharedstate.BeamD){
					weapons.Beam b = new weapons.Beam((sharedstate.BeamD)obj, assetManager);
					rootNode.attachChild(b.bnode);
					objs.put(obj,  b);

				}
			}
		}
		
		for (GraphicsObject go : objs.values()) {
			go.update();
		}

		earth.update();
		moon.update();

		playerChecker();
		warChecker();
		String s1 = Long.toString(ward.counter1);
		String s2 = Long.toString(ward.counter2);
		
		GREEN.setText("Green: " + s1);
		RED.setText("Red: " + s2);
		chat.setText(state.chatMessage);
	}

	@Override
	public void simpleInitApp() {
		/*meny = new Meny();
		while(!meny.started()){
			// g�r inget f�rens man har valt join eller host
			System.out.println("lol");
		}*/
		//IP = meny.getIP();
		initLights();
		initObjects();
		initKeys();
		initCam();
		initAudio();
		
		threads = new Vector<Thread>();
		try {
			//System.out.println("bajs");
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
	
	private void initAudio() {
		assetManager.registerLocator("Assets", FileLocator.class);
		audio_beam = new AudioNode(assetManager, "LASER1.WAV", false);
		audio_beam.setPositional(false);
		audio_beam.setLooping(false);
		audio_beam.setVolume(1);
		rootNode.attachChild(audio_beam);
	}

	private void initKeys() {
		inputManager.addMapping("down", new KeyTrigger(KeyInput.KEY_W));
		inputManager.addMapping("up", new KeyTrigger(KeyInput.KEY_S));
		inputManager.addMapping("left", new KeyTrigger(KeyInput.KEY_A));
		inputManager.addMapping("right", new KeyTrigger(KeyInput.KEY_D));

		inputManager.addMapping("one", new KeyTrigger(KeyInput.KEY_1));
		inputManager.addMapping("two", new KeyTrigger(KeyInput.KEY_2));
		inputManager.addMapping("three", new KeyTrigger(KeyInput.KEY_3));
		inputManager.addMapping("four", new KeyTrigger(KeyInput.KEY_4));
		inputManager.addMapping("shootB", new MouseButtonTrigger(
				MouseInput.BUTTON_LEFT));

		inputManager.addListener(analogListener, "left", "right", "down", "up",
				"one", "two", "three", "four", "pulse");

		ActionListener acl = new ActionListener() {

			public void onAction(String name, boolean keyPressed, float tpf) {
				if (name.equals("shootB") && keyPressed) {
					Vector2f click2d = inputManager.getCursorPosition();
					Vector3f click3d = cam.getWorldCoordinates(
							new Vector2f(click2d.x, click2d.y), 0f).clone();
					Vector3f dir = cam
							.getWorldCoordinates(
									new Vector2f(click2d.x, click2d.y), 1f)
							.subtractLocal(click3d).normalizeLocal();
					sharedstate.BeamD beamdata = new sharedstate.BeamD(playerData, dir);
					state.getMyObjects().add(beamdata);
					state.getObjects().add(beamdata);
					audio_beam.playInstance();
				}
			}
		};

		inputManager.addListener(acl, "shootB");
	}

	private void initCam() {
		flyCam.setEnabled(false);
		Cam = new ChaseCamera(cam, player.pnode, inputManager);
		Cam.setInvertVerticalAxis(true);
		Cam.setMaxDistance(4000);
		Cam.setMinDistance(2);
		Cam.setDefaultDistance(50);
		//Cam.setSmoothMotion(true);
		
		Cam.setDefaultHorizontalRotation((float) (-Math.PI / 2));
		Cam.setDefaultVerticalRotation((float) (Math.PI / 8));
		Cam.setMaxVerticalRotation(360);
		Cam.setMinVerticalRotation(-360);
		Cam.setToggleRotationTrigger(new MouseButtonTrigger(
				MouseInput.BUTTON_RIGHT));

	}

	private void initObjects() {

		//cam.setLocation(new Vector3f(0f, 0f, 50f));
		playerData = new sharedstate.Player();
		playerData.setName("namn");
		state = new sharedstate.SharedState(playerData);
		
		
		sharedstate.Planet earthdata = new sharedstate.Planet(150);
		earth = new Earth(earthdata, assetManager);
		state.getObjects().add(earthdata);
		objs.put(earthdata, earth);
		sharedstate.Planet moondata = new sharedstate.Planet(50);
		moon = new Moon(moondata, assetManager, earth);
		state.getObjects().add(moondata);
		objs.put(moondata, moon);

		player = new Player(playerData, assetManager);
		objs.put(playerData, player);
		rootNode.attachChild(player.pnode);
		rootNode.attachChild(earth.enode);
		earth.enode.setLocalTranslation(0, 0, 400);

		war = new War(earth, assetManager);
		rootNode.attachChild(war.war);
		war.war.setLocalTranslation(earth.enode.getLocalTranslation());
		ward = new WarD();

		//guiNode.addLight(sun);
		setDisplayFps(false); 
		setDisplayStatView(false);

		String s1 = Long.toString(ward.counter1);
		String s2 = Long.toString(ward.counter2);
		
		GREEN = new BitmapText(guiFont, false);
		GREEN.setSize(guiFont.getCharSet().getRenderedSize()); 
		GREEN.setColor(ColorRGBA.Green); 
		GREEN.setText("Green: " + s1); 
		GREEN.setLocalTranslation(50, 100, 0); 

		RED = new BitmapText(guiFont, false);
		RED.setSize(guiFont.getCharSet().getRenderedSize()); 
		RED.setColor(ColorRGBA.Red);
		RED.setText("Red: " + s2);
		RED.setLocalTranslation(50, 50, 0); 
		
		chat = new BitmapText(guiFont, false);
		chat.setSize(guiFont.getCharSet().getRenderedSize());
		chat.setColor(ColorRGBA.Yellow);
		chat.setText("Chat");
		chat.setLocalTranslation(50, 150, 0);
		
		guiNode.attachChild(RED);
		guiNode.attachChild(GREEN);
		guiNode.attachChild(chat);

	}

	private void initLights() {

		sun = new DirectionalLight();
		sun.setDirection(new Vector3f(0, 0, 1.0f));
		DirectionalLight sun2 = new DirectionalLight();
		sun2.setDirection(new Vector3f(0, 0, -1.0f));
		DirectionalLight sun3 = new DirectionalLight();
		sun3.setDirection(new Vector3f(0, 1, -1.0f));
		DirectionalLight sun4 = new DirectionalLight();
		sun4.setDirection(new Vector3f(1, 0, 1.0f));
		rootNode.addLight(sun);
		rootNode.addLight(sun2);
		// rootNode.addLight(sun3);
		// rootNode.addLight(sun4);

	}

	private AnalogListener analogListener = new AnalogListener() {
		public void onAnalog(String name, float value, float tpf) {

			if (name.equals("right")) {
				player.setRotation(0, 0, player.rotateSpeed);
			}
			if (name.equals("left")) {
				player.setRotation(0, 0, -player.rotateSpeed);
			}
			if (name.equals("down")) {
				player.setRotation(player.rotateSpeed, 0, 0);
			}
			if (name.equals("up")) {
				player.setRotation(-player.rotateSpeed, 0, 0);
			}
			if (name.equals("one")) {
				playerData.setSpeed(10);
			}
			if (name.equals("two")) {
				playerData.setSpeed(20);
			}
			if (name.equals("three")) {
				playerData.setSpeed(30);
			}
			if (name.equals("four")) {
				playerData.setSpeed(40);
			}
		}
	};

	
	private void playerChecker() {
		CollisionResults results = new CollisionResults();
		Vector3f ppos = playerData.getPosition();
		Vector3f pdir = playerData.getDirection();
		Ray ray = new Ray(ppos, pdir);
		rootNode.collideWith(ray, results);
		for (int i = 0; i < results.size(); i++) {
			float dist = results.getCollision(i).getDistance();
			if (dist < 2) {

				Geometry target = results.getClosestCollision().getGeometry();
				if (target.getName().equals("Beam") || target.getName().equals("warzone")) {
					break;
				}
				
				playerData.setPosition(new Vector3f(0, 0, 0));
			}

		}
	}
	
}
