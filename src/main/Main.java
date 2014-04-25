package main;

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

	public static void main(String[] args) {
		Main app = new Main();
		app.start();
	}
	
	@Override
	public void simpleUpdate(float tpf){
		player.update();
	}
	

	@Override
	public void simpleInitApp() {
		initLights();
		initObjects();
		initKeys();
		initCam();

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
		Cam.setMaxDistance(1000);
		Cam.setMinDistance(2);
		Cam.setDefaultDistance(50);

		Cam.setDefaultHorizontalRotation((float) (-Math.PI / 2));
		Cam.setDefaultVerticalRotation((float) (Math.PI / 8));
		Cam.setMaxVerticalRotation(360);
		Cam.setMinVerticalRotation(-360);

	}

	private void initObjects() {
		//cam.setLocation(new Vector3f(0f, 0f, 50f));
		earth = new Earth(150, assetManager);
		player = new Player(assetManager);
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
				player.pnode.rotate(0, 0, player.rotateSpeed);
			}
			if (name.equals("left")) {
				player.pnode.rotate(0, 0, -player.rotateSpeed);
			}
			if (name.equals("down")) {
				player.pnode.rotate(player.rotateSpeed, 0, 0);
			}
			if (name.equals("up")) {
				player.pnode.rotate(-player.rotateSpeed, 0, 0);
			}
		}
	};

}
