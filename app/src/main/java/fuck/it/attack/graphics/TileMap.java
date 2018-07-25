package fuck.it.attack.graphics;

import fuck.it.attack.graphics.sprite.Sprite;

public class TileMap {

	public static final float TILE_SIZE = 128;

	private int tileIds[];
	private int width, height;
	private int playerX, playerY;

	private Sprite sprites[];

	public TileMap(Sprite sprites[], int width, int height) {
		this.width = width;
		this.height = height;
		tileIds = new int[width * height];
		this.sprites = new Sprite[sprites.length];
		System.arraycopy(sprites, 0, this.sprites, 0, sprites.length);
		playerX = width / 2;
		playerY = height / 2;
	}

	public void setTileIds(int tileIds[]) {
		System.arraycopy(tileIds, 0, this.tileIds, 0, tileIds.length);
	}

	public void setTileId(int tileId, int posX, int posY) {
		tileIds[posX + posY * height] = tileId;
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

	public int[] getTiles() {
		return tileIds;
	}

	public Sprite[] getSprites() {
		return sprites;
	}

	public int getPlayerX() {
		return playerX;
	}

	public int getPlayerY() {
		return playerY;
	}
}
