package fuck.it.attack.graphics.ui;

import java.util.ArrayList;
import java.util.List;

import fuck.it.attack.graphics.Color;
import fuck.it.attack.graphics.Renderer;
import fuck.it.attack.graphics.Sprite;

public abstract class GuiElement {

	protected List<Sprite> spriteList = new ArrayList<>();

	protected Color backgroundColor;

	protected GuiElement(float x, float y, float width, float height) {
		spriteList.add(new Sprite(x, y, width, height));
	}

	public void submit(Renderer renderer) {
		renderer.submit(spriteList);
	}

	public void setBackgroundColor(Color backgroundColor) {
		spriteList.get(0).setColor(backgroundColor);
		this.backgroundColor = backgroundColor;
	}
}
