package planets;

import com.jme3.asset.AssetManager;
import com.jme3.asset.plugins.FileLocator;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Quaternion;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial.CullHint;
import com.jme3.scene.shape.Box;
import com.jme3.scene.shape.Sphere;
import com.jme3.util.TangentBinormalGenerator;

public class Moon extends Planet{
	
	public Node mnode;
	public Geometry moon, moonB;
	public sharedstate.Planet data;
	float rotspeed = 0.0005f;
	int dir;

	public Moon(sharedstate.Planet data, int size,  AssetManager assetManager, Planet earth, int dir) {
		super(data, size);
		this.data = data;
		mnode = new Node();
		assetManager.registerLocator("Assets", FileLocator.class);
		Sphere sphere = new Sphere(50, 50, size);
		moon = new Geometry("Moon", sphere);
		sphere.setTextureMode(Sphere.TextureMode.Projected);
		TangentBinormalGenerator.generate(sphere);
		Material mat1 = new Material(assetManager,
				"Common/MatDefs/Light/Lighting.j3md");
		mat1.setTexture("DiffuseMap", assetManager.loadTexture("moon.jpg"));
		mat1.setBoolean("UseMaterialColors", true);
		mat1.setColor("Diffuse", ColorRGBA.White);
		mat1.setColor("Specular", ColorRGBA.White);
		mat1.setFloat("Shininess", 1f); // [0,128]
		moon.setMaterial(mat1);
		moon.setLocalTranslation(400, 0, 0);
		
		mnode.attachChild(moon);
		//moon.setLocalRotation(earth.node.getLocalRotation());
		earth.node.attachChild(mnode);
		this.dir = dir;
		
		//Sphere sphere = new Sphere(50, 50, planet.size + 100);
		Box box = new Box(size * 1.2f, size * 1.2f, size * 1.2f);
		moonB = new Geometry("moonbox", box);
		Material mat2 = new Material(assetManager,
				"Common/MatDefs/Light/Lighting.j3md");
		moonB.setMaterial(mat2);
		moonB.setCullHint(CullHint.Always);
		mnode.attachChild(moonB);
		
		
	}
	
	public void setRotation(){
		mnode.setLocalRotation(data.rotation);
	}
	
	public void update(){
		if(dir == 1){
			mnode.rotate(0, rotspeed, 0);
		}else{
			mnode.rotate(0, -rotspeed, 0);
		}	
	}
	
	

}
