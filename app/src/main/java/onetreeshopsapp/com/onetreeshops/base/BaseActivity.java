package onetreeshopsapp.com.onetreeshops.base;

import android.app.Activity;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.LocalBroadcastManager;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.Toast;



import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import onetreeshopsapp.com.onetreeshops.R;

/**
 * Created by admin on 2016-05-09.
 */
public class BaseActivity extends Activity implements View.OnClickListener{


    /** USB驱动管理 */
    private UsbManager manager = null;
    /** USB驱动对话框 */
    private Dialog dialog_usbdevices = null;


    /** 本地广播 */
    private LocalBroadcastManager localBroadcastManager = null;
    private static List<Activity> activities;
    public void onClick(View v) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        /*set it to be full screen*/
        /*getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);*/


        if (activities == null) {
            activities = new ArrayList<Activity>();
        }
        activities.add(this);
    }
    public void exitSystem() {
        for (Activity a : activities) {
            a.finish();
        }
    }

    @Override
    protected void onDestroy() {
        activities.remove(this);
        super.onDestroy();

    }

    @Override
    public void finish() {
        activities.remove(this);
        super.finish();
    }

    public void setbarColors() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window window = getWindow();
            ViewGroup rootView;
            View statusView;
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            ViewGroup viewGroup = (ViewGroup) findViewById(android.R.id.content);
            rootView = (ViewGroup) viewGroup.getChildAt(0);
            rootView.setFitsSystemWindows(false);
            rootView.setClipToPadding(true);
            // 绘制一个和状态栏一样高的矩形
            int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
            int statusBarHeight = getResources().getDimensionPixelSize(resourceId);
            statusView = new View(this);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, statusBarHeight);
            statusView.setLayoutParams(params);
            Drawable drawable = rootView.getChildAt(0).getBackground();
            if (drawable instanceof ColorDrawable) {
                statusView.setBackgroundColor(((ColorDrawable) drawable).getColor());
            } else {
                statusView.setBackgroundColor(Color.parseColor("#009943"));
            }
            rootView.addView(statusView, 0);
        }
    }



}
