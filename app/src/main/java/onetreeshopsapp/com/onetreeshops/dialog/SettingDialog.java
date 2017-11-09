package onetreeshopsapp.com.onetreeshops.dialog;

import android.app.Dialog;
import android.content.Context;
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
public class SettingDialog extends Dialog {

    private Button but_confirm,but_cancle;
    private EditText editText_ip;
    private TextView textview_terminal;
    private SharedPreferences sp;
    private Context context;

    public SettingDialog(Context context) {
        super(context, R.style.OrderDialog);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sp = context.getSharedPreferences("SP",Context.MODE_PRIVATE);
        setCancelable(false);
        setContentView(R.layout.dialog_setting);
        init();
        setlisten();
    }

    private void init() {
        but_confirm = (Button) findViewById(R.id.but_confirm);
        but_cancle = (Button) findViewById(R.id.but_cancle);
        editText_ip  = (EditText)findViewById(R.id.EditText_ip);

        textview_terminal = (TextView)findViewById(R.id.Textview_terminl);
        // 设置保存的登陆名
        String ip = (null == SharedPreferencesUtils.readObjFromSp(sp,
                "IP") ? "" : ""
                + SharedPreferencesUtils.readObjFromSp(sp,"IP"));
        editText_ip.setText(ip);
        textview_terminal.setText(HttpValue.terminal);
    }

    private void setlisten() {
        but_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HttpValue.IP = editText_ip.getText().toString();
                SharedPreferencesUtils.saveObjToSp(sp,
                        "IP", HttpValue.IP);
                dismiss();
            }
        });
        but_cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }
}
