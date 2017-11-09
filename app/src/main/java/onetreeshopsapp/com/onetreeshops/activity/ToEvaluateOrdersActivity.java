package onetreeshopsapp.com.onetreeshops.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import onetreeshopsapp.com.onetreeshops.R;
import onetreeshopsapp.com.onetreeshops.adapter.CompleteOrdersInfoAdapter;
import onetreeshopsapp.com.onetreeshops.base.BaseActivity;
import onetreeshopsapp.com.onetreeshops.bean.DelectOrderResult;
import onetreeshopsapp.com.onetreeshops.bean.GetOrdersResult;
import onetreeshopsapp.com.onetreeshops.http.JsonRPCAsyncTask;
import onetreeshopsapp.com.onetreeshops.utils.Const;
import onetreeshopsapp.com.onetreeshops.utils.GsonUtil;
import onetreeshopsapp.com.onetreeshops.utils.HttpValue;
import onetreeshopsapp.com.onetreeshops.utils.IntentUtils;
import onetreeshopsapp.com.onetreeshops.utils.NetWorkUtils;
import onetreeshopsapp.com.onetreeshops.utils.ToastUtils;
import onetreeshopsapp.com.onetreeshops.utils.UIProgressUtil;
import onetreeshopsapp.com.onetreeshops.utils.view.XListView;

/**
 * Created by ERP on 2016/10/20.
 */
public class ToEvaluateOrdersActivity extends BaseActivity implements View.OnClickListener,CompleteOrdersInfoAdapter.ListItemClickHelp {
    private ImageButton evluate_orders_back_imgbtn;
    private XListView lv_toevaluate_orders;
    private ImageView iv_toevaluate_orders_nodata;
    private TextView tv_toevaluate_orders_nodata;
    private SharedPreferences sp;
    private List<GetOrdersResult.ResultBean.ResBean> resBeans1 = null;
    private List<GetOrdersResult.ResultBean.ResBean> resBeans2 = null;
    private CompleteOrdersInfoAdapter ordersInfoAdapter;
    private int page = 1;
    private boolean getmore_flag = true;
    private int tag_position;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_toevaluate_orders);
        sp = getSharedPreferences("SP", Context.MODE_PRIVATE);
        initview();
    }

    @Override
    protected void onResume() {
        super.onResume();
        resBeans1 = null;
        resBeans2 = null;
        getorders();
    }

    private void initview() {
        evluate_orders_back_imgbtn = (ImageButton) findViewById(R.id.evluate_orders_back_imgbtn);
        evluate_orders_back_imgbtn.setOnClickListener(this);
        iv_toevaluate_orders_nodata = (ImageView) findViewById(R.id.iv_toevaluate_orders_nodata);
        tv_toevaluate_orders_nodata = (TextView) findViewById(R.id.tv_toevaluate_orders_nodata);
        lv_toevaluate_orders = (XListView) findViewById(R.id.lv_toevaluate_orders);
        lv_toevaluate_orders.setPullLoadEnable(true);
        lv_toevaluate_orders.setPullRefreshEnable(true);
        lv_toevaluate_orders.setXListViewListener(new XListView.IXListViewListener() {
            @Override
            public void onRefresh() {
                page = 1;
                getmore_flag = false;
                resBeans1=null;
                getorders();

            }
            @Override
            public void onLoadMore() {
                getmore_flag = true;
                page++;
                getorders();

            }
        });
    }
    private void stoponLoad(){
        lv_toevaluate_orders.stopLoadMore();
        lv_toevaluate_orders.stopRefresh(true);
    }


    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.evluate_orders_back_imgbtn:
                //IntentUtils.getIntentUtils().intent(CompleteOrdersActivity.this,IndexActivity.class);
                finish();
                break;

        }
    }
    //item中按钮点击事件
    @Override
    public void onClickitemButton(View item, View widget, int position, int which) {
        switch (which){
            case R.id.tv_orders_delect:
                //删除订单
                tag_position = position;
                System.out.println("删除"+position);
                delectdialog(resBeans1.get(position).getPos_reference());
                break;
            case R.id.tv_orders_toevaluate:
                Intent intent = new Intent(this,EvaluateActivity.class);
                intent.putExtra("store_id",resBeans1.get(position).getStore_id()+"");
                startActivity(intent);
                break;
        }

    }
    //删除订单提示对话框
    private void delectdialog(final String ordernumber) {
        getConfirmDialog(ToEvaluateOrdersActivity.this, "确认删除订单?", new DialogInterface.OnClickListener
                () {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
               delectorders(ordernumber);
            }
        }).show();
    }
    public static AlertDialog.Builder getConfirmDialog(Context context, String message, DialogInterface.OnClickListener onClickListener) {
        AlertDialog.Builder builder = getDialog(context);
        builder.setMessage(Html.fromHtml(message));
        builder.setPositiveButton("确定", onClickListener);
        builder.setNegativeButton("取消", null);
        return builder;
    }
    public static AlertDialog.Builder getDialog(Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        return builder;
    }

    //查询订单
    public void getorders() {
        if (!NetWorkUtils.getNetConnecState(this)) {
            Toast.makeText(ToEvaluateOrdersActivity.this, "请检查网络是否连接！", Toast.LENGTH_SHORT).show();
            return;
        }
        UIProgressUtil.showProgress(ToEvaluateOrdersActivity.this,"订单查询中...",true);
        try {
            JSONObject params = new JSONObject();
            params.put("uesr_id",HttpValue.SP_USERID);
            params.put("pay_state", "待付款");
            params.put("page", page);
            new JsonRPCAsyncTask(ToEvaluateOrdersActivity.this, mHandler,
                    Const.SEARCH_ALLORDERS_CODE, HttpValue.getHttp() + Const.SEARCH_ALLORDERS, "call",
                    params).execute();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    //删除订单
    public void delectorders(String ordernumber) {
        if (!NetWorkUtils.getNetConnecState(this)) {
            Toast.makeText(ToEvaluateOrdersActivity.this, "请检查网络是否连接！", Toast.LENGTH_SHORT).show();
            return;
        }
        UIProgressUtil.showProgress(ToEvaluateOrdersActivity.this,"订单删除中...",true);
        try {
            JSONObject params = new JSONObject();
            params.put("uesr_id",HttpValue.SP_USERID);
            params.put("pos_reference", ordernumber);
            new JsonRPCAsyncTask(ToEvaluateOrdersActivity.this, mHandler,
                    Const.DELECT_ORDERS_CODE, HttpValue.getHttp() + Const.DELECT_ORDERS, "call",
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
                ToastUtils.show(ToEvaluateOrdersActivity.this,"亲，服务器出问题啦，请稍后再试！");
                return;
            }
            if (msg.what == Const.SEARCH_ALLORDERS_CODE) {//查询全部订单信息
                stoponLoad();
                if(result!=null) {
                    System.out.println("查询待评价订单返回数据为：" + result);
                    if (GsonUtil.isError(result)) {
                        ToastUtils.show(ToEvaluateOrdersActivity.this, "查询订单出错");
                        return;
                    }
                    GetOrdersResult getOrdersResult = GsonUtil.jsonToBean(result, GetOrdersResult.class);
                    if (getOrdersResult != null && getOrdersResult.getResult() != null) {
                        if (getOrdersResult.getResult().isFlag()) {
                            lv_toevaluate_orders.setVisibility(View.VISIBLE);
                            lv_toevaluate_orders.setPullLoadEnable(true);
                            iv_toevaluate_orders_nodata.setVisibility(View.GONE);
                            tv_toevaluate_orders_nodata.setVisibility(View.GONE);
                            if (resBeans1 == null) {
                                resBeans1 = getOrdersResult.getResult().getRes();
                            } else {
                                resBeans2 = getOrdersResult.getResult().getRes();
                                resBeans1.addAll(resBeans2);
                                resBeans2 = null;
                            }
                            if (ordersInfoAdapter == null) {
                                ordersInfoAdapter = new CompleteOrdersInfoAdapter(ToEvaluateOrdersActivity.this, ToEvaluateOrdersActivity.this);
                                ordersInfoAdapter.SetList(resBeans1);
                                lv_toevaluate_orders.setAdapter(ordersInfoAdapter);
                                System.out.println(">>>>xin" + resBeans1.size() + ">>>" + getOrdersResult.getResult().getRes().size());
                            } else {
                                ordersInfoAdapter.SetList(resBeans1);
                                ordersInfoAdapter.notifyDataSetChanged();
                                System.out.println(">>>>>加载1");
                            }

                        } else {
                            if (getmore_flag){
                                page--;
                            }
                            System.out.println(">>>失败信息" + getOrdersResult.getResult().getInfo());
                            if (resBeans1 != null && resBeans2 == null) {
                                ToastUtils.show(ToEvaluateOrdersActivity.this, "亲，已经到底了...");
                                lv_toevaluate_orders.setPullLoadEnable(false);
                            } else {
                                lv_toevaluate_orders.setVisibility(View.GONE);
                                iv_toevaluate_orders_nodata.setVisibility(View.VISIBLE);
                                tv_toevaluate_orders_nodata.setVisibility(View.VISIBLE);

                            }
                        }
                    } else {
                        ToastUtils.show(ToEvaluateOrdersActivity.this, "查询订单失败！");
                    }
                }

            }else if (msg.what == Const.DELECT_ORDERS_CODE){//删除订单
                System.out.println("删除订单返回数据为：" + result);
                if (GsonUtil.isError(result)) {
                    ToastUtils.show(ToEvaluateOrdersActivity.this, "删除订单出错");
                    return;
                }
                DelectOrderResult delectOrderResult = GsonUtil.jsonToBean(result,DelectOrderResult.class);
                if (delectOrderResult.getResult().isFlag()){
                    ToastUtils.show(ToEvaluateOrdersActivity.this,"删除订单成功！");
                    resBeans1.remove(tag_position);
                    ordersInfoAdapter.notifyDataSetChanged();
                }else {
                    ToastUtils.show(ToEvaluateOrdersActivity.this,"删除订单失败！");
                }
                }
            }
    };
}
