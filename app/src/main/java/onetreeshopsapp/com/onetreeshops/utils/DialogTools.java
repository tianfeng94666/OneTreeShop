package onetreeshopsapp.com.onetreeshops.utils;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Handler;
import android.provider.Settings;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import onetreeshopsapp.com.onetreeshops.R;


/**
 * 退出对话框(系统类型)
 * 
 * @author C's
 */
public class DialogTools {

	/**
	 * 创建普通单按钮对话框
	 * 
	 * @param ctx
	 *            上下文 必填
	 * @param iconId
	 *            图标，如：R.drawable.icon 必填
	 * @param title
	 *            标题 必填
	 * @param message
	 *            显示内容 必填
	 * @param btnName
	 *            按钮名称 必填
	 * @param listener
	 *            监听器，需实现android.content.DialogInterface.OnClickListener接口 必填
	 * @return
	 */
	public static Dialog createDialog(Context ctx, int iconId, String title,
			String message, String btnName, View.OnClickListener listener) {
		/*
		 * Dialog dialog = null; android.app.AlertDialog.Builder builder = new
		 * android.app.AlertDialog.Builder(ctx); // 设置对话框的图标
		 * builder.setIcon(iconId); // 设置对话框的标题 builder.setTitle(title); //
		 * 设置对话框的显示内容 builder.setMessage(message); //
		 * 添加按钮，android.content.DialogInterface.OnClickListener.OnClickListener
		 * builder.setPositiveButton(btnName, listener); // 创建一个普通对话框 dialog =
		 * builder.create(); return dialog;
		 */

		final Dialog loadingDialog = new Dialog(ctx, R.style.loading_dialog);
		View view = View.inflate(ctx, R.layout.dialog_alter, null);
		TextView tv_title = (TextView) view.findViewById(R.id.tv_title);
		TextView tv_msg = (TextView) view.findViewById(R.id.tv_msg);
		ImageView img_log = (ImageView) view.findViewById(R.id.img_log);
		Button btn_cancel = (Button) view.findViewById(R.id.btn_cancel);

		tv_title.setText(title);
		tv_msg.setText(message);
		img_log.setImageResource(iconId);
		btn_cancel.setText(btnName);
		btn_cancel.setOnClickListener(listener);

		loadingDialog.setCancelable(false);// 不可以用“返回键”取消
		loadingDialog.setContentView(view, new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT,
				LinearLayout.LayoutParams.MATCH_PARENT));// 设置布局
		return loadingDialog;

	}

	public static Dialog createDialogok(Context ctx, String message,
			View.OnClickListener listener) {

		final Dialog loadingDialog = new Dialog(ctx, R.style.loading_dialog);
		View view = View.inflate(ctx, R.layout.dialog_okmc_alter, null);
		TextView tv_okmc_message = (TextView) view
				.findViewById(R.id.tv_okmc_message);
		ImageView iv_okmc_cancel = (ImageView) view
				.findViewById(R.id.iv_okmc_cancel);

		tv_okmc_message.setText(message);
		iv_okmc_cancel.setOnClickListener(listener);
		loadingDialog.setCancelable(false);
		loadingDialog.setContentView(view, new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT,
				LinearLayout.LayoutParams.MATCH_PARENT));// 设置布局

		return loadingDialog;
	}

	/**
	 * 创建普通双按钮对话框
	 * 
	 * @param ctx
	 *            上下文 必填
	 * @param iconId
	 *            图标，如：R.drawable.icon[不想显示就写0] 必填
	 * @param title
	 *            标题 必填
	 * @param message
	 *            显示内容 必填
	 * @param btnPositiveName
	 *            第一个按钮名称 必填
	 * @param listener_Positive
	 *            第一个监听器，需实现android.content.DialogInterface.OnClickListener接口 必填
	 * @param btnNegativeName
	 *            第二个按钮名称 必填
	 * @param listener_Negative
	 *            第二个监听器，需实现android.content.DialogInterface.OnClickListener接口 必填
	 * @return 对话框实例
	 */
	/*public static Dialog createDialog(Context ctx, int iconId, String title,
			String message, String btnPositiveName,
			View.OnClickListener listener_Positive, String btnNegativeName,
			View.OnClickListener listener_Negative) {

		final Dialog loadingDialog = new Dialog(ctx, R.style.loading_dialog);
		View view = View.inflate(ctx, R.layout.dialog_alter2, null);
		TextView tv_title = (TextView) view.findViewById(R.id.tv_title);
		TextView tv_msg = (TextView) view.findViewById(R.id.tv_msg);
		ImageView img_log = (ImageView) view.findViewById(R.id.img_log);
		Button btn_sure = (Button) view.findViewById(R.id.btn_sure);
		Button btn_cancel = (Button) view.findViewById(R.id.btn_cancel);

		tv_title.setText(title);
		tv_msg.setText(message);
		img_log.setImageResource(iconId);
		btn_sure.setText(btnPositiveName);
		btn_sure.setOnClickListener(listener_Positive);
		btn_cancel.setText(btnNegativeName);
		btn_cancel.setOnClickListener(listener_Negative);

		loadingDialog.setCancelable(false);// 不可以用“返回键”取消
		loadingDialog.setContentView(view, new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT,
				LinearLayout.LayoutParams.MATCH_PARENT));// 设置布局
		return loadingDialog;
	}*/

	/**
	 * 创建普通双按钮对话框
	 * 
	 * @param ctx
	 *            上下文 必填
	 * @param iconId
	 *            图标，如：R.drawable.icon[不想显示就写0] 必填
	 * @param title
	 *            标题 必填
	 * @param message
	 *            显示内容 必填
	 * @param btnPositiveName
	 *            第一个按钮名称 必填
	 * @param listener_Positive
	 *            第一个监听器，需实现android.content.DialogInterface.OnClickListener接口 必填
	 * @param btnNegativeName
	 *            第二个按钮名称 必填
	 * @param listener_Negative
	 *            第二个监听器，需实现android.content.DialogInterface.OnClickListener接口 必填
	 * @return 对话框实例
	 */
	public static Dialog createDialog(Context ctx, int iconId, String title,
			String message, String btnPositiveName,
			View.OnClickListener listener_Positive, String btnNegativeName,
			View.OnClickListener listener_Negative, boolean f) {

		final Dialog loadingDialog = new Dialog(ctx, R.style.loading_dialog);
		View view = View.inflate(ctx, R.layout.dialog_alter2, null);
		TextView tv_title = (TextView) view.findViewById(R.id.tv_title);
		TextView tv_msg = (TextView) view.findViewById(R.id.tv_msg);
		if (f) {
			tv_msg.setGravity(Gravity.CENTER_VERTICAL);
			tv_msg.setPadding(DensityUtil.dip2px(ctx, 20), 0, 0, 0);
		}
		ImageView img_log = (ImageView) view.findViewById(R.id.img_log);
		Button btn_sure = (Button) view.findViewById(R.id.btn_sure);
		Button btn_cancel = (Button) view.findViewById(R.id.btn_cancel);

		tv_title.setText(title);
		tv_msg.setText(message);
		img_log.setImageResource(iconId);
		btn_sure.setText(btnPositiveName);
		btn_sure.setOnClickListener(listener_Positive);
		btn_cancel.setText(btnNegativeName);
		btn_cancel.setOnClickListener(listener_Negative);

		loadingDialog.setCancelable(false);// 不可以用“返回键”取消

		loadingDialog.setContentView(view, new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT,
				LinearLayout.LayoutParams.MATCH_PARENT));// 设置布局
		return loadingDialog;
	}

	/**
	 * 加载对话框
	 * 
	 * @param context
	 *            上下文
	 * @param msg
	 *            内容
	 * @return
	 */
	public static Dialog createLoadDialog(Context context, String msg) {

		/*
		 * LayoutInflater inflater = LayoutInflater.from(context); View v =
		 * inflater.inflate(R.layout.loading_dialog, null);// 得到加载view
		 * 
		 * // main.xml中的ImageView ImageView spaceshipImage = (ImageView)
		 * v.findViewById(R.id.img); TextView tipTextView = (TextView)
		 * v.findViewById(R.id.tipTextView);// 提示文字 // 加载动画 Animation
		 * hyperspaceJumpAnimation = AnimationUtils.loadAnimation(context,
		 * R.anim.loading_animation); // 使用ImageView显示动画
		 * spaceshipImage.startAnimation(hyperspaceJumpAnimation);
		 * tipTextView.setText(msg);// 设置加载信息
		 * 
		 * final Dialog loadingDialog = new Dialog(context,
		 * R.style.loading_dialog);// 创建自定义样式dialog
		 * loadingDialog.setCancelable(false);// 不可以用“返回键”取消 new
		 * Handler().postAtTime(new Runnable() {
		 * 
		 * @Override public void run() { loadingDialog.setCancelable(true);
		 * 
		 * } }, 15000); LinearLayout layout = (LinearLayout)
		 * v.findViewById(R.id.dialog_view);// 加载布局
		 * loadingDialog.setContentView(layout, new
		 * LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
		 * LinearLayout.LayoutParams.MATCH_PARENT));// 设置布局
		 * 
		 * return loadingDialog;
		 */

		//return loadingDialog;
		return null;
	}

	/*public static Dialog createLoaDialog(Context context) {

		Dialog loadingDialog = new LoadDialog(context, R.style.myDialogTheme);
		return loadingDialog;
	}*/

	static Dialog dialog;

	public static void showProgressDialog(Context context, String msg) {

		dialog = createLoadDialog(context, msg);
		dialog.show();

	}

	public static void closeProgressDialog() {
		if (dialog != null && dialog.isShowing()) {
			dialog.dismiss();
		}
	}

	/**
	 * 当判断当前手机没有网络时使用
	 * 
	 * @param context
	 */
	public static void showNoNetWork(final Context context) {
		Builder builder = new Builder(context);
		//builder.setIcon(R.drawable.ic_launcher)
				//
				builder.setTitle(R.string.app_name)
				//
				.setMessage("当前无网络")
				.setPositiveButton("设置", new OnClickListener() {

					public void onClick(DialogInterface dialog, int which) {
						// 跳转到系统的网络设置界面
						dialog.dismiss();
						Intent intent = new Intent(
								Settings.ACTION_WIFI_SETTINGS);
						context.startActivity(intent);

					}
				}).setNegativeButton("知道了", new OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				}).show();
	}

	/**
	 * 创建自定义对话框
	 * 
	 * @param context
	 */
	/*public static Dialog createSelfDialog(Context context, int iconId,
			String title, View contentView) {
		final Dialog dialog = new Dialog(context, R.style.loading_dialog);// 创建自定义样式dialog
		View view = View.inflate(context, R.layout.dialog_model, null);
		LinearLayout ll_content = (LinearLayout) view
				.findViewById(R.id.ll_content);
		ll_content.addView(contentView);
		TextView tv_title = (TextView) view.findViewById(R.id.tv_title);
		tv_title.setText(title);
		ImageView img_title = (ImageView) view.findViewById(R.id.img_title);
		img_title.setImageResource(iconId);
		final Button cancel = (Button) view.findViewById(R.id.btn_cancel);
		cancel.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				dialog.dismiss();
			}
		});
		dialog.setCancelable(false);// 不可以用“返回键”取消
		dialog.setContentView(view, new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT,
				LinearLayout.LayoutParams.MATCH_PARENT));// 设置布局
		return dialog;
	}*/

	/**
	 * 创建普通单按钮对话框
	 * 
	 * @param ctx
	 *            上下文 必填
	 * @param iconId
	 *            图标，如：R.drawable.icon 必填
	 * @param //title
	 *            标题 必填
	 * @param message
	 *            显示内容 必填
	 * @param btnName
	 *            按钮名称 必填
	 * @param listener
	 *            监听器，需实现android.content.DialogInterface.OnClickListener接口 必填
	 * @return
	 */
	public static Dialog createDialog(Context ctx, int iconId, String message,
			String btnName, View.OnClickListener listener) {

		final Dialog loadingDialog = new Dialog(ctx, R.style.loading_dialog);
		View view = View.inflate(ctx, R.layout.dialog_alter3, null);
		TextView tv_msg = (TextView) view.findViewById(R.id.tv_msg);
		ImageView img_log = (ImageView) view.findViewById(R.id.img_log);
		Button btn_cancel = (Button) view.findViewById(R.id.btn_cancel);

		tv_msg.setText(message);
		img_log.setImageResource(iconId);
		btn_cancel.setText(btnName);
		btn_cancel.setOnClickListener(listener);

		loadingDialog.setCancelable(false);// 不可以用“返回键”取消
		loadingDialog.setContentView(view, new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT,
				LinearLayout.LayoutParams.MATCH_PARENT));// 设置布局
		return loadingDialog;

	}

}
