package main;

public abstract class GraphicsObject {
	private sharedstate.GameObject data;
	public GraphicsObject(sharedstate.GameObject obj) {
		data = obj;
	}
	
	public abstract void update();
	
	public sharedstate.GameObject getData() { return data; }
}
