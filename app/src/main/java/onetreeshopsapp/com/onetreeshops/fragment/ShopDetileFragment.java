package onetreeshopsapp.com.onetreeshops.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import onetreeshopsapp.com.onetreeshops.R;
import onetreeshopsapp.com.onetreeshops.adapter.recycleAdapter.ShopEvaluateAdapter;
import onetreeshopsapp.com.onetreeshops.bean.GetShopEvaluateResult;
import onetreeshopsapp.com.onetreeshops.bean.MyAddresRecord;
import onetreeshopsapp.com.onetreeshops.http.JsonRPCAsyncTask;
import onetreeshopsapp.com.onetreeshops.utils.Const;
import onetreeshopsapp.com.onetreeshops.utils.GsonUtil;
import onetreeshopsapp.com.onetreeshops.utils.HttpValue;
import onetreeshopsapp.com.onetreeshops.utils.ToastUtils;
import onetreeshopsapp.com.onetreeshops.utils.UIProgressUtil;
import onetreeshopsapp.com.onetreeshops.utils.view.DividerDecoration;
import onetreeshopsapp.com.onetreeshops.utils.view.XListView;

/**
 * Created by ERP on 2016/10/10.
 */
public class ShopDetileFragment extends Fragment {
    @BindView(R.id.tv_shopdetile_telphone)
    TextView tvShopdetileTelphone;
    @BindView(R.id.tv_shopdetile_addres)
    TextView tvShopdetileAddres;
    @BindView(R.id.tv_shopdetile_peisongtime)
    TextView tvShopdetilePeisongtime;
    @BindView(R.id.rv_null)
    RecyclerView rvNull;
    private XListView lv_evaluate_orders;
    private boolean flag = false;
    private int page = 1;
    private List<GetShopEvaluateResult.ResultBean.ResBean.DataBean> resBeanList;
    private ShopEvaluateAdapter shopEvaluateAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_shop_detile, null);
        ButterKnife.bind(this, v);
        initView(v);
        return v;
    }

    private void initView(View v) {
        setRecycleviewAdapter();

    }
    private void setRecycleviewAdapter() {

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        rvNull.setLayoutManager(linearLayoutManager);
        rvNull.addItemDecoration(new DividerDecoration(getActivity()));
        if(resBeanList==null){
            resBeanList = new ArrayList<>();
        }
        shopEvaluateAdapter = new ShopEvaluateAdapter(getActivity(),resBeanList);
        rvNull.setAdapter(shopEvaluateAdapter);

    }
    private void getorders(int page) {
        try {
            JSONObject params = new JSONObject();
            params.put("page", page);
            new JsonRPCAsyncTask(getActivity(), mHandler,
                    1, HttpValue.getHttp() + Const.SHIFT_RECORD, "call",
                    params).execute();
        } catch (JSONException e) {
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
                System.out.println("记录返回数据为：" + result);
                if (GsonUtil.isError(result)) {
                    ToastUtils.show(getActivity(), "查询我的订单出错");
                    return;
                } else {
                    lv_evaluate_orders.stopRefresh(false);
                    lv_evaluate_orders.stopLoadMore();
                }
                MyAddresRecord myAddresRecord = GsonUtil.jsonToBean(result, MyAddresRecord.class);
                if (myAddresRecord.getResult().getFlag()) {
                    if (myAddresRecord.getResult().getRes().size() > 0) {

                    } else {

                    }
                }

            }
        }
    };

}
