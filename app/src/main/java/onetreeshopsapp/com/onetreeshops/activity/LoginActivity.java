package onetreeshopsapp.com.onetreeshops.activity;//package onetreeposapp.com.onetreeposapp.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import onetreeshopsapp.com.onetreeshops.R;
import onetreeshopsapp.com.onetreeshops.application.LYJApplication;
import onetreeshopsapp.com.onetreeshops.base.BaseActivity;
import onetreeshopsapp.com.onetreeshops.bean.Loginresult;
import onetreeshopsapp.com.onetreeshops.bean.Payway;
import onetreeshopsapp.com.onetreeshops.dialog.SettingDialog;
import onetreeshopsapp.com.onetreeshops.http.JsonRPCAsyncTask;
import onetreeshopsapp.com.onetreeshops.http.Session;
import onetreeshopsapp.com.onetreeshops.update.NotificationUpdateActivity;
import onetreeshopsapp.com.onetreeshops.update.UpdataResult;
import onetreeshopsapp.com.onetreeshops.utils.Const;
import onetreeshopsapp.com.onetreeshops.utils.GsonUtil;
import onetreeshopsapp.com.onetreeshops.utils.HttpValue;
import onetreeshopsapp.com.onetreeshops.utils.IntentUtils;
import onetreeshopsapp.com.onetreeshops.utils.NetWorkUtils;
import onetreeshopsapp.com.onetreeshops.utils.SharedPreferencesUtils;
import onetreeshopsapp.com.onetreeshops.utils.ToastUtils;
import onetreeshopsapp.com.onetreeshops.utils.UIProgressUtil;
import onetreeshopsapp.com.onetreeshops.utils.view.OwlView;


/**
 * Created by admin on 2016-05-09.
 */

public class LoginActivity extends BaseActivity {
    private OwlView owlView;
    public EditText usernameEdit;
    public EditText passwordEdit;
    public Button loginBut;
    private TextView textview_terminal,tv_forgetpassword,tv_reject;
    private final int UPDATE_SELECT_CODE = 1001;
    private LYJApplication lyjApplication;
    private String apk_size = "";
    /**
     * 用户id
     */
    private int uid = 0;
    /**
     * session 收银id 获取
     */
    private int SessionRequestCode = 2;

    private SharedPreferences sp;
    private ImageButton imagebut_setting;
    private String phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login2);
        setbarColors();
        lyjApplication = (LYJApplication) getApplication();
        sp = getSharedPreferences("SP",Context.MODE_PRIVATE);
        HttpValue.IP =  (null == SharedPreferencesUtils.readObjFromSp(sp,
                "IP") ? "" : ""
                + SharedPreferencesUtils.readObjFromSp(sp,"IP"));
        init();

    }

    private void init() {
        //setTerminal();
        owlView = (OwlView) findViewById(R.id.longin_owlview);
        tv_forgetpassword = (TextView) findViewById(R.id.tv_forgetpassword);
        tv_forgetpassword.setOnClickListener(this);
        tv_reject = (TextView) findViewById(R.id.tv_reject);
        tv_reject.setOnClickListener(this);
        loginBut = (Button) findViewById(R.id.login_but);
        loginBut.setOnClickListener(this);
        usernameEdit = (EditText) findViewById(R.id.username_edit);

        passwordEdit = (EditText) findViewById(R.id.password_edit);
        passwordEdit.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    owlView.open();

                }else{
                    owlView.close();
                }
            }
        });
        imagebut_setting = (ImageButton) findViewById(R.id.imageButton_setting);
        imagebut_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SettingDialog settingDialog = new SettingDialog(LoginActivity.this);
                settingDialog.show();
            }
        });

        // 设置保存的登陆名
        String et_name = (null == SharedPreferencesUtils.readObjFromSp(sp,
                Const.SP_USERNAME) ? "" : ""
                + SharedPreferencesUtils.readObjFromSp(sp, Const.SP_USERNAME));
        usernameEdit.setText(et_name);
        usernameEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(!passwordEdit.getText().toString().isEmpty()){
                    passwordEdit.setText("");
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.login_but:
                //isShowTerminal();
                //loginBut.setEnabled(false);
                login();
                break;
            case R.id.tv_reject:
                IntentUtils.getIntentUtils().intent(LoginActivity.this,RegisterActivity.class);
                finish();
                break;
            case R.id.tv_forgetpassword:
                Intent intent = new Intent(LoginActivity.this,ModifyPasswordActivity.class);
                intent.putExtra("type","忘记密码");
                startActivity(intent);
                break;
        }
    }
    private void login() {
        if (!NetWorkUtils.getNetConnecState(this)) {
            Toast.makeText(LoginActivity.this, "请检查网络是否连接！", Toast.LENGTH_SHORT).show();
            loginBut.setEnabled(true);
            return;
        }
        // 将保存的设置信息取出
        setConfig();
    }

    /**
     * 登入请求
     */
    private void setConfig() {
        String username = usernameEdit.getText().toString();
        String password = passwordEdit.getText().toString();
        if ("".equals(username) || "".equals(password)) {
            Toast.makeText(LoginActivity.this, "用户名和密码不能为空！", Toast.LENGTH_SHORT).show();
            loginBut.setEnabled(true);
            return;
        }

        UIProgressUtil.showProgress(LoginActivity.this, "登录中......",true);
        new Session().login(username, password,
                LoginActivity.this, mHandler);
    }

    //下载更新
    private void getupdateVersion() {
        try {
            JSONObject params = new JSONObject();
            params.put("","");
            new JsonRPCAsyncTask(LoginActivity.this, mHandler,
                    UPDATE_SELECT_CODE, HttpValue.getHttp() + Const.UPDATE_SELECT, "call",
                    params).execute();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    /**
     * 获取版本
     * @return 当前应用的版本
     */
    public String getVersion(String type) {
        try {
            PackageManager manager = getPackageManager();
            PackageInfo info = manager.getPackageInfo(getPackageName(), 0);
            String version="";
            System.out.println("版本信息："+info.versionName+"///"+info.versionCode);
            if (type.equals("versionName")) {
                version = info.versionName;
            }else if (type.equals("versionCode")){
                version = info.versionCode+"";
            }
            return version;
        } catch (Exception e) {
            e.printStackTrace();
            return "未知版本";
        }

    }
    /**
     *
     * @param version
     *            当前客户端的版本号信息
     * @return 是否需要更新
     */
    private void isNeedUpdate(String version,boolean ismustupdate) {
        long nowVersion = getVerCode(getVersion("versionCode"));
        long serverVersion = getVerCode(version);
        // 进行code匹对
        if (nowVersion < serverVersion&&ismustupdate==true) {// 有重大版本更新，强制更新
            Log.i("UPDATE", "版本不同,需要升级");
            showUpdateDialog();

        } else if (nowVersion == serverVersion||nowVersion > serverVersion) {
            startactivity();
            Log.i("UPDATE", "版本相同,无需升级");

        } else {
            startactivity();
            Log.i("UPDATE", "小版本,无需升级");
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
    //是否要更新的对话框
    private void showUpdateDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("检测到新版本");
        builder.setMessage("您当前版本过低，请下载更新后使用。     "+"文件大小 "+apk_size);
        builder.setPositiveButton("下载", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub
                lyjApplication.setDownload(true);
                Intent intent = new Intent(LoginActivity.this,NotificationUpdateActivity.class);
                startActivity(intent);

            }
        }).setNegativeButton(null, null);
        builder.show();
    }
    //登录成功，跳往主界面
    private void startactivity(){
        Intent intent = new Intent(LoginActivity.this,
                IndexActivity.class);
        startActivity(intent);
        finish();
    }
    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            String result = (String) msg.obj;
            if (result==null){
                ToastUtils.show(LoginActivity.this,"亲，服务器出问题啦，请稍后再试！");
                return;
            }
            if (msg.what == Const.loginCode) {
                UIProgressUtil.cancelProgress();
                System.out.println("login--:"+result);
                if (result != null && !result.contains("error")) {
                    Loginresult loginresult = null;
                    try {
                        loginresult = GsonUtil.jsonToBean(result, Loginresult.class);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    if(loginresult !=null){
                        Loginresult.ResultBean resultBean = loginresult.getResult();
                        uid = resultBean.getRes().getuser_id();
                        if (resultBean.isFlag() == true) {
                            HttpValue.SP_USERID = uid;
                            Log.i("登录成功！", ""+uid);
                            SharedPreferencesUtils.saveObjToSp(sp,
                                    Const.SP_USERNAME,usernameEdit.getText().toString());
                            getupdateVersion();
                           /* Intent intent = new Intent(LoginActivity.this,
                                    IndexActivity.class);
                            startActivity(intent);
                            finish();*/
                        } else {
                            ToastUtils.show(LoginActivity.this,
                                    resultBean.getInfo());
                        }
                    }

                } else {
                    ToastUtils.show(LoginActivity.this,
                            "登陆失败，请检查您的登陆信息！");

                }
                loginBut.setEnabled(true);
                UIProgressUtil.cancelProgress();

            }

            if (msg.what == SessionRequestCode) {
                Log.e("session获取id：", result);
                Payway pay = GsonUtil.jsonToBean(result,
                        Payway.class);

            }
            if (msg.what ==  UPDATE_SELECT_CODE) {
                UIProgressUtil.cancelProgress();
                System.out.println("返回数据为：" + result);
                if (GsonUtil.isError(result)) {
                    return;
                }
                UpdataResult updataresult = GsonUtil.jsonToBean(result, UpdataResult.class);
                if (updataresult.getResult().isFlag()){
                    apk_size = updataresult.getResult().getRes().getSize();
                    isNeedUpdate(updataresult.getResult().getRes().getVersion(),updataresult.getResult().getRes().isImportant());
                    System.out.println(apk_size);
                }else {
                    System.out.println(updataresult.getResult().getInfo());
                    startactivity();
                }

            }

        }
    };
    private void setbar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.maincolor));
            /*ViewGroup viewGroup = (ViewGroup) findViewById(android.R.id.content);
            ViewGroup rootView = (ViewGroup) viewGroup.getChildAt(0);
            rootView.setFitsSystemWindows(false);
            rootView.setClipToPadding(true);

            // 绘制一个和状态栏一样高的矩形
            int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
            int statusBarHeight = getResources().getDimensionPixelSize(resourceId);
            View statusView = new View(this);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, statusBarHeight);
            statusView.setLayoutParams(params);
            Drawable drawable = rootView.getChildAt(0).getBackground();
            if (drawable instanceof ColorDrawable) {
                statusView.setBackgroundColor(((ColorDrawable) drawable).getColor());
            } else {
                statusView.setBackgroundColor(Color.parseColor("#009943"));
            }
            rootView.addView(statusView, 0);*/

        }
    }



}
