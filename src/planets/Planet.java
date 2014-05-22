package planets;

import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Sphere;

public class Planet {
	
	public int size;
	public Node node;
	
	
	public Planet(int size){
		this.size = size;
	}
	
	public void setWarzone(Geometry child){
		node.attachChild(child);
	}

}
