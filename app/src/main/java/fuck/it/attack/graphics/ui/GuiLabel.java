package fuck.it.attack.graphics.ui;

import fuck.it.attack.core.input.Event;
import fuck.it.attack.graphics.Font;
import fuck.it.attack.graphics.sprite.Sprite;

public class GuiLabel extends GuiElement {

	public GuiLabel(String name, float x, float y, String text, Font font) {
		super(name, x, y, (font.getSize() + 3) * text.length(), font.getSize());

		float modX = x;

		for (char c : text.toCharArray()) {
			Sprite sprite = new Sprite(font.getCharSprite(c));
			sprite.x = modX;
			sprite.y = y;
			spriteList.add(sprite);
			modX += font.getSize() + 3;
		}
	}

	@Override
	public boolean onDown(Event e) {
		// no need for checking if it's inside the bounds since if it's not in the bounds
		// then it won't be called
		return true;
	}
}
