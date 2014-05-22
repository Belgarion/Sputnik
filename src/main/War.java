package main;

import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Sphere;

import planets.Planet;

public class War {
	
	public long counter1;
	public long counter2;
	public Geometry war;
	
	public War(Planet planet, AssetManager assetManager){
		
		counter1 = 0;
		counter2 = 0;
		
		Sphere sphere = new Sphere(50, 50, planet.size + 25);
		war = new Geometry("warzone", sphere);
		Material mat1 = new Material(assetManager,
				"Common/MatDefs/Light/Lighting.j3md");
		war.setMaterial(mat1);
		planet.setWarzone(war);
		
	}
	
	public void Update(int team){
		if(team == 1){
			
		}
		else if(team == 2){
			
		}
	}

}
