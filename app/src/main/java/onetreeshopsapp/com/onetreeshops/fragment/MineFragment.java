package onetreeshopsapp.com.onetreeshops.fragment;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.image.ImageOptions;
import org.xutils.x;

import butterknife.ButterKnife;

import onetreeshopsapp.com.onetreeshops.R;
import onetreeshopsapp.com.onetreeshops.activity.AllOrdersActivity;
import onetreeshopsapp.com.onetreeshops.activity.CompleteOrdersActivity;
import onetreeshopsapp.com.onetreeshops.activity.EvaluateActivity;
import onetreeshopsapp.com.onetreeshops.activity.MyAddresActivity;
import onetreeshopsapp.com.onetreeshops.activity.MyEditActivity;
import onetreeshopsapp.com.onetreeshops.activity.MySetActivity;
import onetreeshopsapp.com.onetreeshops.activity.ToEvaluateOrdersActivity;
import onetreeshopsapp.com.onetreeshops.activity.ToPayOrdersActivity;
import onetreeshopsapp.com.onetreeshops.adapter.OrdersInfoAdapter;
import onetreeshopsapp.com.onetreeshops.bean.CancelOrderResult;
import onetreeshopsapp.com.onetreeshops.bean.DelectOrderResult;
import onetreeshopsapp.com.onetreeshops.bean.GetOrdersResult;
import onetreeshopsapp.com.onetreeshops.bean.UserManager;
import onetreeshopsapp.com.onetreeshops.http.JsonRPCAsyncTask;
import onetreeshopsapp.com.onetreeshops.utils.GsonUtil;
import onetreeshopsapp.com.onetreeshops.utils.HttpValue;
import onetreeshopsapp.com.onetreeshops.utils.NetWorkUtils;
import onetreeshopsapp.com.onetreeshops.utils.ToastUtils;
import onetreeshopsapp.com.onetreeshops.utils.UIProgressUtil;
import onetreeshopsapp.com.onetreeshops.utils.view.CircleImageView;
import onetreeshopsapp.com.onetreeshops.utils.Const;
import onetreeshopsapp.com.onetreeshops.utils.IntentUtils;
import onetreeshopsapp.com.onetreeshops.utils.SharedPreferencesUtils;

/**
 * Created by fiona on 2016/9/23.
 */
public class MineFragment extends Fragment implements View.OnClickListener{
    private CircleImageView my_img;
    private TextView user_phone,tv_topay_amount;
    private LinearLayout ll_mine_orders,ll_mine_receipt_address,ll_mine_set,ll_topay_orders,ll_complete_orders,ll_toevaluate_orders;
    private SharedPreferences sp;
    private static byte[] imageAsBytes;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = View.inflate(getActivity(), R.layout.fragment_mine, null);
        sp = getActivity().getSharedPreferences("SP", Context.MODE_PRIVATE);
        init(view);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        /*if (my_img.getDrawable() != null) {
            Glide.with(getActivity())
                    .load(HttpValue.getHttp() + "/pos/binary/image?model=res.users&id=" + HttpValue.SP_USERID + "&field=image")
                    .into(my_img);
           // getInfo(getActivity());
        }*/
        getuserinfo();
        getorders();
    }

    private void init(View view) {
        tv_topay_amount = (TextView) view.findViewById(R.id.tv_topay_amount);
        my_img = (CircleImageView) view.findViewById(R.id.my_img);
        my_img.setOnClickListener(this);
        ll_topay_orders = (LinearLayout) view.findViewById(R.id.ll_topay_orders);
        ll_topay_orders.setOnClickListener(this);
        ll_complete_orders = (LinearLayout) view.findViewById(R.id.ll_complete_orders);
        ll_complete_orders.setOnClickListener(this);
        ll_toevaluate_orders = (LinearLayout) view.findViewById(R.id.ll_toevaluate_orders);
        ll_toevaluate_orders.setOnClickListener(this);
        ll_mine_receipt_address = (LinearLayout) view.findViewById(R.id.ll_mine_receipt_address);
        ll_mine_receipt_address.setOnClickListener(this);
        ll_mine_orders = (LinearLayout) view.findViewById(R.id.ll_mine_orders);
        ll_mine_orders.setOnClickListener(this);
        ll_mine_set = (LinearLayout) view.findViewById(R.id.ll_mine_set);
        ll_mine_set.setOnClickListener(this);
        user_phone = (TextView) view.findViewById(R.id.user_phone);
        // 设置保存的登陆名
        String phone = (null == SharedPreferencesUtils.readObjFromSp(sp,
                Const.SP_USERNAME) ? "" : ""
                + SharedPreferencesUtils.readObjFromSp(sp, Const.SP_USERNAME));
        user_phone.setText(phone);
        user_phone.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.my_img:
                IntentUtils.getIntentUtils().intent(getActivity(), MyEditActivity.class);
                break;
            case R.id.ll_mine_receipt_address:
                IntentUtils.getIntentUtils().intent(getActivity(), MyAddresActivity.class);
                break;
            case R.id.ll_mine_orders:
                IntentUtils.getIntentUtils().intent(getActivity(), AllOrdersActivity.class);
                break;
            case R.id.ll_mine_set:
                IntentUtils.getIntentUtils().intent(getActivity(), MySetActivity.class);
                break;
            case R.id.user_phone:
                IntentUtils.getIntentUtils().intent(getActivity(), MyEditActivity.class);
                break;
            case R.id.ll_complete_orders:
                IntentUtils.getIntentUtils().intent(getActivity(), CompleteOrdersActivity.class);
                break;
            case R.id.ll_topay_orders:
                IntentUtils.getIntentUtils().intent(getActivity(), ToPayOrdersActivity.class);
                break;
            case R.id.ll_toevaluate_orders:
                IntentUtils.getIntentUtils().intent(getActivity(), ToEvaluateOrdersActivity.class);
                break;
        }
    }
    private void setMy_img(String url){
        Log.v("url",HttpValue.getHttp()+url);
        Picasso
                .with(getActivity())
                .load(HttpValue.getHttp()+url)
                .fit()
                .error(R.mipmap.icon)
                .into(my_img, new Callback() {
                    @Override
                    public void onSuccess() {
                        System.out.println("1111111");
                    }
                    @Override
                    public void onError() {
                        System.out.println("22222222");
                    }
                });

    }

    /**
     * 获取本地图片
     */
    public  byte[] getInfo(Context context) {
        SharedPreferences sp = context.getSharedPreferences("photo", Context.MODE_PRIVATE);
        String picture = (String) sp.getString("btm", "");
        imageAsBytes = Base64.decode(picture.getBytes(), Base64.DEFAULT);
        if (imageAsBytes.length == 0) {
            my_img.setImageResource(R.mipmap.mine_icon_person);
        } else {
            my_img.setImageBitmap(BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length));
        }
        return imageAsBytes;
    }
    private void getuserinfo() {
        if (!NetWorkUtils.getNetConnecState(getActivity())) {
            Toast.makeText(getActivity(), "请检查网络是否连接！", Toast.LENGTH_SHORT).show();
            return;
        }
        try {
            JSONObject params = new JSONObject();
            params.put("user_id",HttpValue.SP_USERID);
            new JsonRPCAsyncTask(getActivity(), mHandler,
                    Const.USER_INFO_CODE, HttpValue.getHttp() + Const.USER_INFO, "call",
                    params).execute();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    //查询订单
    public void getorders() {
        if (!NetWorkUtils.getNetConnecState(getActivity())) {
            Toast.makeText(getActivity(), "请检查网络是否连接！", Toast.LENGTH_SHORT).show();
            return;
        }
        try {
            JSONObject params = new JSONObject();
            params.put("uesr_id", HttpValue.SP_USERID);
            params.put("pay_state", "待付款");
            params.put("page",1);
            new JsonRPCAsyncTask(getActivity(), mHandler,
                    Const.SEARCH_ALLORDERS_CODE, HttpValue.getHttp() + Const.SEARCH_ALLORDERS, "call",
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
            if (msg.what == Const.SEARCH_ALLORDERS_CODE) {//查询全部订单信息
                System.out.println("查询全部订单返回数据为：" + result);
                if (GsonUtil.isError(result)) {
                    return;
                }
                GetOrdersResult getOrdersResult = GsonUtil.jsonToBean(result,GetOrdersResult.class);
                if(getOrdersResult!=null&& getOrdersResult.getResult()!=null){
                    if(getOrdersResult.getResult().isFlag()){
                        tv_topay_amount.setVisibility(View.VISIBLE);
                        tv_topay_amount.setText(getOrdersResult.getResult().getTotal()+"");
                    }else {
                        tv_topay_amount.setVisibility(View.GONE);
                        }
                    }else {
                    tv_topay_amount.setVisibility(View.GONE);
                }
                }else if (msg.what == Const.USER_INFO_CODE) {
                System.out.println("查询用户返回数据为：" + result);
                if (GsonUtil.isError(result)) {
                    return;
                }
                UserManager userManager = GsonUtil.jsonToBean(result,UserManager.class);
                if (userManager.getResult().getRes()!=null&&userManager.getResult().getFlag()){
                    setMy_img(userManager.getResult().getRes().getImage());
                }else {
                    my_img.setImageResource(R.mipmap.mine_icon_person);
                }
            }
            }
    };
}
