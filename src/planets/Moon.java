package planets;

import com.jme3.asset.AssetManager;
import com.jme3.asset.plugins.FileLocator;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Sphere;
import com.jme3.util.TangentBinormalGenerator;

public class Moon extends Planet{
	
	public Node mnode;
	public Geometry moon;
	float rotspeed = 0.0005f;

	public Moon(sharedstate.Planet data, AssetManager assetManager, Planet earth) {
		super(data);
		mnode = new Node();
		assetManager.registerLocator("Assets", FileLocator.class);
		Sphere sphere = new Sphere(50, 50, data.getSize());
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
		//moon.rotate(-(float)Math.PI / 2, -(float)Math.PI / 2,0);
		//mnode.rotate(0,0, (float) (2 * Math.PI / 360) * 23);
		moon.setLocalTranslation(400, 0, 0);
		mnode.attachChild(moon);
		earth.node.attachChild(mnode);
	}
	
	public void update(){
		mnode.setLocalRotation(((sharedstate.Planet)data).getRotation());
	}
	
	

}
