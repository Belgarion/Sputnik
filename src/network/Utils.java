package network;

import java.util.UUID;

import com.jme3.math.Vector3f;

public class Utils {
	public static UUID parseId(String data) {
		String[] lines = data.split("\n");
		for (String line : lines) {
			String[] parts = line.split(":", 2);
			if (parts[0].equals("id")) {
				return UUID.fromString(parts[1]);
			}
		}
		return null;
	}
	
	public static String parseType(String data) {
		String[] lines = data.split("\n");
		for (String line : lines) {
			String[] parts = line.split(":", 2);
			if (parts[0].equals("type")) {
				return parts[1];
			}
		}
		return null;
	}
	
	public static Double parseTimestamp(String data) {
		String[] lines = data.split("\n");
		for (String line : lines) {
			String[] parts = line.split(":", 2);
			if (parts[0].equals("t")) {
				return Double.parseDouble( parts[1].replace(",",".") );
			}
		}
		return null;
	}
	
	public static Vector3f parsePosition(String data) {
		String[] lines = data.split("\n");
		for (String line : lines) {
			String[] parts = line.split(":", 2);
			if (parts[0].equals("p")) {
				return ParseVector3f(parts[1]);
			}
		}
		return null;
	}
	
	public static Vector3f parseDirection(String data) {
		String[] lines = data.split("\n");
		for (String line : lines) {
			String[] parts = line.split(":", 2);
			if (parts[0].equals("d")) {
				return ParseVector3f(parts[1]);
			}
		}
		return null;
	}
	
	public static Vector3f ParseVector3f(String vector) {
		String[] parts = vector.replace("(","").replace(")","").split(",");
		float x = Float.parseFloat(parts[0]);
		float y = Float.parseFloat(parts[1]);
		float z = Float.parseFloat(parts[2]);
		return new Vector3f(x, y, z);
	}
	
	public static Double parseSpeed(String data) {
		String[] lines = data.split("\n");
		for (String line : lines) {
			String[] parts = line.split(":", 2);
			if (parts[0].equals("s")) {
				return Double.parseDouble( parts[1].replace(",",".") );

			}
		}
		return null;
	}
}
