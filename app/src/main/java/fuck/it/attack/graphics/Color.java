package fuck.it.attack.graphics;

import java.nio.ByteBuffer;
import java.util.HashMap;

public class Color {

	private static final HashMap<String, Color> COLORS = new HashMap<>();

	public static void loadColors() {
		COLORS.put("red", new Color(1, 0, 0));
		COLORS.put("green", new Color(0, 1, 0));
		COLORS.put("blue", new Color(0, 0, 1));
	}

	public static Color getColor(String name) {
		return COLORS.get(name);
	}

	private byte r, g, b, a;

	public Color(float r, float g, float b) {
		this.r = (byte) (r * 255);
		this.g = (byte) (g * 255);
		this.b = (byte) (b * 255);
		this.a = (byte) 255;
	}

	public Color(float r, float g, float b, float a) {
		this.r = (byte) (r * 255);
		this.g = (byte) (g * 255);
		this.b = (byte) (b * 255);
		this.a = (byte) (a * 255);
	}

	public Color(int argb) {
		// pls work
		b = (byte) (argb >> 0 & 0xFF);
		g = (byte) (argb >> 8 & 0xFF);
		r = (byte) (argb >> 16 & 0xFF);
		a = (byte) (argb >> 24 & 0xFF);
	}

	public Color(Color other) {
		this.r = other.r;
		this.g = other.g;
		this.b = other.b;
		this.a = other.a;
	}

	public float toFloat() {
		return ByteBuffer.wrap(new byte[]{a, b, g, r}, 0, 4).getFloat();
	}

}
