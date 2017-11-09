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
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import onetreeshopsapp.com.onetreeshops.R;
import onetreeshopsapp.com.onetreeshops.adapter.FragAdapter;
import onetreeshopsapp.com.onetreeshops.fragment.AllMyOrdersFragment;
import onetreeshopsapp.com.onetreeshops.fragment.EvaluateOrdersFragment;
import onetreeshopsapp.com.onetreeshops.utils.IntentUtils;


/**
 * Created by fiona on 2016/9/23.
 */
public class MyOrdersActivity extends FragmentActivity {
    @BindView(R.id.myorders_back_imgbtn)
    ImageButton myorders_back_imgbtn;
    @BindView(R.id.vw_title_allorders)
    View vw_title_allorders;
    @BindView(R.id.vw_titile_evaluate)
    View vw_titile_evaluate;
    @BindView(R.id.viewpager_orders)
    ViewPager viewPager;
    @BindView(R.id.tv_title_allorders)
    TextView tvTitleallorders;
    @BindView(R.id.tv_titile_evaluate)
    TextView tvTitileevaluate;
    private FragAdapter fragAdapter;
    private int currentpager;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_mine_orders);
        ButterKnife.bind(this);
        initView();
    }


    public void initView() {
        List<Fragment> fragments = new ArrayList<>();
        AllMyOrdersFragment allMyOrdersFragment = new AllMyOrdersFragment();
        EvaluateOrdersFragment evaluateOrdersFragment = new EvaluateOrdersFragment();
        fragments.add(allMyOrdersFragment);
        fragments.add(evaluateOrdersFragment);
        fragAdapter = new FragAdapter(getSupportFragmentManager(), fragments);
        viewPager.setAdapter(fragAdapter);
        setpagerselect0();
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
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
    }
    private void setpagerselect0(){
        tvTitleallorders.setBackgroundResource(R.color.white);
        tvTitileevaluate.setBackgroundResource(R.color.white);
        vw_title_allorders.setVisibility(View.VISIBLE);
        vw_titile_evaluate.setVisibility(View.GONE);
    }
    private void setpagerselect1(){
        tvTitleallorders.setBackgroundResource(R.color.white);
        tvTitileevaluate.setBackgroundResource(R.color.white);
        vw_titile_evaluate.setVisibility(View.VISIBLE);
        vw_title_allorders.setVisibility(View.GONE);
    }
    @OnClick({R.id.tv_title_allorders, R.id.tv_titile_evaluate,R.id.myorders_back_imgbtn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_title_allorders:
                viewPager.setCurrentItem(0);
                break;
            case R.id.tv_titile_evaluate:
                viewPager.setCurrentItem(1);
                break;
            case R.id.myorders_back_imgbtn:
                //IntentUtils.getIntentUtils().intent(MyOrdersActivity.this, IndexActivity.class);
                this.finish();
                break;
        }
    }

    //监听返回键
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getRepeatCount() == 0) {
            IntentUtils.getIntentUtils().intent(MyOrdersActivity.this, IndexActivity.class);
            this.finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
