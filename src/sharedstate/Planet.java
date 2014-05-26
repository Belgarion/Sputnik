package sharedstate;

import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;

public class Planet extends GameObject {

	public Quaternion rotation;

	public Planet(){
		super();
		this.type = "planet";
	}

	@Override
	public void update(){
		this.direction = this.realDirection;
		this.position = this.realPosition.add(this.direction.mult((float) (getSpeed()*getTimeDifference())));

	}
	
	public void setRotation(Quaternion rot){
		this.rotation = rot;
	}


}
