package fuck.it.attack.core.input;

import android.view.MotionEvent;

public class Event {
	public float x, y;

	public Event() {
		x = -1;
		y = -1;
	}

	public Event(Event other) {
		x = other.x;
		y = other.y;
	}

	public Event(float x, float y) {
		this.x = x;
		this.y = y;
	}

	public void set(MotionEvent e) {
		x = e.getX();
		y = e.getY();
	}

}
