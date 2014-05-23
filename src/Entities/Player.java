package Entities;

import jme3tools.optimize.LodGenerator;

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

public class Player extends main.GraphicsObject {

	public Node pnode;
	// public Geometry ship;
	public Spatial ship;
	public float speed;
	public float rotateSpeed;
	public Vector3f dir, pos;
	public float rotx, roty, rotz;

	public sharedstate.Player data;
	public Vector3f upVector;

	
	public String name;
	public int team;


	public Player(sharedstate.Player data, AssetManager assetManager) {
		super(data);
		rotateSpeed = 0.005f;
		upVector = new Vector3f(0,1,0);
		this.team = 1;
		this.data = data;
		pnode = new Node();
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
		ship = assetManager.loadModel("shipA_OBJ.obj");
		ship.setLocalScale(0.1f);
		ship.rotate(0, (float)Math.PI, 0);

		Material shipmat = new Material(assetManager,
				"Common/MatDefs/Light/Lighting.j3md");
		shipmat.setBoolean("UseMaterialColors", true);
		shipmat.setColor("Diffuse", ColorRGBA.White);
		shipmat.setColor("Specular", ColorRGBA.White);
		shipmat.setFloat("Shininess", 1f); // [0,128]

		
		ship.setMaterial(shipmat);

		//data.setPosition(new Vector3f(posx, posy, posz));
		//data.setDirection(new Vector3f(0, 0, 1));
		
		pnode.setLocalTranslation(data.getPosition());
		
		pnode.attachChild(ship);
	}


	public void update() {
		Vector3f pos = data.getPosition();
		pnode.setLocalTranslation(pos);
	}

	public void setRotation(float i, float j, float k) {
		pnode.rotate(i, j, k);
		dir = pnode.getLocalRotation().getRotationColumn(2);
		data.setDirection(dir);
	}

}
