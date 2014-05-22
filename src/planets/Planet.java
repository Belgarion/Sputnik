package planets;

import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Sphere;

public class Planet extends main.GraphicsObject {
	
	public int size;
	public Node node;
	
	public Planet(sharedstate.GameObject data, int size){
		super(data);
		this.size = size;
	}
	
	public void setWarzone(Geometry child){
		node.attachChild(child);
	}

}
