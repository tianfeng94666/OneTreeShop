package onetreeshopsapp.com.onetreeshops.activity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;
import onetreeshopsapp.com.onetreeshops.R;
import onetreeshopsapp.com.onetreeshops.adapter.ConfigOrderShopProductAdapter;
import onetreeshopsapp.com.onetreeshops.base.BaseActivity;
import onetreeshopsapp.com.onetreeshops.bean.CreateOrderResult;
import onetreeshopsapp.com.onetreeshops.bean.CreateShoppingCar;
import onetreeshopsapp.com.onetreeshops.bean.CreateShoppingcarResult;
import onetreeshopsapp.com.onetreeshops.bean.MyAddresRecord;
import onetreeshopsapp.com.onetreeshops.bean.OrderProduct;
import onetreeshopsapp.com.onetreeshops.bean.Orders;
import onetreeshopsapp.com.onetreeshops.bean.ProductInfo;
import onetreeshopsapp.com.onetreeshops.http.JsonRPCAsyncTask;
import onetreeshopsapp.com.onetreeshops.utils.Const;
import onetreeshopsapp.com.onetreeshops.utils.GsonUtil;
import onetreeshopsapp.com.onetreeshops.utils.HttpValue;
import onetreeshopsapp.com.onetreeshops.utils.ToastUtils;
import onetreeshopsapp.com.onetreeshops.utils.UIProgressUtil;

/**
 * Created by fiona on 2016/10/18.
 */
public class ConfigOrderActivity extends BaseActivity {
    @BindView(R.id.iv_title_left)
    ImageView ivTitleLeft;

    @BindView(R.id.tv_config_activity_consignee)
    TextView tvConfigActivityConsignee;
    @BindView(R.id.tv_config_activity_address)
    TextView tvConfigActivityAddress;
    @BindView(R.id.lv_shoopping_cart_produt)
    ListView lvShooppingCartProdut;
    @BindView(R.id.tv_config_activity_sum_money)
    TextView tvConfigActivitySumMoney;
    @BindView(R.id.tv_config_activity_dispatching_money)
    TextView tvConfigActivityDispatchingMoney;
    @BindView(R.id.tv_config_activity_commit)
    TextView tvConfigActivityCommit;
    @BindView(R.id.ll_config_address)
    LinearLayout llConfigAddress;
    private Orders orders;
    public  int GETMYADDRES_CODE = 2;
    //address
    List<MyAddresRecord.Result.Res> addresses;
    MyAddresRecord.Result.Res configAddress;
    private int CHOOSE_CODE = 3;
    private String sumMoney;
    private int COMMIT_ORDER = 1;
    private PopupWindow pop;
    private int DELETE_SHOPPINGCAT_PRODUCT_CODE = 3;
    private ArrayList<List<ProductInfo>> blancedeShopsLists;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config_order);
        ButterKnife.bind(this);
        getData();
        init();
        getmyaddres();
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    private void init() {
        llConfigAddress.setOnClickListener(this);
        ConfigOrderShopProductAdapter configOrderShopProductAdapter = new ConfigOrderShopProductAdapter(this,orders);
        lvShooppingCartProdut.setAdapter(configOrderShopProductAdapter);
        tvConfigActivitySumMoney.setText("￥"+sumMoney);
        tvConfigActivityCommit.setOnClickListener(this);
        ivTitleLeft.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.ll_config_address:
                gotoSelectAddress();
                break;
            case R.id.tv_config_activity_commit:
                if(configAddress!=null){
                    editOrders();
                    UIProgressUtil.showProgress(ConfigOrderActivity.this,"订单提交中...",false);
                    commitOrders();
                }else {
                    ToastUtils.show(ConfigOrderActivity.this,"请编辑地址！");
                }

                break;
            case R.id.iv_title_left:
                finish();
                break;
        }
    }

    private void editOrders() {
        List<Orders.OrdersAppBean> orders_app = new ArrayList<>();;
        /**
         * 生成订单
         */
        for (int i = 0; i < orders.getOrders_app().size(); i++) {
            Orders.OrdersAppBean ordersAppBean =  orders.getOrders_app().get(i);
            List<ProductInfo> lines = new ArrayList<>();
            ordersAppBean.setAddress_id(configAddress.getAddr_id());
        }
    }

    /**
     * 提交订单
     */
    private void commitOrders() {
        try {
            Log.v("orders", GsonUtil.beanToJson(orders));
            JSONObject params = new JSONObject(GsonUtil.beanToJson(orders));
            new JsonRPCAsyncTask(this, mHandler,
                    COMMIT_ORDER, HttpValue.getHttp() + Const.COMMIT_ORDER, "call",
                    params).execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void gotoSelectAddress() {
        Intent intent = new Intent(this,SelectAdressActivity.class);
        startActivityForResult(intent,CHOOSE_CODE);
    }
    private void gotoCreateAddress() {
        Intent intent = new Intent(this,AddNewAddresActivity.class);
        intent.putExtra("type","default_addr");
        startActivityForResult(intent,CHOOSE_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case 3:
                try {
                    configAddress = (MyAddresRecord.Result.Res) data.getSerializableExtra("address");
                    setAddressView();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    /**
     * 获取地址
     */
    private void getmyaddres() {
        try {
            JSONObject params = new JSONObject();
            params.put("sig", "GET");
            params.put("user_id", HttpValue.SP_USERID);
            new JsonRPCAsyncTask(this, mHandler,
                    GETMYADDRES_CODE  , HttpValue.getHttp() + Const.MYADDRES_URL, "call",
                    params).execute();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取订单数据
     */
    private void getData() {
        orders = (Orders) getIntent().getSerializableExtra("orders");
        sumMoney = getIntent().getStringExtra("sumMoney");
        blancedeShopsLists =   (ArrayList<List<ProductInfo>>)getIntent().getSerializableExtra("blancedeShopsLists");
        System.out.println("sumMoney="+sumMoney);
    }

    private String orderNumber;
    private Handler mHandler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            String result = (String) msg.obj;
            UIProgressUtil.cancelProgress();
            if (result==null){
                ToastUtils.show(ConfigOrderActivity.this,"亲，服务器出问题啦，请稍后再试！");
                return;
            }
            if (msg.what == GETMYADDRES_CODE) {
                UIProgressUtil.cancelProgress();
                System.out.println("地址记录返回数据为：" + result);
                if (GsonUtil.isError(result)) {
                    ToastUtils.show(ConfigOrderActivity.this, "查询我的地址出错");
                    return;
                }
                MyAddresRecord myAddresRecord = GsonUtil.jsonToBean(result, MyAddresRecord.class);
                if (myAddresRecord.getResult().getFlag()) {
                    if (myAddresRecord.getResult().getRes().size() > 0) {
                        addresses = myAddresRecord.getResult().getRes();
                        setAddress();
                    } else {
                      adressPrompt();
                    }
                }else {
                    adressPrompt();
                }

            }else if (msg.what == COMMIT_ORDER) {
                if (GsonUtil.isError(result)) {
                    ToastUtils.show(ConfigOrderActivity.this, "提交订单出错");
                    return;
                }
                CreateOrderResult createOrderResult = GsonUtil.jsonToBean(result,CreateOrderResult.class);
                if(createOrderResult.getResult().isFlag()){
                    Log.v("订单", result);
                    commitDelete(getChooseProduct());
                    orderNumber = createOrderResult.getResult().getRes().getPayment_way_number();
                   Intent intent = new Intent(ConfigOrderActivity.this,ChoosePayWayActivity.class);
                    intent.putExtra("sig","configorderactivity");
                    intent.putExtra("orderNumber",orderNumber);
                    intent.putExtra("sumMoney",sumMoney);
                    startActivity(intent);
                    overridePendingTransition(R.anim.push_left_in,
                            R.anim.push_left_out);

                }

            }else if (msg.what == DELETE_SHOPPINGCAT_PRODUCT_CODE) {
                if(result!=null){
                    Log.v("删除购物车", result);
                }
                if (result != null && result.contains("error")) {
                    ToastUtils.show(ConfigOrderActivity.this, "删除购物车错误");
                    return;
                }
                if (result != null && !result.contains("error")) {
                    CreateShoppingcarResult createShoppingcarResult = GsonUtil.jsonToBean(result, CreateShoppingcarResult.class);
                    if(createShoppingcarResult.getResult().isFlag()){

                    }
                }
            }
        }
    };

    /**
     * 取出選中的產品
     * @return
     */
    private ArrayList<ProductInfo> getChooseProduct() {
        ArrayList<ProductInfo> lines = new ArrayList<>();
        List<Orders.OrdersAppBean>  appBeans = orders.getOrders_app();
        for(int i = 0 ;i <appBeans.size();i++){
            List<OrderProduct> shopsList = appBeans.get(i).getLines();
            for(int j = 0;j<shopsList.size();j++){
                ProductInfo productInfo = new ProductInfo();
                OrderProduct orderProduct = shopsList.get(j);
                productInfo.setAmount(orderProduct.getAmount());
                productInfo.setStore_name(appBeans.get(i).getStroes_name());
                productInfo.setStore_id(Integer.parseInt(appBeans.get(i).getStore_id()));
                productInfo.setPrice(orderProduct.getPrice());
                productInfo.setImage(orderProduct.getImage());
                productInfo.setName(orderProduct.getName());
                productInfo.setProduct_id(orderProduct.getProduct_id());
                lines.add(productInfo);
            }
        }
        return lines;
    }

    private void adressPrompt() {
        final Dialog dialog = new Dialog(ConfigOrderActivity.this, R.style.OrderDialog);
        View view = View.inflate(ConfigOrderActivity.this, R.layout.dialog_delete, null);
        TextView type_text = (TextView) view.findViewById(R.id.type_text);
        type_text.setText("你还没有收货地址，设置收货地址？" );
        TextView mine_ok = (TextView) view.findViewById(R.id.mine_ok);
        TextView mine_delete = (TextView) view.findViewById(R.id.mine_delete);

        mine_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoCreateAddress();
                dialog.dismiss();
            }
        });
        mine_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.setCancelable(false); // 不可以用“返回键”取消
        dialog.setContentView(view);
        dialog.show();
    }

    private void popwindow() {
        pop = new PopupWindow(this);

        View view = this.getLayoutInflater().inflate(R.layout.activity_choosepayway, null);
        pop.setWidth(LinearLayout.LayoutParams.MATCH_PARENT);
        pop.setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
        pop.setBackgroundDrawable(new BitmapDrawable());
        pop.setFocusable(true);
        pop.setOutsideTouchable(true);
        pop.setContentView(view);


    }
    private void setAddress() {
        for(int i = 0;i<addresses.size();i++){
            MyAddresRecord.Result.Res res = addresses.get(i);
            if(res.isDefault_addr()){
                configAddress = res;
            }
        }
        setAddressView();

    }

    private void setAddressView() {
        if(configAddress!=null){
            System.out.println(configAddress.getTelephone());
            tvConfigActivityConsignee.setText("收件人："+configAddress.getName()+"     "+configAddress.getTelephone());
            tvConfigActivityAddress.setText("地址："+configAddress.getAddress());
        }

    }

    private void commitDelete(ArrayList<ProductInfo> lines) {
        try {
            CreateShoppingCar createShoppingCar = new CreateShoppingCar();
            CreateShoppingCar.Shopping shopping = new CreateShoppingCar.Shopping();
            shopping.setPos_session_id(HttpValue.SP_USERID);
            shopping.setLines(lines);
            List<CreateShoppingCar.Shopping> shoppings = new ArrayList<>();
            shoppings.add(shopping);
            createShoppingCar.setShopping(shoppings);
            JSONObject params = new JSONObject(GsonUtil.beanToJson(createShoppingCar));
            new JsonRPCAsyncTask(this, mHandler,
                    DELETE_SHOPPINGCAT_PRODUCT_CODE, HttpValue.getHttp() + Const.DELETE_SHOPPINGCAR_PRODUCT, "call",
                    params).execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
