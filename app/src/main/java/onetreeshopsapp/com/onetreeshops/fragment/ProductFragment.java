package onetreeshopsapp.com.onetreeshops.fragment;

import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;
import onetreeshopsapp.com.onetreeshops.R;
import onetreeshopsapp.com.onetreeshops.activity.IndexActivity;
import onetreeshopsapp.com.onetreeshops.activity.ShopActivity2;
import onetreeshopsapp.com.onetreeshops.adapter.recycleAdapter.SortAdapter;
import onetreeshopsapp.com.onetreeshops.adapter.recycleAdapter.SortProductAdapter;
import onetreeshopsapp.com.onetreeshops.bean.CreateShoppingCar;
import onetreeshopsapp.com.onetreeshops.bean.CreateShoppingcarResult;
import onetreeshopsapp.com.onetreeshops.bean.EventProduct;
import onetreeshopsapp.com.onetreeshops.bean.GetProductDateResult;
import onetreeshopsapp.com.onetreeshops.bean.ProductInfo;
import onetreeshopsapp.com.onetreeshops.bean.SearchSortResult;
import onetreeshopsapp.com.onetreeshops.bean.SortProductResult;
import onetreeshopsapp.com.onetreeshops.http.JsonRPCAsyncTask;
import onetreeshopsapp.com.onetreeshops.interfaces.MyItemClickListenerInterface;
import onetreeshopsapp.com.onetreeshops.utils.Const;
import onetreeshopsapp.com.onetreeshops.utils.GsonUtil;
import onetreeshopsapp.com.onetreeshops.utils.HttpValue;
import onetreeshopsapp.com.onetreeshops.utils.NumberFormatUtil;
import onetreeshopsapp.com.onetreeshops.utils.ToastUtils;
import onetreeshopsapp.com.onetreeshops.utils.view.DividerDecoration;
import onetreeshopsapp.com.onetreeshops.utils.view.NXHooldeView;

/**
 * Created by @vitovalov on 30/9/15.
 */
public class ProductFragment extends Fragment implements SortProductAdapter.FoodActionCallback {


    @BindView(R.id.edit_search)
    EditText editSearch;
    @BindView(R.id.lv_sort)
    RecyclerView rvSort;
    @BindView(R.id.lv_goods)
    RecyclerView rvGoods;
    private String stote_id;
    private int GET_SEARCH_SORT_RESULT = 1;
    private int GET_SORT_PRODUCT = 2;
    private int CREATE_SHOPPINGCAR_CODE = 3;
    private int SHOP_PRODUCT_SEARCH = 4;
    private SortAdapter sortAdapter;
    private List<ProductInfo> choosedProducts = new ArrayList<>();
    private List<SearchSortResult.ResultBean.ResBean> sortlist;
    private List<ProductInfo> productList;
    private List<ProductInfo> currentProductList ;
    private SortProductAdapter shopsProductAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        EventBus.getDefault().register(this);
        View view = inflater.inflate(R.layout.fragment_product, container, false);
        ButterKnife.bind(this, view);
        getData();
        getSort();
        //getProductList();
        init();
        return view;
    }

    private void getProductList(String categoryId) {
        try {
            JSONObject params = new JSONObject();
            params.put("store_id", stote_id + "");
            params.put("category_id", categoryId);
            new JsonRPCAsyncTask(getActivity(), mHandler,
                    GET_SORT_PRODUCT, HttpValue.getHttp() + Const.SHOPS_PRODUCT, "call",
                    params).execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    private void init() {
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
        rvGoods.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (rvGoods.canScrollVertically(1) == false) {
                    //false表示已经滚动到底部
                    System.out.println("已到底部");
                } else if (rvGoods.canScrollVertically(-1) == false) {
                    //false表示已经滚动到部
                    System.out.println("已到顶部");
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });


    }

    /***
     * 搜索产品
     */
    private void seachProduct() {
        JSONObject params = new JSONObject();
        try {
            params.put("store_id", stote_id);
            params.put("key_word", editSearch.getText().toString().trim());
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
     *
     * @param position
     */
    private void setSelectSortItem(int position) {
        sortAdapter.setCurrentPosition(position);
        getProduct(position);
        sortAdapter.setClick(position);
        rvSort.setAdapter(sortAdapter);
    }

    /**
     * 获取产品
     *
     * @param position
     */
    private void getProduct(int position) {
        currentProductList = getCurrentSortIdProductList(position);
        setSortProductAdapter(currentProductList);
    }

    /**
     * 获取门店下产品
     * @param position
     * @return
     */
    private List<ProductInfo> getCurrentSortIdProductList(int position) {
       ArrayList<ProductInfo> currentSortProductList = new ArrayList<>();
        for (int i = 0; i < productList.size(); i++) {
            if(sortlist.get(position).getProduct_category().equals("所有")){
                currentSortProductList = (ArrayList<ProductInfo>) productList;
                break;
            }
            ProductInfo product = productList.get(i);
            if (sortAdapter.getCurrentSortId()==product.getCategory_id()) {
                currentSortProductList.add(product);
            }
        }
        return currentSortProductList;
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
        stote_id = ((ShopActivity2) getActivity()).getStote_id();
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
                    for(int i=0;i<sortlist.size();i++){
                        if (sortlist.get(i).getProduct_category().equals("所有")){
                            getProductList(sortlist.get(i).getCategory_id()+"");
                        }
                    }
//                    setSelectSortItem(0);

                }

            } else if (msg.what == CREATE_SHOPPINGCAR_CODE) {
                String result = (String) msg.obj;
                if (result != null) {
                    Log.v("创建购物车", result);
                }
                if (result != null && result.contains("error")) {
                    ToastUtils.show(getActivity(), "创建购物车错误");
                    return;
                }
                if (result != null && !result.contains("error")) {
                    CreateShoppingcarResult createShoppingcarResult = GsonUtil.jsonToBean(result, CreateShoppingcarResult.class);

                    if (createShoppingcarResult.getResult().isFlag()) {
                        Intent intent = new Intent(getActivity(), IndexActivity.class);
                        intent.putExtra("page", 1);
                        intent.putExtra("isGotoshopCar", "true");
                        startActivity(intent);
                        getActivity().finish();

                    }
                }

            } else if (msg.what == SHOP_PRODUCT_SEARCH) {
                String result = (String) msg.obj;
                if (result != null) {
                    Log.v("产品搜索", result);
                }
                if (result != null && result.contains("error")) {
                    ToastUtils.show(getActivity(), "创建购物车错误");
                    return;
                }
                GetProductDateResult productDateResult = GsonUtil.jsonToBean(result, GetProductDateResult.class);
                if (productDateResult != null && productDateResult.getResult().isFlag()) {
                    List<ProductInfo> productList = productDateResult.getResult().getRes();
                    setSortProductAdapter(productList);

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
                    setSortProductAdapter(productList);

                }
            }


        }
    };

    private void setSortProductAdapter(List<ProductInfo> productList) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        rvGoods.setLayoutManager(linearLayoutManager);
        rvGoods.addItemDecoration(new DividerDecoration(getActivity()));
        shopsProductAdapter = new SortProductAdapter(getActivity(), productList);
        shopsProductAdapter.setCallback(this);
        rvGoods.setAdapter(shopsProductAdapter);
    }

    private void changeSortView() {
        sortAdapter = new SortAdapter(getActivity(), (ArrayList<SearchSortResult.ResultBean.ResBean>) sortlist);
        sortAdapter.setOnItemClickListener(new MyItemClickListenerInterface() {
            @Override
            public void onItemClick(View view, int position) {
                setSelectSortItem(position);
            }
        });
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        rvSort.setLayoutManager(linearLayoutManager);
        rvSort.addItemDecoration(new DividerDecoration(getActivity()));
        rvSort.setAdapter(sortAdapter);
    }



    /*private void setTotalMoney() {
        total_money = 0;
        total_number = 0;
        for (int i = 0; i < choosedProducts.size(); i++) {
            ProductInfo temp = choosedProducts.get(i);
            total_money = total_money + temp.getPrice() * temp.getAmount();
            total_number = total_number + temp.getAmount();
        }
        tvGoodFittingNum.setText(total_number + "");
        tvTotalMoney.setText("￥" + NumberFormatUtil.formatToDouble2(total_money) + "元");

    }*/

    @Override
    public void onDestroy() {
        super.onDestroy();

    }

    @Override
    public void addAction(View view) {
       /* NXHooldeView nxHooldeView = new NXHooldeView(getActivity());
        int position[] = new int[2];
        view.getLocationInWindow(position);
        nxHooldeView.setStartPosition(new Point(position[0], position[1]));
        ViewGroup rootView = (ViewGroup) getActivity().getWindow().getDecorView();
        rootView.addView(nxHooldeView);
        int endPosition[] = new int[2];
        tvGoodFittingNum.getLocationInWindow(endPosition);
        nxHooldeView.setEndPosition(new Point(endPosition[0], endPosition[1]));
        nxHooldeView.startBeizerAnimation();
        //AnimationUtil.setShakeAnimation(getActivity(), ivGoodsCarNums);*/
        ShopActivity2.instance.addAction(view);

    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (getUserVisibleHint()){
            ShopActivity2.instance.setllShoppingCar("visible");
            System.out.println("visible");
        }else {
            ShopActivity2.instance.setllShoppingCar("gone");
            System.out.println("goneeeeeeee");
        }
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
            sortAdapter.setSortAmount(resBean.getCategory_id(),1);
        } else if (event.equals("sortReduce")) {
            System.out.println("sortreduce");
            sortAdapter.setSortAmount(resBean.getCategory_id(),-1);
        }
    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
    }
}
