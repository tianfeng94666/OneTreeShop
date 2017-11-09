package onetreeshopsapp.com.onetreeshops.utils;

/**
 * 
 */
public class LogUtils {
	private static final String TAG = "MallposCashier";
	private static boolean flag = true;

	public static void i(String msg) {
		if (flag) {
			android.util.Log.i(TAG, msg);
		}
	}

	public static void d(String msg) {
		if (flag) {
			android.util.Log.d(TAG, msg);
		}
	}

	public static void e(String msg) {
		if (flag) {
			android.util.Log.e(TAG, msg);
		}
	}

	public static void i(String tag, String msg) {
		if (flag) {
			android.util.Log.i(tag, msg);
		}
	}

	public static void d(String tag, String msg) {
		if (flag) {
			android.util.Log.d(tag, msg);
		}
	}

	public static void e(String tag, String msg) {
		if (flag) {
			android.util.Log.e(tag, msg);
		}
	}

}
