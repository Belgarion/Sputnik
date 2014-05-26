package sharedstate;

import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;

public class Player extends GameObject {
	private float rotateSpeed;
	private ColorRGBA color;
	private String name;
	private int team;
	
	public Player() {
		super();
		setSpeed(0.6f);
		setColor(ColorRGBA.Blue);
		setName("Unnamed player");
		setTeam(1);
	}

	@Override
	public void update() {
		System.out.println("Updating player " + this + " oldPosition " + this.position + " direction " + this.direction );
		this.direction = this.realDirection;
		this.position = this.realPosition.add(this.direction.mult((float) (getSpeed()*getTimeDifference())));
		System.out.println("New position " + this.position);
	}

	@Override
	public String toNetString() {
		StringBuffer sb = new StringBuffer();
		sb.append("type:Player\n");
		sb.append("rs:" + rotateSpeed + "\n");
		sb.append("co:" + color + "\n");
		sb.append(super.toNetString());
		return sb.toString();
	}

	@Override
	public void fromNetString(String data) {
		super.fromNetString(data);

	}

	public float getRotateSpeed() {
		return rotateSpeed;
	}

	public void setRotateSpeed(float rotateSpeed) {
		this.rotateSpeed = rotateSpeed;
	}

	public ColorRGBA getColor() {
		return color;
	}

	public void setColor(ColorRGBA color) {
		this.color = color;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getTeam() {
		return team;
	}

	public void setTeam(int team) {
		this.team = team;
	}

}
