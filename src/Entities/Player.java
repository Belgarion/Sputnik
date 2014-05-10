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
	
	private sharedstate.Player data;
	
	public Player(sharedstate.Player data, AssetManager assetManager){
		this.data = data;
		pnode = new Node();
		Box box1 = new Box(5,2,3);
        ship = new Geometry("Ship", box1);
        ship.setLocalTranslation(data.getPosition());
        Material mat1 = new Material(assetManager, 
                "Common/MatDefs/Misc/Unshaded.j3md");
        mat1.setColor("Color", data.getColor());
        ship.setMaterial(mat1);
        ship.rotate(0, (float) Math.PI / 2, 0);
        pnode.attachChild(ship);
	}
	
	public void update(){
		ship.setLocalTranslation(data.getPosition());
		Quaternion rot = pnode.getLocalRotation();//Riktning på skeppet ska sättas på något sätt.
		//pnode.setLocal
	}

}
