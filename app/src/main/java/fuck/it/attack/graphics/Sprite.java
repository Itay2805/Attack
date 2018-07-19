package fuck.it.attack.graphics;

public class Sprite {

	public float x, y;
	public float width, height;
	public Color color;
	//public Texture texture;

	public Sprite(float x, float y, float width, float height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}

	public Sprite(float x, float y, float width, float height, Color color) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.color = color;
	}
}
