package main;

import java.util.ArrayList;
import java.util.Vector;

import network.Client;
import network.NetworkRecvThread;
import network.NetworkSendThread;
import planets.Earth;
import planets.Moon;
import planets.Planet;
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
import com.jme3.math.Quaternion;
import com.jme3.math.Ray;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.util.SkyFactory;

public class Main extends SimpleApplication {

	ChaseCamera Cam;
	Player player;
	Earth earth;
	ArrayList<Beam> beams;
	ArrayList<Player> players = new ArrayList<>();
	AudioNode audio_beam;
	Moon moon;
	DirectionalLight sun;
	float sunrot = 0;
	Client client;
	Global global = Global.getInstance();
	Meny meny;
	War war;
	
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
		warChecker();
		earth.update();
		moon.update();
		for(int i = 0; i < beams.size(); i++){
			beams.get(i).update();
		}
		playerChecker();
	}
	

	private void warChecker() {
		
		
	}

	private void playerChecker() {
		CollisionResults results = new CollisionResults();
        // Convert screen click to 3d position
        Vector3f ppos = playerData.getPosition();
        Vector3f pdir = playerData.getDirection();
        // Aim the ray from the clicked spot forwards.
        Ray ray = new Ray(ppos, pdir);
        // Collect intersections between ray and all nodes in results list.
        rootNode.collideWith(ray, results);
        // (Print the results so we see what is going on:)
        for (int i = 0; i < results.size(); i++) {
          // (For each “hit”, we know distance, impact point, geometry.)
          float dist = results.getCollision(i).getDistance();
          if(dist < 2){
        	  Geometry target = results.getClosestCollision().getGeometry();
        	  if(target.getName().equals("Beam")){
        		  break;
        	  }
        	  
        	  if(target.getName().equals("warzone")){
        		  System.out.println("warzone");
        		  break;
        	  }
        	  playerData.setPosition(new Vector3f(0,0,0));
          }
        }
	}

	@Override
	public void simpleInitApp() {
		initLights();
		initObjects();
		initKeys();
		initCam();
		initAudio();
		meny = new Meny(assetManager);
		
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
	
	private void initAudio(){
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
		inputManager.addMapping("shootB", new MouseButtonTrigger(MouseInput.BUTTON_LEFT));

		inputManager.addListener(analogListener, "left", "right", "down",
				"up", "one", "two", "three", "four", "pulse");
		
		ActionListener acl = new ActionListener() {

			public void onAction(String name, boolean keyPressed, float tpf) {
				if(name.equals("shootB") && keyPressed){
		        Vector2f click2d = inputManager.getCursorPosition();
		        Vector3f click3d = cam.getWorldCoordinates(new Vector2f(click2d.x, click2d.y), 0f).clone();
		        Vector3f dir = cam.getWorldCoordinates(new Vector2f(click2d.x, click2d.y), 1f).subtractLocal(click3d).normalizeLocal();
		        Beam beam = new Beam(state, player, dir, assetManager);
		        beams.add(beam);
		        rootNode.attachChild(beam.bnode);
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

		Cam.setDefaultHorizontalRotation((float) (-Math.PI / 2));
		Cam.setDefaultVerticalRotation((float) (Math.PI / 8));
		Cam.setMaxVerticalRotation(360);
		Cam.setMinVerticalRotation(-360);
		Cam.setToggleRotationTrigger(new MouseButtonTrigger(MouseInput.BUTTON_RIGHT));

	}

	private void initObjects() {
		//cam.setLocation(new Vector3f(0f, 0f, 50f));
		playerData = new sharedstate.Player("namn");
		state = new sharedstate.SharedState(playerData);
		
		
		earth = new Earth(150, assetManager);
		moon = new Moon(50, assetManager, (Planet)earth);
		player = new Player(playerData,assetManager, "namn", 1);
		players.add(player);
		rootNode.attachChild(player.pnode);
		rootNode.attachChild(earth.enode);
		earth.enode.setLocalTranslation(0, 0, 400);
		beams = new ArrayList<Beam>();
		/*assetManager.registerLocator("Skybox360_002.Zip", ZipLocator.class);
		rootNode.attachChild(SkyFactory.createSky(
	            assetManager, "Skybox360_002", false));
		 */
		war = new War((Planet)earth, assetManager);
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
		//rootNode.addLight(sun2);
		//rootNode.addLight(sun3);
		//rootNode.addLight(sun4);

	}

	private AnalogListener analogListener = new AnalogListener() {
		public void onAnalog(String name, float value, float tpf) {

			Quaternion rotation = cam.getRotation();

			if (name.equals("right")) {
				player.setRotation(0,0, player.rotateSpeed);
				//player.pnode.rotate(0, 0, player.rotateSpeed);
			}
			if (name.equals("left")) {
				player.setRotation(0,0, -player.rotateSpeed);
				//player.pnode.rotate(0, 0, -player.rotateSpeed);
			}
			if (name.equals("down")) {
				player.setRotation(player.rotateSpeed,0, 0);
				//player.pnode.rotate(player.rotateSpeed, 0, 0);
			}
			if (name.equals("up")) {
				player.setRotation(-player.rotateSpeed,0, 0);
				//player.pnode.rotate(-player.rotateSpeed, 0, 0);
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

	

}
