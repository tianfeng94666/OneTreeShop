package onetreeshopsapp.com.onetreeshops.activity;

import android.app.Activity;
import android.content.Intent;
import android.hardware.Camera;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;


import onetreeshopsapp.com.onetreeshops.R;
import onetreeshopsapp.com.onetreeshops.utils.HttpValue;
import onetreeshopsapp.com.onetreeshops.utils.IntentUtils;


/**
 * Created by fiona on 2016/7/1.
 */
public class FirstActivity extends Activity {

    private ImageView iv_first;
    private TextView tv_first;
    private CountDownTimer countdowntimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);
        init();
    }

    private void init() {
        tv_first = (TextView) findViewById(R.id.tv_first);
        tv_first.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                countdowntimer.cancel();
                countdowntimer.onFinish();
            }
        });
        iv_first = (ImageView) findViewById(R.id.iv_first);
        //设置图片
        String url = HttpValue.getHttp()
                + "/pos/binary/adpic";
//        Glide.with(this)
//                .load("http://192.168.1.181:8069/pos/binary/adpic")
//                .skipMemoryCache(true)
//                .into(iv_first);


        Picasso
                .with(FirstActivity.this)
                .load("http://192.168.1.181:8069/pos/binary/adpic")
                .fit()
                .error(R.mipmap.icon)
                .into(iv_first, new Callback() {
                    @Override
                    public void onSuccess() {
                        docountdown();
                    }

                    @Override
                    public void onError() {
                        docountdown();
                    }
                });


    }
    //倒计时
    private void docountdown() {
         countdowntimer = new CountDownTimer(3000, 1000) {

            @Override
            public void onTick(long millisUntilFinished) {
                tv_first.setVisibility(View.VISIBLE);
                tv_first.setText("跳过广告 "+(millisUntilFinished / 1000));
            }
            @Override
            public void onFinish() {
                Intent intentlogin = new Intent(FirstActivity.this, LoginActivity.class);
                startActivity(intentlogin);
                finish();

            }
        };
        countdowntimer.start();
    }
    /*private void settime(){
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                IntentUtils.getIntentUtils().intent(FirstActivity.this,LoginActivity.class);
                finish();
            }
        };
        Timer timer = new Timer();
        timer.schedule(timerTask, 3000);
    }*/

    /**
     * startActivity屏蔽物理返回按钮
     *
     * @param keyCode
     * @param event
     * @return
     */

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
