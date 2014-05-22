package weapons;

import sharedstate.BeamD;
import sharedstate.SharedState;
import Entities.Player;

import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Box;

public class Beam {
	public Node bnode;
	float speed;
	Player player;
	sharedstate.BeamD data;
	
	public Beam(sharedstate.BeamD data, AssetManager assetManager){
		this.data = data;
		
		bnode = new Node();
		Box box1 = new Box(0.5f,0.5f,0.5f);
        Geometry beam = new Geometry("Beam", box1);
        beam.setLocalTranslation(new Vector3f(0,0,0));
        Material mat1 = new Material(assetManager, 
                "Common/MatDefs/Misc/Unshaded.j3md");
        mat1.setColor("Color", ColorRGBA.Red);
        beam.setMaterial(mat1);
        bnode.setLocalTranslation(data.getPosition());
        bnode.attachChild(beam);
	}

	
	public void update(){
		Vector3f pos = data.getPosition();
		bnode.setLocalTranslation(pos);
	}

}
