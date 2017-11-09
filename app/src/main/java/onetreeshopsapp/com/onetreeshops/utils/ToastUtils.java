package onetreeshopsapp.com.onetreeshops.utils;

import android.content.Context;
import android.os.Handler;
import android.widget.Toast;

/**
 * Toast信息提示
 * 
 * @update 2014-10-17 下午3:41:49
 * @version V1.0
 */
public class ToastUtils {

	/**
	 * 显示Toast信息
	 * 
	 * @param msg
	 *            需要显示的信息
	 */
	public static void show(Context context, String msg) {
		if(context != null){
			Toast.makeText(context, "" + msg, Toast.LENGTH_SHORT).show();
		}

	}

	/**
	 * 显示Toast信息
	 * 
	 * @param msg
	 *            需要显示的信息
	 */
	public static void show(Context context, String msg, int duration) {
		Toast.makeText(context, "" + msg, duration).show();
	}


	public static Handler mhHandler = new Handler();
	/**
	 * 主线程执行
	 */
	public static  void runInMainThread(Runnable task){
		mhHandler.post(task);
	}
}
