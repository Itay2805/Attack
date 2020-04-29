package fuck.it.attack.gameplay;

import fuck.it.attack.core.Logger;
import fuck.it.attack.graphics.Camera;
import fuck.it.attack.graphics.TileMap;

public class Player {

	private float x, y;

	private Camera camera;

	public Player(Camera camera) {
		this.camera = camera;
	}

	public void update(float delta, TileMap map, float screenWidth, float screenHeight) {
		camera.update(delta);

		int[] hitLayer = map.getHitLayer();

		int playerX = (int)((camera.getPosition().x + screenWidth / 2.0f) / TileMap.TILE_SIZE);
		int playerY = (int)((camera.getPosition().y + screenHeight / 2.0f) / TileMap.TILE_SIZE);
		int playerI = playerX + playerY * map.getWidth();

		if (0 <= playerI && playerI <= hitLayer.length) {
			int currentTile = hitLayer[playerX + playerY * map.getWidth()];

			if (currentTile != 0) {
				// do collision my friend
				Logger.error("ERMAGAWD COLLISION");
			}
		}
	}

}
