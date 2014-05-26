package main;

import java.util.ArrayList;

import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Spatial.CullHint;
import com.jme3.scene.shape.Box;
import com.jme3.scene.shape.Sphere;

import planets.Earth;
import planets.Planet;
import sharedstate.Player;

public class War {
	
	public long counter1;
	public long counter2;
	Earth planet;
	public Geometry war;
	public Geometry score1;
	public Geometry score2;
	public ArrayList<Player> players;
	public Vector3f pos;
	private float size;
	public boolean warzone;
	
	public War(Earth planet, AssetManager assetManager){
		this.warzone = false;
		counter1 = 0;
		counter2 = 0;
		this.planet = planet;
		this.size = (((sharedstate.Planet)(planet.data)).getSize()) * 1.3f;
		//Sphere sphere = new Sphere(50, 50, planet.size + 100);
		Box box = new Box(size, size, size);
		war = new Geometry("warzone", box);
		Material mat1 = new Material(assetManager,
				"Common/MatDefs/Light/Lighting.j3md");
		war.setMaterial(mat1);
		war.setCullHint(CullHint.Always);
		//planet.enode.attachChild(war);
		//war.setLocalTranslation(planet.node.getLocalTranslation());
		planet.setWarzone(war);
		pos = planet.enode.getLocalTranslation();
	}
	
	
	
	public void updateScore(long score1, long score2){
		counter1 = score1;
		counter2 = score2;
	}
	
	public void checker(Player player){
		
		pos = war.getLocalTranslation();

		if((player.getPosition().x >= (pos.x - size) && player.getPosition().x <= pos.x + size ) &&
				(player.getPosition().y >= (pos.y - size) && player.getPosition().y <= pos.y + size) &&
				player.getPosition().z >= (pos.z - size) && player.getPosition().z <= pos.z + size ){
			warzone = true;
		}else{
			warzone = false;
		}
		
	}

}
