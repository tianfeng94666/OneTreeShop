package onetreeshopsapp.com.onetreeshops.activity;


import android.annotation.TargetApi;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;
import onetreeshopsapp.com.onetreeshops.R;
import onetreeshopsapp.com.onetreeshops.bean.CreateShoppingCar;
import onetreeshopsapp.com.onetreeshops.bean.CreateShoppingcarResult;
import onetreeshopsapp.com.onetreeshops.bean.EventProduct;
import onetreeshopsapp.com.onetreeshops.bean.GetShopsDataResult;
import onetreeshopsapp.com.onetreeshops.bean.GetSingglShopInfoResult;
import onetreeshopsapp.com.onetreeshops.bean.ProductInfo;
import onetreeshopsapp.com.onetreeshops.fragment.ProductFragment;
import onetreeshopsapp.com.onetreeshops.fragment.ShopDetileFragment;
import onetreeshopsapp.com.onetreeshops.fragment.ShopEvaluateFragment;
import onetreeshopsapp.com.onetreeshops.http.JsonRPCAsyncTask;
import onetreeshopsapp.com.onetreeshops.utils.Const;
import onetreeshopsapp.com.onetreeshops.utils.GsonUtil;
import onetreeshopsapp.com.onetreeshops.utils.HttpValue;
import onetreeshopsapp.com.onetreeshops.utils.NumberFormatUtil;
import onetreeshopsapp.com.onetreeshops.utils.ToastUtils;
import onetreeshopsapp.com.onetreeshops.utils.view.NXHooldeView;


/**
 * Created by 田丰 on 2016/12/9.
 */

public class ShopActivity2 extends AppCompatActivity implements View.OnClickListener {
    @BindView(R.id.header)
    ImageView header;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tabs)
    TabLayout tabs;
    @BindView(R.id.collapse_toolbar)
    CollapsingToolbarLayout collapseToolbar;
    @BindView(R.id.viewpager)
    ViewPager viewpager;
    @BindView(R.id.tv_good_fitting_num)
    TextView tvGoodFittingNum;
    @BindView(R.id.tv_total_money)
    TextView tvTotalMoney;
    @BindView(R.id.tv_config)
    TextView tvConfig;
    @BindView(R.id.ll_shopping_car)
    LinearLayout llShoppingCar;
    @BindView(R.id.iv_goods_car_nums)
    ImageView ivGoodsCarNums;
    @BindView(R.id.rl_goods_fits_car)
    RelativeLayout rlGoodsFitsCar;
    @BindView(R.id.appbar_layout)
    AppBarLayout appbarLayout;
    private String stote_id;
    private double total_money = 0;
    private int total_number = 0;
    public static ShopActivity2 instance = null;
    private int CREATE_SHOPPINGCAR_CODE = 3;
    private List<ProductInfo> choosedgoods;
    private List<ProductInfo> choosedProducts = new ArrayList<>();
    private String storename = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        instance = this;
        setContentView(R.layout.activity_shop2);
        ButterKnife.bind(this);
        setupToolbar();
        setupViewPager();
        setupCollapsingToolbar();
        initview();
        getData();
    }

    private void initview() {
        tvConfig.setText("30起送");
        header.getBackground().setAlpha(100);
        tvConfig.setClickable(false);

    }

    private void getData() {
        stote_id = getIntent().getStringExtra("stote_id");
        storename = getIntent().getStringExtra("storename");


    }




    private void setupCollapsingToolbar() {
        CollapsingToolbarLayout collapsingToolbar = (CollapsingToolbarLayout) findViewById(
                R.id.collapse_toolbar);


    }

    private void setupViewPager() {
        final ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        tvConfig.setOnClickListener(this);

    }

    private void setupToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        appbarLayout.addOnOffsetChangedListener(new AppBarStateChangeListener() {
            @TargetApi(Build.VERSION_CODES.M)
            @Override
            public void onStateChanged(AppBarLayout appBarLayout, State state) {
                if( state == State.EXPANDED ) {
                    //展开状态
                    toolbar.setBackgroundColor(toolbar.getResources().getColor(R.color.transparent));
                }else if(state == State.COLLAPSED){
                    toolbar.setBackgroundColor(toolbar.getResources().getColor(R.color.maincolor));
                    //折叠状态

                }else {
                    toolbar.setBackgroundColor(toolbar.getResources().getColor(R.color.translucent));
                    //中间状态

                }
            }
        });
        //使用CollapsingToolbarLayout必须把title设置到CollapsingToolbarLayout上，设置到Toolbar上则不会显示
        CollapsingToolbarLayout mCollapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapse_toolbar);
        mCollapsingToolbarLayout.setTitle(storename);
        //通过CollapsingToolbarLayout修改字体颜色
        mCollapsingToolbarLayout.setExpandedTitleColor(Color.WHITE);//设置还没收缩时状态下字体颜色
        mCollapsingToolbarLayout.setCollapsedTitleTextColor(Color.WHITE);//设置收缩后Toolbar上字体的颜色
        //toolbar navigationicon 改变返回按钮颜色
        final Drawable upArrow = getResources().getDrawable(R.mipmap.back);
//        upArrow.setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);

    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFrag(new ProductFragment(), "商品");
        adapter.addFrag(new ShopEvaluateFragment(), "评价");
        adapter.addFrag(new ShopDetileFragment(), "详情");
        viewPager.setOffscreenPageLimit(2);
        viewPager.setAdapter(adapter);
    }

    static class ViewPagerAdapter extends FragmentPagerAdapter {

        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFrag(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_config:
                String goodsnumber = tvGoodFittingNum.getText().toString().trim();
                if (goodsnumber.equals("0")) {
                    ToastUtils.show(this, "请先选择要购买的产品！");
                } else {
                    commitToShoppingCar(choosedgoods);
                }
                break;

        }

    }

    public String getStote_id() {
        return stote_id;
    }

    public void setStote_id(String stote_id) {
        this.stote_id = stote_id;
    }
    public void setTotalMoney(List<ProductInfo> choosedProducts) {
        total_money = 0;
        total_number = 0;
        for (int i = 0; i < choosedProducts.size(); i++) {
            ProductInfo temp = choosedProducts.get(i);
            total_money = total_money + temp.getPrice() * temp.getAmount();
            total_number = total_number + temp.getAmount();
        }
        tvGoodFittingNum.setText(total_number + "");
        System.out.println("数量为：" + total_number);
        tvTotalMoney.setText("￥" + NumberFormatUtil.formatToDouble2(total_money) + "元");
        choosedgoods = choosedProducts;
        if (NumberFormatUtil.formatToDouble2(total_money)>=30){
            tvConfig.setText("选好了");
            tvConfig.setClickable(true);
        }else if (NumberFormatUtil.formatToDouble2(total_money)>0&&NumberFormatUtil.formatToDouble2(total_money)<30){
            tvConfig.setText("还差"+(30-NumberFormatUtil.formatToDouble2(total_money)));
            tvConfig.setClickable(false);
        }else {
            tvConfig.setText("30起送");
            tvConfig.setClickable(false);
        }
    }

    public void addAction(View view) {
        NXHooldeView nxHooldeView = new NXHooldeView(this);
        int position[] = new int[2];
        view.getLocationInWindow(position);
        nxHooldeView.setStartPosition(new Point(position[0], position[1]));
        ViewGroup rootView = (ViewGroup) getWindow().getDecorView();
        rootView.addView(nxHooldeView);
        int endPosition[] = new int[2];
        tvGoodFittingNum.getLocationInWindow(endPosition);
        nxHooldeView.setEndPosition(new Point(endPosition[0], endPosition[1]));
        nxHooldeView.startBeizerAnimation();
    }

    /**
     * 传递数据到购物车中
     */
    private void commitToShoppingCar(List<ProductInfo> choosedProducts) {
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
     * 接收处理数量的改变
     *
     * @param send
     */
    public void onEventMainThread(EventProduct send) {
        String event = send.getEventname();
        ProductInfo resBean = send.getData();
        if (event.equals("sortAdd")) {
            changeChooseList(resBean, "sortAdd");
        } else if (event.equals("sortReduce")) {
            changeChooseList(resBean, "sortReduce");
        }
        this.setTotalMoney(choosedProducts);
    }

    private void changeChooseList(ProductInfo resBean, String st) {
        boolean isHave = false;
        for (int i = 0; i < choosedProducts.size(); i++) {
            ProductInfo productInfo = choosedProducts.get(i);
            if (productInfo.getProduct_id() == resBean.getProduct_id()) {
                isHave = true;
                int amount = resBean.getAmount();
                System.out.println("product_amount=" + resBean.getAmount());
                System.out.println("amount=" + amount);
                if (st.equals("sortAdd")) {
                    productInfo.setAmount(amount);
                    System.out.println("amount++=" + amount);
                } else {
                    productInfo.setAmount(amount);
                    if (amount == 0) {
                        choosedProducts.remove(productInfo);
                    }
                }

            }
        }

        if (!isHave) {
            System.out.println("1product_amount=" + resBean.getAmount());
            choosedProducts.add(resBean);
            System.out.println("resBean=" + resBean.getAmount());
        }

    }

    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == CREATE_SHOPPINGCAR_CODE) {
                String result = (String) msg.obj;
                if (result != null) {
                    Log.v("创建购物车", result);
                }
                if (result != null && result.contains("error")) {
                    ToastUtils.show(ShopActivity2.this, "创建购物车错误");
                    return;
                }
                if (result != null && !result.contains("error")) {
                    CreateShoppingcarResult createShoppingcarResult = GsonUtil.jsonToBean(result, CreateShoppingcarResult.class);

                    if (createShoppingcarResult.getResult().isFlag()) {
                        Intent intent = new Intent(ShopActivity2.this, IndexActivity.class);
                        intent.putExtra("page", 1);
                        intent.putExtra("isGotoshopCar", "true");
                        startActivity(intent);
                        finish();

                    }
                }

            }

        }
    };


    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);

    }

    public void setllShoppingCar(String visibility) {
        if (visibility.equals("gone")) {
            llShoppingCar.setVisibility(View.GONE);
        } else {
            llShoppingCar.setVisibility(View.VISIBLE);
        }
    }

    public abstract static class AppBarStateChangeListener implements AppBarLayout.OnOffsetChangedListener {

        public enum State {
            EXPANDED,
            COLLAPSED,
            IDLE
        }

        private State mCurrentState = State.IDLE;

        @Override
        public final void onOffsetChanged(AppBarLayout appBarLayout, int i) {
            if (i == 0) {
                if (mCurrentState != State.EXPANDED) {
                    onStateChanged(appBarLayout, State.EXPANDED);
                }
                mCurrentState = State.EXPANDED;
            } else if (Math.abs(i) >= appBarLayout.getTotalScrollRange()) {
                if (mCurrentState != State.COLLAPSED) {
                    onStateChanged(appBarLayout, State.COLLAPSED);
                }
                mCurrentState = State.COLLAPSED;
            } else {
                if (mCurrentState != State.IDLE) {
                    onStateChanged(appBarLayout, State.IDLE);
                }
                mCurrentState = State.IDLE;
            }
        }

        public abstract void onStateChanged(AppBarLayout appBarLayout, State state);
    }
}
