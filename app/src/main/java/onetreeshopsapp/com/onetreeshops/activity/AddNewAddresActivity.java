package onetreeshopsapp.com.onetreeshops.activity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import org.json.JSONException;
import org.json.JSONObject;

import onetreeshopsapp.com.onetreeshops.R;
import onetreeshopsapp.com.onetreeshops.base.BaseActivity;
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
public class AddNewAddresActivity extends BaseActivity implements View.OnClickListener{
    private ImageButton addaddres_back_imgbtn;
    private TextView myaddress_textview_title;
    private Button btn_add_myaddres_save;
    private EditText et_add_myaddres_name,et_add_myaddres_phone,et_add_myaddres;
    private String type;
    private Intent intent;
    private ToggleButton TogBtn_default;
    private boolean default_addr = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_myaddres);
        initview();
    }

    private void initview() {
        myaddress_textview_title = (TextView) findViewById(R.id.myaddress_textview_title);
        et_add_myaddres_name = (EditText) findViewById(R.id.et_add_myaddres_name);
        et_add_myaddres_phone = (EditText) findViewById(R.id.et_add_myaddres_phone);
        et_add_myaddres = (EditText) findViewById(R.id.et_add_myaddres);
        TogBtn_default = (ToggleButton) findViewById(R.id.TogBtn_default);

        TogBtn_default.setOnCheckedChangeListener(onCheckedChangeListener);
         intent = getIntent();
        type = intent.getStringExtra("type");
        if (type.equals("modify")) {
            myaddress_textview_title.setText("修改地址");
            et_add_myaddres_name.setText(intent.getStringExtra("name"));
            et_add_myaddres_phone.setText(intent.getStringExtra("telephone"));
            et_add_myaddres.setText(intent.getStringExtra("address"));
            if (intent.getBooleanExtra("default_addr",false)){
                TogBtn_default.setChecked(true);
                TogBtn_default.setEnabled(false);
            }else {
                TogBtn_default.setChecked(false);
            }
        }else {
            myaddress_textview_title.setText("新增地址");
        }
        addaddres_back_imgbtn = (ImageButton) findViewById(R.id.addaddres_back_imgbtn);
        addaddres_back_imgbtn.setOnClickListener(this);
        btn_add_myaddres_save = (Button) findViewById(R.id.btn_add_myaddres_save);
        btn_add_myaddres_save.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.addaddres_back_imgbtn:
                IntentUtils.getIntentUtils().intent(AddNewAddresActivity.this, MyAddresActivity.class);
                finish();
                break;
            case R.id.btn_add_myaddres_save:
                if (type.equals("modify")){
                    dosave("PATH");
                }else {
                    dosave("POST");
                }
                break;
        }
    }
    /*手机号码格式验证*/
    public static boolean isMobileNO(String mobiles) {
    /*第一位必定为1，第二位必定为3,4或5或8，其他位置的可以为0-9*/
        String telRegex = "[1][3458]\\d{9}";//"[1]"代表第1位为数字1，"[3458]"代表第二位可以为3、4、5、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
        if (TextUtils.isEmpty(mobiles))
            return false;
        else return mobiles.matches(telRegex);
    }
    ToggleButton.OnCheckedChangeListener onCheckedChangeListener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if(isChecked){
                default_addr = true;
            }else {
                default_addr = false;
            }
        }
    };

    private void dosave(String sig) {
        String name = et_add_myaddres_name.getText().toString().trim();
        String phone = et_add_myaddres_phone.getText().toString().trim();
        String addres = et_add_myaddres.getText().toString().trim();
        if (name.isEmpty()||phone.isEmpty()||addres.isEmpty()){
            ToastUtils.show(AddNewAddresActivity.this,"请填完整信息！");
            return;
        }
        if(!isMobileNO(phone)){
            ToastUtils.show(AddNewAddresActivity.this,"请填写正确的手机号码！");
            return;
        }
        if (!NetWorkUtils.getNetConnecState(this)) {
            Toast.makeText(AddNewAddresActivity.this, "请检查网络是否连接！", Toast.LENGTH_SHORT).show();
            return;
        }
        try {
            int Code = 0x0000;
            JSONObject params = new JSONObject();
             if(sig.equals("POST")){
                Code=  Const.ADDMYADDRES_CODE;
                 params.put("sig", "POST");
                 params.put("name", name);
                 params.put("telephone", phone);
                 params.put("address", addres);
                 params.put("default_addr",default_addr);
                 params.put("user_id",HttpValue.SP_USERID);
            }else if(sig.equals("PATH")){
                Code = Const.EDITMYADDRES_CODE;
                 params.put("user_id",HttpValue.SP_USERID);
                 params.put("addr_id", intent.getIntExtra("addr_id",1));
                 params.put("sig", "PATH");
                 params.put("name", name);
                 params.put("telephone", phone);
                 params.put("address", addres);
                 params.put("default_addr",default_addr);
            }
            new JsonRPCAsyncTask(AddNewAddresActivity.this, mHandler,
                    Code, HttpValue.getHttp() + Const.MYADDRES_URL, "call",
                    params).execute();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    private Handler mHandler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            String result = (String) msg.obj;
            if (msg.what == Const.EDITMYADDRES_CODE) {
                System.out.println("修改返回数据为：" + result);
                if (result==null){
                    ToastUtils.show(AddNewAddresActivity.this,"亲，服务器出问题啦，请稍后再试！");
                    return;
                }
                if (GsonUtil.isError(result)) {
                    ToastUtils.show(AddNewAddresActivity.this, "修改我的地址出错");
                    return;
                }
                MyAddresRecord myAddresRecord = GsonUtil.jsonToBean(result, MyAddresRecord.class);
                if (myAddresRecord.getResult().getFlag()) {
                    ToastUtils.show(AddNewAddresActivity.this,"修改地址成功！");
                    IntentUtils.getIntentUtils().intent(AddNewAddresActivity.this, MyAddresActivity.class);
                    finish();
                }else {
                    ToastUtils.show(AddNewAddresActivity.this,"修改地址失败！");
                }

            }else if(msg.what == Const.ADDMYADDRES_CODE){
                System.out.println("新增返回数据为：" + result);
                if (result==null){
                    ToastUtils.show(AddNewAddresActivity.this,"亲，服务器出问题啦，请稍后再试！");
                    return;
                }
                if (GsonUtil.isError(result)) {
                    ToastUtils.show(AddNewAddresActivity.this, "新增我的地址出错");
                    return;
                }
                MyAddresRecord myAddresRecord = GsonUtil.jsonToBean(result, MyAddresRecord.class);
                if (myAddresRecord.getResult().getFlag()) {
                    ToastUtils.show(AddNewAddresActivity.this,"新增地址成功！");
//                    IntentUtils.getIntentUtils().intent(AddNewAddresActivity.this, MyAddresActivity.class);
                    finish();
                }else {
                    ToastUtils.show(AddNewAddresActivity.this,"新增地址失败！");
                }


            }
        }
    };
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getRepeatCount() == 0) {
            IntentUtils.getIntentUtils().intent(AddNewAddresActivity.this, MyAddresActivity.class);
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
