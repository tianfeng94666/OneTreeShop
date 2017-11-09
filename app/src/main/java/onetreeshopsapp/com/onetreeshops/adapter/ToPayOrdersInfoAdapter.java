package onetreeshopsapp.com.onetreeshops.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;
import onetreeshopsapp.com.onetreeshops.R;
import onetreeshopsapp.com.onetreeshops.activity.OrdersDetileActivity;
import onetreeshopsapp.com.onetreeshops.activity.ShopActivity;
import onetreeshopsapp.com.onetreeshops.activity.ShopActivity2;
import onetreeshopsapp.com.onetreeshops.bean.GetOrdersResult;
import onetreeshopsapp.com.onetreeshops.bean.ProductInfo;

/**
 * Created by fiona on 2016/10/22.
 */
public class ToPayOrdersInfoAdapter extends BaseAdapter {
    private final Context context;
    List<GetOrdersResult.ResultBean.ResBean> ordersList;
    private ListItemClickHelp callback;
    private int m =0;
    private int parentposition;
    public ToPayOrdersInfoAdapter(Context context, ListItemClickHelp callback) {
        this.context = context;
        this.callback = callback;
    }
    public void SetList(List<GetOrdersResult.ResultBean.ResBean> ordersList){
        this.ordersList = ordersList;

    }
    @Override
    public int getCount() {
        return ordersList.size();
    }

    @Override
    public Object getItem(int position) {
        return ordersList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, final View convertView, final ViewGroup parent) {
        View view = convertView;
        final ViewHold viewHold;
        final List<GetOrdersResult.ResultBean.ResBean.OrdersProductBean> listproduct = ordersList.get(position).getLines();
        if (view == null) {
            viewHold = new ViewHold();
            view = View.inflate(context, R.layout.item_topayorders_info, null);
            viewHold.tv_topayorders_shopname = (TextView) view.findViewById(R.id.tv_topayorders_shopname);
            Drawable drawable= context.getResources().getDrawable(R.mipmap.arrow_next);
            drawable.setBounds(0,0,45,45);
            viewHold.tv_topayorders_shopname.setCompoundDrawables(null,null,drawable,null);
            viewHold.tv_topayorders_paystadu = (TextView) view.findViewById(R.id.tv_topayorders_paystadu);
            viewHold.lv_topayorders_detile = (ListView) view.findViewById(R.id.lv_topayorders_detile);
            viewHold.tv_topayorders_product_total = (TextView) view.findViewById(R.id.tv_topayorders_product_total);
            viewHold.tv_topayorders_monney = (TextView) view.findViewById(R.id.tv_topayorders_monney);
            viewHold.tv_topayorders_freight = (TextView) view.findViewById(R.id.tv_topayorders_freight);
            viewHold.tv_topayorders_delect = (TextView) view.findViewById(R.id.tv_topayorders_delect);
            viewHold.tv_topayorders_cancle = (TextView) view.findViewById(R.id.tv_topayorders_cancle);
            viewHold.tv_topayorders_topay = (TextView) view.findViewById(R.id.tv_topayorders_topay);
            viewHold.cb_topay_isselect = (CheckBox) view.findViewById(R.id.cb_topay_isselect);
            viewHold.lv_topayorders_detile.setTag(position);
            view.setTag(viewHold);
        } else {
            viewHold = (ViewHold) view.getTag();
        }
        final OrdersProductAdapter ordersProductAdapter = new OrdersProductAdapter(context, listproduct);
        viewHold.lv_topayorders_detile.setAdapter(ordersProductAdapter);
        setListViewHeightBasedOnChildren(viewHold.lv_topayorders_detile);
        viewHold.tv_topayorders_shopname.setText(ordersList.get(position).getStore_name());
        String orderstate = ordersList.get(position).getPay_state();//待付款,交易关闭,交易成功
        if (ordersList.size()<2){
            viewHold.cb_topay_isselect.setVisibility(View.GONE);
        }else {
            viewHold.cb_topay_isselect.setVisibility(View.VISIBLE);
        }
        int amount = 0;
        for (int i=0; i<listproduct.size();i++){
            amount = amount + listproduct.get(i).getAmount();
        }
        viewHold.tv_topayorders_product_total.setText("共"+amount+"件商品");
        viewHold.tv_topayorders_monney.setText("￥"+ordersList.get(position).getAmount_total());
        viewHold.tv_topayorders_freight.setText("(含运费￥"+ordersList.get(position).getTransport_cost()+")");
        viewHold.tv_topayorders_paystadu.setText(orderstate);
        //

        viewHold.tv_topayorders_shopname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ShopActivity2.class);
                intent.putExtra("stote_id",ordersList.get(position).getStore_id()+"");
                intent.putExtra("storename",ordersList.get(position).getStore_name());
                context.startActivity(intent);
            }
        });
        viewHold.lv_topayorders_detile.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(context, OrdersDetileActivity.class);
                intent.putExtra("position",(int)viewHold.lv_topayorders_detile.getTag());
                intent.putExtra("ordersList",(Serializable) ordersList);
                context.startActivity(intent);
            }
        });
        viewHold.tv_topayorders_delect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callback.onClickitemButton(convertView,parent,position,viewHold.tv_topayorders_delect.getId());
            }
        });
        viewHold.tv_topayorders_cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callback.onClickitemButton(convertView,parent,position,viewHold.tv_topayorders_cancle.getId());
            }
        });
        viewHold.tv_topayorders_topay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callback.onClickitemButton(convertView,parent,position,viewHold.tv_topayorders_topay.getId());
            }
        });
        viewHold.cb_topay_isselect.setChecked(ordersList.get(position).isselect());
        ProductInfo productInfo = new ProductInfo();
        final List<String> list = new ArrayList<>();
        viewHold.cb_topay_isselect.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    m = m +1;
                }else if (isChecked==false&&m>0){
                    m = m -1;
                }
                if (m>1){
                    EventBus.getDefault().post("setButtonvisible");
                }else {
                    EventBus.getDefault().post("setButtongone");

                }
            }
        });
        viewHold.cb_topay_isselect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ordersList.get(position).setIsselect(viewHold.cb_topay_isselect.isChecked());
                EventBus.getDefault().post("sumOrdersMoney");
            }
        });
        System.out.println(">>>>"+m);

        return view;

    }

    private class ViewHold {
        TextView tv_topayorders_shopname;
        TextView tv_topayorders_paystadu;
        ListView lv_topayorders_detile;
        TextView tv_topayorders_product_total;
        TextView tv_topayorders_monney;
        TextView tv_topayorders_freight;
        TextView tv_topayorders_delect;
        TextView tv_topayorders_cancle;
        TextView tv_topayorders_topay;
        CheckBox cb_topay_isselect;
    }

    //根据情况设置商品listview高度
    public void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            // pre-condition
            return;
        }

        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }


    /**
     * 用于item中按钮事件回调的接口
     * @author
     *
     */

    public interface ListItemClickHelp {
        void onClickitemButton(View item, View widget, int position, int which);
    }
}
