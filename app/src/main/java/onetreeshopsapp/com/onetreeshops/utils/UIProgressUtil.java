package onetreeshopsapp.com.onetreeshops.utils;

import android.app.ProgressDialog;
import android.content.Context;
import onetreeshopsapp.com.onetreeshops.R;


/**
 * 进度条
 * 
 * @author kunliu
 * 
 */
public class UIProgressUtil {

	protected static ProgressDialog progressDialog;// 加载进度条

	public static void showProgress(Context mContext) {
		showProgress(mContext, "加载中", true);
	}

	public static void showProgress(Context mContext, boolean canBack) {
		showProgress(mContext, "加载中", canBack);
	}

	public static void showProgress(Context mContext, String mMessage) {
		showProgress(mContext, mMessage, true);
	}

	public static void showProgress(Context mContext, int resID, boolean canBack) {
		try {
			if (progressDialog != null) {
				if (progressDialog.isShowing()) {
					progressDialog.dismiss();
				}
				
				if (mContext != null)
				{
				progressDialog.setMessage(mContext.getResources().getString(
						resID));
				progressDialog.setCancelable(canBack);
					progressDialog.show();
				}
			} else {
				progressDialog = ProgressDialog.show(mContext, "", mContext
						.getResources().getString(resID));
				progressDialog.setContentView(R.layout.loading_dialogs);
				progressDialog.setCancelable(canBack);
			}
		} catch (Exception e) {
		}

	}

	public static void showProgress(Context mContext, String res,
									boolean canBack) {
		try {
			if (progressDialog != null) {
				if (progressDialog.isShowing()) {
					progressDialog.dismiss();
				}
				progressDialog.setMessage(res);
				progressDialog.setCancelable(canBack);
				if(mContext != null){
					progressDialog.show();
				}
				
			} else {
				progressDialog = ProgressDialog.show(mContext, "", res);
				progressDialog.setCancelable(canBack);
			}
		} catch (Exception e) {
		}
	}
	
	public static void cancelProgress() {
		try {
			if (progressDialog != null) {

				progressDialog.dismiss();
				progressDialog.cancel();
				progressDialog = null;
			}
		} catch (Exception e) {
		}
	}

}

