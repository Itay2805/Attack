package fuck.it.attack.graphics.ui;

import fuck.it.attack.core.Logger;
import fuck.it.attack.core.input.Event;
import fuck.it.attack.graphics.Color;
import fuck.it.attack.graphics.Font;
import fuck.it.attack.graphics.Sprite;

public class GuiLabel extends GuiElement {

	public GuiLabel(String name, float x, float y, String text, Font font) {
		this(name, x, y, text, font, 64, new Color(0, 0, 0, 0));
	}
	public GuiLabel(String name, float x, float y, String text, Font font, float size){
		this(name, x, y, text, font, size, new Color(0, 0, 0, 0));
	}

	public GuiLabel(String name, float x, float y, String text, Font font, float size, Color color) {
		super(name, x, y, (size + 3) * text.length(), size);

		float modX = x;
		float modY = y;
		Color original = color;
		boolean skip = false;

		for (int i = 0; i < text.length(); i++) {
			char c = text.charAt(i);
			switch(c) {
				// special escape sequence
				case '#': {
					if(text.length() >= i || text.charAt(i + 1) != '[') {
						Sprite sprite = new Sprite(font.getCharSprite(c));
						sprite.x = modX;
						sprite.y = modY;
						sprite.width = size;
						sprite.height = size;
						sprite.color = color;
						spriteList.add(sprite);
						modX += size + 3;
						continue;
					}
					int end = text.indexOf(']', i);
					if(end == -1) {
						throw new IllegalStateException("Missing closing ] for special escape sequence in label. `" + text + "` at position " + i + "");
					}
					String colorName = text.substring(i + 2, end);
					Logger.debug(colorName);
					switch(colorName) {
						case "reset": {
							color = original;
						} break;
						default: {
							color = Color.getColor(colorName);
						} break;
					}
					i += colorName.length() + 2;
				} break;

				// tab
				case '\t': {
					modX += size * 4 + 3;
				} break;

				// newline
				case '\n': {
					modY += size + 3;
					modX = x;
				} break;

				// space
				case ' ': {
					modX += size + 3;
					// do nothing
				} break;

				// any other char
				default: {
					Sprite sprite = new Sprite(font.getCharSprite(c));
					sprite.x = modX;
					sprite.y = modY;
					sprite.width = size;
					sprite.height = size;
					sprite.color = color;
					spriteList.add(sprite);
					modX += size + 3;
				}
			}
		}
	}

	@Override
	public boolean onDown(Event e) {
		// no need for checking if it's inside the bounds since if it's not in the bounds
		// then it won't be called
		return true;
	}
}
