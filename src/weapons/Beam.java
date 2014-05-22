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
	
	Vector3f dir;
	public Vector3f pos;
	public Node bnode;
	float speed;
	Player player;
	sharedstate.BeamD data;
	
	public Beam(SharedState state, Player player, Vector3f dir, AssetManager assetManager){
		this.data = new BeamD(state, player.data);
		this.dir = dir;
		this.pos = player.data.getPosition();
		this.player = player;
		
		data.setPosition(pos);
		data.setDirection(dir);
		
		bnode = new Node();
		Box box1 = new Box(0.5f,0.5f,0.5f);
        Geometry beam = new Geometry("Beam", box1);
        beam.setLocalTranslation(new Vector3f(0,0,0));
        Material mat1 = new Material(assetManager, 
                "Common/MatDefs/Misc/Unshaded.j3md");
        mat1.setColor("Color", ColorRGBA.Red);
        beam.setMaterial(mat1);
        bnode.setLocalTranslation(player.data.getPosition());
        bnode.attachChild(beam);
	}
	
	public void update(){
		//data.update();
		data.setDirection(dir);
		Vector3f pos = data.getPosition();
		bnode.setLocalTranslation(pos);
	}

}
