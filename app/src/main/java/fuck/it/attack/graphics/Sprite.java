package fuck.it.attack.graphics;

public class Sprite {

	public float x, y;
	public float width, height;
	public Color color;
	public Texture texture;
	public boolean hasTexture = false;


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
		hasTexture = false;
	}

	public Sprite(float x, float y, float width, float height, Texture texture) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.texture = texture;
		hasTexture = true;
	}

	public void setColor(Color color) {
		this.color = color;
		hasTexture = false;
	}

	public void setTexture(Texture texture) {
		this.texture = texture;
		hasTexture = true;
	}
}
