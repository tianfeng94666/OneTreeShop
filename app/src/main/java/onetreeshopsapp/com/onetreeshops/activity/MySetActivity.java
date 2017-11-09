package onetreeshopsapp.com.onetreeshops.activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.Properties;

import onetreeshopsapp.com.onetreeshops.R;
import onetreeshopsapp.com.onetreeshops.adapter.MyAddresInfoAdapter;
import onetreeshopsapp.com.onetreeshops.application.LYJApplication;
import onetreeshopsapp.com.onetreeshops.base.BaseActivity;
import onetreeshopsapp.com.onetreeshops.bean.MyAddresRecord;
import onetreeshopsapp.com.onetreeshops.dialog.MyDialog;
import onetreeshopsapp.com.onetreeshops.fragment.MineFragment;
import onetreeshopsapp.com.onetreeshops.http.JsonRPCAsyncTask;
import onetreeshopsapp.com.onetreeshops.update.NotificationUpdateActivity;
import onetreeshopsapp.com.onetreeshops.update.ProgressAcitivty;
import onetreeshopsapp.com.onetreeshops.update.UpdataResult;
import onetreeshopsapp.com.onetreeshops.utils.AppConfig;
import onetreeshopsapp.com.onetreeshops.utils.CacheFileUtil;
import onetreeshopsapp.com.onetreeshops.utils.Const;
import onetreeshopsapp.com.onetreeshops.utils.DataCleanManager;
import onetreeshopsapp.com.onetreeshops.utils.GsonUtil;
import onetreeshopsapp.com.onetreeshops.utils.HttpValue;
import onetreeshopsapp.com.onetreeshops.utils.IntentUtils;
import onetreeshopsapp.com.onetreeshops.utils.MethodsCompat;
import onetreeshopsapp.com.onetreeshops.utils.SharedPreferencesUtils;
import onetreeshopsapp.com.onetreeshops.utils.ToastUtils;
import onetreeshopsapp.com.onetreeshops.utils.UIProgressUtil;
import onetreeshopsapp.com.onetreeshops.utils.UpdateUtil;

/**
 * Created by ERP on 2016/10/9.
 */
public class MySetActivity extends BaseActivity implements View.OnClickListener{
    private ImageButton mineset_back_imgbtn;
    private TextView tv_aboutus,tv_version,tv_clear_data;
    private LinearLayout ll_newversion,ll_clear;
    private Button btn_exit;
    private final int CLEAN_SUC=1001;
    private final int CLEAN_FAIL=1002;
    private LYJApplication lyjApplication;
    private SharedPreferences sp;
    private String apk_size = "未知";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mine_set);
        sp = getSharedPreferences("SP", Context.MODE_PRIVATE);
        lyjApplication = (LYJApplication) getApplication();
        initview();
    }

    private void initview() {
        mineset_back_imgbtn  = (ImageButton) findViewById(R.id.mineset_back_imgbtn);
        mineset_back_imgbtn.setOnClickListener(this);
        ll_newversion = (LinearLayout) findViewById(R.id.ll_newversion);
        ll_newversion.setOnClickListener(this);
        ll_clear = (LinearLayout) findViewById(R.id.ll_clear);
        ll_clear.setOnClickListener(this);
        tv_aboutus = (TextView) findViewById(R.id.tv_aboutus);
        tv_aboutus.setOnClickListener(this);
        btn_exit = (Button) findViewById(R.id.btn_exit);
        btn_exit.setOnClickListener(this);
        tv_clear_data = (TextView) findViewById(R.id.tv_clear_data);
        tv_version = (TextView) findViewById(R.id.tv_version);
        tv_version.setText("当前版本："+"V"+getVersion("versionName"));
        caculateCacheSize();
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.mineset_back_imgbtn:
                //IntentUtils.getIntentUtils().intent(MySetActivity.this, IndexActivity.class);
                this.finish();
                break;
            case R.id.ll_newversion:
                //IntentUtils.getIntentUtils().intent(this,ProgressAcitivty.class);
                // 判断服务器版本号 和客户端的版本号 是否相同
                getupdateVersion();
                break;
            case R.id.ll_clear:
                onClickCleanCache();
                break;
            case R.id.tv_aboutus:
                IntentUtils.getIntentUtils().intent(MySetActivity.this,AboutusActivity.class);
                finish();
                break;
            case R.id.btn_exit:
                IntentUtils.getIntentUtils().intent(MySetActivity.this,LoginActivity.class);
                //IndexActivity.instance.finish();
                this.finish();
                break;
        }
    }
   //清空缓存
    private void onClickCleanCache() {
        getConfirmDialog(MySetActivity.this, "是否清空缓存?", new DialogInterface.OnClickListener
                () {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                clearAppCache();
            }
        }).show();

    }
    public static AlertDialog.Builder getConfirmDialog(Context context, String message, DialogInterface.OnClickListener onClickListener) {
        AlertDialog.Builder builder = getDialog(context);
        builder.setMessage(Html.fromHtml(message));
        builder.setPositiveButton("确定", onClickListener);
        builder.setNegativeButton("取消", null);
        return builder;
    }
    public static AlertDialog.Builder getDialog(Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        return builder;
    }
    //
    /**
     * 计算缓存的大小
     */
    private void caculateCacheSize() {
        long fileSize = 0;
        String cacheSize = "0KB";
        File filesDir = getFilesDir();
        File cacheDir = getCacheDir();
        fileSize += CacheFileUtil.getDirSize(filesDir);
        fileSize += CacheFileUtil.getDirSize(cacheDir);
        // 2.2版本才有将应用缓存转移到sd卡的功能
        /*if (isMethodsCompat(android.os.Build.VERSION_CODES.FROYO)) {
            File externalCacheDir = MethodsCompat
                    .getExternalCacheDir(MySetActivity.this);
            fileSize += CacheFileUtil.getDirSize(externalCacheDir);
            fileSize += CacheFileUtil.getDirSize(new File(
                    getSDCardPath()
                            + File.separator + "KJLibrary/cache"));

        }*/
        if (fileSize > 0)
            cacheSize = CacheFileUtil.formatFileSize(fileSize);
        tv_clear_data.setText(cacheSize);
    }

    public static boolean isMethodsCompat(int VersionCode) {
        int currentVersion = android.os.Build.VERSION.SDK_INT;
        return currentVersion >= VersionCode;
    }
    /**
     * 清除app缓存
     */
    public void myclearaAppCache() {
        DataCleanManager.clearAllCache(MySetActivity.this);
        // 清除数据缓存
        // 2.2版本才有将应用缓存转移到sd卡的功能
        /*if (isMethodsCompat(android.os.Build.VERSION_CODES.FROYO)) {
            DataCleanManager.cleanCustomCache(MethodsCompat
                    .getExternalCacheDir(MySetActivity.this));
        }*/
        // 清除编辑器保存的临时内容
       /* Properties props = getProperties();
        for (Object key : props.keySet()) {
            String _key = key.toString();
            if (_key.startsWith("temp"))
                removeProperty(_key);
        }
        Core.getKJBitmap().cleanCache();*/
    }

    /**
     * 清除保存的缓存
     */
    public Properties getProperties() {
        return AppConfig.getAppConfig(MySetActivity.this).get();
    }
    public void removeProperty(String... key) {
        AppConfig.getAppConfig(MySetActivity.this).remove(key);
    }

    public void clearAppCache() {

        new Thread() {
            @Override
            public void run() {
                Message msg = new Message();
                try {
                    myclearaAppCache();
                    msg.what = CLEAN_SUC;
                } catch (Exception e) {
                    e.printStackTrace();
                    msg.what = CLEAN_FAIL;
                }
                handler.sendMessage(msg);
            }
        }.start();
    }
    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case CLEAN_FAIL:
                    ToastUtils.show(MySetActivity.this,"清除失败");
                    break;
                case CLEAN_SUC:
                    ToastUtils.show(MySetActivity.this,"清除成功");
                    tv_clear_data.setText("0KB");
                    break;
            }
        };
    };

    /**
     * 获取版本
     * @return 当前应用的版本
     */
    public String getVersion(String type) {
        try {
            PackageManager manager = this.getPackageManager();
            PackageInfo info = manager.getPackageInfo(this.getPackageName(), 0);
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
    /**
     *
     * @param version
     *            当前客户端的版本号信息
     * @return 是否需要更新
     */
    private void isNeedUpdate(String version) {
        long nowVersion = getVerCode(getVersion("versionCode"));
        long serverVersion = getVerCode(version);
        // 进行code匹对
        if (nowVersion < serverVersion) {// 可以更新
            Log.i("UPDATE", "版本不同,需要升级");
            showUpdateDialog();

        } else if (nowVersion == serverVersion) {
            ToastUtils.show(MySetActivity.this,"当前已是最新版本！");
            Log.i("UPDATE", "版本相同,无需升级");

        } else if (nowVersion > serverVersion) {
            ToastUtils.show(MySetActivity.this,"当前已是最新版本！");
            Log.i("UPDATE", "版本相同,无需升级");
        }
    }
    //是否要更新的对话框
    private void showUpdateDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("检测到新版本");
        builder.setMessage("是否下载更新?     "+"文件大小 "+apk_size);
        builder.setPositiveButton("下载", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub
                IntentUtils.getIntentUtils().intent(MySetActivity.this,NotificationUpdateActivity.class);
                lyjApplication.setDownload(true);
            }
        }).setNegativeButton("下次再说", null);
        builder.show();
    }
    private void getupdateVersion() {
        UIProgressUtil.showProgress(this,"正在检查更新...");
        try {
            JSONObject params = new JSONObject();
               params.put("","");
            new JsonRPCAsyncTask(MySetActivity.this, mHandler,
                    Const.UPDATE_SELECT_CODE, HttpValue.getHttp() + Const.UPDATE_SELECT, "call",
                    params).execute();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    private Handler mHandler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            String result = (String) msg.obj;
            UIProgressUtil.cancelProgress();
            if (result==null){
                ToastUtils.show(MySetActivity.this,"亲，服务器出问题啦，请稍后再试！");
                return;
            }
            if (msg.what ==  Const.UPDATE_SELECT_CODE) {
                System.out.println("返回数据为：" + result);
                if (GsonUtil.isError(result)) {
                    ToastUtils.show(MySetActivity.this, "获取版本信息异常");
                    return;
                }
                UpdataResult updataresult = GsonUtil.jsonToBean(result, UpdataResult.class);
                if (updataresult.getResult().isFlag()){
                    SharedPreferencesUtils.saveObjToSp(sp,
                            Const.SP_FILENAME,updataresult.getResult().getRes().getFile_name());
                    apk_size = updataresult.getResult().getRes().getSize();
                    isNeedUpdate(updataresult.getResult().getRes().getVersion());
                }else {
                    ToastUtils.show(MySetActivity.this,updataresult.getResult().getInfo());
                }

            }
        }
    };

}
