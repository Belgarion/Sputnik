package network;

import java.util.UUID;

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
}
