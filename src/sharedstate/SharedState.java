package sharedstate;

import java.util.Vector;

public class SharedState {
	Vector<GameObject> objects; // all objects
	Vector<GameObject> myObjects; // objects owned by me
	Player player; // my player
	
	public SharedState(Player player) {
		objects = new Vector<GameObject>();
		myObjects = new Vector<GameObject>();
		this.player = player;
		myObjects.add(player);
	}
	
	public void update() {
		player.update();
		for (GameObject obj : objects) {
			obj.update();
		}
	}
	
	public Vector<GameObject> getMyObjects() {
		return myObjects;
	}
}
