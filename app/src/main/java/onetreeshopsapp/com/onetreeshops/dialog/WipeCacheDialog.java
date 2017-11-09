package onetreeshopsapp.com.onetreeshops.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import onetreeshopsapp.com.onetreeshops.R;


/**
 * Created by fiona on 2016/8/18.
 */
public class WipeCacheDialog extends Dialog {
    private TextView btn_no,btn_yes;
    private Context context;
    private final String ACTION_NAME = "SET_SEX";
    public WipeCacheDialog(Context context) {
        super(context, R.style.OrderDialog);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setCancelable(true);
        setContentView(R.layout.dialog_clear);
        init();
        setlisten();
    }

    private void init() {
        btn_yes = (TextView) findViewById(R.id.btn_yes);
        btn_no = (TextView) findViewById(R.id.btn_no);
    }

    private void setlisten() {
        btn_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendbroadcast("female");
                dismiss();
            }
        });
        btn_yes.setOnClickListener(new View.OnClickListener() {
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
