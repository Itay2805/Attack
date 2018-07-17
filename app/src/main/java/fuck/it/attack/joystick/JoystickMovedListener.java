package fuck.it.attack.joystick;

public interface JoystickMovedListener {

	void onMoved(int pan, int tilt);
	void onReleased();

}
