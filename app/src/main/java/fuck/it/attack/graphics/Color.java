package fuck.it.attack.graphics;

import java.nio.ByteBuffer;

public class Color {

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
