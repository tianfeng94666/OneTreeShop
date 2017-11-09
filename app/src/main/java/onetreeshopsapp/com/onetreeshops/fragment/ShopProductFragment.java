package onetreeshopsapp.com.onetreeshops.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;
import onetreeshopsapp.com.onetreeshops.R;
import onetreeshopsapp.com.onetreeshops.activity.IndexActivity;
import onetreeshopsapp.com.onetreeshops.adapter.SortNameAdapter;
import onetreeshopsapp.com.onetreeshops.adapter.SortProductAdapter;
import onetreeshopsapp.com.onetreeshops.bean.CreateShoppingCar;
import onetreeshopsapp.com.onetreeshops.bean.CreateShoppingcarResult;
import onetreeshopsapp.com.onetreeshops.bean.EventProduct;
import onetreeshopsapp.com.onetreeshops.bean.GetProductDateResult;
import onetreeshopsapp.com.onetreeshops.bean.MyAddresRecord;
import onetreeshopsapp.com.onetreeshops.bean.ProductInfo;
import onetreeshopsapp.com.onetreeshops.bean.SearchSortResult;
import onetreeshopsapp.com.onetreeshops.bean.SortProductResult;
import onetreeshopsapp.com.onetreeshops.http.JsonRPCAsyncTask;
import onetreeshopsapp.com.onetreeshops.utils.Const;
import onetreeshopsapp.com.onetreeshops.utils.GsonUtil;
import onetreeshopsapp.com.onetreeshops.utils.HttpValue;
import onetreeshopsapp.com.onetreeshops.utils.NumberFormatUtil;
import onetreeshopsapp.com.onetreeshops.utils.ToastUtils;
import onetreeshopsapp.com.onetreeshops.utils.UIProgressUtil;
import onetreeshopsapp.com.onetreeshops.utils.view.XListView;

/**
 * Created by ERP on 2016/10/10.
 */
public class ShopProductFragment extends Fragment {
    private ListView lv_sort,lv_goods;
    private LinearLayout ll_shopping_car;
    private TextView tv_total_money,tv_config;
    private String stote_id;
    private int GET_SEARCH_SORT_RESULT = 1;
    private SortNameAdapter sortNameAdapter;
    private int GET_SORT_PRODUCT = 2;
    private List<ProductInfo> choosedProducts = new ArrayList<>();
    private double total_money;
    private List<SearchSortResult.ResultBean.ResBean> sortlist;
    private List<ProductInfo> productList;
    private SortProductAdapter shopsProductAdapter;
    private int CREATE_SHOPPINGCAR_CODE= 3;
    private int SHOP_PRODUCT_SEARCH = 4;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_shop_product, null);
        initView(v);
        getData();
        getSort();
        EventBus.getDefault().register(this);
        return v;
    }

    private void initView(View v) {
        lv_sort = (ListView) v.findViewById(R.id.lv_sort);
        lv_goods = (ListView) v.findViewById(R.id.lv_goods);
        ll_shopping_car = (LinearLayout) v.findViewById(R.id.ll_shopping_car);
        tv_config = (TextView) v.findViewById(R.id.tv_config);
        tv_total_money = (TextView) v.findViewById(R.id.tv_total_money);
        lv_sort.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (sortNameAdapter != null) {
                    setSelectSortItem(position);
                }

            }
        });

        tv_config.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                commitToShoppingCar();
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
            //params.put("key_word",editSearch.getText().toString().trim());
            new JsonRPCAsyncTask(getActivity(), mHandler,
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
            new JsonRPCAsyncTask(getActivity(), mHandler,
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
        lv_sort.setAdapter(sortNameAdapter);
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
            new JsonRPCAsyncTask(getActivity(), mHandler,
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

            new JsonRPCAsyncTask(getActivity(), mHandler,
                    GET_SEARCH_SORT_RESULT, HttpValue.getHttp() + Const.SEARCH_SORT_CODE, "call",
                    params).execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getData() {
        stote_id = getActivity().getIntent().getStringExtra("stote_id");

    }



    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {

            //门店信息
            if (msg.what == GET_SEARCH_SORT_RESULT) {
                String result = (String) msg.obj;

                if (result != null && result.contains("error")) {
                    ToastUtils.show(getActivity(), "获取门店错误");
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
                    ToastUtils.show(getActivity(), "获取产品错误");
                    return;
                }
                SortProductResult sortProductResult = GsonUtil.jsonToBean(result, SortProductResult.class);
                if (sortProductResult != null && sortProductResult.getResult().isFlag()) {
                    productList = sortProductResult.getResult().getRes();
                    shopsProductAdapter = new SortProductAdapter(getActivity(), productList);
                    lv_goods.setAdapter(shopsProductAdapter);

                }
            }else  if(msg.what == CREATE_SHOPPINGCAR_CODE){
                String result = (String) msg.obj;
                if(result!=null){
                    Log.v("创建购物车", result);
                }
                if (result != null && result.contains("error")) {
                    ToastUtils.show(getActivity(), "创建购物车错误");
                    return;
                }
                if (result != null && !result.contains("error")) {
                    CreateShoppingcarResult createShoppingcarResult = GsonUtil.jsonToBean(result, CreateShoppingcarResult.class);

                    if (createShoppingcarResult.getResult().isFlag()) {
                        Intent intent = new Intent(getActivity(),IndexActivity.class);
                        intent.putExtra("page",1);
                        intent.putExtra("isGotoshopCar","true");
                        startActivity(intent);


                    }
                }

            }else  if(msg.what == SHOP_PRODUCT_SEARCH){
                String result = (String) msg.obj;
                if(result!=null){
                    Log.v("产品搜索", result);
                }
                if (result != null && result.contains("error")) {
                    ToastUtils.show(getActivity(), "创建购物车错误");
                    return;
                }
                GetProductDateResult productDateResult = GsonUtil.jsonToBean(result, GetProductDateResult.class);
                if (productDateResult != null && productDateResult.getResult().isFlag()) {
                    productList = productDateResult.getResult().getRes();
                    shopsProductAdapter = new SortProductAdapter(getActivity(), productList);
                    lv_goods.setAdapter(shopsProductAdapter);

                }
            }


        }
    };

    private void changeSortView() {
        sortNameAdapter = new SortNameAdapter(getActivity(), (ArrayList<SearchSortResult.ResultBean.ResBean>) sortlist);
        lv_sort.setAdapter(sortNameAdapter);
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
            ll_shopping_car.setVisibility(View.VISIBLE);
        }else {
            ll_shopping_car.setVisibility(View.GONE);
        }
    }
    private void setTotalMoney() {
        total_money = 0;
        for (int i = 0; i < choosedProducts.size(); i++) {
            ProductInfo temp = choosedProducts.get(i);
            total_money = total_money + temp.getPrice() * temp.getAmount();
        }
        tv_total_money.setText("￥" + NumberFormatUtil.formatToDouble2(total_money) + "元");
        if (choosedProducts.size() == 0) {
            ll_shopping_car.setVisibility(View.GONE);
        } else {
            ll_shopping_car.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
