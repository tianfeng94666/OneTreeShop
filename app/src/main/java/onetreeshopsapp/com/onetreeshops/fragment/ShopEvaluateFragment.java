package onetreeshopsapp.com.onetreeshops.fragment;

import android.app.ActionBar;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RatingBar;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import onetreeshopsapp.com.onetreeshops.R;
import onetreeshopsapp.com.onetreeshops.activity.ShopActivity2;
import onetreeshopsapp.com.onetreeshops.adapter.recycleAdapter.ShopEvaluateAdapter;
import onetreeshopsapp.com.onetreeshops.bean.GetShopEvaluateResult;
import onetreeshopsapp.com.onetreeshops.bean.GetSingglShopInfoResult;
import onetreeshopsapp.com.onetreeshops.http.JsonRPCAsyncTask;
import onetreeshopsapp.com.onetreeshops.utils.Const;
import onetreeshopsapp.com.onetreeshops.utils.GsonUtil;
import onetreeshopsapp.com.onetreeshops.utils.HttpValue;
import onetreeshopsapp.com.onetreeshops.utils.ToastUtils;
import onetreeshopsapp.com.onetreeshops.utils.UIProgressUtil;
import onetreeshopsapp.com.onetreeshops.utils.view.DividerDecoration;

import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

/**
 * Created by ERP on 2016/10/10.
 */
public class ShopEvaluateFragment extends Fragment {
    @BindView(R.id.tv_shop_evaluate_scoc)
    TextView tv_shop_evaluate_scoc;
    @BindView(R.id.rb_peisong_pingfen)
    RatingBar rbPeisongPingfen;
    @BindView(R.id.tv_peigsong_fenshu)
    TextView tvPeigsongFenshu;
    @BindView(R.id.rb_shop_pingfen)
    RatingBar rbShopPingfen;
    @BindView(R.id.tv_shop_fenshu)
    TextView tvShopFenshu;
    @BindView(R.id.rv_pingjia_cont)
    RecyclerView rvPingjiaCont;
    @BindView(R.id.iv_pingjia_nodata)
    ImageView iv_pingjia_nodata;
    @BindView(R.id.tv_pingjia_nodata)
    TextView tv_pingjia_nodata;
    @BindView(R.id.rb_all)
    RadioButton rbAll;
    @BindView(R.id.rb_good)
    RadioButton rbGood;
    @BindView(R.id.rb_mid)
    RadioButton rbMid;
    @BindView(R.id.rb_bad)
    RadioButton rbBad;
    private ShopEvaluateAdapter shopEvaluateAdapter;
    private List<GetShopEvaluateResult.ResultBean.ResBean.DataBean> resBeanList;
    private String stote_id;
    private float pro_score,service_score,score;
    private int GET_SHOP_INFO = 2;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_shop_evaluate, container, false);
        ButterKnife.bind(this, v);
        initView(v);
        return v;
    }
    private void getCommonData() {
        stote_id = ((ShopActivity2) getActivity()).getStote_id();
        getdata("all");
        getShopScroeInfo();
    }


    private void initView(View v) {
        rbAll.setChecked(true);
        getCommonData();
    }

    private void getdata(String sig) {
        try {
            JSONObject params = new JSONObject();
            params.put("store_id", stote_id);
            params.put("sig",sig);
            new JsonRPCAsyncTask(getActivity(), mHandler,
                    1, HttpValue.getHttp() + Const.SHOP_EVALUATE, "call",
                    params).execute();
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
    private void getShopScroeInfo() {
        try {
            JSONObject params = new JSONObject();
            params.put("store_id", stote_id + "");
            new JsonRPCAsyncTask(getActivity(), mHandler,
                    GET_SHOP_INFO, HttpValue.getHttp() + Const.SINGLE_SHOPS_DATA, "call",
                    params).execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            String result = (String) msg.obj;
            UIProgressUtil.cancelProgress();
            if (result == null) {
                ToastUtils.show(getActivity(), "亲，服务器出问题啦，请稍后再试！");
                return;
            }
            if (msg.what == 1) {
                System.out.println("记录返回评论数据为：" + result);
                if (GsonUtil.isError(result)) {
                    ToastUtils.show(getActivity(), "查询商家评价出错");
                    return;
                }
                GetShopEvaluateResult getShopEvaluateResult = GsonUtil.jsonToBean(result,GetShopEvaluateResult.class);
                if (getShopEvaluateResult!=null&&getShopEvaluateResult.getResult().isFlag()) {
                    if (getShopEvaluateResult.getResult().getRes().getData().size() > 0) {
                          rvPingjiaCont.setVisibility(View.VISIBLE);
                          iv_pingjia_nodata.setVisibility(View.GONE);
                          tv_pingjia_nodata.setVisibility(View.GONE);
                          resBeanList = getShopEvaluateResult.getResult().getRes().getData();
                          rbAll.setText("全部("+getShopEvaluateResult.getResult().getRes().getAll_num()+")");
                          rbGood.setText("好评("+getShopEvaluateResult.getResult().getRes().getGood_num()+")");
                          rbMid.setText("中评("+getShopEvaluateResult.getResult().getRes().getCommon_num()+")");
                         rbBad.setText("差评("+getShopEvaluateResult.getResult().getRes().getBad_num()+")");
                          setRecycleviewAdapter();
                    } else {
                        setview();
                        setRecycleviewAdapter();
                    }
                }else {
                    setview();
                    setRecycleviewAdapter();
                }

            }else if(msg.what == GET_SHOP_INFO){
                if (result != null) {
                    Log.v("门店信息", result);
                }
                if (result != null && result.contains("error")) {
                    ToastUtils.show(getActivity(), "获取门店信息错误");
                    return;
                }
                if (result!=null){
                    GetSingglShopInfoResult getshopresult = GsonUtil.jsonToBean(result,GetSingglShopInfoResult.class);
                    if (getshopresult.getResult().isFlag()){
                        pro_score = (float) getshopresult.getResult().getRes().getPro_score();
                        service_score = (float) getshopresult.getResult().getRes().getService_score();
                        score = (float) getshopresult.getResult().getRes().getScore();
                        System.out.println(""+pro_score+service_score+score);
                        setdata();
                    }
                }
            }
        }
    };
    private void setdata(){
        tv_shop_evaluate_scoc.setText(""+score);
        tvPeigsongFenshu.setText(""+service_score);
        rbPeisongPingfen.setRating(service_score);
        tvShopFenshu.setText(""+pro_score);
        rbShopPingfen.setRating(pro_score);
    }
    private void setview(){
        if (resBeanList!=null) {
            resBeanList.clear();
        }
      ViewGroup.LayoutParams params = rvPingjiaCont.getLayoutParams();
        params.width = ViewGroup.LayoutParams.MATCH_PARENT;
        params.height = ViewGroup.LayoutParams.MATCH_PARENT;
        rvPingjiaCont.setLayoutParams(params);
        iv_pingjia_nodata.setVisibility(View.VISIBLE);
        tv_pingjia_nodata.setVisibility(View.VISIBLE);
    }

    private void setRecycleviewAdapter() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        rvPingjiaCont.setLayoutManager(linearLayoutManager);
        rvPingjiaCont.addItemDecoration(new DividerDecoration(getActivity()));
        if(resBeanList==null||resBeanList.size()==0){
            resBeanList = new ArrayList<>();
        }
        shopEvaluateAdapter = new ShopEvaluateAdapter(getActivity(),resBeanList);
        rvPingjiaCont.setAdapter(shopEvaluateAdapter);

    }

    @OnClick({R.id.rb_all, R.id.rb_good, R.id.rb_mid, R.id.rb_bad})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rb_all:
                rbGood.setChecked(false);
                rbMid.setChecked(false);
                rbBad.setChecked(false);
                getdata("all");
                rbAll.setTextColor(getResources().getColor(R.color.white));
                rbGood.setTextColor(getResources().getColor(R.color.dark_gray));
                rbMid.setTextColor(getResources().getColor(R.color.dark_gray));
                rbBad.setTextColor(getResources().getColor(R.color.dark_gray));
                break;
            case R.id.rb_good:
                rbAll.setChecked(false);
                rbMid.setChecked(false);
                rbBad.setChecked(false);
                getdata("good");
                rbAll.setTextColor(getResources().getColor(R.color.dark_gray));
                rbGood.setTextColor(getResources().getColor(R.color.white));
                rbMid.setTextColor(getResources().getColor(R.color.dark_gray));
                rbBad.setTextColor(getResources().getColor(R.color.dark_gray));
                break;
            case R.id.rb_mid:
                rbGood.setChecked(false);
                rbAll.setChecked(false);
                rbBad.setChecked(false);
                getdata("common");
                rbAll.setTextColor(getResources().getColor(R.color.dark_gray));
                rbGood.setTextColor(getResources().getColor(R.color.dark_gray));
                rbMid.setTextColor(getResources().getColor(R.color.white));
                rbBad.setTextColor(getResources().getColor(R.color.dark_gray));
                break;
            case R.id.rb_bad:
                rbGood.setChecked(false);
                rbMid.setChecked(false);
                rbAll.setChecked(false);
                getdata("bad");
                rbAll.setTextColor(getResources().getColor(R.color.dark_gray));
                rbGood.setTextColor(getResources().getColor(R.color.dark_gray));
                rbMid.setTextColor(getResources().getColor(R.color.dark_gray));
                rbBad.setTextColor(getResources().getColor(R.color.white));
                break;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
    }
}
