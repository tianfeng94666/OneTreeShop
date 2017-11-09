package onetreeshopsapp.com.onetreeshops.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import onetreeshopsapp.com.onetreeshops.R;
import onetreeshopsapp.com.onetreeshops.adapter.SearchProductAdapter;
import onetreeshopsapp.com.onetreeshops.base.BaseActivity;
import onetreeshopsapp.com.onetreeshops.bean.CreateOrderResult;
import onetreeshopsapp.com.onetreeshops.bean.CreateShoppingCar;
import onetreeshopsapp.com.onetreeshops.bean.CreateShoppingcarResult;
import onetreeshopsapp.com.onetreeshops.bean.GetProductDateResult;
import onetreeshopsapp.com.onetreeshops.bean.MyAddresRecord;
import onetreeshopsapp.com.onetreeshops.bean.OrderProduct;
import onetreeshopsapp.com.onetreeshops.bean.Orders;
import onetreeshopsapp.com.onetreeshops.bean.ProductInfo;
import onetreeshopsapp.com.onetreeshops.dialog.dmax.SpotsDialog;
import onetreeshopsapp.com.onetreeshops.http.JsonRPCAsyncTask;
import onetreeshopsapp.com.onetreeshops.utils.Const;
import onetreeshopsapp.com.onetreeshops.utils.GsonUtil;
import onetreeshopsapp.com.onetreeshops.utils.HttpValue;
import onetreeshopsapp.com.onetreeshops.utils.NumberFormatUtil;
import onetreeshopsapp.com.onetreeshops.utils.ToastUtils;

/**
 * Created by fiona on 2016/10/24.
 */
public class SearchproductResultActivity extends BaseActivity {

    @BindView(R.id.iv_title_left)
    ImageView ivTitleLeft;
    @BindView(R.id.et_dish_search)
    EditText etDishSearch;
    @BindView(R.id.lv_products)
    ListView lvProducts;
    @BindView(R.id.tv_hint)
    TextView tvHint;
    private String key;
    private String sig;
    private int GET_SEARCH_RESULT = 1;
    private MyAddresRecord.Result.Res configAddress;
    private int CHOOSE_CODE = 3;
    private PopupWindow pop;
    private int amount;
    private TextView tvAdress;
    private TextView tvProductAmount;
    private ProductInfo currentProduct;
    private int CREATE_SHOPPINGCAR_CODE = 4;
    private Orders orders;
    private int COMMIT_ORDER = 5;
    //返回订单号
    private String orderNumber;
    private SpotsDialog spotsDialog;
    private double sumMoney;

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_product_result);
        ButterKnife.bind(this);
        getData();
        seach();
        init();
    }

    private void init() {
        etDishSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEND || (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
                    //do something;
                    key = etDishSearch.getText().toString();
                    seach();
                    return true;
                }
                return false;
            }
        });

    }

    private void seach() {
        try {
            JSONObject params = new JSONObject();
            params.put("sig", sig);
            params.put("key", key);
            new JsonRPCAsyncTask(this, mHandler,
                    GET_SEARCH_RESULT, HttpValue.getHttp() + Const.SEARCH_CODE, "call",
                    params).execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getData() {
        key = getIntent().getStringExtra("key");
        sig = getIntent().getStringExtra("sig");
    }


    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            String result = (String) msg.obj;
            if (result == null) {
                ToastUtils.show(SearchproductResultActivity.this, "亲，服务器出问题啦，请稍后再试！");
                return;
            }
            //门店信息
            if (msg.what == GET_SEARCH_RESULT) {
                if (result != null && result.contains("error")) {
                    ToastUtils.show(SearchproductResultActivity.this, "获取门店错误");
                    return;
                }
                GetProductDateResult searchProductResult = GsonUtil.jsonToBean(result, GetProductDateResult.class);
                if (searchProductResult != null && searchProductResult.getResult().isFlag()) {
                    lvProducts.setVisibility(View.VISIBLE);
                    tvHint.setVisibility(View.GONE);
                    Log.v("搜索结果", result);
                    SearchProductAdapter searchProductAdapter = new
                            SearchProductAdapter(SearchproductResultActivity.this, searchProductResult.getResult().getRes());
                    lvProducts.setAdapter(searchProductAdapter);
                } else {
                    lvProducts.setVisibility(View.GONE);
                    tvHint.setVisibility(View.VISIBLE);
                    tvHint.setText("没有搜索到相关宝贝！");
                }
            } else if (msg.what == COMMIT_ORDER) {
                Log.v("订单", result);
                if (GsonUtil.isError(result)) {
                    ToastUtils.show(SearchproductResultActivity.this, "查询我的地址出错");
                    return;
                }
                CreateOrderResult createOrderResult = GsonUtil.jsonToBean(result, CreateOrderResult.class);
                if (createOrderResult.getResult().isFlag()) {
                    orderNumber = createOrderResult.getResult().getRes().getPayment_way_number();
                    Intent intent = new Intent(SearchproductResultActivity.this, ConfigOrderActivity.class);
                    intent.putExtra("orders", orders);
                    startActivity(intent);
                    overridePendingTransition(R.anim.push_left_in,
                            R.anim.push_left_out);
                }

            } else if (msg.what == CREATE_SHOPPINGCAR_CODE) {
                if (result != null && result.contains("error")) {
                    ToastUtils.show(SearchproductResultActivity.this, "创建购物车错误");
                    return;
                }
                if (result != null && !result.contains("error")) {
                    Log.v("创建购物车", result);
                    CreateShoppingcarResult createShoppingcarResult = GsonUtil.jsonToBean(result, CreateShoppingcarResult.class);
                    spotsDialog.dismiss();
                    if (createShoppingcarResult.getResult().isFlag()) {
                        pop.dismiss();
                        backgroundAlpha(SearchproductResultActivity.this, 1);
                        ToastUtils.show(SearchproductResultActivity.this, "已经添加到购物车中了！");
                    }


                } else {
                    ToastUtils.show(SearchproductResultActivity.this, "获取门店信息失败！");
                }
            }


        }
    };


    @OnClick({R.id.iv_title_left, R.id.et_dish_search})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_title_left:
                finish();
                break;
            case R.id.et_dish_search:
                break;
        }
    }

    public void gotoSelectAddress() {
        Intent intent = new Intent(this, SelectAdressActivity.class);
        this.startActivityForResult(intent, CHOOSE_CODE);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 3:
                configAddress = (MyAddresRecord.Result.Res) data.getSerializableExtra("address");
                tvAdress.setText(configAddress.getAddress());
                break;
        }
    }

    public MyAddresRecord.Result.Res getConfigAddress() {
        return configAddress;
    }

    public void setConfigAddress(MyAddresRecord.Result.Res configAddress) {
        this.configAddress = configAddress;
    }

    /**
     * 选中产品后弹窗
     *
     * @param productInfo
     * @return
     */
    public PopupWindow popwindow(ProductInfo productInfo) {
        amount = 1;
        pop = new PopupWindow(this);
        currentProduct = productInfo;
        PopuWindowListener popuWindowListener = new PopuWindowListener();
        View view = (this).getLayoutInflater().inflate(R.layout.popuwindow_product_deal, null);
        ImageView ivProductDealImage = (ImageView) view.findViewById(R.id.iv_product_deal_image);
        TextView productPrice = (TextView) view.findViewById(R.id.product_price);
        ImageView ivClose = (ImageView) view.findViewById(R.id.iv_close);
//        tvAdress = (TextView) view.findViewById(R.id.tv_address);
        ImageView ivProductAdd = (ImageView) view.findViewById(R.id.iv_product_add);
//        ImageView ivPopuwindNext = (ImageView) view.findViewById(R.id.iv_popuwindow_next);
        tvProductAmount = (TextView) view.findViewById(R.id.tv_product_amount);
        ImageView ivProductReduce = (ImageView) view.findViewById(R.id.iv_product_reduce);
        TextView tvPutIntoShoppingcar = (TextView) view.findViewById(R.id.tv_put_into_shoppingcar);
        TextView tvBuyProductNow = (TextView) view.findViewById(R.id.tv_buy_product_now);
        Glide.with(this)
                .load(HttpValue.getHttp() + productInfo.getImage())
                .into(ivProductDealImage);
        productPrice.setText("￥" + productInfo.getPrice());
        ivClose.setOnClickListener(popuWindowListener);
        ivProductAdd.setOnClickListener(popuWindowListener);
        ivProductReduce.setOnClickListener(popuWindowListener);
//        ivPopuwindNext.setOnClickListener(popuWindowListener);
        tvBuyProductNow.setOnClickListener(popuWindowListener);
        tvPutIntoShoppingcar.setOnClickListener(popuWindowListener);

        DisplayMetrics metric = new DisplayMetrics();
        (this).getWindowManager().getDefaultDisplay().getMetrics(metric);
        int width = metric.widthPixels;     // 屏幕宽度（像素）
        int height = metric.heightPixels;   // 屏幕高度（像素）
        pop.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        pop.setHeight((int) (height * 0.5));
        pop.setBackgroundDrawable(new BitmapDrawable());
        pop.setFocusable(true);
        pop.setOutsideTouchable(true);
        pop.setContentView(view);
        pop.setAnimationStyle(R.style.PopupAnimation);
        //实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0x00000000);
        //设置SelectPicPopupWindow弹出窗体的背景
        pop.setBackgroundDrawable(dw);
        backgroundAlpha(this, 0.5f);//0.0-1.0
        return pop;

    }

    /**
     * 设置添加屏幕的背景透明度
     *
     * @param bgAlpha
     */
    public void backgroundAlpha(Activity context, float bgAlpha) {
        WindowManager.LayoutParams lp = context.getWindow().getAttributes();
        lp.alpha = bgAlpha;
        context.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        context.getWindow().setAttributes(lp);
    }

    class PopuWindowListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.iv_close:
                    pop.dismiss();
                    backgroundAlpha(SearchproductResultActivity.this, 1);
                    break;
                case R.id.iv_product_add:
                    ++amount;
                    tvProductAmount.setText(amount + "");
                    break;
                case R.id.iv_product_reduce:
                    if (amount > 1) {
                        --amount;
                    }
                    tvProductAmount.setText(amount + "");
                    break;
//                case R.id.iv_popuwindow_next:
//                    gotoSelectAddress();
//                    break;
                case R.id.tv_put_into_shoppingcar:
                    commitToShoppingCar();
                    break;
                case R.id.tv_buy_product_now:
                    createOrder();
                    goToConfigOrderActivity();
//                    commitOrders();
                    break;
            }
        }
    }

    private void goToConfigOrderActivity() {
        Intent intent = new Intent(SearchproductResultActivity.this, ConfigOrderActivity.class);
        intent.putExtra("orders", orders);
        intent.putExtra("sumMoney", sumMoney + "");
        System.out.println("SSSSsuMoney=" + sumMoney);
        startActivity(intent);
        overridePendingTransition(R.anim.push_left_in,
                R.anim.push_left_out);
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

    /**
     * 生成订单
     */
    private void createOrder() {
        currentProduct.setAmount(Integer.parseInt(tvProductAmount.getText().toString()));
        orders = new Orders();
        Orders.OrdersAppBean ordersAppBean = new Orders.OrdersAppBean();
        List<OrderProduct> lines = new ArrayList<>();
        OrderProduct currentOrderProduct = new OrderProduct();
        currentOrderProduct.setAmount(currentProduct.getAmount());
        currentOrderProduct.setStore_name(currentProduct.getStore_name());
        currentOrderProduct.setImage(currentProduct.getImage());
        currentOrderProduct.setName(currentProduct.getName());
        currentOrderProduct.setPrice(currentProduct.getPrice());
        currentOrderProduct.setUom(currentProduct.getUom());
        currentOrderProduct.setProduct_id(currentProduct.getProduct_id());
        lines.add(currentOrderProduct);
        ordersAppBean.setStore_id(currentProduct.getStore_id() + "");
        ordersAppBean.setStroes_name(currentProduct.getStore_name());
        ordersAppBean.setLines(lines);

        List<Orders.OrdersAppBean> ordersAppBeanList = new ArrayList<>();
        ordersAppBeanList.add(ordersAppBean);

        orders.setOrders_app(ordersAppBeanList);
        sumMoney = NumberFormatUtil.formatToDouble1(currentProduct.getPrice() * currentProduct.getAmount());
    }

    /**
     * 传递数据到购物车中
     */
    private void commitToShoppingCar() {
        try {
            spotsDialog = new SpotsDialog(SearchproductResultActivity.this, "正添加到购物车中。。。");
            spotsDialog.show();
            CreateShoppingCar createShoppingCar = new CreateShoppingCar();
            CreateShoppingCar.Shopping shopping = new CreateShoppingCar.Shopping();
            shopping.setPos_session_id(HttpValue.SP_USERID);
            List<ProductInfo> list = new ArrayList<>();
            list.add(currentProduct);
            shopping.setLines(list);
            List<CreateShoppingCar.Shopping> shoppings = new ArrayList<>();
            shoppings.add(shopping);
            createShoppingCar.setShopping(shoppings);
            JSONObject params = new JSONObject(GsonUtil.beanToJson(createShoppingCar));
            new JsonRPCAsyncTask(this, mHandler,
                    CREATE_SHOPPINGCAR_CODE, HttpValue.getHttp() + Const.CREATE_SHOPPINGCAR, "call",
                    params).execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
