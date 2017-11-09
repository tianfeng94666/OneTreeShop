package onetreeshopsapp.com.onetreeshops.update;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import onetreeshopsapp.com.onetreeshops.R;
import onetreeshopsapp.com.onetreeshops.activity.MySetActivity;
import onetreeshopsapp.com.onetreeshops.application.LYJApplication;
import onetreeshopsapp.com.onetreeshops.utils.IntentUtils;

public class NotificationUpdateActivity extends Activity {
	private static final int NOTIFY_ID = 1;
	private NotificationManager mNotificationManager;
	private PendingIntent contentIntent;
	private int progress;
	private NotificationCompat.Builder mBuilder;
	private Button btn_cancel;// btn_update,
	private TextView tv_progress;
	private DownloadService.DownloadBinder binder;
	private boolean isBinded;
	private boolean isInDownload = false;
	private ProgressBar mProgressBar;
	// 获取到下载url后，直接复制给MapApp,里面的全局变量
	private String downloadUrl;
	//
	private boolean isDestroy = true;
	private LYJApplication lyjApplication;
	private ServiceConnection conn;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dialog_update);
		lyjApplication = (LYJApplication) getApplication();
		btn_cancel = (Button) findViewById(R.id.cancel);
		tv_progress = (TextView) findViewById(R.id.currentPos);
		mProgressBar = (ProgressBar) findViewById(R.id.progressbar1);
		btn_cancel.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				binder.cancel();
				binder.cancelNotification();
				finish();
				isDestroy=true;
				lyjApplication.setDownload(true);
				lyjApplication.setInDownload(false);
			}
		});
	}
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		System.out.println("???"+isDestroy+lyjApplication.isDownload()+lyjApplication.isInDownload());
		if (isDestroy && lyjApplication.isDownload()&&lyjApplication.isInDownload()==false) {
			initService();
			Intent it = new Intent(NotificationUpdateActivity.this, DownloadService.class);
			startService(it);
			bindService(it, conn, Context.BIND_AUTO_CREATE);
			System.out.println(" notification  onresume");
		}else {
			System.out.println("服务第二次启动!!!");
		}

	}
	private void initService() {
		conn = new ServiceConnection() {
			@Override
			public void onServiceDisconnected(ComponentName name) {
				// TODO Auto-generated method stub
				isBinded = false;
			}
			@Override
			public void onServiceConnected(ComponentName name, IBinder service) {
				// TODO Auto-generated method stub
				binder = (DownloadService.DownloadBinder) service;
				System.out.println("服务初次启动!!!");
				// 开始下载
				isBinded = true;
				binder.addCallback(callback);
				binder.start();
			}
		};
	}

	@Override
	protected void onNewIntent(Intent intent) {
		// TODO Auto-generated method stub
		super.onNewIntent(intent);
		/*if (isDestroy && lyjApplication.isDownload()) {
			Intent it = new Intent(NotificationUpdateActivity.this, DownloadService.class);
			startService(it);
			bindService(it, conn, Context.BIND_AUTO_CREATE);
		}*/
		System.out.println(" notification  onNewIntent");
	}
	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();

	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		System.out.println(" notification  onPause");
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		isDestroy = false;
		System.out.println(" notification  onStop");
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		if (isBinded) {
			System.out.println(" onDestroy   unbindservice");
			unbindService(conn);
		}
		if (binder != null && binder.isCanceled()) {
			System.out.println(" onDestroy  stopservice");
			Intent it = new Intent(this, DownloadService.class);
			stopService(it);
		}
	}

	private ICallbackResult callback = new ICallbackResult() {

		@Override
		public void OnBackResult(Object result) {
			// TODO Auto-generated method stub
			if ("finish".equals(result)) {
				finish();
				return;
			}
			int i = (Integer) result;
			mProgressBar.setProgress(i);
			mHandler.sendEmptyMessage(i);
		}

	};

	private Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			tv_progress.setText("当前进度 ： " + msg.what + "%");

		};
	};

	public interface ICallbackResult {
		public void OnBackResult(Object result);
	}

}
