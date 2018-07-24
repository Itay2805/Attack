package fuck.it.attack;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ConfigurationInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.DisplayMetrics;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.games.Games;
import com.google.android.gms.games.GamesClient;
import com.google.android.gms.signin.SignIn;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import fuck.it.attack.core.FileUtils;
import fuck.it.attack.core.Logger;
import fuck.it.attack.core.input.EventDispatcher;
import fuck.it.attack.googlePlayServices.SignInHelper;
import fuck.it.attack.joystick.JoystickView;

public class MainActivity extends Activity {

	private static MainActivity context;
	private static JoystickView rightJoystick;
	private static JoystickView leftJoystick;
	private static MainRenderer mainRenderer;
	private static MainSurfaceView mainSurfaceView;

	public static JoystickView getLeftJoystick() {
		return leftJoystick;
	}

	public static JoystickView getRightJoystick() {
		return rightJoystick;
	}

	public static MainActivity getContext() {
		return context;
	}

	public static MainSurfaceView getMainSurfaceView() {
		return mainSurfaceView;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		context = this;

		context.requestWindowFeature(Window.FEATURE_NO_TITLE);
		context.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

		FileUtils.assetManager = getAssets();

		ActivityManager am = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
		ConfigurationInfo info = am.getDeviceConfigurationInfo();
		boolean supportES3 = (info.reqGlEsVersion >= 0x30000);
		if (supportES3) {
			mainRenderer = new MainRenderer();
			mainSurfaceView = new MainSurfaceView(context);
			mainSurfaceView.setEGLContextClientVersion(3);
			mainSurfaceView.setRenderer(mainRenderer);

			leftJoystick = new JoystickView(context);
			rightJoystick = new JoystickView(context);

			DisplayMetrics display = new DisplayMetrics();
			((Activity) MainActivity.getContext()).getWindowManager()
					.getDefaultDisplay()
					.getMetrics(display);

			int size = display.widthPixels / 5;

			FrameLayout.LayoutParams leftLayoutParams = new FrameLayout.LayoutParams(size, size);
			leftLayoutParams.setMargins(20, display.heightPixels - size - 20, 0, 0);
			leftJoystick.setLayoutParams(leftLayoutParams);

			FrameLayout.LayoutParams rightLayoutParams = new FrameLayout.LayoutParams(size, size);
			rightLayoutParams.setMargins(display.widthPixels - size - 20, display.heightPixels - size - 20, 0, 0);
			rightJoystick.setLayoutParams(rightLayoutParams);

			FrameLayout layout = new FrameLayout(context);
			layout.addView(mainSurfaceView);
			layout.addView(leftJoystick);
			layout.addView(rightJoystick);

			context.setContentView(layout);

			SignInHelper.setPopupsView(layout);
		} else {
			new AlertDialog.Builder(context).setMessage("Your device doesn't support ES3. (\" + info.reqGlEsVersion + \")").setNeutralButton("Exit", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					System.exit(0);
				}
			}).show();
		}

		EventDispatcher.init(this, mainSurfaceView);
	}

	@Override
	protected void onStart() {
		super.onStart();
		SignInHelper.signIn();
	}

	@Override
	protected void onResume() {
		super.onResume();
		SignInHelper.silentSignIn();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == SignInHelper.RC_SIGN_IN) {
			SignInHelper.signInResult(Auth.GoogleSignInApi.getSignInResultFromIntent(data));
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		mainRenderer.cleanUp();
	}

}
