package sharedstate;


import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;

public class SharedState {
	CopyOnWriteArrayList<GameObject> objects; // all objects
	CopyOnWriteArrayList<GameObject> myObjects; // objects owned by me
	Player player; // my player
	public String chatMessage = "Chat";
	
	public SharedState(Player player) {
		objects = new CopyOnWriteArrayList<GameObject>();
		myObjects = new CopyOnWriteArrayList<GameObject>();
		
		this.player = player;
		myObjects.add(player);
		objects.add(player);
	}
	
	public void update() {
		for (GameObject obj : myObjects) {
			obj.update();
		}

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
	
	public UUID getMyId() {
		return player.getId();
	}
}
