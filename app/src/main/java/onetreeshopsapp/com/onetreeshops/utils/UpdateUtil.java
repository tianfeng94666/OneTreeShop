package onetreeshopsapp.com.onetreeshops.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import onetreeshopsapp.com.onetreeshops.R;


/**
 * 更新类
 *
 */
public class UpdateUtil {
	/** 下载中... */
	private static final int DOWN_UPDATE = 1;
	/** 下载完成 */
	private static final int DOWN_OVER = 2;
	/** 上下文 */
	private Context context = null;
	/** 提示更新对话框 */
	private Dialog noticeDialog = null;
	/** 带进度条下载对话框 */
	private Dialog downloadDialog = null;
	/** 显示进度 */
	private TextView tv_update = null;
	/** 进度条 */
	private ProgressBar mProgress = null;
	/** 进度 */
	private int progress = 0;
	/** 是否取消下载 */
	private boolean interceptFlag = false;
	/** 文件名 */
	private static String fname = null;
	/** 保存文件路径 */
	private static String savepath = null;
	
	private String file_name;

	/**
	 * 带参上下文
	 * 
	 * @param context
	 */
	public UpdateUtil(Context context) {
		this.context = context;
	}

	/** 更新UI */
	private Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case DOWN_UPDATE:// 下载中
				mProgress.setProgress(progress);
				tv_update.setText("" + progress);
				break;
			case DOWN_OVER:// 下载完成
				installApk();
				break;
			default:
				break;
			}
		};
	};

	/**
	 * 获取更新信息
	 * 
	 * @param dialogflag
	 *            是否开启对话框
	 */
	public void getUpdateInfo(boolean dialogflag) {
		if (FileUtil.checkStorage()) {
			if (savepath == null) {
				savepath = Environment.getExternalStorageDirectory() + File.separator + "onetreeshopsapp" + File.separator + "download" + File.separator;
			}
			fname = "onetreeshopsapp.apk";
			new UpdateAsyn(dialogflag).execute(HttpValue.getHttp() + Const.UPDATE_SELECT);
		} else {
			if (dialogflag) {
				Toast.makeText(context, "检测到设备没有SD卡!", Toast.LENGTH_SHORT).show();
			}
		}
	}

	/**
	 * 异步线程(获取更新信息)
	 */
	private class UpdateAsyn extends AsyncTask<String, Void, String> {

		/** 对话框 */
		private Dialog dialog = null;

		/** 是否开启对话框 */
		private boolean dialogflag = false;

		/** 参数 */
		private Map<String, String> pars = null;

		/** 版本号 */
		private String versionCode = null;

		/** 版本名称 */
		private String versionName = null;

		/**
		 * 带参构造方法
		 * 
		 * @param dialogflag
		 *            是否开启对话框
		 */
		public UpdateAsyn(boolean dialogflag) {
			this.dialogflag = dialogflag;
		}

		@Override
		protected void onPreExecute() {
			if (dialogflag) {
				dialog = DialogTools.createLoadDialog(context, "正在获取更新中...");
				dialog.setCancelable(true);
				// 显示
				dialog.show();
			}
			if (pars == null) {
				pars = new HashMap<String, String>();
			}
			super.onPreExecute();
		}

		@Override
		protected String doInBackground(String... params) {
			// 请求
			String gets = null;
			try {
				// 获取版本号
				PackageInfo pinfo = context.getPackageManager().getPackageInfo("onetreeshopsapp.com.onetreeshops", PackageManager.GET_CONFIGURATIONS);
				// 获取版本号
				//versionCode = "" + pinfo.versionCode;
				versionName = pinfo.versionName;
				// 获取更新信息
				//gets = HttpUtil.doGet(params[0]);
			} catch (Exception e) {
			}
			return gets;
		}

		@Override
		protected void onPostExecute(String result) {
			if (dialogflag) {
				dialog.dismiss();
			}
			System.out.println("-->>UpdateAsyn:"+result);
			if (result != null) {
				try {
					// 判断json
					JSONObject jsonObject = new JSONObject(result.toString());
					String code = jsonObject.getString("version");
					String message = jsonObject.getString("message");
					file_name = jsonObject.getString("file_name");
					String utf_8message=new String(message.getBytes("iso-8859-1"),"utf-8");
					String time = jsonObject.getString("time");
					long smallposCode = getVerCode(versionName);
					long serverCode = getVerCode(code);
					// 进行code匹对
					if (smallposCode < serverCode) {// 可以更新
						showNoticeDialog("当前版本:" + versionName + "\n升级版本:" + code + "\n更新信息:" + utf_8message + "\n更新时间:" + time);
					} else if (smallposCode == serverCode) {
						if (dialogflag) {
							Toast.makeText(context, "当前已经是最新版!", Toast.LENGTH_SHORT).show();
						}
					} else if (smallposCode > serverCode) {
						if (dialogflag) {
							Toast.makeText(context, "当前已经是最新版!", Toast.LENGTH_SHORT).show();
						}
					}
				} catch (Exception e) {
					if (dialogflag) {
						Toast.makeText(context, "当前已经是最新版!", Toast.LENGTH_SHORT).show();
					}
				}
			} else {
				if (dialogflag) {
					Toast.makeText(context, "当前已经是最新版!", Toast.LENGTH_SHORT).show();
				}
			}
			super.onPostExecute(result);
		}
	}

	/**
	 * 删除已经更新的apk
	 */
	public static void delSmallparkApk() {
		if (FileUtil.checkStorage()) {
			if (savepath == null) {
				savepath = Environment.getExternalStorageDirectory() + File.separator + "smallpark" + File.separator + "download" + File.separator;
			}
			fname = "smallpark.apk";
			File file = new File(savepath + fname);
			if (file.exists()) {
				file.delete();
			}
		}
	}

	/**
	 * 获取long型的版本号
	 * 
	 * @param vercode
	 *            版本号
	 * @return
	 */
	private long getVerCode(String vercode) {
		long a = 0;
		if (vercode != null && vercode.length() > 0) {
			a = Long.parseLong(vercode.replaceAll("[^0-9]", ""));
		}
		return a;
	}

	private Dialog downdDialog;
	private void showNoticeDialog(String mes) {
		downdDialog=DialogTools.createDialog(context, R.mipmap.ic_launcher, "软件版本更新", mes, "下载", new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				downdDialog.dismiss();
				//SmallparkApplication.getInstance().ISAPPUPDATE=true;

				//开启更新服务UpdateService
				//这里为了把update更好模块化，可以传一些updateService依赖的值
				//如布局ID，资源ID，动态获取的标题,这里以app_name为例
				/*Intent updateIntent =new Intent(context, UpdateService.class);
				String savepath = Environment.getExternalStorageDirectory() + File.separator + "smallpark" + File.separator + "download" + File.separator;
				updateIntent.putExtra("updateDir",savepath);
				updateIntent.putExtra("fname",fname);
				updateIntent.putExtra("downpath",HttpConstant.UPDATE_DOWNLOAD+file_name);*/
				
				//context.startService(updateIntent);

			}
		}, "以后再说", new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				downdDialog.dismiss();
			}
		},true);
		
		downdDialog.show();
	}

	private void showDownloadDialog() {
		Builder builder = new Builder(context);
		builder.setCancelable(false);
		builder.setTitle("软件版本更新");
		final LayoutInflater inflater = LayoutInflater.from(context);
		View v = inflater.inflate(R.layout.update_progress, null);
		mProgress = (ProgressBar) v.findViewById(R.id.progress);
		tv_update = (TextView) v.findViewById(R.id.tv_update);
		builder.setView(v);
		builder.setNegativeButton("取消", new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				interceptFlag = true;
			}
		});
		downloadDialog = builder.create();
		downloadDialog.show();
		// 下载apk线程
		new DownApkThread().start();
	}

	private class DownApkThread extends Thread {

		@Override
		public void run() {
			try {
				if (FileUtil.checkStorage()) {
					URL url = new URL(HttpValue.UPDATE_DOWNLOAD+file_name);
					HttpURLConnection conn = (HttpURLConnection) url.openConnection();
					conn.connect();
					int length = conn.getContentLength();
					InputStream is = conn.getInputStream();

					String savepath = Environment.getExternalStorageDirectory() + File.separator + "smallpark" + File.separator + "download" + File.separator;

					File file = new File(savepath);
					if (!file.exists()) {
						file.mkdir();
					}
					String apkFile = savepath + fname;
					File ApkFile = new File(apkFile);
					FileOutputStream fos = new FileOutputStream(ApkFile);
					int count = 0;
					byte buf[] = new byte[1024];
					do {
						int numread = is.read(buf);
						count += numread;
						progress = (int) (((float) count / length) * 100) + 1;
						// 更新进度
						mHandler.sendEmptyMessage(DOWN_UPDATE);
						if (numread <= 0 && progress == 100) {
							// 下载完成通知安装
							mHandler.sendEmptyMessage(DOWN_OVER);
							break;
						}
						fos.write(buf, 0, numread);
					} while (!interceptFlag);// 点击取消就停止下载.
					fos.close();
					is.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

			super.run();
		}
	}

	/**
	 * 安装apk
	 * 
	 * @param //url
	 */
	private void installApk() {
		if (FileUtil.checkStorage()) {
			String savepath = Environment.getExternalStorageDirectory() + File.separator + "smallpark" + File.separator + "download" + File.separator;
			File apkfile = new File(savepath + fname);
			if (apkfile.exists()) {
				Intent i = new Intent(Intent.ACTION_VIEW);
				i.setDataAndType(Uri.parse("file://" + apkfile.toString()), "application/vnd.android.package-archive");
				i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				context.startActivity(i);
			}
		}
	}
}
