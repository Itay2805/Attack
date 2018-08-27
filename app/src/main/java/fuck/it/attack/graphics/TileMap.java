package fuck.it.attack.graphics;

import fuck.it.attack.core.FileUtils;
import fuck.it.attack.graphics.sprite.Sprite;
import fuck.it.attack.graphics.sprite.SpriteSheet;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.IllegalFormatException;

public class TileMap {

	public static final float TILE_SIZE = 128;

	public enum Layer {
		BACK,
		HIT,
		OVER,
		DATA
	}

	private int backLayer[];
	private int hitLayer[];
	private int overLayer[];
	private int dataLayer[];

	private SpriteSheet spriteSheet;
	private Sprite[] sprites;

	public String tileset;
	private Color maskColor;
	private int width, height;

	private int playerX, playerY;

	private ByteBuffer data;

	public TileMap(String file) {
		data = ByteBuffer.wrap(FileUtils.readFileBytes(file));
		data.order(ByteOrder.LITTLE_ENDIAN);

		// header
		//expectByte((byte)23, "Header 1");
		//expectByte((byte)179, "Header 2");
		//expectByte((byte)243, "Header 3");
		//expectByte((byte)56, "Header 4");
		data.get();
		data.get();
		data.get();
		data.get();

		// version
		expectFloat(1.3f, "Version");

		// misc, empty
		for(int i = 0; i < 256; i++) {
			data.get();
		}

		// Tileset settings, empty
		for(int i = 0; i < 256; i++) {
			data.get();
		}

		// mask RED
		byte red = data.get();
		// mask GREEN
		byte green = data.get();
		// mask BLUE
		byte blue = data.get();
		maskColor = new Color(red / 255.0f, green / 255.0f, blue / 255.0f);

		// null byte, not in use
		data.get();

		// advanced settings (empty)
		for(int i = 0; i < 40; i++) {
			data.get();
		}

		// tileset name
		tileset = "";
		boolean finishedName = false;
		for(int i = 0; i < 256; i++){
			char c = (char)data.get();
			if(finishedName) continue;
			if(c == 0) {
				finishedName = true;
				continue;
			}
			tileset += c;
		}

		// how many tiles were stored, not important for us
		data.getInt();

		// tile width and height in pixels, not important for us
		data.getInt();
		data.getInt();

		// width and height
		width = data.getInt();
		height = data.getInt();

		// BACK layer check
		expectByte((byte)254, "BACK layer check 1");
		expectByte((byte)45, "BACK layer check 2");
		expectByte((byte)12, "BACK layer check 3");
		expectByte((byte)166, "BACK layer check 4");

		backLayer = new int[width * height];
		for(int i = 0; i < width * height; i++) {
			backLayer[i] = data.getInt();
		}

		// HIT layer check
		expectByte((byte)253, "HIT layer check 1");
		expectByte((byte)44, "HIT layer check 2");
		expectByte((byte)11, "HIT layer check 3");
		expectByte((byte)165, "HIT layer check 4");

		hitLayer = new int[width * height];
		for(int i = 0; i < width * height; i++) {
			hitLayer[i] = data.getInt();
		}

		// OVER layer check
		expectByte((byte)252, "OVER layer check 1");
		expectByte((byte)43, "OVER layer check 2");
		expectByte((byte)10, "OVER layer check 3");
		expectByte((byte)164, "OVER layer check 4");

		overLayer = new int[width * height];
		for(int i = 0; i < width * height; i++) {
			overLayer[i] = data.getInt();
		}

		// BACK layer check
		expectByte((byte)251, "DATA layer check 1");
		expectByte((byte)42, "DATA layer check 2");
		expectByte((byte)9, "DATA layer check 3");
		expectByte((byte)163, "DATA layer check 4");

		dataLayer = new int[width * height];
		for(int i = 0; i < width * height; i++) {
			dataLayer[i] = data.getInt();
		}

		// Load tilemap
		spriteSheet = new SpriteSheet(tileset);
		sprites = new Sprite[spriteSheet.getCols() * spriteSheet.getRows()];
		for(int y = 0; y < spriteSheet.getRows(); y++) {
			for(int x = 0; x < spriteSheet.getCols(); x++) {
				sprites[x + y * spriteSheet.getCols()] = new Sprite(0, 0, TileMap.TILE_SIZE, TileMap.TILE_SIZE, spriteSheet, x, y);
			}
		}
	}

	public int[] getLayer(Layer layer) {
		switch(layer) {
			case BACK: return backLayer;
			case HIT: return hitLayer;
			case DATA: return dataLayer;
			case OVER: return overLayer;
		}
		return null;
	}

	public Sprite[] getSprites() {
		return sprites;
	}

	public SpriteSheet getSpriteSheet() {
		return spriteSheet;
	}

	private void expectFloat(float b, String what) {
		float got = data.getFloat();
		if(got != b) {
			throw new RuntimeException("Expected " + what + "(float) " + Float.toString(b) + " , float: " + Float.toString(got));
		}
	}

	private void expectByte(byte b, String what) {
		byte got = data.get();
		if(got != b) {
			throw new RuntimeException("Expected " + what + "(byte) " + Byte.toString(b) + " , got: " + Byte.toString(got));
		}
	}

	public void setPlayerPosition(int x, int y) {
		playerX = x;
		playerY = y;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public ByteBuffer getData() {
		return data;
	}

	public Color getMaskColor() {
		return maskColor;
	}

	public int[] getBackLayer() {
		return backLayer;
	}

	public int[] getDataLayer() {
		return dataLayer;
	}

	public int[] getHitLayer() {
		return hitLayer;
	}

	public int[] getOverLayer() {
		return overLayer;
	}

	public String getTileset() {
		return tileset;
	}

	public int getPlayerX() {
		return playerX;
	}

	public int getPlayerY() {
		return playerY;
	}
}
