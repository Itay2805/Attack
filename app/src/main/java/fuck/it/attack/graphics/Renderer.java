package fuck.it.attack.graphics;

import org.joml.Vector2f;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import static android.opengl.GLES30.*;

public class Renderer {

	public static final int NUM_SPRITES = 10000;
	public static final int VERTEX_SIZE = 3 + 2 + 1 + 1; // position, uvs, color, texId
	public static final int VERTEX_SIZE_IN_BYTES = VERTEX_SIZE * 4;
	public static final int INDICES_COUNT = NUM_SPRITES * 6;
	public static final int VERTEX_BUFFER_SIZE = VERTEX_SIZE * NUM_SPRITES;

	private int vbo;
	private int ibo;
	private int vao;

	private int indicesCount = 0;

	private FloatBuffer vboData;

	private Shader shader;

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

		vboData.put(new float[]{sprite.x, sprite.y, 0});
		vboData.put(new float[]{uv1.x, uv1.y});
		vboData.put(new float[]{sprite.color.toFloat()});
		vboData.put(new float[]{0});

		vboData.put(new float[]{sprite.x + sprite.width, sprite.y, 0});
		vboData.put(new float[]{uv1.x + uv2.x, uv1.y});
		vboData.put(new float[]{sprite.color.toFloat()});
		vboData.put(new float[]{0});

		vboData.put(new float[]{sprite.x + sprite.width, sprite.y + sprite.height, 0});
		vboData.put(new float[]{uv1.x + uv2.x, uv1.y + uv2.y});
		vboData.put(new float[]{sprite.color.toFloat()});
		vboData.put(new float[]{0});

		vboData.put(new float[]{sprite.x, sprite.y + sprite.height, 0});
		vboData.put(new float[]{uv1.x, uv1.y + uv2.y});
		vboData.put(new float[]{sprite.color.toFloat()});
		vboData.put(new float[]{0});

		indicesCount += 6;
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

		glDrawElements(GL_TRIANGLES, indicesCount, GL_UNSIGNED_INT, 0);

		glBindBuffer(GL_ARRAY_BUFFER, 0);
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
		glBindVertexArray(0);
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
}
