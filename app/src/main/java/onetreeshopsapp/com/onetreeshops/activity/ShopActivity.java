package onetreeshopsapp.com.onetreeshops.activity;

import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;
import onetreeshopsapp.com.onetreeshops.R;
import onetreeshopsapp.com.onetreeshops.adapter.SortNameAdapter;
import onetreeshopsapp.com.onetreeshops.adapter.SortProductAdapter;
import onetreeshopsapp.com.onetreeshops.base.BaseActivity;
import onetreeshopsapp.com.onetreeshops.bean.CreateShoppingcarResult;
import onetreeshopsapp.com.onetreeshops.bean.EventProduct;
import onetreeshopsapp.com.onetreeshops.bean.GetProductDateResult;
import onetreeshopsapp.com.onetreeshops.bean.ProductInfo;
import onetreeshopsapp.com.onetreeshops.bean.SearchSortResult;
import onetreeshopsapp.com.onetreeshops.bean.CreateShoppingCar;
import onetreeshopsapp.com.onetreeshops.bean.SortProductResult;
import onetreeshopsapp.com.onetreeshops.http.JsonRPCAsyncTask;
import onetreeshopsapp.com.onetreeshops.utils.Const;
import onetreeshopsapp.com.onetreeshops.utils.GsonUtil;
import onetreeshopsapp.com.onetreeshops.utils.HttpValue;
import onetreeshopsapp.com.onetreeshops.utils.NumberFormatUtil;
import onetreeshopsapp.com.onetreeshops.utils.ToastUtils;
import onetreeshopsapp.com.onetreeshops.utils.view.NXHooldeView;

/**
 * Created by fiona on 2016/10/25.
 */
public class ShopActivity extends BaseActivity implements SortProductAdapter.FoodActionCallback {
    @BindView(R.id.edit_search)
    EditText editSearch;
    @BindView(R.id.lv_sort)
    ListView lvSort;
    @BindView(R.id.lv_goods)
    ListView lvGoods;
    @BindView(R.id.tv_total_money)
    TextView tvTotalMoney;
    @BindView(R.id.tv_config)
    TextView tvConfig;
    @BindView(R.id.ll_shopping_car)
    LinearLayout llShoppingCar;
    @BindView(R.id.tv_good_fitting_num)
    TextView tv_good_fitting_num;
    private String stote_id;
    private int GET_SEARCH_SORT_RESULT = 1;
    private int GET_SORT_PRODUCT = 2;
    private int CREATE_SHOPPINGCAR_CODE= 3;
    private int SHOP_PRODUCT_SEARCH = 4;
    private SortNameAdapter sortNameAdapter;
    private List<ProductInfo> choosedProducts = new ArrayList<>();
    private double total_money;
    private int total_number;
    private List<SearchSortResult.ResultBean.ResBean> sortlist;
    private List<ProductInfo> productList;
    private SortProductAdapter shopsProductAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);
        ButterKnife.bind(this);
        getData();
        getSort();
        init();
        EventBus.getDefault().register(this);
    }

    private void init() {
        lvSort.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (sortNameAdapter != null) {
                    setSelectSortItem(position);
                }

            }
        });

        tvConfig.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                commitToShoppingCar();
            }
        });
        editSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEND || (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
                    //do something;
                  seachProduct();
                    return true;
                }
                return false;
            }
        });
    }

    /***
     * 搜索产品
     */
    private void seachProduct() {
        JSONObject params = new JSONObject();
        try {
            params.put("store_id",stote_id);
            params.put("key_word",editSearch.getText().toString().trim());
            new JsonRPCAsyncTask(this, mHandler,
                    SHOP_PRODUCT_SEARCH, HttpValue.getHttp() + Const.SHOP_PRODUCT_SEARCH, "call",
                    params).execute();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
 * 传递数据到购物车中
 */
    private void commitToShoppingCar() {
        try {
            CreateShoppingCar createShoppingCar = new CreateShoppingCar();
            CreateShoppingCar.Shopping shopping = new CreateShoppingCar.Shopping();
            shopping.setPos_session_id(HttpValue.SP_USERID);
            shopping.setLines(choosedProducts);
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

    /**
     * 设置选中的分类
     * @param position
     */
    private void setSelectSortItem(int position) {
        sortNameAdapter.setCurrentPosition(position);
        getProduct(sortNameAdapter.getCurrentSortId());
        lvSort.setAdapter(sortNameAdapter);
    }

    /**
     * 获取产品
     * @param currentSortId
     */
    private void getProduct(int currentSortId) {
        try {
            JSONObject params = new JSONObject();
            params.put("store_id", stote_id);
            params.put("category_id", currentSortId);
            new JsonRPCAsyncTask(this, mHandler,
                    GET_SORT_PRODUCT, HttpValue.getHttp() + Const.SHOPS_PRODUCT, "call",
                    params).execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取分类
     */
    private void getSort() {
        try {
            JSONObject params = new JSONObject();
            params.put("store_id", stote_id);

            new JsonRPCAsyncTask(this, mHandler,
                    GET_SEARCH_SORT_RESULT, HttpValue.getHttp() + Const.SEARCH_SORT_CODE, "call",
                    params).execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getData() {
        stote_id = getIntent().getStringExtra("stote_id");

    }



    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {

            //门店信息
            if (msg.what == GET_SEARCH_SORT_RESULT) {
                String result = (String) msg.obj;

                if (result != null && result.contains("error")) {
                    ToastUtils.show(ShopActivity.this, "获取门店错误");
                    return;
                }
                SearchSortResult searchSortResult = GsonUtil.jsonToBean(result, SearchSortResult.class);
                if (searchSortResult != null && searchSortResult.getResult().isFlag()) {
                    Log.v("搜索分类结果", result);
                    sortlist = searchSortResult.getResult().getRes();
                    changeSortView();
                    setSelectSortItem(0);
                }

            } else if (msg.what == GET_SORT_PRODUCT) {
                String result = (String) msg.obj;
                Log.v("分类下产品", result);
                if (result != null && result.contains("error")) {
                    ToastUtils.show(ShopActivity.this, "获取产品错误");
                    return;
                }
                SortProductResult sortProductResult = GsonUtil.jsonToBean(result, SortProductResult.class);
                if (sortProductResult != null && sortProductResult.getResult().isFlag()) {
                    productList = sortProductResult.getResult().getRes();
                    shopsProductAdapter = new SortProductAdapter(ShopActivity.this, productList);
                    shopsProductAdapter.setCallback(ShopActivity.this);
                    lvGoods.setAdapter(shopsProductAdapter);

                }
            }else  if(msg.what == CREATE_SHOPPINGCAR_CODE){
                String result = (String) msg.obj;
                if(result!=null){
                    Log.v("创建购物车", result);
                }
                if (result != null && result.contains("error")) {
                    ToastUtils.show(ShopActivity.this, "创建购物车错误");
                    return;
                }
                if (result != null && !result.contains("error")) {
                    CreateShoppingcarResult createShoppingcarResult = GsonUtil.jsonToBean(result, CreateShoppingcarResult.class);

                    if (createShoppingcarResult.getResult().isFlag()) {
                        Intent intent = new Intent(ShopActivity.this,IndexActivity.class);
                        intent.putExtra("page",1);
                        intent.putExtra("isGotoshopCar","true");
                        startActivity(intent);
                        finish();

                    }
                }

            }else  if(msg.what == SHOP_PRODUCT_SEARCH){
                String result = (String) msg.obj;
                if(result!=null){
                    Log.v("产品搜索", result);
                }
                if (result != null && result.contains("error")) {
                    ToastUtils.show(ShopActivity.this, "创建购物车错误");
                    return;
                }
                GetProductDateResult  productDateResult = GsonUtil.jsonToBean(result, GetProductDateResult.class);
                if (productDateResult != null && productDateResult.getResult().isFlag()) {
                    productList = productDateResult.getResult().getRes();
                    shopsProductAdapter = new SortProductAdapter(ShopActivity.this, productList);
                    shopsProductAdapter.setCallback(ShopActivity.this);
                    lvGoods.setAdapter(shopsProductAdapter);

                }
            }


        }
    };

    private void changeSortView() {
        sortNameAdapter = new SortNameAdapter(ShopActivity.this, (ArrayList<SearchSortResult.ResultBean.ResBean>) sortlist);
        lvSort.setAdapter(sortNameAdapter);
    }
    /**
     * 接收处理数量的改变
     *
     * @param send
     */
    public void onEventMainThread(EventProduct send) {
        String event = send.getEventname();
        ProductInfo resBean = send.getData();
        if (event.equals("sortAdd")) {
           changeChooseList(resBean,"sortAdd");
        } else if (event.equals("sortReduce")) {
            changeChooseList(resBean,"sortReduce");
        }
    }

    private void changeChooseList(ProductInfo resBean, String st) {
        boolean isHave = false;
        for(int i = 0;i<choosedProducts.size();i++){
            ProductInfo productInfo = choosedProducts.get(i);
            if(productInfo.getProduct_id()==resBean.getProduct_id()){
                isHave = true;
                int amount =resBean.getAmount();
                System.out.println("product_amount="+resBean.getAmount());
                System.out.println("amount="+amount);
                if(st.equals("sortAdd")){
                    productInfo.setAmount(amount);
                    System.out.println("amount++="+amount);
                }else {
                    productInfo.setAmount(amount);
                    if(amount == 0){
                        choosedProducts.remove(productInfo);
                    }
                }

            }
        }

        if(!isHave){
            System.out.println("1product_amount="+resBean.getAmount());
            choosedProducts.add(resBean);
            System.out.println("resBean="+resBean.getAmount());
        }
        setTotalMoney();
        if(choosedProducts.size()>0){
            llShoppingCar.setVisibility(View.VISIBLE);
        }else {
            llShoppingCar.setVisibility(View.GONE);
        }
    }
    private void setTotalMoney() {
        total_money = 0;
        total_number = 0;
        for (int i = 0; i < choosedProducts.size(); i++) {
            ProductInfo temp = choosedProducts.get(i);
            total_money = total_money + temp.getPrice() * temp.getAmount();
            total_number = total_number + temp.getAmount();
        }
        tv_good_fitting_num.setText(total_number+"");
        tvTotalMoney.setText("￥" + NumberFormatUtil.formatToDouble2(total_money) + "元");
        if (choosedProducts.size() == 0) {
            llShoppingCar.setVisibility(View.GONE);
        } else {
            llShoppingCar.setVisibility(View.VISIBLE);
        }

    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void addAction(View view) {
        NXHooldeView nxHooldeView = new NXHooldeView(this);
        int position[] = new int[2];
        view.getLocationInWindow(position);
        nxHooldeView.setStartPosition(new Point(position[0], position[1]));
        ViewGroup rootView = (ViewGroup) this.getWindow().getDecorView();
        rootView.addView(nxHooldeView);
        int endPosition[] = new int[2];
        tv_good_fitting_num.getLocationInWindow(endPosition);
        nxHooldeView.setEndPosition(new Point(endPosition[0], endPosition[1]));
        nxHooldeView.startBeizerAnimation();
    }
}
