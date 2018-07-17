package fuck.it.attack;

import android.opengl.GLSurfaceView;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.games.Games;
import com.google.android.gms.games.RealTimeMultiplayerClient;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import fuck.it.attack.core.Logger;
import fuck.it.attack.graphics.Shader;

public class MainRenderer implements GLSurfaceView.Renderer {

	private final double ns = 1000000000.0 / 60.0;
	private long lastTime = System.nanoTime();
	private long timer = System.currentTimeMillis();
	private double delta = 0;
	private int frames = 0;
	private int updates = 0;

	private Shader shader;

	@Override
	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		shader = new Shader("basicShader.vert", "basicShader.frag");
		shader.start();
		shader.stop();
		// lol
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

	}
}
