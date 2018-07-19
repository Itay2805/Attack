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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import fuck.it.attack.core.FileUtils;
import fuck.it.attack.core.Logger;
import fuck.it.attack.joystick.JoystickView;

public class MainActivity extends Activity {

	public static final int SIGN_IN_RESULT = 9001;
	private static MainActivity context;
	private static JoystickView rightJoystick;
	private static JoystickView leftJoystick;
	private static GoogleSignInAccount googleSignInAccount;
	private static GoogleSignInOptions googleSignInOptions;
	private static GoogleSignInClient googleSignInClient;
	private MainRenderer mainRenderer;

	public static JoystickView getLeftJoystick() {
		return leftJoystick;
	}

	public static JoystickView getRightJoystick() {
		return rightJoystick;
	}

	public static MainActivity getContext() {
		return context;
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
			MainSurfaceView mainSurfaceView = new MainSurfaceView(context);
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
		} else {
			new AlertDialog.Builder(context).setMessage("Your device doesn't support ES3. (\" + info.reqGlEsVersion + \")").setNeutralButton("Exit", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					System.exit(0);
				}
			}).show();
		}

		googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();

		googleSignInClient = GoogleSignIn.getClient(this, googleSignInOptions);
	}

	@Override
	protected void onStart() {
		super.onStart();

		if(GoogleSignIn.getLastSignedInAccount(this) != null) {
			Task<GoogleSignInAccount> task = googleSignInClient.silentSignIn();
			task.addOnCompleteListener(this, new OnCompleteListener<GoogleSignInAccount>() {
				@Override
				public void onComplete(@NonNull Task<GoogleSignInAccount> task) {
					if(task.isSuccessful()) {
						Logger.info("Log in successful!");
					} else {
						Logger.error("Log in failed!");
						ApiException e = (ApiException)task.getException();
						Logger.error("Status code: " + e.getStatusCode() + "\nStack trace: " + e.getStackTrace().toString());
						explicitSignIn();
					}
				}
			});
		} else {
			explicitSignIn();
		}
	}

	@Override
	protected void onResume() {
		super.onResume();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if(requestCode == SIGN_IN_RESULT) {
			GoogleSignInResult googleSignInResult = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
			if(googleSignInResult.isSuccess()) {
				googleSignInAccount = googleSignInResult.getSignInAccount();
				Logger.info("Sign in successful! Welcome, " + googleSignInAccount.getDisplayName());
			} else {
				Logger.error("Could not log in " + googleSignInResult.getStatus().toString());
			}
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}


	private void explicitSignIn() {
		Intent intent = googleSignInClient.getSignInIntent();
		startActivityForResult(intent, SIGN_IN_RESULT);
	}
}
