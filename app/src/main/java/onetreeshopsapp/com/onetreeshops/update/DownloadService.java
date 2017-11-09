package onetreeshopsapp.com.onetreeshops.update;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Binder;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.v4.app.NotificationCompat;
import android.text.TextUtils;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.Toast;

import org.xutils.http.request.HttpRequest;

import onetreeshopsapp.com.onetreeshops.R;
import onetreeshopsapp.com.onetreeshops.application.LYJApplication;
import onetreeshopsapp.com.onetreeshops.utils.Const;
import onetreeshopsapp.com.onetreeshops.utils.FileUtil;
import onetreeshopsapp.com.onetreeshops.utils.HttpValue;
import onetreeshopsapp.com.onetreeshops.utils.ToastUtils;


public class DownloadService extends Service {
	private static final int NOTIFY_ID = 102;
	private NotificationManager mNotificationManager;
	private PendingIntent contentIntent;
	private NotificationCompat.Builder mBuilder;
	private int progress;
	private boolean canceled;
	private HttpURLConnection conn;
	private File ApkFile;
	// 返回的安装包url
	//private String apkUrl = "http://192.168.25.21:8180/test.apk";
	private String apkUrl ;
	/* 下载包安装路径 */
	private static  String savePath = null;
	/* 下载包文件名 */
	private static  String saveFileName = null;
	private NotificationUpdateActivity.ICallbackResult callback;
	private DownloadBinder binder;
	private LYJApplication lyjApplication;
	private boolean serviceIsDestroy = false;
	private SharedPreferences sp;
	private Context mContext = this;
	private Handler mHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			switch (msg.what) {
			case 0:
				lyjApplication.setDownload(false);
				// 下载完毕
				// 取消通知
				mNotificationManager.cancel(NOTIFY_ID);
				installApk();
				break;
			case 2:
				lyjApplication.setDownload(false);
				// 这里是用户界面手动取消，所以会经过activity的onDestroy();方法
				// 取消通知
				conn.disconnect();
				mNotificationManager.cancel(NOTIFY_ID);
				//delSmallparkApk();
				break;
			case 1:
				int rate = msg.arg1;
				lyjApplication.setDownload(true);
				if (rate < 100) {
					setNotify(rate);
				} else {
					System.out.println("下载完毕!!!!!!!!!!!");
					lyjApplication.setInDownload(false);
					// 下载完毕后变换通知形式
					mBuilder.setContentText("下载完成")
							.setProgress(100, 100, false);
					mNotificationManager.notify(NOTIFY_ID, mBuilder.build());
					serviceIsDestroy = true;
					stopSelf();// 停掉服务自身
				}
				break;
			}
		}
	};

	//
	// @Override
	// public int onStartCommand(Intent intent, int flags, int startId) {
	// // TODO Auto-generated method stub
	// return START_STICKY;
	// }

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		System.out.println("是否执行了 onBind");
		return binder;
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		System.out.println("downloadservice ondestroy");
		// 假如被销毁了，无论如何都默认取消了。
		lyjApplication.setDownload(false);
	}

	@Override
	public boolean onUnbind(Intent intent) {
		// TODO Auto-generated method stub
		System.out.println("downloadservice onUnbind");
		return super.onUnbind(intent);
	}

	@Override
	public void onRebind(Intent intent) {
		// TODO Auto-generated method stub

		super.onRebind(intent);
		System.out.println("downloadservice onRebind");
	}

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		binder = new DownloadBinder();
		//setForeground(true);// 这个不确定是否有作用
		lyjApplication = (LYJApplication) getApplication();
		System.out.println("onCreate");
		initNotify();
	}

	public class DownloadBinder extends Binder {
		public void start() {
			if (downLoadThread == null || !downLoadThread.isAlive()) {
				new Thread() {
					public void run() {
						// 下载
						startDownload();
					};
				}.start();
			}
		}

		public void cancel() {
			canceled = true;
		}

		public int getProgress() {
			return progress;
		}

		public boolean isCanceled() {
			return canceled;
		}

		public boolean serviceIsDestroy() {
			return serviceIsDestroy;
		}

		public void cancelNotification() {
			mHandler.sendEmptyMessage(2);
		}

		public void addCallback(NotificationUpdateActivity.ICallbackResult callback) {
			DownloadService.this.callback = callback;
		}
	}

	private void startDownload() {
		// TODO Auto-generated method stub
		canceled = false;
		if (FileUtil.checkStorage()) {
			downloadApk();
		} else {
			System.out.println("检测到设备没有SD卡!");
			return;
		}

	}

	//
   //新建apk文件
	private File createApkFile() throws IOException {
		savePath = Environment.getExternalStorageDirectory() + File.separator +"OneTreeShopDownload" + File.separator;
		File dir = new File(savePath);
		if (!dir.exists()) {
			dir.mkdirs();
		}
		saveFileName = "onetreeshop.apk";
		String path = savePath + saveFileName;
		 ApkFile = new File(path);
		// Save a file: path for use with ACTION_VIEW intents
		String mCurrentPhotoPath = ApkFile.getAbsolutePath();
		Log.i("upload", "path = " + mCurrentPhotoPath);
		return ApkFile;
	}



	//
	/**
	 * 下载apk
	 * 
	 * @param url
	 */
	private Thread downLoadThread;

	private void downloadApk() {
		showProgressNotify();
		downLoadThread = new Thread(mdownApkRunnable);
		downLoadThread.start();
	}

	/**
	 * 安装apk
	 * 
	 * @param 、、url
	 */
	private void installApk() {
		File apkfile = ApkFile;
		if (!apkfile.exists()) {
			return;
		}
		Intent i = new Intent(Intent.ACTION_VIEW);
		i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		i.setDataAndType(Uri.parse("file://" + apkfile.toString()),
				"application/vnd.android.package-archive");
		mContext.startActivity(i);
		callback.OnBackResult("finish");


	}
	private int lastRate = 0;
	private Runnable mdownApkRunnable = new Runnable() {
		@Override
		public void run() {
			try {
				sp = getSharedPreferences("SP", Context.MODE_PRIVATE);
				// 获取保存的文件名
				/*String file_name = (null == SharedPreferencesUtils.readObjFromSp(sp,
						Const.SP_FILENAME) ? "" : ""
						+ SharedPreferencesUtils.readObjFromSp(sp, Const.SP_FILENAME));
				if (TextUtils.isEmpty(file_name)){
					System.out.println("文件不存在！");
					return;
				}*/
				URL url = new URL(HttpValue.getHttp()+ Const.UPDATE_DOWNLOAD);
				//URL url = new URL(HttpValue.getHttp()+ Const.UPDATE_DOWNLOAD + file_name);
				System.out.println("下载地址："+url);
				 conn = (HttpURLConnection) url
						.openConnection();
				conn.connect();
				int length = conn.getContentLength();
				InputStream is = conn.getInputStream();
				File apkFile = createApkFile();
				FileOutputStream fos = new FileOutputStream(apkFile);
				int count = 0;
				byte buf[] = new byte[1024];

				do {
					int numread = is.read(buf);
					count += numread;
					progress = (int) (((float) count / length) * 100);
					// 更新进度
					Message msg = mHandler.obtainMessage();
					msg.what = 1;
					msg.arg1 = progress;
					if (progress >= lastRate + 1) {
						mHandler.sendMessage(msg);
						lastRate = progress;
						if (callback != null)
							callback.OnBackResult(progress);
					}
					if (numread <= 0) {
						// 下载完成通知安装
						mHandler.sendEmptyMessage(0);
						// 下载完了，cancelled也要设置
						canceled = true;
						break;
					}
					fos.write(buf, 0, numread);
				} while (!canceled);// 点击取消就停止下载.

				fos.close();
				is.close();
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
	};

	// 通知栏
	/** 初始化通知栏 */
	private void initNotify() {
		System.out.println("初始化通知栏");
		mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		mBuilder = new NotificationCompat.Builder(mContext);
		Intent intent = new Intent(mContext, NotificationUpdateActivity.class);
		//Intent intent = new Intent();
		contentIntent = PendingIntent.getActivity(this, 0,
				intent, PendingIntent.FLAG_UPDATE_CURRENT);
		mBuilder.setWhen(System.currentTimeMillis())// 通知产生的时间，会在通知信息里显示
				.setContentIntent(contentIntent)
				.setPriority(Notification.PRIORITY_LOW)// 设置该通知优先级
				.setAutoCancel(true)//设置这个标志当用户单击面板就可以让通知将自动取消
				.setOngoing(false)// ture，设置他为一个正在进行的通知。他们通常是用来表示一个后台任务,用户积极参与(如播放音乐)或以某种方式正在等待,因此占用设备(如一个文件下载,同步操作,主动网络连接)
				.setDefaults(Notification.DEFAULT_VIBRATE)// 向通知添加声音、闪灯和振动效果的最简单、最一致的方式是使用当前的用户默认设置，使用defaults属性，可以组合：
				// Notification.DEFAULT_ALL Notification.DEFAULT_SOUND 添加声音 //
				// requires VIBRATE permission
				.setSmallIcon(R.mipmap.icon);
	}
	/**
	 * 创建通知
	 */
	/** 显示带进度条通知栏 */
	public void showProgressNotify() {
		Bitmap bitmap =  BitmapFactory.decodeResource(getResources(), R.mipmap.icon);
		lyjApplication.setInDownload(true);
		System.out.println("创建通知栏");
		mBuilder.setContentTitle("正在下载...")
				.setWhen(System.currentTimeMillis())
				.setLargeIcon(bitmap)
				.setContentText("点击进入下载对话框")
				.setTicker("开始下载");// 通知首次出现在通知栏，带上升动画效果的
		mBuilder.setProgress(100, progress, false); // 这个方法是显示进度条  设置为true就是不确定的那种进度条效果
		mNotificationManager.notify(NOTIFY_ID, mBuilder.build());
	}
	/** 设置下载进度 */
	public void setNotify(int progress) {
		mBuilder.setProgress(100, progress, false); // 这个方法是显示进度条
		mNotificationManager.notify(NOTIFY_ID, mBuilder.build());
	}
}

