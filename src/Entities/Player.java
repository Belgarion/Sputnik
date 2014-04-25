package Entities;

import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Box;

public class Player {
	
	public Node pnode;
	public Geometry ship;
	public float speed;
	public float rotateSpeed;
	
	public Player(AssetManager assetManager){
		rotateSpeed = 0.005f;
		pnode = new Node();
		Box box1 = new Box(5,2,3);
        Geometry ship = new Geometry("Ship", box1);
        ship.setLocalTranslation(new Vector3f(1,-1,1));
        Material mat1 = new Material(assetManager, 
                "Common/MatDefs/Misc/Unshaded.j3md");
        mat1.setColor("Color", ColorRGBA.Blue);
        ship.setMaterial(mat1);
        ship.rotate(0, (float) Math.PI / 2, 0);
        pnode.attachChild(ship);
	}
	
	public void update(){
		Quaternion rot = pnode.getLocalRotation();
		//pnode.setLocal
	}

}
