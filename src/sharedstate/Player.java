package sharedstate;

public class Player extends GameObject {

	@Override
	public void update() {
		this.position.add(this.direction.mult(getSpeed()));
	}

	@Override
	public String toNetString() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void fromNetString() {
		// TODO Auto-generated method stub

	}

}
