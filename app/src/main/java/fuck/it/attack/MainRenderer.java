package fuck.it.attack;

import android.opengl.GLSurfaceView;

import org.joml.Matrix4f;

import java.util.Random;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import fuck.it.attack.core.Logger;
import fuck.it.attack.gameplay.Player;
import fuck.it.attack.graphics.Camera;
import fuck.it.attack.graphics.Color;
import fuck.it.attack.graphics.Font;
import fuck.it.attack.graphics.Renderer;
import fuck.it.attack.graphics.Texture;
import fuck.it.attack.graphics.TileMap;
import fuck.it.attack.graphics.sprite.AnimatedSprite;
import fuck.it.attack.graphics.sprite.Sprite;
import fuck.it.attack.graphics.sprite.SpriteSheet;
import fuck.it.attack.graphics.ui.GuiJoystick;
import fuck.it.attack.graphics.ui.GuiJoystickMovedListener;
import fuck.it.attack.graphics.ui.GuiLabel;
import fuck.it.attack.graphics.ui.GuiRenderer;

import static android.opengl.GLES30.*;

public class MainRenderer implements GLSurfaceView.Renderer {

	private static final boolean DEBUG = true;

	private static final int WIDTH = 1920;
	private static final int HEIGHT = 1080;
	private final double ns = 1000000000.0 / 60.0;
	private long lastTime = System.nanoTime();
	private long timer = System.currentTimeMillis();
	private double delta = 0;
	private float nsLastFrame = 0;
	private int frames = 0;
	private int updates = 0;
	private int avgRenderTime = 0;
	private int avgWorldRenderTime = 0;
	private int avgUpdateTime = 0;
	private int avgTilesDrawn = 0;

	private GuiRenderer renderer;
	private Renderer worldRenderer;
	private Camera camera;
	private Player player;
	private Sprite sprite;
	private AnimatedSprite animatedSprite;
	private SpriteSheet spriteSheet;
	private SpriteSheet spriteSheet2;

	private GuiJoystick joystick;

	private Texture textures[];

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

		Font font = new Font(spriteSheet, "ABCEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*()[]{}\"'|\\/-+=_~☺♥`<>.,;:D\u0000");
		GuiLabel label = new GuiLabel("label", 0, HEIGHT / 2, "Normal #[red] red\n#[reset]back to normal", font, 64);
		// label.setBackgroundColor(new Color(0.5f, 0.5f, 0.5f));

		joystick = new GuiJoystick("joystick", WIDTH - 400, 10, 190, 100);

		textures = new Texture[2];
		textures[0] = Texture.createTexture("joystickinner.png");
		textures[1] = Texture.createTexture("joystickouter.png");
		joystick.setInnerTexture(textures[0]);
		joystick.setOuterTexture(textures[1]);

		//joystick.setBackgroundColor(new Color(0, 0, 0, 0));

		renderer.submit(label);
		renderer.submit(joystick);


		worldRenderer = new Renderer();
		worldRenderer.setProjectionMatrix(new Matrix4f().ortho(0, WIDTH, 0, HEIGHT, 1.0f, -1.0f));
		camera = new Camera();
		camera.getPosition().x = 0;
		camera.getPosition().y = 0;
		camera.setMoveFactor(64.0f * 5.0f, 64.0f * 5.0f); // 5 sprites per second, a sprite is 64 units

		joystick.setOnJostickMovedListener(new GuiJoystickMovedListener() {
			@Override
			public void onMoved(float pan, float tilt) {
				camera.setMove(pan, tilt);
			}

			@Override
			public void onReleased() {
				camera.setMove(0, 0);
			}
		});

		tileMap = new TileMap("test.til");

		sprite = new Sprite(tileMap.getSprites()[0]);
		sprite.x = 0;
		sprite.y = 0;
		sprite.width = 128;
		sprite.height = 128;
		player = new Player(camera);
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
			avgRenderTime /= frames;
			avgWorldRenderTime /= frames;
			avgUpdateTime /= updates;
			avgTilesDrawn /= frames;
			tick();
			timer += 1000;
			avgTilesDrawn = 0;
			avgRenderTime = 0;
			avgUpdateTime = 0;
			avgWorldRenderTime = 0;
			frames = 0;
			updates = 0;
		}
	}

	// once a second
	public void tick() {
		animatedSprite.nextAnimation();
		Logger.debug("[FPS] fps: ", frames, "(gui: ", avgRenderTime, "mil, world: ", avgTilesDrawn, "/", avgWorldRenderTime, "mil), ups: ", updates, " (", avgUpdateTime, "mil)");
	}

	// 60 times a second
	public void update(double delta) {
		long start = System.currentTimeMillis();
		//camera.update((float) delta);
		player.update((float) delta, tileMap, WIDTH, HEIGHT);
		long end = System.currentTimeMillis();
		avgUpdateTime += end - start;
	}

	// as fast as possible I guess
	public void render() {
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

		long start = System.currentTimeMillis();
		worldRenderer.begin();
		avgTilesDrawn += worldRenderer.submit(tileMap, camera, WIDTH, HEIGHT, TileMap.Layer.BACK);

		worldRenderer.end();
		worldRenderer.draw();
		long end = System.currentTimeMillis();
		avgWorldRenderTime += end - start;

		start = System.currentTimeMillis();
		renderer.draw();
		end = System.currentTimeMillis();
		avgRenderTime += end - start;

	}

	public void cleanUp() {
		renderer.cleanUp();
		worldRenderer.cleanUp();
		spriteSheet.cleanUp();
		spriteSheet2.cleanUp();
		textures[0].cleanUp();
		textures[1].cleanUp();
	}
}
