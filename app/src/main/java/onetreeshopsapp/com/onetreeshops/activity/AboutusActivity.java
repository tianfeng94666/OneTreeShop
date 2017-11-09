package onetreeshopsapp.com.onetreeshops.activity;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import onetreeshopsapp.com.onetreeshops.R;
import onetreeshopsapp.com.onetreeshops.base.BaseActivity;
import onetreeshopsapp.com.onetreeshops.utils.IntentUtils;

/**
 * Created by ERP on 2016/11/1.
 */
public class AboutusActivity extends BaseActivity implements View.OnClickListener{
    private ImageButton aboutus_back_imgbtn;
    private TextView tv_suggest,versionName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aboutus);
        initview();
    }

    private void initview() {
        aboutus_back_imgbtn = (ImageButton) findViewById(R.id.aboutus_back_imgbtn);
        aboutus_back_imgbtn.setOnClickListener(this);
        tv_suggest = (TextView) findViewById(R.id.tv_suggest);
        tv_suggest.setOnClickListener(this);
        versionName = (TextView) findViewById(R.id.versionName);
        versionName.setText("一棵树商城"+"  "+ "V"+getVersion());

    }
    /**
     * 获取版本名
     * @return 当前应用的版本名
     */
    public String getVersion() {
        try {
            PackageManager manager = this.getPackageManager();
            PackageInfo info = manager.getPackageInfo(this.getPackageName(), 0);
            String version="";
            System.out.println("版本信息："+info.versionName+"///"+info.versionCode);
            version = info.versionName;
            return version;
        } catch (Exception e) {
            e.printStackTrace();
            return "未知版本";
        }

    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.aboutus_back_imgbtn:
                IntentUtils.getIntentUtils().intent(AboutusActivity.this,MySetActivity.class);
                finish();
                break;
            case R.id.tv_suggest:
                IntentUtils.getIntentUtils().intent(AboutusActivity.this,SuggestActivity.class);
                break;
        }
    }
}
