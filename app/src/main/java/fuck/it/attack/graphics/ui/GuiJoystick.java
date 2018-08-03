package fuck.it.attack.graphics.ui;

import android.os.Handler;

import org.joml.Vector2f;

import fuck.it.attack.core.Logger;
import fuck.it.attack.core.input.Event;
import fuck.it.attack.graphics.Texture;
import fuck.it.attack.graphics.sprite.Sprite;

public class GuiJoystick extends GuiElement {

	Vector2f innerCirclePosition;

	Vector2f innerMove;

	Vector2f move;

	GuiJoystickMovedListener listener;

	float innerRadius;
	float outerRadius;

	boolean currentlyClicked = false;

	public GuiJoystick(String name, float x, float y, float outerRadius, float innerRadius) {
		super(name, x, y, outerRadius * 2, outerRadius * 2);

		this.innerRadius = innerRadius;
		this.outerRadius = outerRadius;

		move = new Vector2f();
		innerMove = new Vector2f();
		innerCirclePosition = new Vector2f();

		innerCirclePosition.x = x + (outerRadius - innerRadius) /* 2.0f*/;
		innerCirclePosition.y = y + (outerRadius - innerRadius) /* 2.0f*/;
		spriteList.add(new Sprite(innerCirclePosition.x, innerCirclePosition.y, innerRadius * 2, innerRadius * 2));
	}


	public void setInnerTexture(Texture texture) {
		spriteList.get(1).setTexture(texture);
	}

	public void setOuterTexture(Texture texture) {
		spriteList.get(0).setTexture(texture);
	}

	@Override
	public boolean onDown(Event e) {
		Logger.debug("Clicked!");
		innerMove.x = e.x - innerCirclePosition.x - innerRadius;
		innerMove.y = e.y - innerCirclePosition.y - innerRadius;

		//clamp innerMove

		if (innerMove.x <= -outerRadius || innerMove.x >= outerRadius || innerMove.y <= -outerRadius || innerMove.y >= outerRadius) {
			innerMove.normalize();
			innerMove.mul(outerRadius);
		}

		if (listener != null) {
			move.x = innerMove.x / outerRadius;
			move.y = -innerMove.y / outerRadius;
			if(move.length() > 1)
				move.normalize();
			listener.onMoved(move.x, move.y);
		}

		updateInnerCircle();

		currentlyClicked = true;
		return true;
	}

	@Override
	public void onUp(Event e) {
		Logger.debug("Un-Clicked!");
		returnHandleToCenter();
		currentlyClicked = false;
		if (listener != null) {
			listener.onReleased();
		}
	}

	@Override
	public boolean contains(Event pos) {
		double innerCircleDiffX = (pos.x - this.innerCirclePosition.x - innerRadius);
		double innerCircleDiffY = (pos.y - this.innerCirclePosition.y - innerRadius);

		double outerCircleDiffX = (pos.x - this.position.x - outerRadius);
		double outerCircleDiffY = (pos.y - this.position.y - outerRadius);


		return  // the mouse position is in the inner circle, which can move
				(innerCircleDiffX * innerCircleDiffX + innerCircleDiffY * innerCircleDiffY) < (innerRadius * innerRadius)
				||
				// the mouse position is in the outer circle
				(outerCircleDiffX * outerCircleDiffX + outerCircleDiffY * outerCircleDiffY) < (outerRadius * outerRadius)
				||
				// the joystick is already currently clicked
				currentlyClicked;
	}

	public void setOnJostickMovedListener(GuiJoystickMovedListener listener) {
		this.listener = listener;
	}

	private void returnHandleToCenter() {
		Handler handler = new Handler();
		final int numberOfFrames = 5;
		final double intervalsX = -innerMove.x / numberOfFrames;
		final double intervalsY = -innerMove.y / numberOfFrames;

		for (int i = 0; i < numberOfFrames; i++) {
			handler.postDelayed(new Runnable() {
				@Override
				public void run() {
					innerMove.x += intervalsX;
					innerMove.y += intervalsY;
					updateInnerCircle();
				}
			}, i * 40);
		}
	}

	private void updateInnerCircle() {
		spriteList.get(1).x = innerCirclePosition.x + innerMove.x;
		spriteList.get(1).y = innerCirclePosition.y + innerMove.y;
	}
}
