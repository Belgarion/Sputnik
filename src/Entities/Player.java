package Entities;

import com.jme3.asset.AssetManager;
import com.jme3.asset.plugins.FileLocator;
import com.jme3.asset.plugins.ZipLocator;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Box;

public class Player {

	public Node pnode;
	// public Geometry ship;
	public Spatial ship;
	public float speed;
	public float rotateSpeed;
	public Vector3f dir, pos;
	public float rotx, roty, rotz;
	private sharedstate.Player data;

	public Player(sharedstate.Player data, AssetManager assetManager,
			float posx, float posy, float posz) {
		rotateSpeed = 0.005f;

		this.data = data;
		pnode = new Node();
		this.speed = 0;
		/*
		 * Geometry ship = new Geometry("Ship", box1);
		 * ship.setLocalTranslation(new Vector3f(0,0,0)); Box box1 = new
		 * Box(5,2,3); ship = new Geometry("Ship", box1);
		 * ship.setLocalTranslation(data.getPosition()); Material mat1 = new
		 * Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
		 * mat1.setColor("Color", ColorRGBA.Blue); ship.setMaterial(mat1);
		 */

		assetManager.registerLocator("Assets", FileLocator.class);
		// assetManager.registerLocator("spaceship.zip", ZipLocator.class);
		ship = assetManager.loadModel("Wraith Raider Starship.obj");
		ship.setLocalScale(0.05f);

		Material shipmat = new Material(assetManager,
				"Common/MatDefs/Light/Lighting.j3md");
		shipmat.setBoolean("UseMaterialColors", true);
		shipmat.setColor("Diffuse", ColorRGBA.White);
		shipmat.setColor("Specular", ColorRGBA.White);
		shipmat.setFloat("Shininess", 1f); // [0,128]
		ship.setMaterial(shipmat);

		pos = new Vector3f(posx, posy, posz);
		dir = new Vector3f(0, 0, 1);
		pnode.setLocalTranslation(pos);
		pnode.attachChild(ship);
	}


	public void update() {
		pos = new Vector3f(pos.x + dir.x * speed, pos.y + dir.y * speed, pos.z
				+ dir.z * speed);
		pnode.setLocalTranslation(pos);
	}

	public void setSpeed(int i) {
		speed = (float) i / 100;
	}

	public void setRotation(float i, float j, float k) {
		pnode.rotate(i, j, k);
		dir = pnode.getLocalRotation().getRotationColumn(2);
	}

}
