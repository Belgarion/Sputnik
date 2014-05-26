package sharedstate;

import java.util.UUID;

import network.Utils;

import com.jme3.math.Vector3f;

public abstract class GameObject { // TODO: synchronized
	private UUID id;
	protected float speed;
	protected Vector3f direction; // calculated direction
	protected Vector3f position; // calculated position
	
	protected double lastTimestamp; // when the latest data was received
	
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
		sb.append("p:" + realPosition + "\n");
		sb.append("d:" + realDirection + "\n");
		sb.append("t:" + lastTimestamp + "\n");
		return sb.toString();
	}
	public void fromNetString(String data) { // update values from string
		id = Utils.parseId(data);
		lastTimestamp = Utils.parseTimestamp(data);
		realPosition = Utils.parsePosition(data);
		realDirection = Utils.parseDirection(data);
		speed = Utils.parseSpeed(data);
	}
	
	public Vector3f getDirection() { return direction; }
	public Vector3f getPosition() { return position; }
	public float getSpeed() { return speed; }
	
	public void setDirection(Vector3f direction) { updateTime(); realDirection = direction; realPosition = position; }
	public void setPosition(Vector3f position) { updateTime(); realPosition = position; }
	public void setSpeed(float s) { updateTime(); speed = s; }
	
	public UUID getId() { return id; }
	
	public double getTimeDifference() {
		double currentTime = System.currentTimeMillis() / 1000.0d;
		double diff = currentTime - lastTimestamp;
		return diff;
	}

	public void updateTime() {
		update();
		lastTimestamp = System.currentTimeMillis() / 1000.0d;
	}
	
	public double getLastTimeStamp(){
		return lastTimestamp;
	}
}
