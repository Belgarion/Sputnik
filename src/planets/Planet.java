package planets;

import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Sphere;

public class Planet extends main.GraphicsObject {
	public Node node;
	
	public Planet(sharedstate.Planet data){
		super(data);
	}
	
	public void setWarzone(Geometry child){
		node.attachChild(child);
	}

	@Override
	public void update() {
		
	}

}
