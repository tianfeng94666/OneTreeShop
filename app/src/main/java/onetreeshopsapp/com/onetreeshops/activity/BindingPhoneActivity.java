package onetreeshopsapp.com.onetreeshops.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import onetreeshopsapp.com.onetreeshops.R;
import onetreeshopsapp.com.onetreeshops.base.BaseActivity;
import onetreeshopsapp.com.onetreeshops.bean.GetCaptchaResult;
import onetreeshopsapp.com.onetreeshops.bean.RegisterResult;
import onetreeshopsapp.com.onetreeshops.http.JsonRPCAsyncTask;
import onetreeshopsapp.com.onetreeshops.utils.Const;
import onetreeshopsapp.com.onetreeshops.utils.GsonUtil;
import onetreeshopsapp.com.onetreeshops.utils.HttpValue;
import onetreeshopsapp.com.onetreeshops.utils.IntentUtils;
import onetreeshopsapp.com.onetreeshops.utils.NetWorkUtils;
import onetreeshopsapp.com.onetreeshops.utils.SharedPreferencesUtils;
import onetreeshopsapp.com.onetreeshops.utils.SmsUtil;
import onetreeshopsapp.com.onetreeshops.utils.ToastUtils;
import onetreeshopsapp.com.onetreeshops.utils.UIProgressUtil;

/**
 * Created by ERP on 2016/10/13.
 */
public class BindingPhoneActivity extends BaseActivity implements View.OnClickListener{
    private EditText edit_modifyphone_phone,edit_modifyphone_captcha,edit_modifyphone_newphone;
    private Button btn_modifyphone_captcha,btn_modifyphone_confirm;
    private String phone,captcha,password;
    private ImageButton modifyphone_back_imgbtn;
    private SharedPreferences sp;
    private String type;
    /** 服务器返回的时间 */
    private String server_time;
    /** 60秒只能获取一次验证码 */
    private int time = 60;
    private SmsUtil smsUtil;
    private final int SUCCESS = 0;
    private static String CODE = "您的验证码是：";
    private static String SENTENCE = "请不要把验证码泄露给其他人。";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_myphone);
        sp = getSharedPreferences("SP", Context.MODE_PRIVATE);
        initview();
        // 调用短信获取工具类
        smsUtil = new SmsUtil(this, hd, CODE, SENTENCE);
        // 注册内容观察者
        this.getContentResolver().registerContentObserver(
                Uri.parse("content://sms/"), true, smsUtil);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            handler.removeCallbacks(runnable);// 关闭计时器
            time = 120;
        } catch (Exception e) {
        }
    }

    private void initview() {
        btn_modifyphone_confirm = (Button)findViewById(R.id.btn_modifyphone_confirm);
        modifyphone_back_imgbtn = (ImageButton) findViewById(R.id.modifyphone_back_imgbtn);
        modifyphone_back_imgbtn.setOnClickListener(this);
        // 设置保存的登陆名
         phone = (null == SharedPreferencesUtils.readObjFromSp(sp,
                Const.SP_USERNAME) ? "" : ""
                + SharedPreferencesUtils.readObjFromSp(sp, Const.SP_USERNAME));
        edit_modifyphone_phone = (EditText) findViewById(R.id.edit_modifyphone_phone);
        if (!TextUtils.isEmpty(phone)){
            edit_modifyphone_phone.setEnabled(false);
            edit_modifyphone_phone.setText(phone.replaceAll("(\\d{3})\\d{4}(\\d{4})","$1****$2"));
        }else {
            edit_modifyphone_phone.setEnabled(true);
        }
        edit_modifyphone_captcha = (EditText) findViewById(R.id.edit_modifyphone_captcha);
        edit_modifyphone_newphone = (EditText) findViewById(R.id.edit_modifyphone_newphone);
        btn_modifyphone_captcha = (Button)findViewById(R.id.btn_modifyphone_captcha);
        btn_modifyphone_captcha.setOnClickListener(this);
        btn_modifyphone_confirm.setOnClickListener(this);
        // 设置验证码输入框不可用
        edit_modifyphone_captcha.setEnabled(false);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.btn_modifyphone_captcha:
                if (!isMobileNO(phone)){
                    ToastUtils.show(BindingPhoneActivity.this,"请填写正确手机号！");
                    return;
                }
                if (!NetWorkUtils.getNetConnecState(this)) {
                    Toast.makeText(BindingPhoneActivity.this, "请检查网络是否连接！", Toast.LENGTH_SHORT).show();
                    return;
                }
                UIProgressUtil.showProgress(BindingPhoneActivity.this,"获取验证码中...",false);
                getcaptcha();
                break;
            case R.id.btn_modifyphone_confirm:
                verifydata();
                break;
            case R.id.modifyphone_back_imgbtn:
                finish();
                break;
        }
    }

    private void verifydata() {
        if (!isMobileNO(phone)){
            ToastUtils.show(BindingPhoneActivity.this,"请填写正确手机号！");
            return;
        }
        if (TextUtils.isEmpty(edit_modifyphone_captcha.getText().toString().trim())){
            ToastUtils.show(BindingPhoneActivity.this,"请输入验证码！");
            return;
        }
        if(!captcha.equals(edit_modifyphone_captcha.getText().toString().trim())){
            ToastUtils.show(BindingPhoneActivity.this,"您输入的验证码不正确！");
            return;
        }
        if (!isMobileNO(edit_modifyphone_newphone.getText().toString().trim())){
            ToastUtils.show(BindingPhoneActivity.this,"请填写正确手机号！");
            return;
        }
        if (edit_modifyphone_newphone.equals(edit_modifyphone_phone)){
            ToastUtils.show(BindingPhoneActivity.this,"不能重复绑定手机号！");
            return;
        }
        if (!NetWorkUtils.getNetConnecState(this)) {
            Toast.makeText(BindingPhoneActivity.this, "请检查网络是否连接！", Toast.LENGTH_SHORT).show();
            return;
        }
        UIProgressUtil.showProgress(BindingPhoneActivity.this,"数据保存中...",false);
            bindingphone();
    }

    //获取验证码
    private void getcaptcha() {
        try {
            JSONObject params = new JSONObject();
                params.put("mobile", phone);
            new JsonRPCAsyncTask(BindingPhoneActivity.this, mHandler,
                    Const.CAPTCHA_CODE, HttpValue.getHttp() + Const.REG_CAPTCHA, "call",
                    params).execute();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    //绑定手机号码
    private void bindingphone() {
        try {
            JSONObject params = new JSONObject();
            params.put("user_id", HttpValue.SP_USERID);
            params.put("new_login",edit_modifyphone_newphone.getText().toString().trim());
            new JsonRPCAsyncTask(BindingPhoneActivity.this, mHandler,
                    Const.BINDING_PHONE_CODE, HttpValue.getHttp() + Const.BINDING_PHONE, "call",
                    params).execute();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            String result = (String) msg.obj;
            UIProgressUtil.cancelProgress();
            if (result==null){
                ToastUtils.show(BindingPhoneActivity.this,"亲，服务器出问题啦，请稍后再试！");
                return;
            }
            if (msg.what== Const.CAPTCHA_CODE) {
                System.out.println("-->>[注册界面]请求验证码返回json = " + result);
                if (GsonUtil.isError(result)) {
                    ToastUtils.show(BindingPhoneActivity.this, "获取验证码出错");
                    return;
                }
                GetCaptchaResult getCaptchaResult = GsonUtil.jsonToBean(result, GetCaptchaResult.class);
                        if (getCaptchaResult !=null&&getCaptchaResult.getResult().getFlag()){
                            captcha = getCaptchaResult.getResult().getRes().getValidatecode();//获得服务器返回的验证码
                            handler.postDelayed(runnable, 1000);
                        }else if (getCaptchaResult.getResult().getFlag()==false){
                            ToastUtils.show(BindingPhoneActivity.this,getCaptchaResult.getResult().getInfo());
                        }

            }else if (msg.what == Const.BINDING_PHONE_CODE){
                System.out.println("绑定手机返回数据为：" + result);
                if (GsonUtil.isError(result)) {
                    ToastUtils.show(BindingPhoneActivity.this, "绑定手机出错");
                    return;
                }
                GetCaptchaResult getCaptchaResult = GsonUtil.jsonToBean(result, GetCaptchaResult.class);
                if (getCaptchaResult!= null&&getCaptchaResult.getResult().getFlag()){
                    ToastUtils.show(BindingPhoneActivity.this, "修改绑定手机成功！");
                    SharedPreferencesUtils.saveObjToSp(sp,
                            Const.SP_USERNAME,edit_modifyphone_newphone.getText().toString().trim());
                    Intent intent = new Intent(BindingPhoneActivity.this,MyEditActivity.class);
                    startActivity(intent);
                    finish();
                }else {
                    ToastUtils.show(BindingPhoneActivity.this, getCaptchaResult.getResult().getInfo());
                }
            }
        }
    };
    /** 刷新UI */
    private Handler handler = new Handler() {

        public void handleMessage(Message msg) {
            btn_modifyphone_captcha.setEnabled(false);// 停止按钮操作
            btn_modifyphone_captcha.setText("" + msg.arg1);// 显示计时数字
            edit_modifyphone_captcha.setEnabled(true);// 恢复验证码输入框可用
            switch (msg.what) {
                case 1:
                    handler.removeCallbacks(runnable);
                    time = 120;
                    btn_modifyphone_captcha.setEnabled(true);
                    btn_modifyphone_captcha.setText("获取验证码");
                    break;
            }
        };
    };
    //读取短信验证码
    private Handler hd = new Handler() {
        // 接收消息
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == SUCCESS) {
                edit_modifyphone_captcha.setText(String.valueOf(msg.obj));
                System.out.println(msg);
            }
        }
    };
    /** 计时线程(用于验证码60s验证) */
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {

            handler.postDelayed(runnable, 1000);// 1秒一次
            time = time - 1;
            Message msg = Message.obtain();
            msg.arg1 = time;
            handler.sendMessage(msg);
            if (time == 0) {
                time = -1;
                handler.sendEmptyMessage(1);
            }
        }
    };

    /*手机号码格式验证*/
    public static boolean isMobileNO(String mobiles) {
    /*第一位必定为1，第二位必定为3,4或5或8，其他位置的可以为0-9*/
        String telRegex = "[1][3458]\\d{9}";//"[1]"代表第1位为数字1，"[3458]"代表第二位可以为3、4、5、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
        if (TextUtils.isEmpty(mobiles))
            return false;
        else return mobiles.matches(telRegex);
    }

    //监听返回键
    private long mExitTime;
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getRepeatCount() == 0) {
                IntentUtils.getIntentUtils().intent(BindingPhoneActivity.this,MyEditActivity.class);
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
