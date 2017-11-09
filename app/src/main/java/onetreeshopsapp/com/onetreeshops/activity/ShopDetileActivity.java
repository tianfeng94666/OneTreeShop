package onetreeshopsapp.com.onetreeshops.activity;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;
import onetreeshopsapp.com.onetreeshops.R;
import onetreeshopsapp.com.onetreeshops.adapter.FragAdapter;
import onetreeshopsapp.com.onetreeshops.fragment.ShopProductFragment;
import onetreeshopsapp.com.onetreeshops.fragment.ShopEvaluateFragment;


/**
 * Created by fiona on 2016/9/23.
 */
public class ShopDetileActivity extends FragmentActivity {
    @BindView(R.id.shopdetile_back_imgbtn)
    ImageButton shopdetile_back_imgbtn;
    @BindView(R.id.shop_detile_shopname)
    TextView shop_detile_shopname;
    @BindView(R.id.edit_search)
    EditText edit_search;
    @BindView(R.id.vw_title_shop_product)
    View vw_title_shop_product;
    @BindView(R.id.vw_titile_shop_evaluate)
    View vw_titile_shop_evaluate;
    @BindView(R.id.shop_detile_viewpager)
    ViewPager shop_detile_viewpager;
    @BindView(R.id.tv_title_shop_product)
    TextView tv_title_shop_product;
    @BindView(R.id.tv_titile_shop_evaluate)
    TextView tv_titile_shop_evaluate;
    private FragAdapter fragAdapter;
    private int currentpager;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_shop_detile);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        initView();
    }


    public void initView() {
        List<Fragment> fragments = new ArrayList<>();
        ShopProductFragment shopProductFragment = new ShopProductFragment();
        ShopEvaluateFragment shopEvaluateFragment = new ShopEvaluateFragment();
        fragments.add(shopProductFragment);
        fragments.add(shopEvaluateFragment);
        fragAdapter = new FragAdapter(getSupportFragmentManager(), fragments);
        shop_detile_viewpager.setAdapter(fragAdapter);
        setpagerselect0();
        shop_detile_viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                currentpager = position;
            }

            @Override
            public void onPageSelected(int position) {
                switch (position){
                    case 0 :
                        setpagerselect0();
                        break;
                    case 1 :
                        setpagerselect1();
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        edit_search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEND || (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
                    //do something;
                    setpagerselect0();

                    return true;
                }
                return false;
            }
        });
    }
    private void setpagerselect0(){
        tv_title_shop_product.setBackgroundResource(R.color.white);
        tv_titile_shop_evaluate.setBackgroundResource(R.color.white);
        vw_title_shop_product.setVisibility(View.VISIBLE);
        vw_titile_shop_evaluate.setVisibility(View.GONE);
    }
    private void setpagerselect1(){
        tv_title_shop_product.setBackgroundResource(R.color.white);
        tv_titile_shop_evaluate.setBackgroundResource(R.color.white);
        vw_title_shop_product.setVisibility(View.GONE);
        vw_titile_shop_evaluate.setVisibility(View.VISIBLE);
    }
    @OnClick({R.id.tv_title_shop_product, R.id.tv_titile_shop_evaluate,R.id.shopdetile_back_imgbtn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_title_shop_product:
                shop_detile_viewpager.setCurrentItem(0);
                break;
            case R.id.tv_titile_shop_evaluate:
                shop_detile_viewpager.setCurrentItem(1);
                break;
            case R.id.shopdetile_back_imgbtn:
                this.finish();
                break;
        }
    }


}
