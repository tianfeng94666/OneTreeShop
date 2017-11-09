package onetreeshopsapp.com.onetreeshops.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
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
import java.util.HashMap;
import java.util.List;

import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;
import onetreeshopsapp.com.onetreeshops.R;
import onetreeshopsapp.com.onetreeshops.activity.IndexActivity;
import onetreeshopsapp.com.onetreeshops.activity.SearchActivity;
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

/**
 * Created by fiona on 2016/9/23.
 */
public class ChooseBuyFragment extends Fragment {
    TextView tv_config;
    private int GET_SHOPS_DATA = 1;
    private ListView lv_shopname;
    private ListView lv_products;
    //门店名适配器
    private ShopsNameAdapter shopsNameAdapter;
    //产品适配器
    private ShopsProductAdapter shopsProductAdapter;
    private int GET_SHOPS_PRODUCT = 2;
    //已选产品集合
    List<ProductInfo> chooseProducts = new ArrayList<>();
    //店铺返回结果
    private GetShopsDataResult getShopsDataResult;
    //店铺集合
    private List<GetShopsDataResult.ResultBean.ResBean> shops1;
    private List<GetShopsDataResult.ResultBean.ResBean> shopsold;
    //已点门店产品集合
    HashMap<String, List<ProductInfo>> shopProducts =null;
    //产品集合
    private List<ProductInfo> products;
    //购物车中产品的总数量
    private int product_total_amount;
    private TextView tv_product_total_amount;
    //购物车中产品总金额
    private TextView tv_total_money;
    private double total_money;
    //购物车布局
    private LinearLayout ll_shopping_car;
    private EditText edit_search;
    private int CREATE_SHOPPINGCAR_CODE = 3;
    //定位
    private TextView tv_location;
    private ImageView iv_locate;
    public LocationClient mLocationClient = null;
    private LocationService locationService;
    private TextView LocationResult;
    private Button startLocation;
    private SpotsDialog spotsDialog;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        getShops();

    }

    private void init() {
        shopProducts = ((IndexActivity)getActivity()).getShopProducts();
//        if(shopsold!=null){
//            shops1 = new ArrayList<>(shopsold);
//            shopsNameAdapter = new ShopsNameAdapter((ArrayList<GetShopsDataResult.ResultBean.ResBean>) shops1, getActivity());
//            shopsNameAdapter.setCurrentPosition(0);
//            lv_shopname.setAdapter(shopsNameAdapter);
//        }
    }

    @Override
    public void onResume() {
        super.onResume();
        init();
    }

    @Nullable
    @Override
    public  void onStart() {
        // TODO Auto-generated method stub
        super.onStart();
        // -----------location config ------------
        locationService = ((LYJApplication) getActivity().getApplication()).locationService;
        //获取locationservice实例，建议应用中只初始化1个location实例，然后使用，可以参考其他示例的activity，都是通过此种方式获取locationservice实例的
        locationService.registerListener(mListener);
        //注册监听
            locationService.setLocationOption(locationService.getDefaultLocationClientOption());

    }
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = View.inflate(getActivity(), R.layout.fragment_choose_buy, null);
        iv_locate = (ImageView) view.findViewById(R.id.iv_locate);
        tv_location = (TextView) view.findViewById(R.id.tv_location);
        lv_shopname = (ListView) view.findViewById(R.id.lv_shopname);
        lv_products = (ListView) view.findViewById(R.id.lv_goods);
        tv_product_total_amount = (TextView) getActivity().findViewById(R.id.tv_product_total_amount);
        tv_total_money = (TextView) view.findViewById(R.id.tv_total_money);
        ll_shopping_car = (LinearLayout) view.findViewById(R.id.ll_shopping_car);
        tv_config = (TextView) view.findViewById(R.id.tv_config);
        tv_config.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                commitToShoppingCar();
            }
        });
        edit_search = (EditText) view.findViewById(R.id.edit_search);
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
        iv_locate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    locationService.start();// 定位SDK
            }
        });
        ButterKnife.bind(this, view);
        return view;
    }

    /**
     * 去结算
     */
    private void gotoShoppingCar() {
        ((ViewPager) getActivity().findViewById(R.id.viewpager)).setCurrentItem(1);
    }

    /**
     * 传递数据到购物车中
     */
    private void commitToShoppingCar() {
        try {
            spotsDialog = new SpotsDialog(getActivity(), R.style.SpotsDialogDefault);
            spotsDialog.show();
            CreateShoppingCar createShoppingCar = new CreateShoppingCar();
            CreateShoppingCar.Shopping shopping = new CreateShoppingCar.Shopping();
            shopping.setPos_session_id(HttpValue.SP_USERID);
            shopping.setLines(chooseProducts);
            List<CreateShoppingCar.Shopping> shoppings = new ArrayList<>();
            shoppings.add(shopping);
            createShoppingCar.setShopping(shoppings);
            JSONObject params = new JSONObject(GsonUtil.beanToJson(createShoppingCar));
            new JsonRPCAsyncTask(getActivity(), mHandler,
                    CREATE_SHOPPINGCAR_CODE, HttpValue.getHttp() + Const.CREATE_SHOPPINGCAR, "call",
                    params).execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void setOnItemClickListener() {
        lv_shopname.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int store_id = (int) shopsNameAdapter.getItemId(position);
                int old_store_id = (int) shopsNameAdapter.getItemId(shopsNameAdapter.getCurrentPosition());

                //删除原有的产品单
                if (shopProducts.containsKey(old_store_id + "")) {
                    shopProducts.remove(old_store_id + "");
                }
                //保存之前的产品数据
                shopProducts.put(old_store_id + "", shopsProductAdapter.getShops());
                //判断缓存中是否有老数据
                if (shopProducts.containsKey(store_id + "")) {
                    //取出就数据
                    products = shopProducts.get(store_id + "");
                    changeProductView();
                } else {
                    //请求新的
                    getProduct(store_id);
                }
                changeShopsView(shopsNameAdapter.getCurrentPosition());
                shopsNameAdapter.setCurrentPosition(position);
            }
        });
    }

    /**
     * 获取门店产品
     *
     * @param id
     */
    private void getProduct(int id) {
        try {
            Log.v("shop_id", id + "");
            JSONObject params = new JSONObject();
            params.put("store_id", id);
            new JsonRPCAsyncTask(getActivity(), mHandler,
                    GET_SHOPS_PRODUCT, HttpValue.getHttp() + Const.SHOPS_PRODUCT, "call",
                    params).execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取所有门店
     */
    private void getShops() {
        try {
            JSONObject params = new JSONObject();
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
                if(result!=null){
                    Log.v("门店信息", result);
                }

                if (result != null && !result.contains("error")) {
                    getShopsDataResult = GsonUtil.jsonToBean(result, GetShopsDataResult.class);
                    if (getShopsDataResult != null) {
                        shops1 = getShopsDataResult.getResult().getRes();
                        shopsold = new ArrayList<>(shops1);
                        changeShopsView(0);
                        spotsDialog = new SpotsDialog(getActivity(),"加载中。。。");
                        spotsDialog.show();
                        getProduct((int) shopsNameAdapter.getItemId(0));
                        shopsNameAdapter.setCurrentPosition(0);
                    }
                } else {
                    ToastUtils.show(getActivity(), "获取门店信息失败！");
                }
            } else if (msg.what == GET_SHOPS_PRODUCT) {
                String result = (String) msg.obj;
                if (result != null && !result.contains("error")) {
                    Log.v("门店产品", result);
                    spotsDialog.dismiss();
                    GetProductDateResult getProductDateResult = GsonUtil.jsonToBean(result, GetProductDateResult.class);
                    products = getProductDateResult.getResult().getRes();
                    changeProductView();
                } else {
                    ToastUtils.show(getActivity(), "获取门店信息失败！");
                }
            }else if(msg.what == CREATE_SHOPPINGCAR_CODE){
                String result = (String) msg.obj;
                if(result!=null){
                    Log.v("创建购物车", result);
                }
                if (result != null && result.contains("error")) {
                    ToastUtils.show(getActivity(), "创建购物车错误");
                    return;
                }
                if (result != null && !result.contains("error")) {
                  CreateShoppingcarResult createShoppingcarResult = GsonUtil.jsonToBean(result, CreateShoppingcarResult.class);
                    spotsDialog.dismiss();
                   if(createShoppingcarResult.getResult().isFlag()){
                       shopProducts.clear();
                       chooseProducts.clear();
                       clearChooseProduct();
                       getShops();
                       setTotalMoney();
                       gotoShoppingCar();

                   }


                } else {
                    ToastUtils.show(getActivity(), "获取门店信息失败！");
                }
            }
        }


    };
    /**
     *  清除记录
     */
    private void clearChooseProduct() {
        ((IndexActivity)getActivity()).clearShopProducts();

    }
    /**
     * 刷新产品的list
     */
    private void changeProductView() {
        shopsProductAdapter = new ShopsProductAdapter(products, getActivity());
        lv_products.setAdapter(shopsProductAdapter);
    }

    /**
     * 接收处理数量的改变
     *
     * @param send
     */
    public void onEventMainThread(EventProduct send) {
        String event = send.getEventname();
        ProductInfo resBean = send.getData();
        if (event.equals("add")) {
            addChooseProducts(resBean);
        } else if (event.equals("reduce")) {
            reduceChooseProducts(resBean);
        } else if (event.equals("shoppingCarAdd")) {
            addChooseProducts(resBean);
            changeShopProductAmount(resBean, false);
        } else if (event.equals("shoppingCarReduce")) {
            reduceChooseProducts(resBean);
            changeShopProductAmount(resBean, true);
        }
        changeShopProductAmout();
    }

    /**
     * 修改对应门店产品数量，isReduce为真就是减少，假就是增加
     *
     * @param resBean
     * @param isReduce
     */
    private void changeShopProductAmount(ProductInfo resBean, boolean isReduce) {
        int store_id = resBean.getStore_id();
        if (shopProducts.containsKey(store_id + "")) {
            //取出就数据
            List<ProductInfo> goods = shopProducts.get(store_id + "");
            for (int i = 0; i < goods.size(); i++) {
                ProductInfo temp = goods.get(i);
                if (temp.getProduct_id() == resBean.getProduct_id()) {
                    int amount = temp.getAmount();
                    if (isReduce) {
                        temp.setAmount(--amount);
                    } else {
                        temp.setAmount(++amount);
                    }
                }
            }

            GetShopsDataResult.ResultBean.ResBean currentShop = (GetShopsDataResult.ResultBean.ResBean)
                    shopsNameAdapter.getItem(shopsNameAdapter.getCurrentPosition());
            if (currentShop.getStore_id() == resBean.getStore_id()) {
                products = goods;
            }
            changeProductView();
        }
    }


    private void addChooseProducts(ProductInfo resBean) {
        boolean isHave = false;
        for (int i = 0; i < chooseProducts.size(); i++) {
            ProductInfo temp = chooseProducts.get(i);
            if (temp.getProduct_id() == resBean.getProduct_id()) {
                int amount = temp.getAmount();
                temp.setAmount(amount);
                isHave = true;
            }
        }
        if (isHave == false) {
            chooseProducts.add(resBean);
        }
    }

    private void reduceChooseProducts(ProductInfo resBean) {
        for (int i = 0; i < chooseProducts.size(); i++) {
            ProductInfo temp = chooseProducts.get(i);
            if (temp.getProduct_id() == resBean.getProduct_id()) {
                int amount = temp.getAmount();
                temp.setAmount(amount);
                if (amount == 0) {
                    chooseProducts.remove(i);
                }
            }
        }

    }


    /**
     * 修改对应门店中选中产品的数量
     */
    private void changeShopProductAmout() {
        if (shops1.size() > 0) {
            product_total_amount = 0;
            for (int j = 0; j < shops1.size(); j++) {
                GetShopsDataResult.ResultBean.ResBean resBean = shops1.get(j);
                int amount = 0;
                for (int i = 0; i < chooseProducts.size(); i++) {
                    ProductInfo resBean1 = chooseProducts.get(i);
                    if (resBean.getStore_id() == resBean1.getStore_id()) {
                        amount = amount + resBean1.getAmount();
                    }
                }
                product_total_amount = product_total_amount + amount;
                resBean.setOrder_num(amount);
            }
        }
//        tv_product_total_amount.setVisibility(View.VISIBLE);
//        tv_product_total_amount.setText(product_total_amount + "");
        setTotalMoney();
        changeShopsView(shopsNameAdapter.getCurrentPosition());

    }

    private void setTotalMoney() {
        total_money = 0;
        for (int i = 0; i < chooseProducts.size(); i++) {
            ProductInfo temp = chooseProducts.get(i);
            total_money = total_money + temp.getPrice() * temp.getAmount();
        }
        tv_total_money.setText("￥" + NumberFormatUtil.formatToDouble2(total_money) + "元");
        if (chooseProducts.size() == 0) {
            ll_shopping_car.setVisibility(View.GONE);
        } else {
            ll_shopping_car.setVisibility(View.VISIBLE);
        }

    }

    /**
     * 刷新适配器
     */
    private void changeShopsView(int oldposition) {
        shopsNameAdapter = new ShopsNameAdapter((ArrayList<GetShopsDataResult.ResultBean.ResBean>) shops1, getActivity());
        shopsNameAdapter.setCurrentPosition(oldposition);
        lv_shopname.setAdapter(shopsNameAdapter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    public List<ProductInfo> getChooseProducts() {
        return chooseProducts;
    }

    public void setChooseProducts(List<ProductInfo> chooseProducts) {
        this.chooseProducts = chooseProducts;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
    }

    public void saveCurrentShop() {
        if (shopsNameAdapter != null&&shopProducts!=null) {
            int old_store_id = (int) shopsNameAdapter.getItemId(shopsNameAdapter.getCurrentPosition());
            //删除原有的产品单
            if (shopProducts.containsKey(old_store_id + "")) {
                shopProducts.remove(old_store_id + "");
            }
            //保存之前的产品数据
            shopProducts.put(old_store_id + "", shopsProductAdapter.getShops());
            //判断缓存中是否有老数据
        }
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
                sb.append("\nlocType : ");// 定位类型
                sb.append(location.getLocType());
                sb.append("\nlocType description : ");// *****对应的定位类型说明*****
                //sb.append(location.getLocTypeDescription());
                sb.append("\nlatitude : ");// 纬度
                sb.append(location.getLatitude());
                sb.append("\nlontitude : ");// 经度
                sb.append(location.getLongitude());
//                sb.append("\nradius : ");// 半径
//                sb.append(location.getRadius());
//                sb.append("\nCountryCode : ");// 国家码
//                sb.append(location.getCountryCode());
//                sb.append("\nCountry : ");// 国家名称
//                sb.append(location.getCountry());
//                sb.append("\ncitycode : ");// 城市编码
//                sb.append(location.getCityCode());
                sb.append("\ncity : ");// 城市
                sb.append(location.getCity());
                sb.append("\nDistrict : ");// 区
                sb.append(location.getDistrict());
                sb.append("\nStreet : ");// 街道
                sb.append(location.getStreet());
                sb.append("\naddr : ");// 地址信息
                sb.append(location.getAddrStr());
                sb.append("\nUserIndoorState: ");// *****返回用户室内外判断结果*****
                //sb.append(location.getUserIndoorState());
                sb.append("\nDirection(not all devices have value): ");
                sb.append(location.getDirection());// 方向
                sb.append("\nlocationdescribe: ");
                sb.append(location.getLocationDescribe());// 位置语义化信息
                sb.append("\nPoi: ");// POI信息
                if (location.getPoiList() != null && !location.getPoiList().isEmpty()) {
                    for (int i = 0; i < location.getPoiList().size(); i++) {
                        Poi poi = (Poi) location.getPoiList().get(i);
                        sb.append(poi.getName() + ";");
                    }
                }
                if (location.getLocType() == BDLocation.TypeGpsLocation) {// GPS定位结果
                    sb.append("\nspeed : ");
                    sb.append(location.getSpeed());// 速度 单位：km/h
                    sb.append("\nsatellite : ");
                    sb.append(location.getSatelliteNumber());// 卫星数目
                    sb.append("\nheight : ");
                    sb.append(location.getAltitude());// 海拔高度 单位：米
                    sb.append("\ngps status : ");
                    //sb.append(location.getGpsAccuracyStatus());// *****gps质量判断*****
                    sb.append("\ndescribe : ");
                    sb.append("gps定位成功");
                } else if (location.getLocType() == BDLocation.TypeNetWorkLocation) {// 网络定位结果
                    // 运营商信息
                    if (location.hasAltitude()) {// *****如果有海拔高度*****
                        sb.append("\nheight : ");
                        sb.append(location.getAltitude());// 单位：米
                    }
                    sb.append("\noperationers : ");// 运营商信息
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
               tv_location.setText(location.getAddrStr());
                locationService.stop();
            }
        }

    };
}
