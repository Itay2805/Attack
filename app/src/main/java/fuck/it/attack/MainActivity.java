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
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.io.File;

import fuck.it.attack.core.FileUtils;
import fuck.it.attack.core.Logger;
import fuck.it.attack.joystick.JoystickMovedListener;
import fuck.it.attack.joystick.JoystickView;

public class MainActivity extends Activity {

	private MainRenderer mainRenderer;
	private static MainActivity context;
	private static JoystickView rightJoystick;
	private static JoystickView leftJoystick;
	private static GoogleSignInAccount account;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		context = this;

		context.requestWindowFeature(Window.FEATURE_NO_TITLE);
		context.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

		FileUtils.assetManager = getAssets();

		ActivityManager am = (ActivityManager)getSystemService(Context.ACTIVITY_SERVICE);
		ConfigurationInfo  info = am.getDeviceConfigurationInfo();
		boolean supportES3 = (info.reqGlEsVersion >= 0x30000);
		if(supportES3) {
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

			FrameLayout.LayoutParams leftLayoutParams=new FrameLayout.LayoutParams(size, size);
			leftLayoutParams.setMargins(20, display.heightPixels - size - 20, 0, 0);
			leftJoystick.setLayoutParams(leftLayoutParams);

			FrameLayout.LayoutParams rightLayoutParams=new FrameLayout.LayoutParams(size, size);
			rightLayoutParams.setMargins(display.widthPixels - size - 20, display.heightPixels - size - 20, 0, 0);
			rightJoystick.setLayoutParams(rightLayoutParams);

			FrameLayout layout = new FrameLayout(context);
			layout.addView(mainSurfaceView);
			layout.addView(leftJoystick);
			layout.addView(rightJoystick);

			context.setContentView(layout);
		}else {
			new AlertDialog.Builder(context).setMessage("Your device doesn't support ES3. (\" + info.reqGlEsVersion + \")").setNeutralButton("Exit", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					System.exit(0);
				}
			}).show();
		}

		/*final GoogleSignInClient signInClient = GoogleSignIn.getClient(this, GoogleSignInOptions.DEFAULT_GAMES_SIGN_IN);
		signInClient.silentSignIn().addOnCompleteListener(this, new OnCompleteListener<GoogleSignInAccount>() {
			@Override
			public void onComplete(@NonNull Task<GoogleSignInAccount> task) {
				if (task.isSuccessful()) {
					account = task.getResult();

					Logger.info("Logged in " + account.getDisplayName());
				} else {
					Intent intent = signInClient.getSignInIntent();
					startActivityForResult(intent, SIGN_IN_RESULT);
				}
			}
		});*/
	}

	public static JoystickView getLeftJoystick() {
		return leftJoystick;
	}

	public static JoystickView getRightJoystick() {
		return rightJoystick;
	}

	public static GoogleSignInAccount getAccount() {
		return account;
	}

	public static final int SIGN_IN_RESULT = 0xFACC0FF;

	@Override
	protected void onResume() {
		super.onResume();

		/*final GoogleSignInClient signInClient = GoogleSignIn.getClient(this, GoogleSignInOptions.DEFAULT_GAMES_SIGN_IN);
		signInClient.silentSignIn().addOnCompleteListener(this, new OnCompleteListener<GoogleSignInAccount>() {
			@Override
			public void onComplete(@NonNull Task<GoogleSignInAccount> task) {
				if (task.isSuccessful()) {
					account = task.getResult();
					Logger.info("Logged in " + account.getDisplayName());
				} else {
					if(!context.isFinishing())
					{
						Intent intent = signInClient.getSignInIntent();
						startActivityForResult(intent, SIGN_IN_RESULT);
					}
				}
			}
		});*/
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if(resultCode == SIGN_IN_RESULT) {
			GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
			if(result.isSuccess()) {
				account = result.getSignInAccount();
				Logger.info("Logged in " + account.getDisplayName());
			}else {
				String message = result.getStatus().getStatusMessage();

				if(!context.isFinishing())
				{
					new AlertDialog.Builder(this).setMessage(message != null ? message : "You must sign-in to in order to play the game").setNeutralButton("Exit", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							System.exit(0);
						}
					}).show();
				}
			}
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	public static MainActivity getContext() {
		return context;
	}

}
