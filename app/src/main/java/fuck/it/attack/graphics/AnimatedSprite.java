package fuck.it.attack.graphics;

public class AnimatedSprite extends Sprite {

	private SpriteSheet spriteSheet;

	private int steps;
	private int currentStep = 0;

	private int posX;
	private int posY;

	private int currentPosX;
	private int currentPosY;

	public AnimatedSprite(float x, float y, float width, float height, SpriteSheet spriteSheet, int posX, int posY, int steps) {
		super(x, y, width, height);
		this.spriteSheet = spriteSheet;
		this.steps = steps;
		this.posX = currentPosX = posX;
		this.posY = currentPosY = posY;
		updateUvs();
		this.texture = spriteSheet.getTexture();
	}

	public void nextAnimation() {
		if(currentStep == steps) {
			currentStep = 0;
			currentPosX = posX;
			currentPosY = posY;
		} else {
			if (currentPosX == spriteSheet.getCols() - 1) {
				currentPosX = 0;
				currentPosY++;
			} else
				currentPosX++;
		}

		currentStep++;

		updateUvs();
	}

	private void updateUvs() {
		uv1 = spriteSheet.getUv1(currentPosX, currentPosY);
		uv2 = spriteSheet.getUv2(currentPosX, currentPosY);
	}


}
