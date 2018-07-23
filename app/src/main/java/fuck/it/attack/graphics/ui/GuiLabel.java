package fuck.it.attack.graphics.ui;

import fuck.it.attack.graphics.Font;
import fuck.it.attack.graphics.Sprite;

public class GuiLabel extends GuiElement {

    public GuiLabel(float x, float y, String text, Font font) {
        super(x, y, font.getSize(), font.getSize() * text.length());

        for(char c : text.toCharArray()) {
            Sprite sprite = new Sprite(font.getCharSprite(c));
            sprite.x = x;
            sprite.y = y;
            spriteList.add(sprite);
            x += font.getSize() + 3;
        }
    }

}
