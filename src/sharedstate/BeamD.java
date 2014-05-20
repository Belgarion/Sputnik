package sharedstate;

import com.jme3.math.Vector3f;

public class BeamD extends GameObject{
	
	private float speed;
	private Player player;
	private int start = 100;
	
	public BeamD(SharedState state, Player player){
		super();
		setSpeed(100);
		this.player = player;
		state.myObjects.add(this);
		
		//this.direction = dir;
	}

	@Override
	public void update(){
		this.direction = this.realDirection;
		this.position = this.realPosition.add(this.direction.mult((float) (getSpeed()*getTimeDifference())));
	}

}
