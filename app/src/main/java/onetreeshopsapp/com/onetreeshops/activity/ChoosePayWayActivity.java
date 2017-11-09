package onetreeshopsapp.com.onetreeshops.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import onetreeshopsapp.com.onetreeshops.R;
import onetreeshopsapp.com.onetreeshops.base.BaseActivity;
import onetreeshopsapp.com.onetreeshops.bean.CreateOrderResult;
import onetreeshopsapp.com.onetreeshops.bean.WXinfo;
import onetreeshopsapp.com.onetreeshops.http.JsonRPCAsyncTask;
import onetreeshopsapp.com.onetreeshops.pay.WXPayInfo;
import onetreeshopsapp.com.onetreeshops.pay.WXPayUtil;
import onetreeshopsapp.com.onetreeshops.utils.Const;
import onetreeshopsapp.com.onetreeshops.utils.GsonUtil;
import onetreeshopsapp.com.onetreeshops.utils.HttpValue;
import onetreeshopsapp.com.onetreeshops.utils.ToastUtils;

/**
 * Created by fiona on 2016/10/22.
 */
public class ChoosePayWayActivity extends BaseActivity {
    @BindView(R.id.iv_alipay_icon)
    ImageView ivAlipayIcon;
    @BindView(R.id.tv_alipay_des)
    TextView tvAlipayDes;
    @BindView(R.id.tv_alipay)
    TextView tvAlipay;
    @BindView(R.id.rel_alipay)
    RelativeLayout relAlipay;
    @BindView(R.id.iv_wx_icon)
    ImageView ivWxIcon;
    @BindView(R.id.tv_wx_des)
    TextView tvWxDes;
    @BindView(R.id.tv_wx)
    TextView tvWx;
    @BindView(R.id.rel_wx)
    RelativeLayout relWx;
    @BindView(R.id.iv_title_left)
    ImageView ivTitleLeft;
    private CreateOrderResult createOrderResult;
    private int WEPAYCODE = 1;
    private String sumMoney;
    private String sig;
    private String orderNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choosepayway);
        ButterKnife.bind(this);
        getData();
    }

    private void getData() {
        sig = getIntent().getStringExtra("sig");
        if (sig.equals("configorderactivity")) {
//            createOrderResult = (CreateOrderResult) getIntent().getSerializableExtra("orderNumbers");
            orderNumber = (String) getIntent().getStringExtra("orderNumber");
            sumMoney = (String) getIntent().getSerializableExtra("sumMoney");
        } else if (sig.equals("topayorderactivity")) {
            orderNumber = (String)getIntent().getSerializableExtra("orderNumbers");
            sumMoney  = (String) getIntent().getSerializableExtra("sumMoney");

        }
    }

    @OnClick({R.id.rel_alipay, R.id.rel_wx,R.id.iv_title_left})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rel_alipay:
                ToastUtils.show(this, "支付宝支付");
                break;
            case R.id.rel_wx:
                final IWXAPI msgApi = WXAPIFactory.createWXAPI(ChoosePayWayActivity.this, null);
                // 将该app注册到微信
                msgApi.registerApp("wxf5c5898d9588568b");
                ToastUtils.show(this, "微信支付");
                if (sig.equals("configorderactivity")) {
                    weiPay(orderNumber);
                } else if (sig.equals("topayorderactivity")) {
                    weiPay(orderNumber);
                }

                break;
            case R.id.iv_title_left:
                finish();
                break;
        }
    }

    private void weiPay(String orderNumber) {
        try {
            JSONObject params = new JSONObject();
//            params.put("payway", "wechart");
            params.put("out_trade_no", orderNumber);
            params.put("total_fee", Double.parseDouble(sumMoney));
            params.put("spbill_create_ip", "196.168.1.1");
            new JsonRPCAsyncTask(
                    this, mHandler, WEPAYCODE,
                    HttpValue.getHttp() + Const.WE_PAY, "call", params
            ).execute();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * 返回客户端ip
     */

    public String getLocalIpAddress() {
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements(); ) {
                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress()) {
                        return inetAddress.getHostAddress().toString();
                    }
                }
            }
        } catch (SocketException ex) {
            Log.v("WifiPreference IpAddress", ex.toString());
        }
        return null;
    }

    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            String result = (String) msg.obj;
            if (result == null) {
                ToastUtils.show(ChoosePayWayActivity.this, "亲，服务器出问题啦，请稍后再试！");
                return;
            }
            if (msg.what == WEPAYCODE) {
                try {
                    Log.i("tag", result + "");
                    if (result != null) {
                        Log.v("微信支付", result);
                        WXinfo wXinfo = GsonUtil.jsonToBean(result, WXinfo.class);
                        WXinfo.ResultBean result1 = wXinfo.getResult();
                        if (wXinfo != null) {
                            WXinfo.ResultBean.ReturnDataBean dataBean = result1.getReturn_data();
                            WXPayInfo info = new WXPayInfo();
                            info.prepayId = dataBean.getPrepayid();
                            info.nonceStr = dataBean.getNoncestr();
                            info.sign = dataBean.getSign();
                            info.timeStamp = dataBean.getTimestamp() + "";
                            WXPayInfo.APP_ID = dataBean.getAppid();
                            info.PARTNER_ID = dataBean.getPartnerid();
                            WXPayUtil pay = new WXPayUtil(ChoosePayWayActivity.this, WXPayInfo.APP_ID);
                            pay.pay(info);
                        } else {
                            ToastUtils.show(ChoosePayWayActivity.this, "错误！");
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }


            }
        }
    };

}
