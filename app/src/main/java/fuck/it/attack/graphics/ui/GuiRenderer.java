package fuck.it.attack.graphics.ui;

import org.joml.Matrix4f;

import java.util.ArrayList;
import java.util.List;

import fuck.it.attack.core.input.Event;
import fuck.it.attack.core.input.EventDispatcher;
import fuck.it.attack.core.input.EventListener;
import fuck.it.attack.graphics.Renderer;

public class GuiRenderer extends EventListener {

	private final Event conversionEvent = new Event();
	private Renderer renderer;
	private List<GuiElement> elements = new ArrayList<>();
	private float width, height;
	private float eventXRatio, eventYRatio;

	public GuiRenderer(float width, float height) {
		super("GuiRenderer");
		EventDispatcher.addEventListener(this);
		this.width = width;
		this.height = height;
		renderer = new Renderer();
		renderer.setProjectionMatrix(new Matrix4f().ortho(0, width, 0, height, 1, -1));
		renderer.setViewMatrix(new Matrix4f()); // no view matrix needed for gui (duh)
	}

	public void submit(GuiElement element) {
		elements.add(element);
	}

	public void draw() {
		renderer.begin();

		for (GuiElement element : elements)
			if (element.isVisible())
				element.submit(renderer);

		renderer.end();
		renderer.draw();
	}

	public void cleanUp() {
		EventDispatcher.removeEventListener(this);
		renderer.cleanUp();
	}

	@Override
	public boolean onSingleTapUp(Event e) {
		Event newE1 = convertToGuiSpace(e);
		for (int i = 0; i < elements.size(); i++)
			if (elements.get(i).contains(newE1) && elements.get(i).onSingleTapUp(newE1))
				return true;
		return super.onSingleTapUp(newE1);
	}

	@Override
	public boolean onLongPress(Event e) {
		Event newE1 = convertToGuiSpace(e);
		for (int i = 0; i < elements.size(); i++)
			if (elements.get(i).contains(newE1) && elements.get(i).onLongPress(newE1))
				return true;
		return super.onLongPress(newE1);
	}

	@Override
	public boolean onScroll(Event e1, Event e2, float distanceX, float distanceY) {
		Event newE1 = convertToGuiSpace(e1);
		Event newE2 = convertToGuiSpace(e2);
		for (int i = 0; i < elements.size(); i++)
			if (elements.get(i).contains(newE1) && elements.get(i).onScroll(newE1, newE2, distanceX, distanceY))
				return true;
		return super.onScroll(newE1, newE2, distanceX, distanceY);
	}

	@Override
	public boolean onFling(Event e1, Event e2, float velocityX, float velocityY) {
		Event newE1 = convertToGuiSpace(e1);
		Event newE2 = convertToGuiSpace(e2);
		for (int i = 0; i < elements.size(); i++)
			if (elements.get(i).contains(newE1) && elements.get(i).onFling(newE1, newE2, velocityX, velocityY))
				return true;
		return super.onFling(newE1, newE2, velocityX, velocityY);
	}

	@Override
	public boolean onShowPress(Event e) {
		Event newE1 = convertToGuiSpace(e);
		for (int i = 0; i < elements.size(); i++)
			if (elements.get(i).contains(newE1) && elements.get(i).onShowPress(newE1))
				return true;
		return super.onShowPress(e);
	}

	@Override
	public boolean onDown(Event e) {
		Event newE1 = convertToGuiSpace(e);
		for (int i = 0; i < elements.size(); i++)
			if (elements.get(i).contains(newE1) && elements.get(i).onDown(newE1))
				return true;
		return super.onDown(e);
	}

	@Override
	public boolean onDoubleTap(Event e) {
		Event newE1 = convertToGuiSpace(e);
		for (int i = 0; i < elements.size(); i++)
			if (elements.get(i).contains(newE1) && elements.get(i).onDoubleTap(newE1))
				return true;
		return super.onDoubleTap(e);
	}

	@Override
	public boolean onDoubleTapEvent(Event e) {
		Event newE1 = convertToGuiSpace(e);
		for (int i = 0; i < elements.size(); i++)
			if (elements.get(i).contains(newE1) && elements.get(i).onDoubleTapEvent(newE1))
				return true;
		return super.onDoubleTapEvent(e);
	}

	@Override
	public boolean onSingleTapConfirmed(Event e) {
		Event newE1 = convertToGuiSpace(e);
		for (int i = 0; i < elements.size(); i++)
			if (elements.get(i).contains(newE1) && elements.get(i).onSingleTapConfirmed(newE1))
				return true;
		return super.onSingleTapConfirmed(e);
	}

	@Override
	public boolean onContextClick(Event e) {
		Event newE1 = convertToGuiSpace(e);
		for (int i = 0; i < elements.size(); i++)
			if (elements.get(i).contains(newE1) && elements.get(i).onContextClick(newE1))
				return true;
		return super.onContextClick(e);
	}

	public void setSurfaceSize(float width, float height) {
		eventXRatio = this.width / width;
		eventYRatio = this.height / height;
	}

	private Event convertToGuiSpace(Event e) {
		conversionEvent.x = e.x * eventXRatio;
		conversionEvent.y = height - e.y * eventYRatio;
		return conversionEvent;
	}
}
