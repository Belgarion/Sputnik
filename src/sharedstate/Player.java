package sharedstate;

import com.jme3.math.ColorRGBA;

public class Player extends GameObject {
	private float rotateSpeed;
	private ColorRGBA color;
	
	public Player() {
		super();
		setSpeed(0.01f);
		setColor(ColorRGBA.Blue);
	}

	@Override
	public void update() {
		//this.position.add(this.direction.mult(getSpeed()));
		this.position = this.realPosition;
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
