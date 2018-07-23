package fuck.it.attack.core.input;

public class EventListener {

	private String name;

	protected EventListener(String name) {
		this.name = name;
	}

	public boolean onSingleTapUp(Event e) {
		return false;
	}

	public boolean onLongPress(Event e) {
		return false;
	}

	public boolean onScroll(Event e1, Event e2, float distanceX, float distanceY) {
		return false;
	}

	public boolean onFling(Event e1, Event e2, float velocityX, float velocityY) {
		return false;
	}

	public boolean onShowPress(Event e) {
		return false;
	}

	public boolean onDown(Event e) {
		return false;
	}

	public boolean onDoubleTap(Event e) {
		return false;
	}

	public boolean onDoubleTapEvent(Event e) {
		return false;
	}

	public boolean onSingleTapConfirmed(Event e) {
		return false;
	}

	public boolean onContextClick(Event e) {
		return false;
	}

	public String getName() {
		return name;
	}
}
