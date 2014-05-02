package sharedstate;

import com.jme3.math.Vector3f;

public abstract class GameObject {
	private float speed;
	protected Vector3f direction; // calculated direction
	protected Vector3f position; // calculated position
	
	protected int lastTimestamp; // when the latest data was received
	
	protected Vector3f realDirection; // last received value for direction
	protected Vector3f realPosition; // last received value for posititon
	
	public abstract void update(); // dead reckoning
	public abstract String toNetString(); // convert to string for sending over network
	public abstract void fromNetString(); // update values from string
	
	public Vector3f getDirection() { return direction; }
	public Vector3f getPosition() { return position; }
	public float getSpeed() { return speed; }
	
	public void setDirection(Vector3f direction) { realDirection = direction; }
	public void setPosition(Vector3f position) { realPosition = position; }
	public void setSpeed(float s) { speed = s; }

}
