package sharedstate;

import java.util.UUID;

import com.jme3.math.Vector3f;

public abstract class GameObject { // TODO: synchronized
	private UUID id;
	private float speed;
	protected Vector3f direction; // calculated direction
	protected Vector3f position; // calculated position
	
	protected int lastTimestamp; // when the latest data was received
	
	protected Vector3f realDirection; // last received value for direction
	protected Vector3f realPosition; // last received value for posititon
	
	public GameObject() {
		id = UUID.randomUUID();
		speed = 0;
		direction = new Vector3f(0,0,0);
		position = new Vector3f(0,0,0);
		lastTimestamp = 0;
		realDirection = new Vector3f(0,0,0);
		realPosition = new Vector3f(0,0,0);
	}
	
	public abstract void update(); // dead reckoning
	public String toNetString() { // convert to string for sending over network
		StringBuffer sb = new StringBuffer();
		sb.append("id:" + id + "\n");
		sb.append("s:" + speed + "\n");
		sb.append("p:" + position + "\n");
		sb.append("d:" + direction + "\n");
		return sb.toString();
	}
	public void fromNetString() { // update values from string
		
	}
	
	public Vector3f getDirection() { return direction; }
	public Vector3f getPosition() { return position; }
	public float getSpeed() { return speed; }
	
	public void setDirection(Vector3f direction) { realDirection = direction; }
	public void setPosition(Vector3f position) { realPosition = position; }
	public void setSpeed(float s) { speed = s; }

}
