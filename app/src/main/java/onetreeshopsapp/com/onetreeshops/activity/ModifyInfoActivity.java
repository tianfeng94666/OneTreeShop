package onetreeshopsapp.com.onetreeshops.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import onetreeshopsapp.com.onetreeshops.R;
import onetreeshopsapp.com.onetreeshops.base.BaseActivity;
import onetreeshopsapp.com.onetreeshops.bean.ModifyResult;
import onetreeshopsapp.com.onetreeshops.bean.MyAddresRecord;
import onetreeshopsapp.com.onetreeshops.fragment.MineFragment;
import onetreeshopsapp.com.onetreeshops.http.JsonRPCAsyncTask;
import onetreeshopsapp.com.onetreeshops.utils.Const;
import onetreeshopsapp.com.onetreeshops.utils.GsonUtil;
import onetreeshopsapp.com.onetreeshops.utils.HttpValue;
import onetreeshopsapp.com.onetreeshops.utils.IntentUtils;
import onetreeshopsapp.com.onetreeshops.utils.NetWorkUtils;
import onetreeshopsapp.com.onetreeshops.utils.ToastUtils;

/**
 * Created by ERP on 2016/10/9.
 */
public class ModifyInfoActivity extends BaseActivity implements View.OnClickListener{
    private ImageButton modify_back_imgbtn;
    private TextView modify_textview_title;
    private EditText modify_editext;
    private Button btn_modify_save;
    private String type;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify);
        initview();
    }

    private void initview() {
        modify_back_imgbtn  = (ImageButton) findViewById(R.id.modify_back_imgbtn);
        modify_back_imgbtn.setOnClickListener(this);
        modify_textview_title = (TextView) findViewById(R.id.modify_textview_title);
        btn_modify_save = (Button) findViewById(R.id.btn_modify_save);
        btn_modify_save.setOnClickListener(this);
        modify_editext = (EditText) findViewById(R.id.modify_editext);
        Intent intent = getIntent();
        type = intent.getStringExtra("type");
        if (type.equals("modifyname")) {
            modify_textview_title.setText("修改名称");
            modify_editext.setText(intent.getStringExtra("data"));
        }else {
            modify_textview_title.setText("修改职业");
            modify_editext.setText(intent.getStringExtra("data"));
        }
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.modify_back_imgbtn:
                finish();
                break;
            case R.id.btn_modify_save:
                if (!NetWorkUtils.getNetConnecState(this)) {
                    Toast.makeText(ModifyInfoActivity.this, "请检查网络是否连接！", Toast.LENGTH_SHORT).show();
                    return;
                }
                dosave();
                break;
        }
    }

    private void dosave() {
        try {
            JSONObject params = new JSONObject();
            if (type.equals("modifyname")) {
                params.put("user_id",HttpValue.SP_USERID);
                params.put("name", modify_editext.getText().toString().trim());
            }else {
                params.put("user_id",HttpValue.SP_USERID);
                params.put("job", modify_editext.getText().toString().trim());
            }
            new JsonRPCAsyncTask(ModifyInfoActivity.this, mHandler,
                    Const.USER_INFO_MODIFY_CODE, HttpValue.getHttp() + Const.USER_INFO_MODIFY, "call",
                    params).execute();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    private Handler mHandler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            String result = (String) msg.obj;
            if (result==null){
                ToastUtils.show(ModifyInfoActivity.this,"亲，服务器出问题啦，请稍后再试！");
                return;
            }
            if (msg.what == Const.USER_INFO_MODIFY_CODE) {
                System.out.println("修改返回数据为：" + result);
                if (GsonUtil.isError(result)) {
                    ToastUtils.show(ModifyInfoActivity.this, "修改出错");
                    return;
                }
               ModifyResult modifyResult = GsonUtil.jsonToBean(result, ModifyResult.class);
                if (modifyResult.getResult().getFlag()) {
                    ToastUtils.show(ModifyInfoActivity.this,"修改成功！");
                    finish();
                }else {
                    ToastUtils.show(ModifyInfoActivity.this,"修改失败！");
                }

            }
        }
    };
}
