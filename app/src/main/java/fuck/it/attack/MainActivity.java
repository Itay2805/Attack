package fuck.it.attack;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ConfigurationInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;

import fuck.it.attack.core.Logger;
import fuck.it.attack.joystick.JoystickMovedListener;
import fuck.it.attack.joystick.JoystickView;

public class MainActivity extends Activity {

	private MainRenderer mainRenderer;
	private static Context context;
	private static JoystickView rightJoystick;
	private static JoystickView leftJoystick;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		context = this;

		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

		ActivityManager am = (ActivityManager)getSystemService(Context.ACTIVITY_SERVICE);
		ConfigurationInfo  info = am.getDeviceConfigurationInfo();
		boolean supportES3 = (info.reqGlEsVersion >= 0x30000);
		if(supportES3) {
			mainRenderer = new MainRenderer();
			MainSurfaceView mainSurfaceView = new MainSurfaceView(this);
			mainSurfaceView.setEGLContextClientVersion(3);
			mainSurfaceView.setRenderer(mainRenderer);

			leftJoystick = new JoystickView(this);
			rightJoystick = new JoystickView(this);

			DisplayMetrics display = new DisplayMetrics();
			((Activity) MainActivity.getContext()).getWindowManager()
					.getDefaultDisplay()
					.getMetrics(display);

			int size = display.widthPixels / 5;

			FrameLayout.LayoutParams leftLayoutParams=new FrameLayout.LayoutParams(size, size);
			leftLayoutParams.setMargins(20, display.heightPixels - size - 20, 0, 0);
			leftJoystick.setLayoutParams(leftLayoutParams);

			FrameLayout.LayoutParams rightLayoutParams=new FrameLayout.LayoutParams(size, size);
			rightLayoutParams.setMargins(display.widthPixels - size - 20, display.heightPixels - size - 20, 0, 0);
			rightJoystick.setLayoutParams(rightLayoutParams);

			FrameLayout layout = new FrameLayout(this);
			layout.addView(mainSurfaceView);
			layout.addView(leftJoystick);
			layout.addView(rightJoystick);

			this.setContentView(layout);
		}else {
			Logger.error("[OpenGL ES 3.0] Your device doesn't support ES3. (" + info.reqGlEsVersion + ")");
		}
	}

	public static JoystickView getLeftJoystick() {
		return leftJoystick;
	}

	public static JoystickView getRightJoystick() {
		return rightJoystick;
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	public static Context getContext() {
		return context;
	}

}
