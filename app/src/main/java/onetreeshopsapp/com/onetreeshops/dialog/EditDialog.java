package onetreeshopsapp.com.onetreeshops.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import onetreeshopsapp.com.onetreeshops.R;
import onetreeshopsapp.com.onetreeshops.utils.HttpValue;
import onetreeshopsapp.com.onetreeshops.utils.SharedPreferencesUtils;


/**
 * Created by fiona on 2016/8/18.
 */
public class EditDialog extends Dialog {


    private TextView tv_dialog_edit,tv_dialog_delect,tv_dialog_defalut;
    private Context context;
    private  String ACTION_NAME = "";
    private int position=0;
    public EditDialog(Context context,int position) {
        super(context, R.style.OrderDialog);
        this.context = context;
        this.position = position;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setCancelable(true);
        setContentView(R.layout.dialog_setaddress);
        init();
        setlisten();
    }

    private void init() {
        tv_dialog_delect = (TextView) findViewById(R.id.tv_dialog_delect);
        tv_dialog_edit = (TextView) findViewById(R.id.tv_dialog_edit);
        tv_dialog_defalut = (TextView) findViewById(R.id.tv_dialog_defalut);
    }

    private void setlisten() {
        tv_dialog_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ACTION_NAME = "Edit";
                sendbroadcast(ACTION_NAME);
                dismiss();
            }
        });
        tv_dialog_delect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ACTION_NAME = "Delect";
                sendbroadcast(ACTION_NAME);
                dismiss();
            }
        });
        tv_dialog_defalut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ACTION_NAME = "SetDefault";
                sendbroadcast(ACTION_NAME);
                dismiss();
            }
        });

    }
    private void sendbroadcast(String action_name){
        //发送广播,
        Intent intent = new Intent(action_name);
        intent.putExtra("position",position);
        context.sendBroadcast(intent);
    }
}
