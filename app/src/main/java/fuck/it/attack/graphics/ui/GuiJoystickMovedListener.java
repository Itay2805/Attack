package fuck.it.attack.joystick;

public interface JoystickMovedListener {

	void onMoved(float pan, float tilt);
	void onReleased();

}
