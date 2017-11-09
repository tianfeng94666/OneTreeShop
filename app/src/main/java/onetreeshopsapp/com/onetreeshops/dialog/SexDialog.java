package onetreeshopsapp.com.onetreeshops.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import onetreeshopsapp.com.onetreeshops.R;
import onetreeshopsapp.com.onetreeshops.activity.MyAddresActivity;
import onetreeshopsapp.com.onetreeshops.adapter.MyAddresInfoAdapter;
import onetreeshopsapp.com.onetreeshops.bean.MyAddresRecord;
import onetreeshopsapp.com.onetreeshops.http.JsonRPCAsyncTask;
import onetreeshopsapp.com.onetreeshops.utils.Const;
import onetreeshopsapp.com.onetreeshops.utils.GsonUtil;
import onetreeshopsapp.com.onetreeshops.utils.HttpValue;
import onetreeshopsapp.com.onetreeshops.utils.IntentUtils;
import onetreeshopsapp.com.onetreeshops.utils.ToastUtils;


/**
 * Created by fiona on 2016/8/18.
 */
public class SexDialog extends Dialog {


   private LinearLayout ll_sex_male,ll_sex_female;
    private RadioButton rb_sex_male,rb_sex_female;
    private Context context;
    private final String ACTION_NAME = "SET_SEX";
    public SexDialog(Context context) {
        super(context, R.style.OrderDialog);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setCancelable(true);
        setContentView(R.layout.dialog_sex);
        init();
        setlisten();
    }

    private void init() {
       ll_sex_female = (LinearLayout) findViewById(R.id.ll_sex_female);
        ll_sex_male = (LinearLayout) findViewById(R.id.ll_sex_male);
        rb_sex_female = (RadioButton) findViewById(R.id.rb_sex_female);
        rb_sex_male = (RadioButton) findViewById(R.id.rb_sex_male);
    }

    private void setlisten() {
        ll_sex_female.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendbroadcast("female");
                dismiss();
            }
        });
        ll_sex_male.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendbroadcast("male");
                dismiss();
            }
        });

    }
    private void sendbroadcast(String sex){
        //发送广播,
        Intent intent = new Intent(ACTION_NAME);
        intent.putExtra("sex",sex);
        context.sendBroadcast(intent);
    }
}
