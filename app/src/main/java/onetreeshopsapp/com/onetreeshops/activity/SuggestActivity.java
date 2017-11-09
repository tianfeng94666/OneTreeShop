package onetreeshopsapp.com.onetreeshops.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import onetreeshopsapp.com.onetreeshops.R;
import onetreeshopsapp.com.onetreeshops.base.BaseActivity;
import onetreeshopsapp.com.onetreeshops.bean.GetCaptchaResult;
import onetreeshopsapp.com.onetreeshops.http.JsonRPCAsyncTask;
import onetreeshopsapp.com.onetreeshops.utils.Const;
import onetreeshopsapp.com.onetreeshops.utils.GsonUtil;
import onetreeshopsapp.com.onetreeshops.utils.HttpValue;
import onetreeshopsapp.com.onetreeshops.utils.NetWorkUtils;
import onetreeshopsapp.com.onetreeshops.utils.ToastUtils;
import onetreeshopsapp.com.onetreeshops.utils.UIProgressUtil;

/**
 * Created by ERP on 2016/11/3.
 */
public class SuggestActivity extends BaseActivity implements View.OnClickListener{
    private EditText suggest_tv;
    private ImageButton suggest_back_imgbtn;
    private Button suggest_btn;

    private RadioButton suggest_tv1, suggest_tv2, suggest_tv3, suggest_tv4;

    private String Message;

    private SharedPreferences sp;

    private static String title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suggest);
        findViews();
    }

    private void findViews() {
        suggest_back_imgbtn = (ImageButton) findViewById(R.id.suggest_back_imgbtn);
        suggest_back_imgbtn.setOnClickListener(this);
        suggest_tv = (EditText) findViewById(R.id.suggest_et);
        suggest_btn = (Button) findViewById(R.id.btn_suggest);
        suggest_tv1 = (RadioButton) findViewById(R.id.suggest_tv1);
        suggest_tv2 = (RadioButton) findViewById(R.id.suggest_tv2);
        suggest_tv3 = (RadioButton) findViewById(R.id.suggest_tv3);
        suggest_tv4 = (RadioButton) findViewById(R.id.suggest_tv4);
        suggest_btn.setOnClickListener(this);
        suggest_tv1.setOnClickListener(this);
        suggest_tv2.setOnClickListener(this);
        suggest_tv3.setOnClickListener(this);
        suggest_tv4.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.suggest_back_imgbtn:
                finish();
                break;
            case R.id.btn_suggest:
                    Message = suggest_tv.getText().toString().trim();
                    if(!(suggest_tv1.isChecked()||suggest_tv2.isChecked()||suggest_tv3.isChecked()||suggest_tv4.isChecked())){
                        ToastUtils.show(SuggestActivity.this,"请选择一个类型");
                        return;
                    }
                    if (TextUtils.isEmpty(Message)){
                        ToastUtils.show(SuggestActivity.this,"请输入问题描述");
                        return;
                    }
                if (!NetWorkUtils.getNetConnecState(this)) {
                    Toast.makeText(SuggestActivity.this, "请检查网络是否连接！", Toast.LENGTH_SHORT).show();
                    return;
                }
                UIProgressUtil.showProgress(SuggestActivity.this,"数据保存中...",false);
                postsuggestdata();
                break;

            case R.id.suggest_tv1:
                suggest_tv2.setChecked(false);
                suggest_tv3.setChecked(false);
                suggest_tv4.setChecked(false);
                suggest_tv1.setTextColor(getResources().getColor(R.color.white));
                suggest_tv2.setTextColor(getResources().getColor(R.color.producttextcolor));
                suggest_tv3.setTextColor(getResources().getColor(R.color.producttextcolor));
                suggest_tv4.setTextColor(getResources().getColor(R.color.producttextcolor));
                title = "功能建议";
                break;

            case R.id.suggest_tv2:
                suggest_tv1.setChecked(false);
                suggest_tv3.setChecked(false);
                suggest_tv4.setChecked(false);
                title = "购买遇到问题";
                suggest_tv2.setTextColor(getResources().getColor(R.color.white));
                suggest_tv1.setTextColor(getResources().getColor(R.color.producttextcolor));
                suggest_tv3.setTextColor(getResources().getColor(R.color.producttextcolor));
                suggest_tv4.setTextColor(getResources().getColor(R.color.producttextcolor));

                break;

            case R.id.suggest_tv3:
                suggest_tv1.setChecked(false);
                suggest_tv2.setChecked(false);
                suggest_tv4.setChecked(false);
                title = "性能问题";
                suggest_tv3.setTextColor(getResources().getColor(R.color.white));
                suggest_tv2.setTextColor(getResources().getColor(R.color.producttextcolor));
                suggest_tv1.setTextColor(getResources().getColor(R.color.producttextcolor));
                suggest_tv4.setTextColor(getResources().getColor(R.color.producttextcolor));
                break;

            case R.id.suggest_tv4:
                suggest_tv1.setChecked(false);
                suggest_tv2.setChecked(false);
                suggest_tv3.setChecked(false);
                title = "其他问题";
                suggest_tv4.setTextColor(getResources().getColor(R.color.white));
                suggest_tv1.setTextColor(getResources().getColor(R.color.producttextcolor));
                suggest_tv2.setTextColor(getResources().getColor(R.color.producttextcolor));
                suggest_tv3.setTextColor(getResources().getColor(R.color.producttextcolor));
                break;

        }
    }
    private void postsuggestdata() {
        try {
            JSONObject params = new JSONObject();
            params.put("title", title);
            params.put("content", Message);
            new JsonRPCAsyncTask(SuggestActivity.this, mHandler,
                    Const.SUGGEST_CODE, HttpValue.getHttp() + Const.SUGGEST_URL, "call",
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
                ToastUtils.show(SuggestActivity.this,"亲，服务器出问题啦，请稍后再试！");
                return;
            }
            if (msg.what== Const.SUGGEST_CODE) {
                System.out.println("意见反馈返回结果 " + result);
                if (GsonUtil.isError(result)) {
                    ToastUtils.show(SuggestActivity.this, "提交意见反馈出错");
                    return;
                }
                GetCaptchaResult getCaptchaResult = GsonUtil.jsonToBean(result, GetCaptchaResult.class);
                if (getCaptchaResult!=null&&getCaptchaResult.getResult().getFlag()){
                    ToastUtils.show(SuggestActivity.this,"反馈提交成功！");
                    finish();

                }else {
                    ToastUtils.show(SuggestActivity.this,"反馈失败！");
                }
            }
        }
    };
}
