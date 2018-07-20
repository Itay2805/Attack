package fuck.it.attack.graphics;

import org.joml.Vector2f;

public class SpriteSheet {

	private static final int SPRITE_SIZE_WIDTH = 32;
	private static final int SPRITE_SIZE_HEIGHT = 32;

	private Texture texture;

	public SpriteSheet(String path) {
		texture = Texture.createTexture(path);
	}

	public void cleanUp() {
		texture.cleanUp();
	}

	public Vector2f getUv1(int indexX, int indexY) {
		Vector2f uv = new Vector2f();
		uv.x = ((float) indexX * SPRITE_SIZE_WIDTH) / (float) texture.getWidth();
		uv.y = (((float) indexY + 1) * SPRITE_SIZE_HEIGHT - 1) / (float) texture.getHeight();
		return uv;
	}

	public Vector2f getUv2(int indexX, int indexY) {
		Vector2f uv = new Vector2f();
		uv.x = (((float) indexX + 1) * SPRITE_SIZE_WIDTH - 1) / (float) texture.getWidth();
		uv.y = (((float) indexY) * SPRITE_SIZE_HEIGHT) / (float) texture.getHeight();
		return uv;
	}

	public Texture getTexture() {
		return texture;
	}
}
