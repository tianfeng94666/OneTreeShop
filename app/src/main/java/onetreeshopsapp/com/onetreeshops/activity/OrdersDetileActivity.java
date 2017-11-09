package onetreeshopsapp.com.onetreeshops.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;
import onetreeshopsapp.com.onetreeshops.R;
import onetreeshopsapp.com.onetreeshops.adapter.OrdersInfoAdapter;
import onetreeshopsapp.com.onetreeshops.adapter.OrdersProductAdapter;
import onetreeshopsapp.com.onetreeshops.base.BaseActivity;
import onetreeshopsapp.com.onetreeshops.bean.CancelOrderResult;
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
import onetreeshopsapp.com.onetreeshops.utils.view.RushBuyCountDownTimerView;
import onetreeshopsapp.com.onetreeshops.utils.view.XListView;

/**
 * Created by ERP on 2016/10/20.
 */
public class OrdersDetileActivity extends BaseActivity implements View.OnClickListener{
    private List<GetOrdersResult.ResultBean.ResBean> ordersList;
    private RushBuyCountDownTimerView timerView;
    private String urplusHour,urplusMinute,urplusSecond;
    private String ordernumber;
    private String sumMoney;
    private List<String> orderNumbersList = new ArrayList<String>();
    private int urplusTime;
    private int data_position;
    private ImageButton orders_detile_back_imgbtn;
    private TextView tv_orders_detile_paystadu;
    private TextView tv_orders_detile_username,tv_orders_detile_userphone,tv_orders_detile_useraddres;
    private ListView lv_orders_detile_product;
    private TextView tv_orders_detile_product_price,tv_orders_detile_freight,tv_orders_detile_orderscout,tv_orders_detile_paymoney;
    private TextView tv_orders_detile_ordersnumber,tv_orders_detile_orderstime;
    private TextView tv_orders_detile_orderdelect,tv_orders_detile_ordercancle,tv_orders_detile_ordertopay,tv_orders_detile_payway,tv_orders_detile_paynumber,tv_orders_detile_paytime;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders_detile);
        EventBus.getDefault().register(this);
        initview();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    private void initview() {
        timerView = (RushBuyCountDownTimerView) findViewById(R.id.tv_timerView);
        orders_detile_back_imgbtn = (ImageButton) findViewById(R.id.orders_detile_back_imgbtn);
        orders_detile_back_imgbtn.setOnClickListener(this);
        tv_orders_detile_paystadu = (TextView) findViewById(R.id.tv_orders_detile_paystadu);
        tv_orders_detile_username = (TextView) findViewById(R.id.tv_orders_detile_username);
        tv_orders_detile_userphone = (TextView) findViewById(R.id.tv_orders_detile_userphone);
        tv_orders_detile_useraddres = (TextView) findViewById(R.id.tv_orders_detile_useraddres);
        tv_orders_detile_product_price = (TextView) findViewById(R.id.tv_orders_detile_product_price);
        tv_orders_detile_freight = (TextView) findViewById(R.id.tv_orders_detile_freight);
        tv_orders_detile_orderscout = (TextView) findViewById(R.id.tv_orders_detile_orderscout);
        tv_orders_detile_paymoney = (TextView) findViewById(R.id.tv_orders_detile_paymoney);
        tv_orders_detile_ordersnumber = (TextView) findViewById(R.id.tv_orders_detile_ordersnumber);
        tv_orders_detile_orderstime = (TextView) findViewById(R.id.tv_orders_detile_orderstime);
        tv_orders_detile_ordercancle = (TextView) findViewById(R.id.tv_orders_detile_ordercancle);
        tv_orders_detile_ordercancle.setOnClickListener(this);
        tv_orders_detile_ordertopay = (TextView) findViewById(R.id.tv_orders_detile_ordertopay);
        tv_orders_detile_ordertopay.setOnClickListener(this);
        tv_orders_detile_orderdelect = (TextView) findViewById(R.id.tv_orders_detile_orderdelect);
        tv_orders_detile_orderdelect.setOnClickListener(this);
        tv_orders_detile_payway = (TextView) findViewById(R.id.tv_orders_detile_payway);
        tv_orders_detile_paynumber = (TextView) findViewById(R.id.tv_orders_detile_paynumber);
        tv_orders_detile_paytime = (TextView) findViewById(R.id.tv_orders_detile_paytime);
        lv_orders_detile_product = (ListView) findViewById(R.id.lv_orders_detile_product);
        setdata();
        
    }

    private void setdata() {
        Intent intent = getIntent();
         data_position = intent.getIntExtra("position",1);
         ordersList = (List<GetOrdersResult.ResultBean.ResBean>)intent.getSerializableExtra("ordersList");
        tv_orders_detile_username.setText("收货人："+ordersList.get(data_position).getConsignee());
        tv_orders_detile_userphone.setText(ordersList.get(data_position).getTelephone());
        tv_orders_detile_useraddres.setText("收货地址："+ordersList.get(data_position).getAddress());
        List<GetOrdersResult.ResultBean.ResBean.OrdersProductBean> ordersProductBeanList = ordersList.get(data_position).getLines();
        System.out.println(">>>>>"+data_position+"<<<<<"+ordersProductBeanList.size());
        OrdersProductAdapter ordersProductAdapter = new OrdersProductAdapter(OrdersDetileActivity.this,ordersProductBeanList);
        lv_orders_detile_product.setAdapter(ordersProductAdapter);
        setListViewHeightBasedOnChildren(lv_orders_detile_product);
        lv_orders_detile_product.setOnItemClickListener(onItemClickListener);
        tv_orders_detile_product_price.setText("￥"+ordersList.get(data_position).getOrder_amount());
        tv_orders_detile_freight.setText("￥"+ordersList.get(data_position).getTransport_cost());
        tv_orders_detile_orderscout.setText("￥"+ordersList.get(data_position).getAmount_total());
        sumMoney = ""+ordersList.get(data_position).getAmount_total();
        tv_orders_detile_paymoney.setText("￥"+sumMoney);
        ordernumber = ordersList.get(data_position).getPos_reference();
        tv_orders_detile_ordersnumber.setText("订单编号："+ordernumber);
        tv_orders_detile_orderstime.setText("创建时间："+ordersList.get(data_position).getCreate_date());
        urplusTime = ordersList.get(data_position).getRemainder_time();
        String paystate = ordersList.get(data_position).getPay_state();
        tv_orders_detile_paystadu.setText(paystate);
        if (paystate.equals("待付款")){
            tv_orders_detile_orderdelect.setVisibility(View.GONE);
            tv_orders_detile_ordercancle.setVisibility(View.VISIBLE);
            tv_orders_detile_ordertopay.setVisibility(View.VISIBLE);
            timerView.setVisibility(View.VISIBLE);
            secToTime(urplusTime);

        }else if (paystate.equals("已完成")){
            tv_orders_detile_orderdelect.setVisibility(View.VISIBLE);
            tv_orders_detile_ordercancle.setVisibility(View.GONE);
            tv_orders_detile_ordertopay.setVisibility(View.GONE);
            tv_orders_detile_payway.setVisibility(View.VISIBLE);
            tv_orders_detile_payway.setText("支付方式：支付宝");
            tv_orders_detile_paynumber.setVisibility(View.VISIBLE);
            tv_orders_detile_paynumber.setText("支付交易号：2012546987");
            tv_orders_detile_paytime.setVisibility(View.VISIBLE);
            tv_orders_detile_paytime.setText("付款时间：2012-25-03 17:16:20");
            timerView.setVisibility(View.GONE);
        } else if (paystate.equals("交易关闭")){
            tv_orders_detile_orderdelect.setVisibility(View.VISIBLE);
            tv_orders_detile_ordercancle.setVisibility(View.GONE);
            tv_orders_detile_ordertopay.setVisibility(View.GONE);
            timerView.setVisibility(View.GONE);
        }
    }
    //根据情况设置商品listview高度
    public void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            // pre-condition
            return;
        }

        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }

    AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Intent intent = new Intent(OrdersDetileActivity.this, ShopActivity.class);
            intent.putExtra("stote_id",ordersList.get(data_position).getStore_id()+"");
            startActivity(intent);
        }
    };

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.orders_detile_back_imgbtn :
                finish();
                break;
            case R.id.tv_orders_detile_ordercancle :
                cancelorders(ordernumber);
                break;
            case R.id.tv_orders_detile_ordertopay :
                orderNumbersList.add(ordernumber);
                if (orderNumbersList.size()>0) {
                    singleTopay();
                }
                break;
            case R.id.tv_orders_detile_orderdelect:
                delectdialog(ordernumber);
                break;
            
        }
    }
    //单个订单支付
    private void singleTopay() {
        Intent intent = new Intent(OrdersDetileActivity.this,ChoosePayWayActivity.class);
        intent.putExtra("sig","topayorderactivity");
        intent.putExtra("orderNumbers",(Serializable)orderNumbersList);
        intent.putExtra("sumMoney",sumMoney);
        startActivity(intent);
    }
    //剩余秒数转换成时分
    private void secToTime(int time) {
        String timeStr = null;
         int hour = 0;
         int minute = 0;
         int second = 0;
         if (time <= 0){

         }
         else {
             minute = time / 60;
             if (minute < 60) {
                 second = time % 60;
                 timeStr = unitFormat(minute) + ":" + unitFormat(second);
                 urplusHour = "00";
                 urplusMinute = unitFormat(minute);
                 urplusSecond = unitFormat(second);
                 } else {
                 hour = minute / 60;
                 if (hour > 99);
                 //return "99:59:59";
                 minute = minute % 60;
                 second = time - hour * 3600 - minute * 60;
                 //timeStr = unitFormat(hour) + ":" + unitFormat(minute) + ":" + unitFormat(second);
                 urplusHour = unitFormat(hour);
                 urplusMinute = unitFormat(minute);
                 urplusSecond = unitFormat(second);
                 }
             System.out.println(urplusHour+"\\"+urplusMinute+"//"+urplusSecond);
             timerView.setTime(Integer.parseInt(urplusHour),Integer.parseInt(urplusMinute),Integer.parseInt(urplusSecond));
             //timerView.setTime(00,01,15);
             timerView.start();
             }
         //return timeStr;
         }
     public static String unitFormat(int i) {
         String retStr = null;
         if (i >= 0 && i < 10)
         retStr = "0" + Integer.toString(i);
         else
         retStr = "" + i;
        return retStr;
         }
    public void onEventMainThread(String send) {
        if (send.equals("timeout")) {
           setview();
        }
    }
    private void setview(){
        tv_orders_detile_paystadu.setText("交易关闭");
        timerView.setVisibility(View.GONE);
        tv_orders_detile_orderdelect.setVisibility(View.VISIBLE);
        tv_orders_detile_ordercancle.setVisibility(View.GONE);
        tv_orders_detile_ordertopay.setVisibility(View.GONE);
    }
    //删除订单提示对话框
    private void delectdialog(final String ordernumber) {
        getConfirmDialog(OrdersDetileActivity.this, "确认删除订单?", new DialogInterface.OnClickListener
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

    //取消订单
    public void cancelorders(String ordernumber) {
        if (!NetWorkUtils.getNetConnecState(this)) {
            Toast.makeText(OrdersDetileActivity.this, "请检查网络是否连接！", Toast.LENGTH_SHORT).show();
            return;
        }
        UIProgressUtil.showProgress(OrdersDetileActivity.this,"订单取消中...",true);
        try {
            JSONObject params = new JSONObject();
            params.put("uesr_id", HttpValue.SP_USERID);
            params.put("pos_reference", ordernumber);
            new JsonRPCAsyncTask(OrdersDetileActivity.this, mHandler,
                    Const.CANCEL_ORDERS_CODE, HttpValue.getHttp() + Const.CANCEL_ORDERS, "call",
                    params).execute();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    //删除订单
    public void delectorders(String ordernumber) {
        if (!NetWorkUtils.getNetConnecState(this)) {
            Toast.makeText(OrdersDetileActivity.this, "请检查网络是否连接！", Toast.LENGTH_SHORT).show();
            return;
        }
        UIProgressUtil.showProgress(OrdersDetileActivity.this,"订单删除中...",true);
        try {
            JSONObject params = new JSONObject();
            params.put("uesr_id",HttpValue.SP_USERID);
            params.put("pos_reference", ordernumber);
            new JsonRPCAsyncTask(OrdersDetileActivity.this, mHandler,
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
                ToastUtils.show(OrdersDetileActivity.this,"亲，服务器出问题啦，请稍后再试！");
                return;
            }
             if (msg.what == Const.CANCEL_ORDERS_CODE){//取消订单
                System.out.println("取消订单返回数据为：" + result);
                if (GsonUtil.isError(result)) {
                    ToastUtils.show(OrdersDetileActivity.this, "取消订单出错");
                    return;
                }
                CancelOrderResult cancelOrderResult = GsonUtil.jsonToBean(result,CancelOrderResult.class);
                if (cancelOrderResult.getResult().isFlag()){
                    ToastUtils.show(OrdersDetileActivity.this, "取消订单成功！");
                    timerView.stop();
                    setview();
                }else {
                    ToastUtils.show(OrdersDetileActivity.this, "取消订单失败");
                }

            }else if (msg.what == Const.DELECT_ORDERS_CODE){//删除订单
                System.out.println("删除订单返回数据为：" + result);
                if (GsonUtil.isError(result)) {
                    ToastUtils.show(OrdersDetileActivity.this, "删除出错");
                    return;
                }
                DelectOrderResult delectOrderResult = GsonUtil.jsonToBean(result,DelectOrderResult.class);
                if (delectOrderResult.getResult().isFlag()){
                    ToastUtils.show(OrdersDetileActivity.this,"删除订单成功！");
                    IntentUtils.getIntentUtils().intent(OrdersDetileActivity.this,IndexActivity.class);
                    finish();
                }else {
                    ToastUtils.show(OrdersDetileActivity.this,"删除订单失败！");
                }
            }
        }
    };

}
