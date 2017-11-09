package onetreeshopsapp.com.onetreeshops.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import onetreeshopsapp.com.onetreeshops.R;
import onetreeshopsapp.com.onetreeshops.adapter.MyAddresInfoAdapter;
import onetreeshopsapp.com.onetreeshops.base.BaseActivity;
import onetreeshopsapp.com.onetreeshops.bean.MyAddresRecord;
import onetreeshopsapp.com.onetreeshops.dialog.EditDialog;
import onetreeshopsapp.com.onetreeshops.http.JsonRPCAsyncTask;
import onetreeshopsapp.com.onetreeshops.utils.Const;
import onetreeshopsapp.com.onetreeshops.utils.GsonUtil;
import onetreeshopsapp.com.onetreeshops.utils.HttpValue;
import onetreeshopsapp.com.onetreeshops.utils.IntentUtils;
import onetreeshopsapp.com.onetreeshops.utils.ToastUtils;
import onetreeshopsapp.com.onetreeshops.utils.UIProgressUtil;

/**
 * Created by fiona on 2016/10/19.
 */
public class SelectAdressActivity extends BaseActivity {

    private ImageButton mineaddres_back_imgbtn;
    private ListView lv_my_addres;
    private TextView tv_add_myaddres;
    private List<MyAddresRecord.Result.Res> resBeans1=null;
    private final String EDIT_ACTION = "Edit";
    private final String DELECT_ACTION = "Delect";
    private final String SETDEFAULT_ACTION = "SetDefault";
    private MyAddresInfoAdapter myAddresInfoAdapter;
    private int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_address);
        initview();
    }

    @Override
    protected void onResume() {
        super.onResume();
        UIProgressUtil.showProgress(SelectAdressActivity.this,"获取地址中...",true);
        getmyaddres("GET");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mBroadcastReceiver);
    }

    private void initview() {
        mineaddres_back_imgbtn = (ImageButton) findViewById(R.id.mineaddres_back_imgbtn);
        mineaddres_back_imgbtn.setOnClickListener(this);
        lv_my_addres = (ListView) findViewById(R.id.lv_my_addres);
        lv_my_addres.setOnItemClickListener(onItemClickListener);
        tv_add_myaddres = (TextView) findViewById(R.id.tv_add_myaddres);
        tv_add_myaddres.setOnClickListener(this);
        lv_my_addres.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent();
                intent.putExtra("address",resBeans1.get(position));
                setResult(1,intent);
                finish();
            }
        });
        registerBoradcastReceiver();
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.mineaddres_back_imgbtn:
                returnDefaultAdress();
                break;
            case R.id.tv_add_myaddres:
                Intent intent = new Intent(SelectAdressActivity.this,AddNewAddresActivity.class);
                intent.putExtra("type","add");
                startActivity(intent);
                break;
        }
    }

    private void returnDefaultAdress() {
        int position =0;
        if(resBeans1 !=null){
            for(int i = 0 ;i<resBeans1.size();i++){
                if(resBeans1.get(i).isDefault_addr()){
                    position = i;
                    break;
                }
            }
            Intent intent = new Intent();
            intent.putExtra("address",resBeans1.get(position));
            setResult(1,intent);
        }
        this.finish();
    }

    AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            editaddress(position);
        }
    };
    private void editaddress(int position){
        Intent intent = new Intent(SelectAdressActivity.this,AddNewAddresActivity.class);
        intent.putExtra("type","modify");
        intent.putExtra("name",resBeans1.get(position).getName());
        intent.putExtra("telephone",resBeans1.get(position).getTelephone());
        intent.putExtra("address",resBeans1.get(position).getAddress());
        intent.putExtra("addr_id",resBeans1.get(position).getAddr_id());
        intent.putExtra("default_addr",resBeans1.get(position).isDefault_addr());
        startActivity(intent);
        finish();
    }

    //查询，删除
    private void getmyaddres(String sig) {
        try {
            int Code = 0x0000;
            JSONObject params = new JSONObject();
            if(sig.equals("GET")) {
                Code = Const.GETMYADDRES_CODE;
                params.put("sig", "GET");
                params.put("user_id", HttpValue.SP_USERID);
            }else if(sig.equals("DELETE")){
                Code = Const.DELECTMYADDRES_CODE;
                params.put("sig", "DELETE");
                params.put("addr_id", resBeans1.get(position).getAddr_id());
            }
            new JsonRPCAsyncTask(SelectAdressActivity.this, mHandler,
                    Code, HttpValue.getHttp() + Const.MYADDRES_URL, "call",
                    params).execute();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    //设置默认地址
    private void setdefaultaddres() {
        try {
            JSONObject params = new JSONObject();
            params.put("user_id",HttpValue.SP_USERID);
            params.put("addr_id", resBeans1.get(position).getAddr_id());
            params.put("sig", "PATH");
            params.put("default_addr",true);
            new JsonRPCAsyncTask(SelectAdressActivity.this, mHandler,
                    Const.EDITMYADDRES_CODE, HttpValue.getHttp() + Const.MYADDRES_URL, "call",
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
                ToastUtils.show(SelectAdressActivity.this,"亲，服务器出问题啦，请稍后再试！");
                return;
            }
            if (msg.what == Const.GETMYADDRES_CODE) {
                System.out.println("地址记录返回数据为：" + result);
                if (GsonUtil.isError(result)) {
                    ToastUtils.show(SelectAdressActivity.this, "查询我的地址出错");
                    return;
                }
                MyAddresRecord myAddresRecord = GsonUtil.jsonToBean(result, MyAddresRecord.class);
                if (myAddresRecord.getResult().getFlag()) {
                    if (myAddresRecord.getResult().getRes().size() > 0) {
                        resBeans1 = myAddresRecord.getResult().getRes();
                        myAddresInfoAdapter = new MyAddresInfoAdapter(SelectAdressActivity.this,resBeans1);
                        lv_my_addres.setAdapter(myAddresInfoAdapter);
                    } else {
                        ToastUtils.show(SelectAdressActivity.this,"暂无收货地址");
                    }
                }else {
                    ToastUtils.show(SelectAdressActivity.this,"暂无收货地址");
                }

            }else if(msg.what == Const.DELECTMYADDRES_CODE){
                System.out.println("删除返回数据为：" + result);
                if (GsonUtil.isError(result)) {
                    ToastUtils.show(SelectAdressActivity.this, "删除我的地址出错");
                    return;
                }
                MyAddresRecord myAddresRecord = GsonUtil.jsonToBean(result, MyAddresRecord.class);
                if (myAddresRecord.getResult().getFlag()) {
                    ToastUtils.show(SelectAdressActivity.this,"删除地址成功！");
                    resBeans1.remove(position);
                    myAddresInfoAdapter = new MyAddresInfoAdapter(SelectAdressActivity.this,resBeans1);
                    lv_my_addres.setAdapter(myAddresInfoAdapter);

                }else {
                    ToastUtils.show(SelectAdressActivity.this,"删除地址失败！");
                }
            }else if (msg.what == Const.EDITMYADDRES_CODE) {
                System.out.println("设置默认地址返回数据为：" + result);
                if (GsonUtil.isError(result)) {
                    ToastUtils.show(SelectAdressActivity.this, "设置默认地址出错");
                    return;
                }
                MyAddresRecord myAddresRecord = GsonUtil.jsonToBean(result, MyAddresRecord.class);
                if (myAddresRecord.getResult().getFlag()) {
                    getmyaddres("GET");
                    ToastUtils.show(SelectAdressActivity.this,"设置默认地址成功！");
                }else {
                    ToastUtils.show(SelectAdressActivity.this,"设置默认地址失败！");
                }

            }
        }
    };
    //广播接收
    private BroadcastReceiver  mBroadcastReceiver = new BroadcastReceiver(){
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            position = intent.getIntExtra("position",1);
            System.out.println(">>"+position);
            if(action.equals("Edit")){
                editaddress(position);
            }else if (action.equals("Delect")){
                getmyaddres("DELETE");
            }else if (action.equals("SetDefault")){
                setdefaultaddres();
            }
        }
    };

    public void registerBoradcastReceiver(){
        IntentFilter myIntentFilter = new IntentFilter();
        myIntentFilter.addAction(EDIT_ACTION);
        myIntentFilter.addAction(DELECT_ACTION);
        myIntentFilter.addAction(SETDEFAULT_ACTION);
        registerReceiver(mBroadcastReceiver, myIntentFilter);
    }

}
