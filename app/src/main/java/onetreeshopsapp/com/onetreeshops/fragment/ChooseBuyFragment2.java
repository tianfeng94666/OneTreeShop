package onetreeshopsapp.com.onetreeshops.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.Poi;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;
import onetreeshopsapp.com.onetreeshops.R;
import onetreeshopsapp.com.onetreeshops.activity.IndexActivity;
import onetreeshopsapp.com.onetreeshops.activity.SearchActivity;
import onetreeshopsapp.com.onetreeshops.activity.ShopActivity2;
import onetreeshopsapp.com.onetreeshops.adapter.ListDropDownAdapter;
import onetreeshopsapp.com.onetreeshops.adapter.OrdersInfoAdapter;
import onetreeshopsapp.com.onetreeshops.adapter.ShopsInfodapter;
import onetreeshopsapp.com.onetreeshops.adapter.ShopsNameAdapter;
import onetreeshopsapp.com.onetreeshops.adapter.ShopsProductAdapter;
import onetreeshopsapp.com.onetreeshops.application.LYJApplication;
import onetreeshopsapp.com.onetreeshops.bean.CreateShoppingCar;
import onetreeshopsapp.com.onetreeshops.bean.CreateShoppingcarResult;
import onetreeshopsapp.com.onetreeshops.bean.EventProduct;
import onetreeshopsapp.com.onetreeshops.bean.GetProductDateResult;
import onetreeshopsapp.com.onetreeshops.bean.GetShopsDataResult;
import onetreeshopsapp.com.onetreeshops.bean.ProductInfo;
import onetreeshopsapp.com.onetreeshops.dialog.dmax.SpotsDialog;
import onetreeshopsapp.com.onetreeshops.http.JsonRPCAsyncTask;
import onetreeshopsapp.com.onetreeshops.service.LocationService;
import onetreeshopsapp.com.onetreeshops.utils.Const;
import onetreeshopsapp.com.onetreeshops.utils.GsonUtil;
import onetreeshopsapp.com.onetreeshops.utils.HttpValue;
import onetreeshopsapp.com.onetreeshops.utils.NumberFormatUtil;
import onetreeshopsapp.com.onetreeshops.utils.ToastUtils;
import onetreeshopsapp.com.onetreeshops.utils.view.DropDownMenu;
import onetreeshopsapp.com.onetreeshops.utils.view.XListView;

/**
 * Created by fiona on 2016/9/23.
 */
public class ChooseBuyFragment2 extends Fragment {
    private int GET_SHOPS_DATA = 1;
    private XListView lv_shopname;
    //门店名适配器
    private ShopsInfodapter shopsInfodapter;
    //店铺返回结果
    private GetShopsDataResult getShopsDataResult;
    //店铺集合
    private List<GetShopsDataResult.ResultBean.ResBean> shops1 = null;
    private List<GetShopsDataResult.ResultBean.ResBean> shopsold = null;
    //已点门店产品集合
    HashMap<String, List<ProductInfo>> shopProducts = null;
    private EditText edit_search;
    private int CREATE_SHOPPINGCAR_CODE = 3;
    //定位
    private TextView tv_getlocation;
    private ImageView iv_getlocate;
    public LocationClient mLocationClient = null;
    private LocationService locationService;
    private TextView LocationResult;
    private Button startLocation;
    private SpotsDialog spotsDialog;
    private int page = 1;
    private String longitude, latitude;//用户经纬度
    private DropDownMenu mDropDownMenu;
    private String headers[] = {"综合排序", "距离最近", "销量最高"};
    private ListDropDownAdapter menuAdapter;
    private List<View> popupViews = new ArrayList<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //EventBus.getDefault().register(this);
        // -----------location config ------------
        locationService = ((LYJApplication) getActivity().getApplication()).locationService;
        //获取locationservice实例，建议应用中只初始化1个location实例，然后使用，可以参考其他示例的activity，都是通过此种方式获取locationservice实例的
        locationService.registerListener(mListener);
        //注册监听
        locationService.setLocationOption(locationService.getDefaultLocationClientOption());
        spotsDialog = new SpotsDialog(getActivity(), "定位中。。。");
        spotsDialog.show();
        locationService.start();// 定位SDK


    }

    private void init() {
        shopProducts = ((IndexActivity) getActivity()).getShopProducts();
    }

    @Override
    public void onResume() {
        super.onResume();
        init();
    }

    @Nullable
    @Override
    public void onStart() {
        // TODO Auto-generated method stub
        super.onStart();
        // -----------location config ------------

    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = View.inflate(getActivity(), R.layout.fragment_choose_buy2, null);
        mDropDownMenu = (DropDownMenu) view.findViewById(R.id.dropDownMenu);
        iv_getlocate = (ImageView) view.findViewById(R.id.iv_getlocate);
        tv_getlocation = (TextView) view.findViewById(R.id.tv_getlocation);
        tv_getlocation.requestFocus();
        lv_shopname = new XListView(getActivity());
        lv_shopname.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        initMenu();
        lv_shopname.setPullLoadEnable(true);
        lv_shopname.setPullRefreshEnable(false);
        lv_shopname.setXListViewListener(new XListView.IXListViewListener() {
            @Override
            public void onRefresh() {
            }

            @Override
            public void onLoadMore() {
                page++;
                getShops();

            }
        });
        edit_search = (EditText) view.findViewById(R.id.edit_search);
        edit_search.setFocusable(false);
        edit_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SearchActivity.class);
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.push_left_in,
                        R.anim.push_left_out);
            }
        });
        setOnItemClickListener();
        iv_getlocate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (mDropDownMenu!=null) {
                    if (mDropDownMenu.isShowing()) {
                        mDropDownMenu.closeMenu();
                    }
                }
                    shops1 = null;
                    shopsold = null;
                    spotsDialog = new SpotsDialog(getActivity(),"定位中。。。");
                    spotsDialog.show();
                    locationService.start();// 定位SDK
            }
        });
        ButterKnife.bind(this, view);

        return view;
    }

    private void initMenu() {
        //init  menu 第一个菜单
        final String menu1[] = {"综合排序", "评分最高", "起送价最低"};
        final ListView menuView = new ListView(getActivity());
        menuView.setDividerHeight(0);
        menuAdapter = new ListDropDownAdapter(getActivity(), Arrays.asList(menu1));
        menuView.setAdapter(menuAdapter);
        final TextView textViewDistont = new TextView(getActivity());
        final TextView textViewSalenumber = new TextView(getActivity());
        //
        popupViews.add(menuView);
        popupViews.add(textViewDistont);
        popupViews.add(textViewSalenumber);
        menuView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                menuAdapter.setCheckItem(position);
                mDropDownMenu.setTabText(menu1[position]);
                mDropDownMenu.closeMenu();
            }
        });
        //init dropdownview
        mDropDownMenu.setDropDownMenu(Arrays.asList(headers), popupViews, lv_shopname);

    }


    private void stoponLoad() {
        lv_shopname.stopLoadMore();
    }

    private void setOnItemClickListener() {
        lv_shopname.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String store_id = shopsInfodapter.getItemId(position - 1) + "";
                Intent intent = new Intent(getActivity(), ShopActivity2.class);
                intent.putExtra("stote_id", store_id);
                intent.putExtra("storename",shops1.get(position-1).getStore_name());
                startActivity(intent);

            }
        });
    }

    /**
     * 获取所有门店
     */
    private void getShops() {
        System.out.println("经纬度：" + latitude + "/" + longitude);
        try {
            JSONObject params = new JSONObject();
            params.put("page", page);
            params.put("user_lat", latitude);
            params.put("user_lng", longitude);
            new JsonRPCAsyncTask(getActivity(), mHandler,
                    GET_SHOPS_DATA, HttpValue.getHttp() + Const.SHOPS_DATA, "call",
                    params).execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {

            //门店信息
            if (msg.what == GET_SHOPS_DATA) {
                String result = (String) msg.obj;
                stoponLoad();
                spotsDialog.dismiss();
                if (result == null) {
                    ToastUtils.show(getActivity(), "亲，服务器出问题啦，请稍后再试！");
                    return;
                }
                if (result != null && !result.contains("error")) {
                    Log.v("门店信息", result);
                    getShopsDataResult = GsonUtil.jsonToBean(result, GetShopsDataResult.class);
                    if (getShopsDataResult != null && getShopsDataResult.getResult().isFlag()) {
                       /* shops1 = getShopsDataResult.getResult().getRes();
                        shopsold = new ArrayList<>(shops1);
                        shopsInfodapter = new ShopsInfodapter((ArrayList<GetShopsDataResult.ResultBean.ResBean>) shops1, getActivity());
                        lv_shopname.setAdapter(shopsInfodapter);*/
                        if (shops1 == null) {
                            shops1 = getShopsDataResult.getResult().getRes();
                        } else {
                            shopsold = getShopsDataResult.getResult().getRes();
                            shops1.addAll(shopsold);
                            shopsold = null;
                        }
                        if (shopsInfodapter == null) {
                            shopsInfodapter = new ShopsInfodapter(getActivity());
                            shopsInfodapter.setList((ArrayList<GetShopsDataResult.ResultBean.ResBean>) shops1);
                            lv_shopname.setAdapter(shopsInfodapter);
                        } else {
                            shopsInfodapter.setList((ArrayList<GetShopsDataResult.ResultBean.ResBean>) shops1);
                            shopsInfodapter.notifyDataSetChanged();
                            System.out.println(">>>>>加载1");
                        }
                        if (shops1.size()<10){
                            lv_shopname.setPullLoadEnable(false);
                        }
                    } else {
                        if (shops1 != null && shopsold == null) {
                            ToastUtils.show(getActivity(), "亲，已经到底了...");
                            lv_shopname.setPullLoadEnable(false);
                            page--;
                        } else {
                            ToastUtils.show(getActivity(), getShopsDataResult.getResult().getInfo());
                        }
                    }
                } else {
                    ToastUtils.show(getActivity(), "获取门店信息失败！");
                }
            }
        }

    };


    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (getUserVisibleHint()){

        }else {
            if (mDropDownMenu!=null) {
                if (mDropDownMenu.isShowing()) {
                    mDropDownMenu.closeMenu();
                }
            }
        }
    }


    @Override
    public void onStop() {
        super.onStop();
        if (mDropDownMenu.isShowing()) {
            mDropDownMenu.closeMenu();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
    }
    private BDLocationListener mListener = new BDLocationListener() {

                @Override
                public void onReceiveLocation(BDLocation location) {
                    // TODO Auto-generated method stub
                    if (null != location && location.getLocType() != BDLocation.TypeServerError) {
                        StringBuffer sb = new StringBuffer(256);
                        sb.append("time : ");
                        /**
                         * 时间也可以使用systemClock.elapsedRealtime()方法 获取的是自从开机以来，每次回调的时间；
                         * location.getTime() 是指服务端出本次结果的时间，如果位置不发生变化，则时间不变
                         */
                        sb.append(location.getTime());
                        sb.append("\nerror code : ");
                        sb.append(location.getLocType());
                        sb.append("\nlatitude : ");
                        sb.append(location.getLatitude());
                        sb.append("\nlontitude : ");
                        sb.append(location.getLongitude());
                        sb.append("\nradius : ");
                        sb.append(location.getRadius());
                        sb.append("\nCountryCode : ");
                        sb.append(location.getCountryCode());
                        sb.append("\nCountry : ");
                        sb.append(location.getCountry());
                        sb.append("\ncitycode : ");
                        sb.append(location.getCityCode());
                        sb.append("\ncity : ");
                        sb.append(location.getCity());
                        sb.append("\nDistrict : ");
                        sb.append(location.getDistrict());
                        sb.append("\nStreet : ");
                        sb.append(location.getStreet());
                        sb.append("\naddr : ");
                        sb.append(location.getAddrStr());
                        sb.append("\nDescribe: ");
                        sb.append(location.getLocationDescribe());
                        sb.append("\nDirection(not all devices have value): ");
                        sb.append(location.getDirection());
                        sb.append("\nPoi: ");
                        if (location.getPoiList() != null && !location.getPoiList().isEmpty()) {
                            for (int i = 0; i < location.getPoiList().size(); i++) {
                                Poi poi = (Poi) location.getPoiList().get(i);
                                sb.append(poi.getName() + ";");
                            }
                        }
                        if (location.getLocType() == BDLocation.TypeGpsLocation) {// GPS定位结果
                            sb.append("\nspeed : ");
                            sb.append(location.getSpeed());// 单位：km/h
                            sb.append("\nsatellite : ");
                            sb.append(location.getSatelliteNumber());
                            sb.append("\nheight : ");
                            sb.append(location.getAltitude());// 单位：米
                            sb.append("\ndescribe : ");
                            sb.append("gps定位成功");
                        } else if (location.getLocType() == BDLocation.TypeNetWorkLocation) {// 网络定位结果
                            // 运营商信息
                            sb.append("\noperationers : ");
                            sb.append(location.getOperators());
                            sb.append("\ndescribe : ");
                            sb.append("网络定位成功");
                        } else if (location.getLocType() == BDLocation.TypeOffLineLocation) {// 离线定位结果
                            sb.append("\ndescribe : ");
                            sb.append("离线定位成功，离线定位结果也是有效的");
                        } else if (location.getLocType() == BDLocation.TypeServerError) {
                            sb.append("\ndescribe : ");
                            sb.append("服务端网络定位失败，可以反馈IMEI号和大体定位时间到loc-bugs@baidu.com，会有人追查原因");
                        } else if (location.getLocType() == BDLocation.TypeNetWorkException) {
                            sb.append("\ndescribe : ");
                            sb.append("网络不同导致定位失败，请检查网络是否通畅");
                        } else if (location.getLocType() == BDLocation.TypeCriteriaException) {
                            sb.append("\ndescribe : ");
                            sb.append("无法获取有效定位依据导致定位失败，一般是由于手机的原因，处于飞行模式下一般会造成这种结果，可以试着重启手机");
                        }
                        System.out.println(sb.toString());
                        longitude = location.getLongitude() + "";
                        latitude = location.getLatitude() + "";
                        System.out.println("经纬度：" + latitude + "/" + longitude + "/" + location.getAddrStr());
                        //tv_getlocation.setText(location.getAddrStr());
                        tv_getlocation.setText(location.getCity()+location.getDistrict()+location.getStreet());
                        tv_getlocation.requestFocus();
                        shops1 = null;
                        locationService.stop();
                        page=1;
                        getShops();

                    }
                }

            };
}
