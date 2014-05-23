package sharedstate;


import java.util.concurrent.CopyOnWriteArrayList;

public class SharedState {
	CopyOnWriteArrayList<GameObject> objects; // all objects
	CopyOnWriteArrayList<GameObject> myObjects; // objects owned by me
	Player player; // my player
	
	public SharedState(Player player) {
		objects = new CopyOnWriteArrayList<GameObject>();
		myObjects = new CopyOnWriteArrayList<GameObject>();
		
		this.player = player;
		myObjects.add(player);
		objects.add(player);
	}
	
	public void update() {
		//player.update();
		for (GameObject obj : objects) {
			obj.update();
		}
	}
	
	public CopyOnWriteArrayList<GameObject> getMyObjects() {
		return myObjects;
	}
	
	public CopyOnWriteArrayList<GameObject> getObjects() {
		return objects;
	}
}
