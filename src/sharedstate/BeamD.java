package sharedstate;

import com.jme3.math.Vector3f;

public class BeamD extends GameObject{
	
	private float speed;
	private Player player;
	private int start = 100;
	
	public BeamD() {
		super();
		this.speed = 100;
	}
	
	public BeamD(Player player){
		this();
		this.player = player;
		this.position = player.getPosition();
	}

	@Override
	public void update(){
		this.direction = this.realDirection;
		this.position = this.realPosition.add(this.direction.mult((float) (getSpeed()*getTimeDifference())));
	}

}
