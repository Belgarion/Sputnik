package main;

import java.util.ArrayList;

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
	AudioNode audio_beam;
	Moon moon;

	public static void main(String[] args) {
		Main app = new Main();
		app.start();
	}
	
	@Override
	public void simpleUpdate(float tpf){
		player.update();
		earth.update();
		for(int i = 0; i < beams.size(); i++){
			beams.get(i).update();
			if(beams.get(i).pos.x > 2000 || beams.get(i).pos.x < -2000){
				beams.remove(i);
				rootNode.detachChild(beams.get(i).bnode);
			}
			else if(beams.get(i).pos.y > 2000 || beams.get(i).pos.y < -2000){
				beams.remove(i);
				rootNode.detachChild(beams.get(i).bnode);
			}
			else if(beams.get(i).pos.z > 2000 || beams.get(i).pos.z < -2000){
				beams.remove(i);
				rootNode.detachChild(beams.get(i).bnode);
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
		        Beam beam = new Beam(player, dir, assetManager);
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
		Cam.setMaxDistance(1000);
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
		earth = new Earth(150, assetManager);
		moon = new Moon(50, assetManager, (Planet)earth);
		player = new Player(assetManager, 0, 0, 0);
		rootNode.attachChild(player.pnode);
		rootNode.attachChild(earth.enode);
		earth.enode.setLocalTranslation(0, 0, 400);
		beams = new ArrayList<Beam>();
		/*assetManager.registerLocator("Skybox360_002.Zip", ZipLocator.class);
		rootNode.attachChild(SkyFactory.createSky(
	            assetManager, "Skybox360_002", false));
		 */
	}

	private void initLights() {
		DirectionalLight sun = new DirectionalLight();
		sun.setDirection(new Vector3f(0, 0, 1.0f));
		DirectionalLight sun2 = new DirectionalLight();
		sun2.setDirection(new Vector3f(0, 0, -1.0f));
		DirectionalLight sun3 = new DirectionalLight();
		sun3.setDirection(new Vector3f(0, 1, -1.0f));
		DirectionalLight sun4 = new DirectionalLight();
		sun4.setDirection(new Vector3f(1, 0, 1.0f));
		rootNode.addLight(sun);
		rootNode.addLight(sun2);
		rootNode.addLight(sun3);
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
				player.setSpeed(1);
			}
			if (name.equals("two")) {
				player.setSpeed(2);
			}
			if (name.equals("three")) {
				player.setSpeed(3);
			}
			if (name.equals("four")) {
				player.setSpeed(4);
			}
			
			
		}
	};

}
