package fuck.it.attack.graphics.ui;

import fuck.it.attack.core.input.Event;
import fuck.it.attack.core.text.TextFormat;
import fuck.it.attack.core.text.Token;
import fuck.it.attack.graphics.Color;
import fuck.it.attack.graphics.Font;
import fuck.it.attack.graphics.sprite.Sprite;

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

		for (Token token : TextFormat.parse(text)) {
			Logger.info(token);
			switch(token.getType()) {
				case SPECIAL: {
					switch(token.getToken()) {
						case "reset": {
							color = original;
						} break;

						default: {
							color = Color.getColor(token.getToken());
						} break;
					}
				} break;

				case TAB: {
					modX += size * 4 + 3;
				} break;

				case NEW_LINE: {
					modY -= size + 3;
					modX = x;
				} break;

				case SPACE: {
					modX += size + 3;
				} break;

				case TEXT: {
					for(char c : token.getToken().toCharArray()) {
						Sprite sprite = new Sprite(font.getCharSprite(c));
						sprite.x = modX;
						sprite.y = modY;
						sprite.width = size;
						sprite.height = size;
						sprite.color = color;
						spriteList.add(sprite);
						modX += size + 3;
					}
				} break;
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
