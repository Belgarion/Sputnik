package sharedstate;

import java.util.Vector;

public class SharedState {
	Vector<GameObject> objects;
	GameObject player;
	
	public SharedState(GameObject player) {
		objects = new Vector<GameObject>();
		this.player = player;
	}
}
