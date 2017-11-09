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
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import onetreeshopsapp.com.onetreeshops.adapter.ToPayOrdersInfoAdapter;
import onetreeshopsapp.com.onetreeshops.base.BaseActivity;
import onetreeshopsapp.com.onetreeshops.bean.CancelOrderResult;
import onetreeshopsapp.com.onetreeshops.bean.CreateOrderResult;
import onetreeshopsapp.com.onetreeshops.bean.DelectOrderResult;
import onetreeshopsapp.com.onetreeshops.bean.GetOrdersResult;
import onetreeshopsapp.com.onetreeshops.http.JsonRPCAsyncTask;
import onetreeshopsapp.com.onetreeshops.utils.Const;
import onetreeshopsapp.com.onetreeshops.utils.GsonUtil;
import onetreeshopsapp.com.onetreeshops.utils.HttpValue;
import onetreeshopsapp.com.onetreeshops.utils.IntentUtils;
import onetreeshopsapp.com.onetreeshops.utils.NetWorkUtils;
import onetreeshopsapp.com.onetreeshops.utils.NumberFormatUtil;
import onetreeshopsapp.com.onetreeshops.utils.ToastUtils;
import onetreeshopsapp.com.onetreeshops.utils.UIProgressUtil;
import onetreeshopsapp.com.onetreeshops.utils.view.XListView;

/**
 * Created by ERP on 2016/10/20.
 */
public class ToPayOrdersActivity extends BaseActivity implements ToPayOrdersInfoAdapter.ListItemClickHelp {
    private ImageButton topayorders_back_imgbtn;
    private LinearLayout ll_topay_all;
    private XListView lv_topay_myorders;
    private Button btn_topay;
    private TextView tv_topay_totalmoney;
    private ImageView iv_topay_orders_nodata;
    private TextView tv_topay_orders_nodata;
    private SharedPreferences sp;
    private List<GetOrdersResult.ResultBean.ResBean> resBeans1 = null;
    private List<GetOrdersResult.ResultBean.ResBean> resBeans2 = null;
    private ToPayOrdersInfoAdapter ordersInfoAdapter;
    private int page = 1;
    private boolean getmore_flag = true;
    private int tag_position;
    private CheckBox cb_topay_select_all;
    private String sumMoney;
    private List<String> orderNumbersList = new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        setContentView(R.layout.activity_topay_orders);
        sp = getSharedPreferences("SP", Context.MODE_PRIVATE);
        initview();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    private void initview() {
        ll_topay_all = (LinearLayout) findViewById(R.id.ll_topay_all);
        tv_topay_totalmoney = (TextView) findViewById(R.id.tv_topay_totalmoney);
        cb_topay_select_all = (CheckBox) findViewById(R.id.cb_topay_select_all);
        cb_topay_select_all.setOnClickListener(this);
        btn_topay = (Button) findViewById(R.id.btn_topay);
        btn_topay.setOnClickListener(this);
        topayorders_back_imgbtn = (ImageButton) findViewById(R.id.topayorders_back_imgbtn);
        topayorders_back_imgbtn.setOnClickListener(this);
        lv_topay_myorders = (XListView) findViewById(R.id.lv_topay_myorders);
        iv_topay_orders_nodata = (ImageView) findViewById(R.id.iv_topay_orders_nodata);
        tv_topay_orders_nodata = (TextView) findViewById(R.id.tv_topay_orders_nodata);
        getorders();
        lv_topay_myorders.setPullLoadEnable(true);
        lv_topay_myorders.setPullRefreshEnable(true);
        lv_topay_myorders.setXListViewListener(new XListView.IXListViewListener() {
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
        lv_topay_myorders.stopLoadMore();
        lv_topay_myorders.stopRefresh(true);
    }


    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.topayorders_back_imgbtn:
                //IntentUtils.getIntentUtils().intent(ToPayOrdersActivity.this,IndexActivity.class);
                finish();
                break;
            case R.id.btn_topay:
                //多个订单合并支付
                getTotalOrders();
                break;
            case R.id.cb_topay_select_all:
                selectAllOrders(cb_topay_select_all.isChecked());
                break;

        }
    }
    //item中按钮点击事件
    @Override
    public void onClickitemButton(View item, View widget, int position, int which) {
        switch (which){

            case R.id.tv_topayorders_cancle:
                //取消订单
                tag_position = position;
                cancelorders(resBeans1.get(position).getPos_reference());
                System.out.println("取消"+position);
                break;
            case R.id.tv_topayorders_topay:
                //单个订单支付
                orderNumbersList.add(resBeans1.get(position).getPos_reference());
                sumMoney = ""+resBeans1.get(position).getAmount_total();
                if (orderNumbersList.size()>0) {
                    Topay(sumMoney);
                }
                System.out.println("付款"+position+"总价："+sumMoney+"订单号："+orderNumbersList.get(0));
                break;
        }

    }
    //跳转支付方式选择界面
    private void Topay(String sumMoney) {
        Intent intent = new Intent(ToPayOrdersActivity.this,ChoosePayWayActivity.class);
        intent.putExtra("sig","topayorderactivity");
        intent.putExtra("orderNumbers",(Serializable)orderNumbersList);
        intent.putExtra("sumMoney",sumMoney);
        startActivity(intent);
    }


    private void selectAllOrders(boolean isselect) {
        GetOrdersResult.ResultBean.ResBean resBean;
        for (int i = 0; i < resBeans1.size(); i++) {
             resBean = resBeans1.get(i);
            resBean.setIsselect(isselect);
        }
        ordersInfoAdapter.notifyDataSetChanged();
    }

    //查询订单
    public void getorders() {
        if (!NetWorkUtils.getNetConnecState(this)) {
            Toast.makeText(ToPayOrdersActivity.this, "请检查网络是否连接！", Toast.LENGTH_SHORT).show();
            return;
        }
        UIProgressUtil.showProgress(ToPayOrdersActivity.this,"订单查询中...",true);
        try {
            JSONObject params = new JSONObject();
            params.put("uesr_id",HttpValue.SP_USERID);
            params.put("pay_state", "待付款");
            params.put("page", page);
            new JsonRPCAsyncTask(ToPayOrdersActivity.this, mHandler,
                    Const.SEARCH_ALLORDERS_CODE, HttpValue.getHttp() + Const.SEARCH_ALLORDERS, "call",
                    params).execute();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    //取消订单
    public void cancelorders(String ordernumber) {
        if (!NetWorkUtils.getNetConnecState(this)) {
            Toast.makeText(ToPayOrdersActivity.this, "请检查网络是否连接！", Toast.LENGTH_SHORT).show();
            return;
        }
        UIProgressUtil.showProgress(ToPayOrdersActivity.this,"订单取消中...",true);
        try {
            JSONObject params = new JSONObject();
            params.put("uesr_id",HttpValue.SP_USERID);
            params.put("pos_reference", ordernumber);
            new JsonRPCAsyncTask(ToPayOrdersActivity.this, mHandler,
                    Const.CANCEL_ORDERS_CODE, HttpValue.getHttp() + Const.CANCEL_ORDERS, "call",
                    params).execute();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private Handler mHandler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            String result = (String) msg.obj;
            UIProgressUtil.cancelProgress();
            UIProgressUtil.cancelProgress();
            if (result==null){
                ToastUtils.show(ToPayOrdersActivity.this,"亲，服务器出问题啦，请稍后再试！");
                return;
            }
            if (msg.what == Const.SEARCH_ALLORDERS_CODE) {//查询全部订单信息
                stoponLoad();
                System.out.println("查询全部订单返回数据为：" + result);
                if (GsonUtil.isError(result)) {
                    ToastUtils.show(ToPayOrdersActivity.this, "查询订单出错");
                    return;
                }
                GetOrdersResult getOrdersResult = GsonUtil.jsonToBean(result,GetOrdersResult.class);
                if(getOrdersResult!=null&& getOrdersResult.getResult()!=null){
                    if(getOrdersResult.getResult().isFlag()){
                        lv_topay_myorders.setVisibility(View.VISIBLE);
                        iv_topay_orders_nodata.setVisibility(View.GONE);
                        tv_topay_orders_nodata.setVisibility(View.GONE);
                        lv_topay_myorders.setPullLoadEnable(true);
                        if(resBeans1 == null){
                            resBeans1 = getOrdersResult.getResult().getRes();
                        }else {
                            resBeans2 = getOrdersResult.getResult().getRes();
                            resBeans1.addAll(resBeans2);
                            resBeans2 = null;
                        }
                        if (ordersInfoAdapter == null){
                            ordersInfoAdapter = new ToPayOrdersInfoAdapter(ToPayOrdersActivity.this,ToPayOrdersActivity.this);
                            ordersInfoAdapter.SetList(resBeans1);
                            lv_topay_myorders.setAdapter(ordersInfoAdapter);
                            System.out.println(">>>>xin"+resBeans1.size()+">>>"+getOrdersResult.getResult().getRes().size());
                        }else {
                            ordersInfoAdapter.SetList(resBeans1);
                            ordersInfoAdapter.notifyDataSetChanged();
                            System.out.println(">>>>>加载1");
                        }
                        if (resBeans1.size()>1){
                            ll_topay_all.setVisibility(View.VISIBLE);
                        }else {
                            ll_topay_all.setVisibility(View.GONE);
                        }
                    }else {
                        if (getmore_flag){
                            page--;
                        }
                        System.out.println(">>>失败信息"+getOrdersResult.getResult().getInfo());
                        if(resBeans1!=null&&resBeans2==null) {
                            ToastUtils.show(ToPayOrdersActivity.this,"亲，已经到底了...");
                            lv_topay_myorders.setPullLoadEnable(false);
                        }else {
                            setview();
                            ToastUtils.show(ToPayOrdersActivity.this, "查询订单记录为空！");
                        }
                    }
                }else {
                    ToastUtils.show(ToPayOrdersActivity.this,"查询订单失败！");
                    setview();
                }

            }else if (msg.what == Const.CANCEL_ORDERS_CODE){//取消订单
                System.out.println("取消订单返回数据为：" + result);
                if (GsonUtil.isError(result)) {
                    ToastUtils.show(ToPayOrdersActivity.this, "取消订单出错");
                    return;
                }
                CancelOrderResult cancelOrderResult = GsonUtil.jsonToBean(result,CancelOrderResult.class);
                if (cancelOrderResult.getResult().isFlag()){
                    ToastUtils.show(ToPayOrdersActivity.this, "取消订单成功！");
                    resBeans1.remove(tag_position);
                    ordersInfoAdapter.notifyDataSetChanged();
                    lv_topay_myorders.setAdapter(ordersInfoAdapter);
                }else {
                    ToastUtils.show(ToPayOrdersActivity.this, "取消订单失败");
                }
            }
            }


    };
    private void setview() {
        lv_topay_myorders.setVisibility(View.GONE);
        iv_topay_orders_nodata.setVisibility(View.VISIBLE);
        tv_topay_orders_nodata.setVisibility(View.VISIBLE);
    }
    public void onEventMainThread(String send) {
        if (send.equals("setButtonvisible")) {
            btn_topay.setVisibility(View.VISIBLE);
            tv_topay_totalmoney.setVisibility(View.VISIBLE);
            tv_topay_totalmoney.setText("合计：￥"+getTotalOrdersMoney());
            System.out.println(">>>>111");
        }else if (send.equals("setButtongone")){
            btn_topay.setVisibility(View.GONE);
            tv_topay_totalmoney.setVisibility(View.GONE);
            tv_topay_totalmoney.setText("合计：￥"+getTotalOrdersMoney());
            System.out.println(">>>>2222");
        }else if (send.equals("sumOrdersMoney")){
            tv_topay_totalmoney.setText("合计：￥"+getTotalOrdersMoney());
            System.out.println(">>>>333");
        }
    }
    /**
     * 计算出选中产品的总金额
     *
     * @return
     */
    private Double getTotalOrdersMoney() {
        double sumMoney = 0;
        for (int i = 0; i < resBeans1.size(); i++) {
                if (resBeans1.get(i).isselect()) {
                    sumMoney = sumMoney + resBeans1.get(i).getAmount_total();
                }
            }
        return NumberFormatUtil.formatToDouble2(sumMoney);
    }
    /**
     * 添加选中订单进行合并支付
     *
     * @return
     */
    private void getTotalOrders() {
        for (int i = 0; i < resBeans1.size(); i++) {
            if (resBeans1.get(i).isselect()) {
                orderNumbersList.add(resBeans1.get(i).getPos_reference());
            }
        }
        if (orderNumbersList.size()>0){
            Topay(getTotalOrdersMoney()+"");
        }
    }
}
