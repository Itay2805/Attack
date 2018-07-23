package fuck.it.attack.graphics.ui;

import org.joml.Matrix4f;

import fuck.it.attack.graphics.Renderer;

public class GuiLayer {

	private Renderer renderer;

	public GuiLayer(Matrix4f projectionMatrix) {
		renderer = new Renderer();
		renderer.setProjectionMatrix(projectionMatrix);
	}

	public void begin() {
		renderer.begin();
	}

	public void submit(GuiElement element) {
		element.submit(renderer);
	}

	public void draw() {
		renderer.end();
		renderer.draw();
	}

}
