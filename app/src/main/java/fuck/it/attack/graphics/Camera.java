package fuck.it.attack.graphics;

import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;

public class Camera {

	private static final Matrix4f viewMatrix = new Matrix4f();
	private float moveFactorX = 10.0f; // 10 pixels units per second
	private float moveFactorY = 10.0f;
	private Vector2f position;
	private float rotationZ;
	private Vector2f move = new Vector2f();

	public Camera(Vector2f position) {
		this.position = new Vector2f(position.x, position.y);
	}

	public Camera() {
		position = new Vector2f(0.0f, 0.0f);
	}

	public void update(float delta) {
		position.x += moveFactorX * move.x * delta;
		position.y -= moveFactorY * move.y * delta;
	}

	public Matrix4f getViewMatrix() {
		viewMatrix.identity();
		//viewMatrix.translate(position.x, position.y, 0.0f);
		viewMatrix.translate(0, 0, 0.0f);
		return viewMatrix;
	}

	public void setMove(float moveX, float moveY) {
		move.x = moveX;
		move.y = moveY;
	}

	public void setMoveFactor(float moveFactorX, float moveFactorY) {
		this.moveFactorX = moveFactorX;
		this.moveFactorY = moveFactorY;
	}

	public Vector2f getPosition() {
		return position;
	}
}
