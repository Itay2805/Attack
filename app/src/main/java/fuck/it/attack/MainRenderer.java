package fuck.it.attack;

import android.opengl.GLSurfaceView;

import org.joml.Matrix4f;

import java.util.Random;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import fuck.it.attack.graphics.Camera;
import fuck.it.attack.graphics.Color;
import fuck.it.attack.graphics.Font;
import fuck.it.attack.graphics.Renderer;
import fuck.it.attack.graphics.TileMap;
import fuck.it.attack.graphics.sprite.AnimatedSprite;
import fuck.it.attack.graphics.sprite.Sprite;
import fuck.it.attack.graphics.sprite.SpriteSheet;
import fuck.it.attack.graphics.ui.GuiLabel;
import fuck.it.attack.graphics.ui.GuiRenderer;
import fuck.it.attack.joystick.JoystickMovedListener;

import static android.opengl.GLES30.*;

public class MainRenderer implements GLSurfaceView.Renderer {

	private static final int WIDTH = 1920;
	private static final int HEIGHT = 1080;
	private final double ns = 1000000000.0 / 60.0;
	private long lastTime = System.nanoTime();
	private long timer = System.currentTimeMillis();
	private double delta = 0;
	private float nsLastFrame = 0;
	private int frames = 0;
	private int updates = 0;

	private GuiRenderer renderer;
	private Renderer worldRenderer;
	private Camera camera;
	private Sprite sprite;
	private AnimatedSprite animatedSprite;
	private SpriteSheet spriteSheet;
	private SpriteSheet spriteSheet2;

	private TileMap tileMap;

	@Override
	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		glClearColor(0.3f, 0.4f, 0.7f, 1.0f);

		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

		renderer = new GuiRenderer(WIDTH, HEIGHT);

		spriteSheet = new SpriteSheet("spritesheet.png");
		spriteSheet2 = new SpriteSheet("rainbow.png");

		animatedSprite = new AnimatedSprite(WIDTH / 2, HEIGHT / 2, 64.0f, 64.0f, spriteSheet2, 0, 0, 16);

		Font font = new Font(spriteSheet, "abcd", 64);
		GuiLabel label = new GuiLabel("label", 0, HEIGHT / 2, "abcd", font);
		label.setBackgroundColor(new Color(0.5f, 0.5f, 0.5f));
		renderer.submit(label);

		worldRenderer = new Renderer();
		worldRenderer.setProjectionMatrix(new Matrix4f().ortho(0, WIDTH, 0, HEIGHT, 1.0f, -1.0f));
		camera = new Camera();
		camera.setMoveFactor(64.0f * 5.0f, 64.0f * 5.0f); // 5 sprites per second, a sprite is 64 units

		MainActivity.getRightJoystick().setOnJostickMovedListener(new JoystickMovedListener() {
			@Override
			public void onMoved(float pan, float tilt) {
				camera.setMove(pan, tilt);
			}

			@Override
			public void onReleased() {
				camera.setMove(0, 0);
			}
		});

		Sprite sprites[] = new Sprite[3];
		sprites[0] = new Sprite(0, 0, 0, 0, new Color(0, 0.4f, 0.2f));
		sprites[1] = new Sprite(0, 0, 0, 0, new Color(1f, 0.4f, 0.8f));
		sprites[2] = new Sprite(0, 0, 0, 0, new Color(0.4f, 0.2f, 0.9f));
		tileMap = new TileMap(sprites, 64, 64);

		int tileIds[] = new int[64 * 64];
		Random random = new Random();

		for(int i=0; i < 64 * 64; i++) {
			tileIds[i] = random.nextInt(3);
		}

		tileMap.setTileIds(tileIds);
	}

	@Override
	public void onSurfaceChanged(GL10 gl, int width, int height) {
		glViewport(0, 0, width, height);
		renderer.setSurfaceSize(width, height);
	}

	@Override
	public void onDrawFrame(GL10 gl) {
		long now = System.nanoTime();
		delta += (now - lastTime) / ns;
		nsLastFrame = (float) (now - lastTime) / 1000000000.0f;
		lastTime = now;

		while (delta >= 1) {
			update(nsLastFrame);
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
		animatedSprite.nextAnimation();

	}

	// 60 times a second
	public void update(double delta) {
		camera.update((float) delta);
	}

	// as fast as possible I guess
	public void render() {
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
		renderer.draw();

		worldRenderer.setViewMatrix(camera.getViewMatrix());
		worldRenderer.begin();
		worldRenderer.submit(animatedSprite);
		worldRenderer.submit(tileMap, camera, WIDTH, HEIGHT);
		worldRenderer.end();
		worldRenderer.draw();
	}

	public void cleanUp() {
		renderer.cleanUp();
		worldRenderer.cleanUp();
		spriteSheet.cleanUp();
		spriteSheet2.cleanUp();
	}
}
