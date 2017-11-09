package onetreeshopsapp.com.onetreeshops.activity;


import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import onetreeshopsapp.com.onetreeshops.R;
import onetreeshopsapp.com.onetreeshops.adapter.FragAdapter;
import onetreeshopsapp.com.onetreeshops.bean.GetProductDateResult;
import onetreeshopsapp.com.onetreeshops.bean.GetShoppingcarData;
import onetreeshopsapp.com.onetreeshops.bean.GetShopsDataResult;
import onetreeshopsapp.com.onetreeshops.bean.ProductInfo;
import onetreeshopsapp.com.onetreeshops.dialog.dmax.SpotsDialog;
import onetreeshopsapp.com.onetreeshops.fragment.ChooseBuyFragment;
import onetreeshopsapp.com.onetreeshops.fragment.ChooseBuyFragment2;
import onetreeshopsapp.com.onetreeshops.fragment.MineFragment;
import onetreeshopsapp.com.onetreeshops.fragment.ShoppingCartFragment;
import onetreeshopsapp.com.onetreeshops.http.JsonParams;
import onetreeshopsapp.com.onetreeshops.http.JsonRPCAsyncTask;
import onetreeshopsapp.com.onetreeshops.utils.Const;
import onetreeshopsapp.com.onetreeshops.utils.GsonUtil;
import onetreeshopsapp.com.onetreeshops.utils.HttpValue;
import onetreeshopsapp.com.onetreeshops.utils.ToastUtils;


/**
 * Created by fiona on 2016/9/23.
 */
public class IndexActivity extends FragmentActivity {
    @BindView(R.id.viewpager)
    ViewPager viewpager;
    @BindView(R.id.tv_main_choosebuy)
    TextView tvMainChoosebuy;
    @BindView(R.id.tv_shoopingcar)
    TextView tv_shoopingcar;
    @BindView(R.id.tv_main_mine)
    TextView tvMainMine;
    @BindView(R.id.ll_main_choosebuy)
    LinearLayout llMainChoosebuy;
    @BindView(R.id.iv_main_choosebuy)
    ImageView iv_main_choosebuy;
    @BindView(R.id.iv_shopping_car)
    ImageView ivShoppingCar;
    @BindView(R.id.iv_main_mine)
    ImageView iv_main_mine;
    @BindView(R.id.tv_product_total_amount)
    TextView tvProductTotalAmount;
    @BindView(R.id.ll_shopping_car)
    LinearLayout llShoppingCar;
    @BindView(R.id.ll_main_mine)
    LinearLayout llMainMine;
    private ShoppingCartFragment shoppingCartFragment;
    private MineFragment mineFragment;
    private ChooseBuyFragment2 chooseBuyFragment;
    private FragAdapter adapter;
    public static IndexActivity instance = null;
    //已点产品
    List<ProductInfo> chooseProducts;
    private int GET_SHOPPINGCAR_CODE = 1;
    private SpotsDialog spotsDialog;
    //已点门店产品集合
    HashMap<String, List<ProductInfo>> shopProducts = new HashMap<>();
    private int setpage;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*requestWindowFeature(Window.FEATURE_NO_TITLE);
        *//*set it to be full screen*//*
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);*/
        instance = this;
        setContentView(R.layout.activtiy_index);
        setbar();
        ButterKnife.bind(this);
        init();
    }

    private void getData() {
        Intent intent = getIntent();
        String isGotoShoppingCar = intent.getStringExtra("isGotoshopCar");
        if(isGotoShoppingCar!=null){
            setpage = intent.getIntExtra("page",1);
            viewpager.setCurrentItem(setpage);
        }
    }

    private void init() {
        //构造适配器
        List<Fragment> fragments = new ArrayList<Fragment>();
        shoppingCartFragment = new ShoppingCartFragment();
        chooseBuyFragment = new ChooseBuyFragment2();
        mineFragment = new MineFragment();
        fragments.add(chooseBuyFragment);
        fragments.add(shoppingCartFragment);
        fragments.add(mineFragment);
        adapter = new FragAdapter(getSupportFragmentManager(), fragments);
        viewpager = (ViewPager) findViewById(R.id.viewpager);
        viewpager.setAdapter(adapter);
        viewpager.setCurrentItem(0);
        viewpager.setOffscreenPageLimit(2);
        viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        chooseBuyFragment.onResume();
                        iv_main_choosebuy.setImageResource(R.mipmap.choosecelect);
                        ivShoppingCar.setImageResource(R.mipmap.shoppingcartno);
                        iv_main_mine.setImageResource(R.mipmap.mine_select_no);
                        tvMainChoosebuy.setTextColor(getResources().getColor(R.color.maincolor));
                        tv_shoopingcar.setTextColor(getResources().getColor(R.color.black));
                        tvMainMine.setTextColor(getResources().getColor(R.color.black));
                        tvProductTotalAmount = (TextView) findViewById(R.id.tv_product_total_amount);
                        if (!TextUtils.isEmpty(tvProductTotalAmount.getText().toString().trim())) {
                            tvProductTotalAmount.setVisibility(View.VISIBLE);
                        } else {
                            tvProductTotalAmount.setVisibility(View.GONE);
                        }
                        //findViewById(R.id.tv_product_total_amount).setVisibility(View.VISIBLE);
                        break;
                    case 1:
                        getShoppingcarProduct();
                        spotsDialog = new SpotsDialog(IndexActivity.this, R.style.SpotsDialogDefault);
                        spotsDialog.show();
                        iv_main_choosebuy.setImageResource(R.mipmap.chooseselect_no);
                        ivShoppingCar.setImageResource(R.mipmap.shoppingcar2);
                        iv_main_mine.setImageResource(R.mipmap.mine_select_no);
                        tvMainChoosebuy.setTextColor(getResources().getColor(R.color.black));
                        tv_shoopingcar.setTextColor(getResources().getColor(R.color.maincolor));
                        tvMainMine.setTextColor(getResources().getColor(R.color.black));

                        findViewById(R.id.tv_product_total_amount).setVisibility(View.INVISIBLE);
                        break;
                    case 2:
                        //chooseBuyFragment.saveCurrentShop();//旧界面
                        iv_main_choosebuy.setImageResource(R.mipmap.chooseselect_no);
                        ivShoppingCar.setImageResource(R.mipmap.shoppingcartno);
                        iv_main_mine.setImageResource(R.mipmap.mine_select);
                        tvMainChoosebuy.setTextColor(getResources().getColor(R.color.black));
                        tv_shoopingcar.setTextColor(getResources().getColor(R.color.black));
                        tvMainMine.setTextColor(getResources().getColor(R.color.maincolor));
                        if (!TextUtils.isEmpty(tvProductTotalAmount.getText().toString().trim())) {
                            tvProductTotalAmount.setVisibility(View.VISIBLE);
                        } else {
                            tvProductTotalAmount.setVisibility(View.GONE);
                        }
                        break;
                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        getData();
    }

    private void getShoppingcarProduct() {
        try {
            JSONObject params = new JSONObject();

            params.put("pos_session_id", HttpValue.SP_USERID);
            new JsonRPCAsyncTask(
                    this, mHandler, GET_SHOPPINGCAR_CODE,
                    HttpValue.getHttp() + Const.GET_SHOPPINGCAR, "call", params
            ).execute();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {

            //购物车数据
            if (msg.what == GET_SHOPPINGCAR_CODE) {
                String result = (String) msg.obj;
                if (result==null){
                    ToastUtils.show(IndexActivity.this,"亲，服务器出问题啦，请稍后再试！");
                    return;
                }
                if (result != null && !result.contains("error")) {
                    Log.v("购物车数据", result);
                    GetShoppingcarData getShoppingcarData
                            = GsonUtil.jsonToBean(result, GetShoppingcarData.class);
                    if (getShoppingcarData != null) {
                        chooseProducts = getShoppingcarData.getResult().getRes().getLines();

                        //chooseBuyFragment.saveCurrentShop();旧界面
//                        if(chooseProducts!=null){
                        shoppingCartFragment.setChooseProducts(chooseProducts);
//                        }
                        spotsDialog.dismiss();
                    }
                } else {
                    ToastUtils.show(IndexActivity.this, "获取购物车信息失败！");
                }
            }
        }


    };

    @OnClick({R.id.ll_main_choosebuy, R.id.ll_shopping_car, R.id.ll_main_mine})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_main_choosebuy:
                viewpager.setCurrentItem(0);
                break;
            case R.id.ll_shopping_car:
                viewpager.setCurrentItem(1);
                break;
            case R.id.ll_main_mine:
                viewpager.setCurrentItem(2);
                break;
        }
    }

    public List<ProductInfo> getChooseProducts() {
        return chooseProducts;
    }

    public void setChooseProducts(List<ProductInfo> chooseProducts) {
        this.chooseProducts = chooseProducts;
    }

    public HashMap<String, List<ProductInfo>> getShopProducts() {
        return shopProducts;
    }

    public void setShopProducts(HashMap<String, List<ProductInfo>> shopProducts) {
        this.shopProducts = shopProducts;
    }

    public void clearShopProducts() {
        shopProducts.clear();
    }
    //监听返回键
    private long mExitTime;
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getRepeatCount() == 0) {
            if ((System.currentTimeMillis() - mExitTime) > 2000) {
                Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
                mExitTime = System.currentTimeMillis();
            } else {
                finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
    private void setbar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window window = getWindow();
            ViewGroup rootView;
            View statusView;
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            ViewGroup viewGroup = (ViewGroup) findViewById(android.R.id.content);
            rootView = (ViewGroup) viewGroup.getChildAt(0);
            rootView.setFitsSystemWindows(false);
            rootView.setClipToPadding(true);
            // 绘制一个和状态栏一样高的矩形
            int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
            int statusBarHeight = getResources().getDimensionPixelSize(resourceId);
            statusView = new View(this);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, statusBarHeight);
            statusView.setLayoutParams(params);
            Drawable drawable = rootView.getChildAt(0).getBackground();
            if (drawable instanceof ColorDrawable) {
                statusView.setBackgroundColor(((ColorDrawable) drawable).getColor());
            } else {
                statusView.setBackgroundColor(Color.parseColor("#009943"));
            }
            rootView.addView(statusView, 0);
        }
    }
}
