package fuck.it.attack.graphics.ui;

import org.joml.Vector2f;

import fuck.it.attack.core.input.Event;
import fuck.it.attack.graphics.Texture;
import fuck.it.attack.graphics.sprite.Sprite;
import fuck.it.attack.graphics.sprite.SpriteSheet;

public class GuiJoystick extends GuiElement {

	Vector2f innerCirclePosition;

	float innerRadius;
	float outerRadius;

	public GuiJoystick(String name, float x, float y, float outerRadius, float innerRadius) {
		super(name, x, y, outerRadius * 2, outerRadius * 2);

		this.innerRadius = innerRadius;
		this.outerRadius = outerRadius;

		innerCirclePosition = new Vector2f();
		innerCirclePosition.x = (outerRadius - innerRadius) / 2.0f;
		innerCirclePosition.y = (outerRadius - innerRadius) / 2.0f;
		spriteList.add(new Sprite(innerCirclePosition.x, innerCirclePosition.y, innerRadius * 2, innerRadius * 2));
	}


	public void setInnerTexture(Texture texture) {
		spriteList.get(1).setTexture(texture);
	}

	public void setOuterTexture(Texture texture) {
		spriteList.get(0).setTexture(texture);
	}

	@Override
	public boolean contains(Event pos) {
		return (pos.x - this.innerCirclePosition.x + innerRadius) * (pos.x - this.innerCirclePosition.x + innerRadius)
				+ (pos.y -  this.innerCirclePosition.y + innerRadius) * (pos.y -  this.innerCirclePosition.y + innerRadius)
				< (innerRadius * innerRadius);
	}
}
