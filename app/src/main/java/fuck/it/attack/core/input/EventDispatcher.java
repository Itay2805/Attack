package fuck.it.attack.core.input;

import android.app.Activity;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class EventDispatcher extends GestureDetector.SimpleOnGestureListener {

	private static List<EventListener> listeners = new ArrayList<>();
	private static EventDispatcher instance = null;

	private GestureDetector detector;

	private EventDispatcher(Activity activity, View view) {
		view.setOnTouchListener(new View.OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if(event.getAction() != 1){
					v.performClick();
					return detector.onTouchEvent(event);
				}
				instance.onUp(event);
				return true;
			}
		});

		instance = this;
	}

	public static void init(Activity activity, View view) {
		if (instance == null) {
			new EventDispatcher(activity, view);
			instance.detector = new GestureDetector(activity, instance);
		}
	}

	public static void addEventListener(EventListener listener) {
		listeners.add(listener);
	}

	public static void removeEventListener(EventListener listener) {
		for (int i = 0; i < listeners.size(); i++) {
			if (listeners.get(i).getName().equals(listener.getName())) {
				listeners.remove(i);
				break;
			}
		}
	}

	public static final Event e1 = new Event();
	public static final Event e2 = new Event();

	@Override
	public boolean onSingleTapUp(MotionEvent e) {
		EventDispatcher.e1.set(e);
		for (int i = 0; i < listeners.size(); i++)
			if (listeners.get(i).onSingleTapUp(e1))
				return true;
		return super.onSingleTapUp(e);
	}

	@Override
	public void onLongPress(MotionEvent e) {
		EventDispatcher.e1.set(e);
		for (int i = 0; i < listeners.size(); i++)
			if (listeners.get(i).onLongPress(EventDispatcher.e1))
				return;
		super.onLongPress(e);
	}

	@Override
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
		EventDispatcher.e1.set(e1);
		EventDispatcher.e2.set(e2);
		for (int i = 0; i < listeners.size(); i++)
			if (listeners.get(i).onScroll(EventDispatcher.e1, EventDispatcher.e2, distanceX, distanceY))
				return true;
		return super.onScroll(e1, e2, distanceX, distanceY);
	}

	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
		EventDispatcher.e1.set(e1);
		EventDispatcher.e2.set(e2);
		for (int i = 0; i < listeners.size(); i++)
			if (listeners.get(i).onFling(EventDispatcher.e1, EventDispatcher.e2, velocityX, velocityY))
				return true;
		return super.onFling(e1, e2, velocityX, velocityY);
	}

	@Override
	public void onShowPress(MotionEvent e) {
		EventDispatcher.e1.set(e);
		for (int i = 0; i < listeners.size(); i++)
			if (listeners.get(i).onShowPress(EventDispatcher.e1))
				return;
		super.onShowPress(e);
	}

	@Override
	public boolean onDown(MotionEvent e) {
		EventDispatcher.e1.set(e);
		for (int i = 0; i < listeners.size(); i++)
			if (listeners.get(i).onDown(EventDispatcher.e1))
				return true;
		return super.onDown(e);
	}

	@Override
	public boolean onDoubleTap(MotionEvent e) {
		EventDispatcher.e1.set(e);
		for (int i = 0; i < listeners.size(); i++)
			if (listeners.get(i).onDoubleTap(EventDispatcher.e1))
				return true;
		return super.onDoubleTap(e);
	}

	@Override
	public boolean onDoubleTapEvent(MotionEvent e) {
		EventDispatcher.e1.set(e);
		for (int i = 0; i < listeners.size(); i++)
			if (listeners.get(i).onDoubleTapEvent(EventDispatcher.e1))
				return true;
		return super.onDoubleTapEvent(e);
	}

	@Override
	public boolean onSingleTapConfirmed(MotionEvent e) {
		EventDispatcher.e1.set(e);
		for (int i = 0; i < listeners.size(); i++)
			if (listeners.get(i).onSingleTapConfirmed(EventDispatcher.e1))
				return true;
		return super.onSingleTapConfirmed(e);
	}

	@Override
	public boolean onContextClick(MotionEvent e) {
		EventDispatcher.e1.set(e);
		for (int i = 0; i < listeners.size(); i++)
			if (listeners.get(i).onContextClick(EventDispatcher.e1))
				return true;
		return super.onContextClick(e);
	}

	public void onUp(MotionEvent e) {
		EventDispatcher.e1.set(e);
		for (int i = 0; i < listeners.size(); i++) {
			listeners.get(i).onUp(e1);
		}
	}
}
