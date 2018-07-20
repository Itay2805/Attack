package fuck.it.attack.core;

import android.util.Log;

public class Logger {

	public static final String TAG = "Attack";

	public static void error(String message) {
		Log.e(TAG, message);
	}

	public static void info(String message) {
		Log.i(TAG, message);
	}

	public static void warn(String message) {
		Log.w(TAG, message);
	}

	public static void msg(String message) {
		Log.v(TAG, message);
	}

	public static void debug(String message) {
		Log.d(TAG, message);
	}

}
