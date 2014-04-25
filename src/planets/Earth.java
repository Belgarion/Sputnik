package planets;

import com.jme3.asset.AssetManager;
import com.jme3.asset.plugins.FileLocator;
import com.jme3.asset.plugins.ZipLocator;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Sphere;
import com.jme3.texture.Texture;
import com.jme3.util.TangentBinormalGenerator;

public class Earth extends Planet {

	public Node enode;
	public Geometry earth;

	public Earth(int size, AssetManager assetManager) {
		super(size);
		enode = new Node();
		assetManager.registerLocator("Assets", FileLocator.class);
		Sphere sphere = new Sphere(50, 50, size);
		earth = new Geometry("Earth", sphere);
		sphere.setTextureMode(Sphere.TextureMode.Projected);
		TangentBinormalGenerator.generate(sphere);
		Material mat1 = new Material(assetManager,
				"Common/MatDefs/Light/Lighting.j3md");
		mat1.setTexture("DiffuseMap", assetManager.loadTexture("earth.jpg"));
		mat1.setBoolean("UseMaterialColors", true);
		mat1.setColor("Diffuse", ColorRGBA.White);
		mat1.setColor("Specular", ColorRGBA.White);
		mat1.setFloat("Shininess", 1f); // [0,128]
		earth.setMaterial(mat1);
		earth.rotate(32, 41, 31);
		enode.attachChild(earth);
	}

}
