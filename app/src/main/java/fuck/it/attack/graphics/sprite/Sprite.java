package fuck.it.attack.graphics.sprite;

import org.joml.Vector2f;

import fuck.it.attack.graphics.Color;
import fuck.it.attack.graphics.Texture;

public class Sprite {

	public float x, y;
	public float width, height;
	public Color color;
	public Texture texture;

	protected Vector2f uv1;
	protected Vector2f uv2;

	public Sprite(Sprite other) {
		this.x = other.x;
		this.y = other.y;
		this.width = other.width;
		this.height = other.height;

		if(other.color != null) this.color = new Color(other.color);
		if(other.texture != null) this.texture = other.texture;
		if(other.uv1 != null) this.uv1 = new Vector2f(other.uv1);
		if(other.uv2 != null) this.uv2 = new Vector2f(other.uv2);
	}

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
	}

	public Sprite(float x, float y, float width, float height, Texture texture) {
		this(x, y, width, height);
		this.texture = texture;
	}

	public Sprite(float x, float y, float width, float height, SpriteSheet sheet, int spritePosX, int spritePosY) {
		this(x, y, width, height, sheet.getTexture());
		uv1 = sheet.getUv1(spritePosX, spritePosY);
		uv2 = sheet.getUv2(spritePosX, spritePosY);
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public void setTexture(Texture texture) {
		this.texture = texture;
	}

	public final boolean hasTexture() {
		return texture != null;
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
