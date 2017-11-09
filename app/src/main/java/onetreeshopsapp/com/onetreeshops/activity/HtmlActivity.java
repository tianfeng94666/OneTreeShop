package onetreeshopsapp.com.onetreeshops.activity;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageButton;

import onetreeshopsapp.com.onetreeshops.R;
import onetreeshopsapp.com.onetreeshops.base.BaseActivity;
import onetreeshopsapp.com.onetreeshops.utils.IntentUtils;

/**
 * Created by ERP on 2016/10/13.
 */
public class HtmlActivity extends BaseActivity implements View.OnClickListener{
    private ImageButton xml_back_imgbtn;
    private WebView wv_about_me;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aboutme);
        initview();
    }

    private void initview() {
        xml_back_imgbtn = (ImageButton) findViewById(R.id.xml_back_imgbtn);
        xml_back_imgbtn.setOnClickListener(this);
        wv_about_me=(WebView) findViewById(R.id.wv_about_me);
        wv_about_me.loadUrl("file:///android_asset/"+"register_protocol.html");
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.xml_back_imgbtn:
                finish();
                break;
        }
    }
}
