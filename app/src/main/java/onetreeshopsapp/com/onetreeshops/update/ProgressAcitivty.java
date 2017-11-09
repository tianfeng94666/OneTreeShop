package onetreeshopsapp.com.onetreeshops.update;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationCompat.Builder;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RemoteViews;

import org.xutils.http.annotation.HttpRequest;

import onetreeshopsapp.com.onetreeshops.R;
import onetreeshopsapp.com.onetreeshops.base.BaseActivity;

public class ProgressAcitivty extends BaseActivity implements OnClickListener {
	private Button btn_show_progress;
	private Button btn_show_un_progress;
	private Button btn_show_custom_progress;
	/** Notification的ID */
	int notifyId = 102;
	/** Notification的进度条数值 */
	int progress = 0;
	Builder mBuilder;
	private NotificationManager mNotificationManager;
	Button btn_download_start;
	Button btn_download_pause;
	Button btn_download_cancel;
	/** 下载线程是否暂停 */
	public boolean isPause = false;
	/** 通知栏内是否是自定义的 */
	public boolean isCustom = false;
	DownloadThread downloadThread;
	/** true:为不确定样式的   false:确定样式*/
	public Boolean indeterminate = false;
	private PendingIntent contentIntent;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.notification_progress);
		initView();
		initNotify();
	}

	private void initView() {
		btn_show_progress = (Button) findViewById(R.id.btn_show_progress);
		btn_show_un_progress = (Button) findViewById(R.id.btn_show_un_progress);
		btn_show_custom_progress = (Button) findViewById(R.id.btn_show_custom_progress);
		btn_download_start = (Button) findViewById(R.id.btn_download_start);
		btn_download_pause = (Button) findViewById(R.id.btn_download_pause);
		btn_download_cancel = (Button) findViewById(R.id.btn_download_cancel);
		btn_show_progress.setOnClickListener(this);
		btn_show_un_progress.setOnClickListener(this);
		btn_show_custom_progress.setOnClickListener(this);
		btn_download_start.setOnClickListener(this);
		btn_download_pause.setOnClickListener(this);
		btn_download_cancel.setOnClickListener(this);
	}

	/** 初始化通知栏 */
	private void initNotify() {
        mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		mBuilder = new Builder(this);
		Intent intent = new Intent(this, NotificationUpdateActivity.class);
		 contentIntent = PendingIntent.getActivity(this, 0,
				intent, PendingIntent.FLAG_UPDATE_CURRENT);
		mBuilder.setWhen(System.currentTimeMillis())// 通知产生的时间，会在通知信息里显示
				.setContentIntent(contentIntent)
				.setPriority(Notification.PRIORITY_DEFAULT)// 设置该通知优先级
				.setAutoCancel(true)//设置这个标志当用户单击面板就可以让通知将自动取消
				.setOngoing(false)// ture，设置他为一个正在进行的通知。他们通常是用来表示一个后台任务,用户积极参与(如播放音乐)或以某种方式正在等待,因此占用设备(如一个文件下载,同步操作,主动网络连接)
				.setDefaults(Notification.DEFAULT_VIBRATE)// 向通知添加声音、闪灯和振动效果的最简单、最一致的方式是使用当前的用户默认设置，使用defaults属性，可以组合：
				// Notification.DEFAULT_ALL Notification.DEFAULT_SOUND 添加声音 //
				// requires VIBRATE permission
				.setSmallIcon(R.mipmap.icon);
	}

	/** 显示带进度条通知栏 */
	public void showProgressNotify() {
		mBuilder.setContentTitle("正在下载...")
				.setContentText("进度:")
				.setTicker("开始下载");// 通知首次出现在通知栏，带上升动画效果的
			mBuilder.setProgress(100, progress, false); // 这个方法是显示进度条  设置为true就是不确定的那种进度条效果

		mNotificationManager.notify(notifyId, mBuilder.build());
	}

	/** 显示自定义的带进度条通知栏 */
	private void showCustomProgressNotify(String status) {
		RemoteViews mRemoteViews = new RemoteViews(getPackageName(), R.layout.view_custom_progress);
		mRemoteViews.setImageViewResource(R.id.custom_progress_icon, R.mipmap.icon);
		mRemoteViews.setTextViewText(R.id.tv_custom_progress_title, "一棵树商城.apk 正在下载...");
		mRemoteViews.setTextViewText(R.id.tv_custom_progress_status, status);
		if(progress >= 100 || downloadThread == null){
			mRemoteViews.setProgressBar(R.id.custom_progressbar, 0, 0, false);
			mRemoteViews.setViewVisibility(R.id.custom_progressbar, View.GONE);
		}else{
			mRemoteViews.setProgressBar(R.id.custom_progressbar, 100, progress, false);
			mRemoteViews.setViewVisibility(R.id.custom_progressbar, View.VISIBLE);
		}
		mBuilder.setContent(mRemoteViews)
				.setContentIntent(contentIntent)
				.setTicker("下载更新");
		Notification nitify = mBuilder.build();
		nitify.contentView = mRemoteViews;
		mNotificationManager.notify(notifyId, nitify);
	}

	/** 开始下载 */
	public void startDownloadNotify() {
		isPause = false;
		if (downloadThread != null && downloadThread.isAlive()) {
//			downloadThread.start();
		}else{
			downloadThread = new DownloadThread();
			downloadThread.start();
		}
	}

	/** 暂停下载 */
	public void pauseDownloadNotify() {
		isPause = true;
		if(!isCustom){
			mBuilder.setContentTitle("已暂停");
			setNotify(progress);
		}else{
			showCustomProgressNotify("已暂停");
		}
	}

	/** 取消下载 */
	public void stopDownloadNotify() {
		if (downloadThread != null) {
			downloadThread.interrupt();
		}
		downloadThread = null;
		if(!isCustom){
			mBuilder.setContentTitle("下载已取消").setProgress(0, 0, false);
			mNotificationManager.notify(notifyId, mBuilder.build());
		}else{
			showCustomProgressNotify("下载已取消");
		}
	}

	/** 设置下载进度 */
	public void setNotify(int progress) {
		mBuilder.setProgress(100, progress, false); // 这个方法是显示进度条
		mNotificationManager.notify(notifyId, mBuilder.build());
	}

	/**
	 * 下载线程
	 */
	class DownloadThread extends Thread {

		@Override
		public void run() {
			int now_progress = 0;
			// Do the "lengthy" operation 20 times
			while (now_progress <= 100) {
				// Sets the progress indicator to a max value, the
				// current completion percentage, and "determinate"
				if(downloadThread == null){
					break;
				}
				if (!isPause) {
					progress = now_progress;
					if(!isCustom){
						mBuilder.setContentTitle("下载中");
						if(!indeterminate){
							setNotify(progress);
						}
					}else{
						showCustomProgressNotify("下载中");
					}
					now_progress += 10;
				}
				try {
					// Sleep for 1 seconds
					Thread.sleep(1 * 1000);
				} catch (InterruptedException e) {
				}
			}
			// When the loop is finished, updates the notification
			if(downloadThread != null){
				if(!isCustom){
					mBuilder.setContentText("下载完成")
							// Removes the progress bar
							.setProgress(0, 0, false);
					mNotificationManager.notify(notifyId, mBuilder.build());
				}else{
					showCustomProgressNotify("下载完成");
				}
			}
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.btn_show_progress:
				downloadThread = null;
				isCustom = false;
				indeterminate = false;
				showProgressNotify();
				break;
			case R.id.btn_show_un_progress:
				downloadThread = null;
				isCustom = false;
				indeterminate = true;
				showProgressNotify();
				break;
			case R.id.btn_show_custom_progress:
				downloadThread = null;
				isCustom = true;
				indeterminate = false;
				showCustomProgressNotify("等待下载..");
				break;
			case R.id.btn_download_start:
				startDownloadNotify();
				break;
			case R.id.btn_download_pause:
				pauseDownloadNotify();
				break;
			case R.id.btn_download_cancel:
				stopDownloadNotify();
				break;
			default:
				break;
		}
	}
	private void test(){

	}

}
