package onetreeshopsapp.com.onetreeshops.fragment;


import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;
import onetreeshopsapp.com.onetreeshops.R;
import onetreeshopsapp.com.onetreeshops.activity.ConfigOrderActivity;
import onetreeshopsapp.com.onetreeshops.activity.IndexActivity;
import onetreeshopsapp.com.onetreeshops.adapter.ShoppingCarAdapter;
import onetreeshopsapp.com.onetreeshops.adapter.ShoppingCarShopsAdapter;
import onetreeshopsapp.com.onetreeshops.bean.CreateShoppingCar;
import onetreeshopsapp.com.onetreeshops.bean.CreateShoppingcarResult;
import onetreeshopsapp.com.onetreeshops.bean.GetShoppingcarData;
import onetreeshopsapp.com.onetreeshops.bean.OrderProduct;
import onetreeshopsapp.com.onetreeshops.bean.Orders;
import onetreeshopsapp.com.onetreeshops.bean.ProductInfo;
import onetreeshopsapp.com.onetreeshops.http.JsonRPCAsyncTask;
import onetreeshopsapp.com.onetreeshops.utils.Const;
import onetreeshopsapp.com.onetreeshops.utils.GsonUtil;
import onetreeshopsapp.com.onetreeshops.utils.HttpValue;
import onetreeshopsapp.com.onetreeshops.utils.NumberFormatUtil;
import onetreeshopsapp.com.onetreeshops.utils.ToastUtils;

/**
 * Created by fiona on 2016/9/23.
 */
public class ShoppingCartFragment extends Fragment implements View.OnClickListener {
    private TextView tvShoppingcar;
    private TextView tvShoppingcarProductAmount, tvShoppingcarSumMoney;
    private RelativeLayout llHead;
    private LinearLayout ll_blance;
    private ListView lvShooppingCartProdut;
    private TextView tvDetele, tvEdite;
    private RelativeLayout rlDetele;
    private List<ProductInfo> chooseProducts;
    private ShoppingCarAdapter shoppingCarAdapter;
    private boolean isEdit = false;
    private TextView tv_blance;
    private HashMap<String, List<ProductInfo>> productLists = new HashMap<>();
    private ShoppingCarShopsAdapter shoppingCarShopAdapter;
    ArrayList<List<ProductInfo>> blancedeShopsLists;
    private CheckBox cbIsSelectAll, cbIsSelectAllEdit;
    private Orders orders;
    private int DELETE_SHOPPINGCAT_PRODUCT_CODE = 1;
    private int GET_SHOPPINGCAR_CODE = 2;
    private Dialog dialog;
    //声明记录停止滚动时候，可见的位置
    private int stop_position;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_shoppingcart, container, false);
        init(view);
        ButterKnife.bind(this, view);
        return view;
    }

    private void init(View view) {
        tvShoppingcarProductAmount = (TextView) view.findViewById(R.id.tv_shoppingcar_product_amount);
        llHead = (RelativeLayout) view.findViewById(R.id.ll_head);
        lvShooppingCartProdut = (ListView) view.findViewById(R.id.lv_shoopping_cart_produt);
        tvShoppingcar = (TextView) view.findViewById(R.id.tv_shoppingcar);
        tvDetele = (TextView) view.findViewById(R.id.tv_detele);
        rlDetele = (RelativeLayout) view.findViewById(R.id.rl_detele);
        tvEdite = (TextView) view.findViewById(R.id.tv_edite);
        ll_blance = (LinearLayout) view.findViewById(R.id.ll_blance);
        tv_blance = (TextView) view.findViewById(R.id.tv_blance);
        tvShoppingcarProductAmount = (TextView) view.findViewById(R.id.tv_shoppingcar_product_amount);
        tvShoppingcarSumMoney = (TextView) view.findViewById(R.id.tv_shoppingcar_sum_money);
        cbIsSelectAll = (CheckBox) view.findViewById(R.id.cb_is_select_all);
        cbIsSelectAllEdit = (CheckBox)view.findViewById(R.id.cb_is_select_all_edit);
        setlisten();
    }

    @Override
    public void onResume() {
        super.onResume();
        getShoppingcarProduct();
    }

    private void setlisten() {
        tvEdite.setOnClickListener(this);
        tv_blance.setOnClickListener(this);
        cbIsSelectAll.setOnClickListener(this);
        tvDetele.setOnClickListener(this);
        cbIsSelectAllEdit.setOnClickListener(this);
    }

    private void changeView() {
        if (isEdit) {
            ll_blance.setVisibility(View.GONE);
            rlDetele.setVisibility(View.VISIBLE);
        } else {
            ll_blance.setVisibility(View.VISIBLE);
            rlDetele.setVisibility(View.GONE);
        }
    }

    public List<ProductInfo> getChooseProducts() {
        return chooseProducts;
    }

    /**
     * 从activity中获得选购中所选的数据
     *
     * @param chooseProducts
     */
    public void setChooseProducts(final List<ProductInfo> chooseProducts) {
        this.chooseProducts = chooseProducts;
        if (chooseProducts != null) {
            shoppingCarShopAdapter = new ShoppingCarShopsAdapter(getActivity(), chooseProducts);
            lvShooppingCartProdut.setAdapter(shoppingCarShopAdapter);
            lvShooppingCartProdut.setOnScrollListener(new AbsListView.OnScrollListener() {
                public void onScrollStateChanged(AbsListView view, int scrollState) {
                    // TODO Auto-generated method stub
                    // 判断是否是最后一行,并且停止滚动
                    if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {
                        //获取可见位置
                        stop_position = lvShooppingCartProdut.getFirstVisiblePosition();
                    }

                }

                @Override
                public void onScroll(AbsListView view, int firstVisibleItem,
                                     int visibleItemCount, int totalItemCount) {

                }
            });
            tvShoppingcarProductAmount.setText(chooseProducts.size() + "");
            tvShoppingcarSumMoney.setText(getTotalMoney() + "");
        }


    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_edite:
                changeEditView();
                break;
            case R.id.tv_blance:
                if(shoppingCarShopAdapter!=null){
                    blancedeShopsLists = shoppingCarShopAdapter.getListproductlist();
                    if(isChooseProduct()){
                        createOrder(blancedeShopsLists);
                        gotoConfigOrderActivity();
                    }else {
                        ToastUtils.show(getActivity(),"您还未选择产品！");
                    }
                }else {
                    ToastUtils.show(getActivity(),"购物车已经清空！");
                }
                break;
            case R.id.cb_is_select_all:
                if(chooseProducts!=null){
                    selectAllProduct(cbIsSelectAll.isChecked());
                    blancedeShopsLists = shoppingCarShopAdapter.getListproductlist();
                    shoppingCarShopAdapter.setListproductlist(blancedeShopsLists);
                    shoppingCarShopAdapter.notifyDataSetChanged();
                    tvShoppingcarSumMoney.setText(getTotalMoney() + "");
                }
                break;
            case R.id.cb_is_select_all_edit:
                if(chooseProducts!=null){
                    selectAllProduct(cbIsSelectAllEdit.isChecked());
                    blancedeShopsLists = shoppingCarShopAdapter.getListproductlist();
                    shoppingCarShopAdapter.setListproductlist(blancedeShopsLists);
                    shoppingCarShopAdapter.notifyDataSetChanged();
                    tvShoppingcarSumMoney.setText(getTotalMoney() + "");
                }
                break;
            case R.id.tv_detele:
                if(chooseProducts!=null){
                    if(chooseProducts.size()>0){
                        detele();
                    }else {
                        ToastUtils.show(getActivity(),"未选择产品！");
                    }
                }
                break;
        }
    }

    private void changeEditView() {
        isEdit = !isEdit;
        changeView();
        tvShoppingcarSumMoney.setText(getTotalMoney() + "");
    }


    private void detele() {
       dialog = showDelete(getActivity(), new View.OnClickListener() {
           @Override
           public void onClick(View view) {
             commitDelete(getIsSelectProducts());
           }
       });
        dialog.show();
    }

    private void commitDelete(List<ProductInfo> lines) {
        try {
            CreateShoppingCar createShoppingCar = new CreateShoppingCar();
            CreateShoppingCar.Shopping shopping = new CreateShoppingCar.Shopping();
            shopping.setPos_session_id(HttpValue.SP_USERID);
            shopping.setLines(lines);
            List<CreateShoppingCar.Shopping> shoppings = new ArrayList<>();
            shoppings.add(shopping);
            createShoppingCar.setShopping(shoppings);
            JSONObject params = new JSONObject(GsonUtil.beanToJson(createShoppingCar));
            new JsonRPCAsyncTask(getActivity(), mHandler,
                    DELETE_SHOPPINGCAT_PRODUCT_CODE, HttpValue.getHttp() + Const.DELETE_SHOPPINGCAR_PRODUCT, "call",
                    params).execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private List<ProductInfo> getIsSelectProducts() {
       List<ProductInfo> lines = new ArrayList<>();
        for (int i = 0; i < chooseProducts.size(); i++) {
            ProductInfo resBean = chooseProducts.get(i);
            if (resBean.isSelect()) {
                lines.add(resBean);
            }
        }
        return  lines;
    }

    /**
     * 删除对话框
     */
    public Dialog showDelete(Context ctx,  View.OnClickListener listener) {
        final Dialog dialog = new Dialog(ctx, R.style.OrderDialog);
        View view = View.inflate(ctx, R.layout.dialog_delete, null);
        TextView type_text = (TextView) view.findViewById(R.id.type_text);
        type_text.setText("确定删除" );
        TextView mine_ok = (TextView) view.findViewById(R.id.mine_ok);
        TextView mine_delete = (TextView) view.findViewById(R.id.mine_delete);

        mine_ok.setOnClickListener(listener);
        mine_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.setCancelable(false); // 不可以用“返回键”取消
        dialog.setContentView(view);
        dialog.show();
        return dialog;
    }

    private boolean isChooseProduct() {
        boolean ishaveProduct = false;
        for(int i = 0;i<blancedeShopsLists.size();i++){
            List<ProductInfo> list = blancedeShopsLists.get(i);
            for(int j = 0;j<list.size();j++){
                ProductInfo productInfo = list.get(j);
                if(productInfo.isSelect()){
                    ishaveProduct = true;
                    return ishaveProduct;
                }
            }
        }
        return ishaveProduct;
    }

    private void gotoConfigOrderActivity() {
        Intent intent=new Intent(getActivity(), ConfigOrderActivity.class);
        intent.putExtra("orders",orders);
        intent.putExtra("sumMoney",tvShoppingcarSumMoney.getText().toString());
        intent.putExtra("blancedeShopsLists",blancedeShopsLists);
        startActivity(intent);
    }

    private void createOrder(ArrayList<List<ProductInfo>> blancedeShopsLists) {
        orders = new Orders();
        List<Orders.OrdersAppBean> orders_app = new ArrayList<>();;

        /**
         * 生成订单
         */
        for (int i = 0; i < blancedeShopsLists.size(); i++) {
            Orders.OrdersAppBean ordersAppBean = new Orders.OrdersAppBean();
            List<OrderProduct> lines = new ArrayList<>();
            List<ProductInfo> shopProducts = blancedeShopsLists.get(i);
            for (int j = 0; j < shopProducts.size(); j++) {
                ProductInfo resBean = shopProducts.get(j);
                if(resBean.isSelect()){
                    OrderProduct linesBean = new OrderProduct();
                    linesBean.setProduct_id(resBean.getProduct_id());
                    linesBean.setAmount(resBean.getAmount());
                    linesBean.setPrice(resBean.getPrice());
                    linesBean.setImage(resBean.getImage());
                    linesBean.setName(resBean.getName());
                    linesBean.setUom(resBean.getUom());
                    lines.add(linesBean);
                }

            }
            if(lines.size()>0){
                ordersAppBean.setLines(lines);
                ordersAppBean.setStore_id(shopProducts.get(0).getStore_id()+"");
                ordersAppBean.setStroes_name(shopProducts.get(0).getStore_name());
                orders_app.add(ordersAppBean);
            }
        }
        orders.setOrders_app(orders_app);

    }





    private void selectAllProduct(boolean isselect) {
        for (int i = 0; i < chooseProducts.size(); i++) {
            ProductInfo temp = chooseProducts.get(i);
            temp.setSelect(isselect);
        }

        shoppingCarShopAdapter.notifyDataSetChanged();
        lvShooppingCartProdut.setAdapter(shoppingCarShopAdapter);
    }

    public void onEventMainThread(String send) {
        if (send.equals("sumMoney")) {
            blancedeShopsLists = shoppingCarShopAdapter.getListproductlist();
            for(int i = 0 ;i<blancedeShopsLists.size();i++){
                List<ProductInfo> list = blancedeShopsLists.get(i);
                shoppingCarShopAdapter.isNoListAllChecked(list);
            }
            tvShoppingcarSumMoney.setText(getTotalMoney() + "");
        }
    }

    /**
     * 计算出选中产品的总金额
     *
     * @return
     */
    private Double getTotalMoney() {
        double sumMoney = 0;
            blancedeShopsLists = shoppingCarShopAdapter.getListproductlist();
        for (int i = 0; i < blancedeShopsLists.size(); i++) {
            List<ProductInfo> list = blancedeShopsLists.get(i);
            for (int j = 0; j < list.size(); j++) {
                ProductInfo resBean = list.get(j);
                if (resBean.isSelect()) {
                    sumMoney = sumMoney + resBean.getAmount() * resBean.getPrice();
                }
            }
        }
        return NumberFormatUtil.formatToDouble2(sumMoney);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {

           if(msg.what == DELETE_SHOPPINGCAT_PRODUCT_CODE){
                String result = (String) msg.obj;
               if(result!=null){
                   Log.v("删除购物车", result);
               }
                if (result != null && result.contains("error")) {
                    ToastUtils.show(getActivity(), "删除购物车错误");
                    return;
                }
                if (result != null && !result.contains("error")) {
                    CreateShoppingcarResult createShoppingcarResult = GsonUtil.jsonToBean(result, CreateShoppingcarResult.class);
                    if(createShoppingcarResult.getResult().isFlag()){
                        changeEditView();
                        getShoppingcarProduct();
                    }
                } else {
                    ToastUtils.show(getActivity(), "获取门店信息失败！");
                }
            }else  //购物车数据
               if (msg.what == GET_SHOPPINGCAR_CODE) {
                   String result = (String) msg.obj;
                   if (result != null && !result.contains("error")) {
                       Log.v("购物车数据", result);
                       GetShoppingcarData getShoppingcarData
                               = GsonUtil.jsonToBean(result, GetShoppingcarData.class);
                       if (getShoppingcarData != null) {
                           chooseProducts = getShoppingcarData.getResult().getRes().getLines();
                           if (chooseProducts == null) {
                               chooseProducts = new ArrayList<>();
                           }
                           shoppingCarShopAdapter = new ShoppingCarShopsAdapter(getActivity(), chooseProducts);
                           lvShooppingCartProdut.setAdapter(shoppingCarShopAdapter);
                           tvShoppingcarProductAmount.setText(chooseProducts.size() + "");
                           tvShoppingcarSumMoney.setText(getTotalMoney() + "");
                           if(dialog!= null){
                               dialog.dismiss();
                           }

                       }
                   } else {
                       ToastUtils.show(getActivity(), "获取购物车信息失败！");
                   }
               }
        }


    };

    /**
     * 获取购物车数据
     */
    private void getShoppingcarProduct() {
        try {
            JSONObject params = new JSONObject();

            params.put("pos_session_id", HttpValue.SP_USERID);
            new JsonRPCAsyncTask(
                    getActivity(), mHandler, GET_SHOPPINGCAR_CODE,
                    HttpValue.getHttp() + Const.GET_SHOPPINGCAR, "call", params
            ).execute();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
