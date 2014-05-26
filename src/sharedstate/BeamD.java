package sharedstate;

import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;

public class BeamD extends GameObject{
	
	private ColorRGBA color;
	private Player player;
	private int start = 100;
	
	public BeamD() {
		super();
		setSpeed(100);
	}
	
	public BeamD(Player player, Vector3f dir){
		this();
		this.player = player;
		this.realPosition = player.getPosition();
		this.realDirection = dir;
	}

	@Override
	public void update(){
		this.direction = this.realDirection;
		this.position = this.realPosition.add(this.direction.mult((float) (getSpeed()*getTimeDifference())));

	}
	public String toNetString() {
		StringBuffer sb = new StringBuffer();
		sb.append("type:BeamD\n");
		sb.append("co:" + color + "\n");
		sb.append(super.toNetString());
		return sb.toString();
	}

}
