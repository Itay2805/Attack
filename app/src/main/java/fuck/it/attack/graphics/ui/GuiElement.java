package fuck.it.attack.graphics.ui;

import org.joml.Vector2f;

import java.util.ArrayList;
import java.util.List;

import fuck.it.attack.core.input.Event;
import fuck.it.attack.core.input.EventListener;
import fuck.it.attack.graphics.Color;
import fuck.it.attack.graphics.Renderer;
import fuck.it.attack.graphics.sprite.Sprite;

public class GuiElement extends EventListener {

	protected List<Sprite> spriteList = new ArrayList<>();
	protected Color backgroundColor;
	protected boolean visible = true;
	protected Vector2f position;
	protected Vector2f size;

	protected GuiElement(String name, float x, float y, float width, float height) {
		super(name);
		position = new Vector2f(x, y);
		size = new Vector2f(width, height);
		spriteList.add(new Sprite(x, y, width, height));
	}

	public void submit(Renderer renderer) {
		renderer.submit(spriteList);
	}

	public void setBackgroundColor(Color backgroundColor) {
		spriteList.get(0).setColor(backgroundColor);
		this.backgroundColor = backgroundColor;
	}

	public boolean isVisible() {
		return visible;
	}

	public boolean contains(Event pos) {
		return (pos.x >= position.x) && (pos.y >= position.y) && (pos.x <= size.x + position.x) && (pos.y <= size.y + position.y);
	}
}
