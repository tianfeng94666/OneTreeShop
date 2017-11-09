package onetreeshopsapp.com.onetreeshops.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import org.json.JSONException;
import org.json.JSONObject;

import onetreeshopsapp.com.onetreeshops.R;
import onetreeshopsapp.com.onetreeshops.bean.MyAddresRecord;
import onetreeshopsapp.com.onetreeshops.http.JsonRPCAsyncTask;
import onetreeshopsapp.com.onetreeshops.utils.Const;
import onetreeshopsapp.com.onetreeshops.utils.GsonUtil;
import onetreeshopsapp.com.onetreeshops.utils.HttpValue;
import onetreeshopsapp.com.onetreeshops.utils.ToastUtils;
import onetreeshopsapp.com.onetreeshops.utils.UIProgressUtil;
import onetreeshopsapp.com.onetreeshops.utils.view.XListView;

/**
 * Created by ERP on 2016/10/10.
 */
public class AllMyOrdersFragment extends Fragment{
    private XListView lv_all_orders;
    private boolean flag = false;
    private int page = 1;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_search_allorders, null);
        initView(v);
        return v;
    }

    private void initView(View v) {
        lv_all_orders = (XListView) v.findViewById(R.id.lv_all_orders);
        lv_all_orders.setPullLoadEnable(true);
        lv_all_orders.setPullRefreshEnable(true);
        lv_all_orders.setXListViewListener(new XListView.IXListViewListener() {
            @Override
            public void onRefresh() {
                flag=false;
                ToastUtils.show(getActivity(), "已经刷新！");
                if (page>=2){
                    getorders(--page);
                }else{
                    lv_all_orders.stopRefresh(false);
                }
            }
            @Override
            public void onLoadMore() {
                flag=true;
                getorders(++page);
            }
        });
    }
    private void getorders(int page){
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
        public void handleMessage(android.os.Message msg) {
            String result = (String) msg.obj;
            UIProgressUtil.cancelProgress();
            if (result==null){
                ToastUtils.show(getActivity(),"亲，服务器出问题啦，请稍后再试！");
                return;
            }
            if (msg.what == 1) {
                System.out.println("记录返回数据为：" + result);
                if (GsonUtil.isError(result)) {
                    ToastUtils.show(getActivity(), "查询我的订单出错");
                    return;
                }else {
                    lv_all_orders.stopRefresh(false);
                    lv_all_orders.stopLoadMore();
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
