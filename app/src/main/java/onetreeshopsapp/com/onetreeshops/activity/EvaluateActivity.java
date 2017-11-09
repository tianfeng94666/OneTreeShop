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
import android.widget.RatingBar;
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
public class EvaluateActivity extends BaseActivity implements View.OnClickListener{
    private EditText pingjia_content;
    private ImageButton pingjia_back_imgbtn;
    private Button btn_pingjia;
    private RatingBar shop_rb_pingfen,product_rb_pingfen;
    private RadioButton pingjia_rb_good, pingjia_rb_mid, pingjia_rb_bad;

    private String Message;

    private SharedPreferences sp;

    private static String grade = null;
    private String store_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pingjia);
        findViews();
        getData();
    }

    private void getData() {
        store_id = getIntent().getStringExtra("store_id");
    }

    private void findViews() {
        pingjia_back_imgbtn = (ImageButton) findViewById(R.id.pingjia_back_imgbtn);
        pingjia_back_imgbtn.setOnClickListener(this);
        pingjia_content = (EditText) findViewById(R.id.pingjia_content);
        btn_pingjia = (Button) findViewById(R.id.btn_pingjia);
        pingjia_rb_good = (RadioButton) findViewById(R.id.pingjia_rb_good);
        pingjia_rb_mid = (RadioButton) findViewById(R.id.pingjia_rb_mid);
        pingjia_rb_bad = (RadioButton) findViewById(R.id.pingjia_rb_bad);
        btn_pingjia.setOnClickListener(this);
        pingjia_rb_good.setOnClickListener(this);
        pingjia_rb_mid.setOnClickListener(this);
        pingjia_rb_bad.setOnClickListener(this);
        shop_rb_pingfen = (RatingBar) findViewById(R.id.shop_rb_pingfen);
        shop_rb_pingfen.setMax(5);
        shop_rb_pingfen.setStepSize(1.0f);
        product_rb_pingfen = (RatingBar) findViewById(R.id.product_rb_pingfen);
        product_rb_pingfen.setStepSize(1.0f);
        shop_rb_pingfen.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                System.out.println(rating+""+fromUser);
            }
        });
        product_rb_pingfen.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                System.out.println(rating+""+fromUser);
            }
        });



    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.pingjia_back_imgbtn:
                finish();
                break;
            case R.id.btn_pingjia:
                    Message = pingjia_content.getText().toString().trim();
                    if(!(pingjia_rb_good.isChecked()||pingjia_rb_mid.isChecked()||pingjia_rb_bad.isChecked())){
                        ToastUtils.show(EvaluateActivity.this,"请选择一个类型");
                        return;
                    }
                    if (TextUtils.isEmpty(Message)){
                        ToastUtils.show(EvaluateActivity.this,"请输入问题描述");
                        return;
                    }
                if (shop_rb_pingfen.getRating()==0||product_rb_pingfen.getRating()==0){
                    Toast.makeText(EvaluateActivity.this, "请对商家服务，商品进行评分！", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!NetWorkUtils.getNetConnecState(this)) {
                    Toast.makeText(EvaluateActivity.this, "请检查网络是否连接！", Toast.LENGTH_SHORT).show();
                    return;
                }
                UIProgressUtil.showProgress(EvaluateActivity.this,"发表评价中...",false);
                postdata();
                break;

            case R.id.pingjia_rb_good:
                pingjia_rb_mid.setChecked(false);
                pingjia_rb_bad.setChecked(false);
                pingjia_rb_good.setTextColor(getResources().getColor(R.color.white));
                pingjia_rb_mid.setTextColor(getResources().getColor(R.color.producttextcolor));
                pingjia_rb_bad.setTextColor(getResources().getColor(R.color.producttextcolor));
                grade = "good";
                break;

            case R.id.pingjia_rb_mid:
                pingjia_rb_good.setChecked(false);
                pingjia_rb_bad.setChecked(false);
                grade = "common";
                pingjia_rb_mid.setTextColor(getResources().getColor(R.color.white));
                pingjia_rb_good.setTextColor(getResources().getColor(R.color.producttextcolor));
                pingjia_rb_bad.setTextColor(getResources().getColor(R.color.producttextcolor));

                break;

            case R.id.pingjia_rb_bad:
                pingjia_rb_good.setChecked(false);
                pingjia_rb_mid.setChecked(false);
                grade = "bad";
                pingjia_rb_bad.setTextColor(getResources().getColor(R.color.white));
                pingjia_rb_mid.setTextColor(getResources().getColor(R.color.producttextcolor));
                pingjia_rb_good.setTextColor(getResources().getColor(R.color.producttextcolor));
                break;


        }
    }
    private void postdata() {
        try {
            JSONObject params = new JSONObject();
            params.put("store_id", store_id);
            params.put("content", Message);
            params.put("grade",grade);
            params.put("pro_score",product_rb_pingfen.getRating());
            params.put("service_score",shop_rb_pingfen.getRating());
            new JsonRPCAsyncTask(EvaluateActivity.this, mHandler,
                    Const.EVALUATE_CODE, HttpValue.getHttp() + Const.EVALUATE_URL, "call",
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
                ToastUtils.show(EvaluateActivity.this,"亲，服务器出问题啦，请稍后再试！");
                return;
            }
            if (msg.what== Const.EVALUATE_CODE) {
                System.out.println("评价返回结果 " + result);
                if (GsonUtil.isError(result)) {
                    ToastUtils.show(EvaluateActivity.this, "评价出错！");
                    return;
                }
                GetCaptchaResult getCaptchaResult = GsonUtil.jsonToBean(result, GetCaptchaResult.class);
                if (getCaptchaResult!=null&&getCaptchaResult.getResult().getFlag()){
                    ToastUtils.show(EvaluateActivity.this,"评价成功！");
                    finish();

                }else {
                    ToastUtils.show(EvaluateActivity.this,"评价失败！");
                }
            }
        }
    };
}
