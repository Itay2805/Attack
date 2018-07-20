package fuck.it.attack.graphics;

import org.joml.Vector2f;

public class Sprite {

	public float x, y;
	public float width, height;
	public Color color;
	public Texture texture;
	public boolean hasTexture = false;

	private Vector2f uv1;
	private Vector2f uv2;

	public Sprite(float x, float y, float width, float height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		uv1 = new Vector2f(0, 0);
		uv2 = new Vector2f(1, 1);
	}

	public Sprite(float x, float y, float width, float height, Color color) {
		this(x, y, width, height);
		this.color = color;
		hasTexture = false;
	}

	public Sprite(float x, float y, float width, float height, Texture texture) {
		this(x, y, width, height);
		this.texture = texture;
		hasTexture = true;
	}

	public Sprite(float x, float y, float width, float height, SpriteSheet sheet, int spritePosX, int spritePosY) {
		this(x, y, width, height, sheet.getTexture());
		uv1 = sheet.getUv1(spritePosX, spritePosY);
		uv2 = sheet.getUv2(spritePosX, spritePosY);
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

	public Vector2f getUv1() {
		return uv1;
	}

	public Vector2f getUv2() {
		return uv2;
	}

	public float getColorFloat() {
		if (color == null) {
			return 0.0f;
		} else {
			return color.toFloat();
		}
	}
}
