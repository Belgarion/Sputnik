package sharedstate;

import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;

public class Player extends GameObject {
	private float rotateSpeed;
	private ColorRGBA color;
	public String name;
	
	public Player(String name) {
		super();
		this.name = name;
		setSpeed(0.6f);
		setColor(ColorRGBA.Blue);
	}

	@Override
	public void update() {
		this.direction = this.realDirection;
		this.position = this.realPosition.add(this.direction.mult((float) (getSpeed()*getTimeDifference())));
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
	public void fromNetString() {
		// TODO Auto-generated method stub

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

}
