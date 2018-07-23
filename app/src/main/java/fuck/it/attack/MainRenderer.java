package fuck.it.attack;

import android.opengl.GLSurfaceView;

import org.joml.Matrix4f;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import fuck.it.attack.graphics.Font;
import fuck.it.attack.graphics.Renderer;
import fuck.it.attack.graphics.Sprite;
import fuck.it.attack.graphics.SpriteSheet;
import fuck.it.attack.graphics.ui.GuiLabel;

import static android.opengl.GLES30.*;

public class MainRenderer implements GLSurfaceView.Renderer {

	private static final int WIDTH = 1920;
	private static final int HEIGHT = 1080;
	private final double ns = 1000000000.0 / 60.0;
	private long lastTime = System.nanoTime();
	private long timer = System.currentTimeMillis();
	private double delta = 0;
	private int frames = 0;
	private int updates = 0;
	private Renderer renderer;
	private Sprite[] sprites;
	private SpriteSheet sheet;
	private Font font;
	private GuiLabel label;

	@Override
	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		glClearColor(0.3f, 0.4f, 0.7f, 1.0f);

		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

		renderer = new Renderer();
		renderer.setProjectionMatrix(new Matrix4f().ortho(0, WIDTH, 0, HEIGHT, 1, -1));

		sheet = new SpriteSheet("spritesheet.png");
		// sprites = new Sprite[4];
		// sprites[0] = new Sprite(     0.0f   , HEIGHT / 2.0f, WIDTH / 2.0f, HEIGHT / 2.0f, sheet, 0, 0);
		// sprites[1] = new Sprite(WIDTH / 2.0f, HEIGHT / 2.0f, WIDTH / 2.0f, HEIGHT / 2.0f, sheet, 1, 0);
		// sprites[2] = new Sprite(     0.0f   ,      0.0f    , WIDTH / 2.0f, HEIGHT / 2.0f, sheet, 0, 1);
		// sprites[3] = new Sprite(WIDTH / 2.0f,      0.0f    , WIDTH / 2.0f, HEIGHT / 2.0f, sheet, 1, 1);

		font = new Font(sheet, "abcd", 64);
		label = new GuiLabel(0, HEIGHT / 2, "abcd", font);
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
			//Logger.debug("[FPS] fps: " + frames + ", ups: " + updates);
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
		renderer.begin();
		label.submit(renderer);
		// renderer.submit(sprites);
		renderer.end();
		renderer.draw();
	}

	public void cleanUp() {
		renderer.cleanUp();
	}
}
