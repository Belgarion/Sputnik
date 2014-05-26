package sharedstate;

<<<<<<< HEAD
import com.jme3.math.FastMath;
=======
>>>>>>> 4a307bfb1aa3d385bc776be59a093249bc689cc4
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;

public class Planet extends GameObject {
	private int size;
	private Quaternion rotation;
	private float angle;

	public Quaternion rotation;

	@Override
	public void update() {
		this.angle = (float)((1 * getTimeDifference()) % 360); // 1 degrees per second
		this.rotation.fromAngleAxis((float)((FastMath.PI*angle/180.0)), new Vector3f((float)0, 1f, (float)0));
	}

	public Planet() {
		super();
		this.setSize(10);
		this.rotation = new Quaternion();
		update();
	}

	public Planet(int size) {
		this();
		this.setSize(size);
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public Quaternion getRotation() {
		return rotation;
	}

	public void setRotation(Quaternion rotation) {
		this.rotation = rotation;
	}

	public float getAngle() {
		return angle;
	}

	public void setAngle(float angle) {
		this.angle = angle;
	}


}
