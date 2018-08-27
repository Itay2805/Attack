package fuck.it.attack.graphics;

import android.service.quicksettings.Tile;
import fuck.it.attack.graphics.sprite.SpriteSheet;
import org.joml.Matrix4f;
import org.joml.Vector2f;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

import fuck.it.attack.graphics.sprite.Sprite;

import static android.opengl.GLES30.*;

public class Renderer {

	private static final int NUM_SPRITES = 1000;
	private static final int VERTEX_SIZE = 3 + 2 + 1 + 1; // position, uvs, color, texId
	private static final int VERTEX_SIZE_IN_BYTES = VERTEX_SIZE * 4;
	private static final int INDICES_COUNT = NUM_SPRITES * 6;
	private static final int VERTEX_BUFFER_SIZE = VERTEX_SIZE * NUM_SPRITES;
	private static final int MAX_TEXTURES = 16;

	private int vbo;
	private int ibo;
	private int vao;

	private int indicesCount = 0;

	private FloatBuffer vboData;

	private Shader shader;

	private List<Texture> textures = new ArrayList<>();
	private float[] textureIds;

	public Renderer() {
		final int[] buffers = new int[3];
		glGenBuffers(2, buffers, 0);
		glGenVertexArrays(1, buffers, 2);

		vbo = buffers[0];
		ibo = buffers[1];
		vao = buffers[2];

		glBindVertexArray(vao);

		glBindBuffer(GL_ARRAY_BUFFER, vbo);

		vboData = FloatBuffer.allocate(VERTEX_BUFFER_SIZE);
		vboData.clear();
		vboData.flip();

		glBufferData(GL_ARRAY_BUFFER, VERTEX_BUFFER_SIZE * 4, null, GL_STATIC_DRAW);

		glEnableVertexAttribArray(0); // position
		glEnableVertexAttribArray(1); // uvs
		glEnableVertexAttribArray(2); // color
		glEnableVertexAttribArray(3); // tId

		glVertexAttribPointer(0, 3, GL_FLOAT, false, VERTEX_SIZE_IN_BYTES, 0);
		glVertexAttribPointer(1, 2, GL_FLOAT, false, VERTEX_SIZE_IN_BYTES, 3 * 4);
		glVertexAttribPointer(2, 4, GL_UNSIGNED_BYTE, true, VERTEX_SIZE_IN_BYTES, (3 + 2) * 4);
		glVertexAttribPointer(3, 1, GL_FLOAT, false, VERTEX_SIZE_IN_BYTES, (3 + 2 + 1) * 4);

		glBindVertexArray(0);

		IntBuffer iboData = IntBuffer.allocate(INDICES_COUNT);

		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, ibo);


		int offset = 0;

		for (int i = 0; i < NUM_SPRITES; i++) {
			iboData.put(offset + 0);
			iboData.put(offset + 1);
			iboData.put(offset + 2);
			iboData.put(offset + 2);
			iboData.put(offset + 3);
			iboData.put(offset + 0);

			offset += 4;
		}

		iboData.flip();
		glBufferData(GL_ELEMENT_ARRAY_BUFFER, iboData.capacity() * 4, iboData, GL_STATIC_DRAW);

		shader = new Shader("batchrenderer.vert", "batchrenderer.frag");
		shader.start();

		int[] textureIds = {
				0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15
		};
		shader.setInts("textures[0]", textureIds);
	}

	public void begin() {
		indicesCount = 0;
		glBindBuffer(GL_ARRAY_BUFFER, vbo);
		vboData = ((ByteBuffer) glMapBufferRange(GL_ARRAY_BUFFER, 0, VERTEX_BUFFER_SIZE * 4, GL_MAP_WRITE_BIT))
				.order(ByteOrder.nativeOrder()).asFloatBuffer();
	}

	public void submit(Sprite sprite) {
		Vector2f uv1 = sprite.getUv1();
		Vector2f uv2 = sprite.getUv2();

		float textureId;

		if (sprite.hasTexture()) {
			textureId = submitTexture(sprite.texture);
		} else {
			textureId = 0;
		}

		vboData.put(sprite.x);
		vboData.put(sprite.y);
		vboData.put(0);
		vboData.put(uv1.x);
		vboData.put(uv1.y);
		vboData.put(sprite.getColorFloat());
		vboData.put(textureId);

		vboData.put(sprite.x + sprite.width);
		vboData.put(sprite.y);
		vboData.put(0);
		vboData.put(uv2.x);
		vboData.put(uv1.y);
		vboData.put(sprite.getColorFloat());
		vboData.put(textureId);

		vboData.put(sprite.x + sprite.width);
		vboData.put(sprite.y + sprite.height);
		vboData.put(0);
		vboData.put(uv2.x);
		vboData.put(uv2.y);
		vboData.put(sprite.getColorFloat());
		vboData.put(textureId);

		vboData.put(sprite.x);
		vboData.put(sprite.y + sprite.height);
		vboData.put(0);
		vboData.put(uv1.x);
		vboData.put(uv2.y);
		vboData.put(sprite.getColorFloat());
		vboData.put(textureId);

		indicesCount += 6;
	}

	public void submit(Sprite[] sprites) {
		for (int i = 0; i < sprites.length; i++) {
			submit(sprites[i]);
		}
	}

	public void submit(List<Sprite> sprites) {
		for (int i = 0; i < sprites.size(); i++) {
			submit(sprites.get(i));
		}
	}

	public int submitBetter(TileMap tileMap, Camera camera, int screenWidth, int screenHeight, TileMap.Layer layer) {
		float offsetX = camera.getPosition().x;
		float offsetY = camera.getPosition().y;

		int startX = (int) (-offsetX / TileMap.TILE_SIZE);
		int startY = (int) (-offsetY / TileMap.TILE_SIZE);
		int endX = (int) (-offsetX / TileMap.TILE_SIZE + 1);
		int endY = (int) (-offsetY / TileMap.TILE_SIZE + 1);
		if(startX < 0)
			startX = 0;
		if(startY < 0)
			startY = 0;
		if(endX > screenWidth)
			endX = screenWidth;
		if(endY > screenHeight)
			endY = screenHeight;

		SpriteSheet sheet = tileMap.getSpriteSheet();
		Sprite[] sprites = tileMap.getSprites();
		float textureId = submitTexture(sheet.getTexture());

		int[] layerTiles = tileMap.getLayer(layer);

		int rendered = 0;
		for(int y = startY; y < endY; y++)
		{
			for(int x = startX; x < endX; x++)
			{
				int tileID = layerTiles[x + y * tileMap.getWidth()];

				int screenY = (int)(x * TileMap.TILE_SIZE + (offsetX > 0 ? 1 : -1) * (Math.abs(offsetX) % TileMap.TILE_SIZE));
				int screenX = (int)(x * TileMap.TILE_SIZE + (offsetY > 0 ? 1 : -1) * (Math.abs(offsetY) % TileMap.TILE_SIZE));

				// rendering code
				Vector2f uv1 = sprites[tileID].getUv1();
				Vector2f uv2 = sprites[tileID].getUv2();

				vboData.put(screenX);
				vboData.put(screenY);
				vboData.put(0);
				vboData.put(uv1.x);
				vboData.put(uv1.y);
				vboData.put(sprites[tileID].getColorFloat());
				vboData.put(textureId);

				vboData.put(screenX + TileMap.TILE_SIZE);
				vboData.put(screenY);
				vboData.put(0);
				vboData.put(uv2.x);
				vboData.put(uv1.y);
				vboData.put(sprites[tileID].getColorFloat());
				vboData.put(textureId);

				vboData.put(screenX + TileMap.TILE_SIZE);
				vboData.put(screenY + TileMap.TILE_SIZE);
				vboData.put(0);
				vboData.put(uv2.x);
				vboData.put(uv2.y);
				vboData.put(sprites[tileID].getColorFloat());
				vboData.put(textureId);

				vboData.put(screenX);
				vboData.put(screenY + TileMap.TILE_SIZE);
				vboData.put(0);
				vboData.put(uv1.x);
				vboData.put(uv2.y);
				vboData.put(sprites[tileID].getColorFloat());
				vboData.put(textureId);

				indicesCount += 6;

				rendered++;
			}
		}

		return rendered;
	}

	// The camera needs to be passed here in order to ensure the culling of the tilemap.
	public int submit(TileMap tileMap, Camera camera, int screenWidth, int screenHeight) {
		Sprite sprites[] = tileMap.getSprites();
		int tiles[] = tileMap.getBackLayer();

		float textureId = submitTexture(tileMap.getSpriteSheet().getTexture());

		float playerX = camera.getPosition().x;
		float playerY = camera.getPosition().y;

		int topTileX = (int) ((Math.floor(playerX / TileMap.TILE_SIZE)) - 1);
		int topTileY = (int) ((Math.floor(playerY / TileMap.TILE_SIZE)) - 1);

		int tilesRendered = 0;

		for (int y = topTileY; y < topTileY + Math.ceil(screenHeight / TileMap.TILE_SIZE) + 2; y++) {
			for (int x = topTileX; x < topTileX + Math.ceil(screenWidth / TileMap.TILE_SIZE) + 2; x++) {
				// if(x < 0 || x >= tileMap.getWidth() || y < 0 || y >= tileMap.getHeight()) continue;
				int i = x + y * tileMap.getWidth();
				if (i < 0 || i >= tileMap.getWidth() * tileMap.getHeight()) continue;
				int screenX = (int) (x * TileMap.TILE_SIZE - playerX);
				int screenY = (int) (y * TileMap.TILE_SIZE - playerY);
				if (screenX < -TileMap.TILE_SIZE || screenX >= screenWidth + TileMap.TILE_SIZE || screenY < -TileMap.TILE_SIZE || screenY >= screenHeight + TileMap.TILE_SIZE)
					continue;
				if (screenX < 0) screenX = 0;
				if (screenY < 0) screenY = 0;

				int tileID = tiles[i];

				tilesRendered++;

				// rendering code
				Vector2f uv1 = sprites[tileID].getUv1();
				Vector2f uv2 = sprites[tileID].getUv2();

				vboData.put(screenX);
				vboData.put(screenY);
				vboData.put(0);
				vboData.put(uv1.x);
				vboData.put(uv1.y);
				vboData.put(sprites[tileID].getColorFloat());
				vboData.put(textureId);

				vboData.put(screenX + TileMap.TILE_SIZE);
				vboData.put(screenY);
				vboData.put(0);
				vboData.put(uv2.x);
				vboData.put(uv1.y);
				vboData.put(sprites[tileID].getColorFloat());
				vboData.put(textureId);

				vboData.put(screenX + TileMap.TILE_SIZE);
				vboData.put(screenY + TileMap.TILE_SIZE);
				vboData.put(0);
				vboData.put(uv2.x);
				vboData.put(uv2.y);
				vboData.put(sprites[tileID].getColorFloat());
				vboData.put(textureId);

				vboData.put(screenX);
				vboData.put(screenY + TileMap.TILE_SIZE);
				vboData.put(0);
				vboData.put(uv1.x);
				vboData.put(uv2.y);
				vboData.put(sprites[tileID].getColorFloat());
				vboData.put(textureId);
			}
		}

		return tilesRendered;
	}

	public void end() {
		glUnmapBuffer(GL_ARRAY_BUFFER);
		vboData = null;
	}

	public void draw() {
		shader.start();
		glBindVertexArray(vao);
		glBindBuffer(GL_ARRAY_BUFFER, vbo);
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, ibo);

		for (int i = 0; i < textures.size(); i++) {
			glActiveTexture(GL_TEXTURE0 + i);
			textures.get(i).bind();
		}

		glDrawElements(GL_TRIANGLES, indicesCount, GL_UNSIGNED_INT, 0);
	}

	public void cleanUp() {
		glBindVertexArray(0);
		glBindBuffer(GL_ARRAY_BUFFER, 0);
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);

		final int[] buffers = {vbo, ibo, vao};

		glDeleteBuffers(2, buffers, 0);
		glDeleteVertexArrays(1, buffers, 2);
		shader.cleanUp();
	}

	private float submitTexture(Texture texture) {
		for (int i = 0; i < textures.size(); i++) {
			if (textures.get(i).getId() == texture.getId()) {
				return (float) (i + 1);
			}
		}
		if (textures.size() > MAX_TEXTURES) {
			end();
			draw();
			begin();
			submitTexture(texture);
		}
		textures.add(texture);
		return textures.size();
	}

	public void setProjectionMatrix(Matrix4f projectionMatrix) {
		shader.start();
		shader.setMat4("projectionMatrix", projectionMatrix);
	}

}
