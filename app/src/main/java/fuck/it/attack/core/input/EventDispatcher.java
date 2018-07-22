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
		detector = new GestureDetector(activity, this);

		view.setOnTouchListener(new View.OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				v.performClick();
				return detector.onTouchEvent(event);
			}
		});

		instance = this;
	}

	public static void init(Activity activity, View view) {
		if(instance == null) {
			new EventDispatcher(activity, view);
		}
	}

	@Override
	public boolean onSingleTapUp(MotionEvent e) {
		for (int i=0; i < listeners.size(); i++)
			if (listeners.get(i).onSingleTapUp(e))
				return true;
		return super.onSingleTapUp(e);
	}

	@Override
	public void onLongPress(MotionEvent e) {
		for (int i=0; i < listeners.size(); i++)
			if (listeners.get(i).onLongPress(e))
				return;
		super.onLongPress(e);
	}

	@Override
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
		for (int i=0; i < listeners.size(); i++)
			if (listeners.get(i).onScroll(e1, e2, distanceX, distanceY))
				return true;
		return super.onScroll(e1, e2, distanceX, distanceY);
	}

	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
		for (int i=0; i < listeners.size(); i++)
			if (listeners.get(i).onFling(e1, e2, velocityX, velocityY))
				return true;
		return super.onFling(e1, e2, velocityX, velocityY);
	}

	@Override
	public void onShowPress(MotionEvent e) {
		for (int i=0; i < listeners.size(); i++)
			if (listeners.get(i).onShowPress(e))
				return;
		super.onShowPress(e);
	}

	@Override
	public boolean onDown(MotionEvent e) {
		for (int i=0; i < listeners.size(); i++)
			if (listeners.get(i).onDown(e))
				return true;
		return super.onDown(e);
	}

	@Override
	public boolean onDoubleTap(MotionEvent e) {
		for (int i=0; i < listeners.size(); i++)
			if (listeners.get(i).onDoubleTap(e))
				return true;
		return super.onDoubleTap(e);
	}

	@Override
	public boolean onDoubleTapEvent(MotionEvent e) {
		for (int i=0; i < listeners.size(); i++)
			if (listeners.get(i).onDoubleTapEvent(e))
				return true;
		return super.onDoubleTapEvent(e);
	}

	@Override
	public boolean onSingleTapConfirmed(MotionEvent e) {
		for (int i=0; i < listeners.size(); i++)
			if (listeners.get(i).onSingleTapConfirmed(e))
				return true;
		return super.onSingleTapConfirmed(e);
	}

	@Override
	public boolean onContextClick(MotionEvent e) {
		for (int i=0; i < listeners.size(); i++)
			if (listeners.get(i).onContextClick(e))
				return true;
		return super.onContextClick(e);
	}
}
