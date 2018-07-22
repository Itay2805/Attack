package fuck.it.attack.core;

import android.util.Log;

public class Logger {

	public static final String TAG = "Attack";

	public static void error(Object... messages) {
		String str = "";
		for(Object obj : messages) {
			str += obj.toString();
		}
		Log.e(TAG, str);
	}

	public static void info(Object... messages) {
		String str = "";
		for(Object obj : messages) {
			str += obj.toString();
		}
		Log.i(TAG, str);
	}

	public static void warn(Object... messages) {
		String str = "";
		for(Object obj : messages) {
			str += obj.toString();
		}
		Log.w(TAG, str);
	}

	public static void msg(Object... messages) {
		String str = "";
		for(Object obj : messages) {
			str += obj.toString();
		}
		Log.v(TAG, str);
	}

	public static void debug(Object... messages) {
		String str = "";
		for(Object obj : messages) {
			str += obj.toString();
		}
		Log.d(TAG, str);
	}

}
