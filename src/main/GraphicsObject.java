package main;

public abstract class GraphicsObject {
	public sharedstate.GameObject data;
	public GraphicsObject(sharedstate.GameObject obj) {
		data = obj;
	}
	
	public abstract void update();
	
	public sharedstate.GameObject getData() { return data; }
}
