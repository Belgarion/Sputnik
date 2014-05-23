package sharedstate;

import com.jme3.math.Vector3f;

public class BeamD extends GameObject{
	
	private float speed;
	private Player player;
	private int start = 100;
	
	public BeamD() {
		super();
		setSpeed(100);
	}
	
	public BeamD(Player player, Vector3f dir){
		this();
		setSpeed(100);
		this.player = player;
		this.position = player.getPosition();
		this.direction = dir;
		System.out.println(this.position);
		System.out.println(dir);
	}

	@Override
	public void update(){
		
		//this.direction = this.realDirection;
		this.position = this.realPosition.add(this.direction.mult((float) (getSpeed()*getTimeDifference())));

	}

}
