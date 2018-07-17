package fuck.it.attack;

import android.opengl.GLSurfaceView;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import fuck.it.attack.core.Logger;
import fuck.it.attack.graphics.Shader;
import fuck.it.attack.graphics.Texture;

import static android.opengl.GLES30.*;

public class MainRenderer implements GLSurfaceView.Renderer {

	private final double ns = 1000000000.0 / 60.0;
	private long lastTime = System.nanoTime();
	private long timer = System.currentTimeMillis();
	private double delta = 0;
	private int frames = 0;
	private int updates = 0;

	private Shader shader;

	private int vbo;
	private int ibo;

	private Texture texture;

	@Override
	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		glClearColor(0.3f, 0.4f, 0.7f, 1.0f);
		shader = new Shader("basicShader.vert", "basicShader.frag");
		shader.start();

		texture = Texture.createTexture("icon.png");

		glActiveTexture(GL_TEXTURE0);
		texture.bind();

		shader.setInt("tex", 0);

		final int[] buffers = new int[2];
		glGenBuffers(2, buffers, 0);
		vbo = buffers[0];
		ibo = buffers[1];
		glBindBuffer(GL_ARRAY_BUFFER, vbo);
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, ibo);

		float[] vertices = new float[]{
				-0.5f, -0.5f, 0.0f, 0.0f,
				 0.5f, -0.5f, 1.0f, 0.0f,
				 0.5f,  0.5f, 1.0f, 1.0f,
				-0.5f,  0.5f, 0.0f, 1.0f
		};

		int[] indices = new int[] {
			0, 1, 2,
			0, 2, 3
		};

		FloatBuffer buf = FloatBuffer.allocate(vertices.length);
		buf.put(vertices);
		buf.flip();

		IntBuffer indx = IntBuffer.allocate(indices.length);
		indx.put(indices);
		indx.flip();

		glBufferData(GL_ARRAY_BUFFER, buf.capacity() * 4, buf, GL_STATIC_DRAW);
		glEnableVertexAttribArray(0);
		glEnableVertexAttribArray(1);
		glVertexAttribPointer(0, 2, GL_FLOAT, false, 4 * 4, 0);
		glVertexAttribPointer(1, 2, GL_FLOAT, false, 4 * 4, 2 * 4);

		glBufferData(GL_ELEMENT_ARRAY_BUFFER, indx.capacity() * 4, indx, GL_STATIC_DRAW);

	}

	@Override
	public void onSurfaceChanged(GL10 gl, int width, int height) {

	}

	@Override
	public void onDrawFrame(GL10 gl) {
		long now = System.nanoTime();
		delta += (now - lastTime) / ns;
		lastTime = now;

		while (delta >= 1) {
			update();
			updates++;
			delta--;
		}
		render();
		frames++;

		if (System.currentTimeMillis() - timer > 1000) {
			tick();
			timer += 1000;
			Logger.debug("[FPS] fps: " + frames + ", ups: " + updates);
			frames = 0;
			updates = 0;
		}
	}

	// once a second
	public void tick() {

	}

	// 60 times a second
	public void update() {

	}

	// as fast as possible I guess
	public void render() {
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
		glDrawElements(GL_TRIANGLES, 6, GL_UNSIGNED_INT, 0);
	}


}
