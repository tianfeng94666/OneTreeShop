package onetreeshopsapp.com.onetreeshops.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.io.Serializable;
import java.util.List;

import onetreeshopsapp.com.onetreeshops.R;
import onetreeshopsapp.com.onetreeshops.activity.OrdersDetileActivity;
import onetreeshopsapp.com.onetreeshops.activity.ShopActivity;
import onetreeshopsapp.com.onetreeshops.activity.ShopActivity2;
import onetreeshopsapp.com.onetreeshops.bean.GetOrdersResult;

/**
 * Created by fiona on 2016/10/22.
 */
public class CompleteOrdersInfoAdapter extends BaseAdapter {
    private final Context context;
    List<GetOrdersResult.ResultBean.ResBean> ordersList;
    private ListItemClickHelp callback;
    private int parentposition;
    public CompleteOrdersInfoAdapter(Context context, ListItemClickHelp callback) {
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
            view = View.inflate(context, R.layout.item_allorders_info, null);
            viewHold.tv_orders_shopname = (TextView) view.findViewById(R.id.tv_orders_shopname);
            Drawable drawable= context.getResources().getDrawable(R.mipmap.arrow_next);
            drawable.setBounds(0,0,45,45);
            viewHold.tv_orders_shopname.setCompoundDrawables(null,null,drawable,null);
            viewHold.tv_orders_paystadu = (TextView) view.findViewById(R.id.tv_orders_paystadu);
            viewHold.lv_orders_detile = (ListView) view.findViewById(R.id.lv_orders_detile);
            viewHold.tv_orders_product_total = (TextView) view.findViewById(R.id.tv_orders_product_total);
            viewHold.tv_orders_monney = (TextView) view.findViewById(R.id.tv_orders_monney);
            viewHold.tv_orders_freight = (TextView) view.findViewById(R.id.tv_orders_freight);
            viewHold.tv_orders_delect = (TextView) view.findViewById(R.id.tv_orders_delect);
            viewHold.tv_orders_cancle = (TextView) view.findViewById(R.id.tv_orders_cancle);
            viewHold.tv_orders_topay = (TextView) view.findViewById(R.id.tv_orders_topay);
            viewHold.tv_orders_toevaluate = (TextView) view.findViewById(R.id.tv_orders_toevaluate);
            viewHold.tv_orders_toreback = (TextView)view.findViewById(R.id.tv_orders_toreback);

            view.setTag(viewHold);
        } else {
            viewHold = (ViewHold) view.getTag();
        }
        viewHold.lv_orders_detile.setTag(position);
        final OrdersProductAdapter ordersProductAdapter = new OrdersProductAdapter(context, listproduct);
        viewHold.lv_orders_detile.setAdapter(ordersProductAdapter);
        setListViewHeightBasedOnChildren(viewHold.lv_orders_detile);
        viewHold.tv_orders_shopname.setText(ordersList.get(position).getStore_name());
        String orderstate = ordersList.get(position).getPay_state();//待付款,交易关闭,交易成功
        if (orderstate.equals("待付款")){
            //取消订单，付款按钮可见，删除订单按钮不可见
            viewHold.tv_orders_delect.setVisibility(View.GONE);
            viewHold.tv_orders_cancle.setVisibility(View.GONE);
            viewHold.tv_orders_topay.setVisibility(View.GONE);
            viewHold.tv_orders_toreback.setVisibility(View.GONE);
            viewHold.tv_orders_toevaluate.setVisibility(View.VISIBLE);
        }else if(orderstate.equals("已完成")){
            //删除订单按钮,取消订单，付款按钮不可见，退款可见
            viewHold.tv_orders_delect.setVisibility(View.GONE);
            viewHold.tv_orders_cancle.setVisibility(View.GONE);
            viewHold.tv_orders_topay.setVisibility(View.GONE);
            viewHold.tv_orders_toreback.setVisibility(View.VISIBLE);
        }else {
            //取消订单，付款按钮不可见，删除订单按钮可见
            viewHold.tv_orders_delect.setVisibility(View.VISIBLE);
            viewHold.tv_orders_cancle.setVisibility(View.GONE);
            viewHold.tv_orders_topay.setVisibility(View.GONE);
            viewHold.tv_orders_toreback.setVisibility(View.GONE);
        }
        int amount = 0;
        for (int i=0; i<listproduct.size();i++){
            amount = amount + listproduct.get(i).getAmount();
        }
        viewHold.tv_orders_product_total.setText("共"+amount+"件商品");
        viewHold.tv_orders_monney.setText("￥"+ordersList.get(position).getAmount_total());
        viewHold.tv_orders_freight.setText("(含运费￥"+ordersList.get(position).getTransport_cost()+")");
        viewHold.tv_orders_paystadu.setText(orderstate);
        //

        viewHold.tv_orders_shopname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ShopActivity2.class);
                intent.putExtra("stote_id",ordersList.get(position).getStore_id()+"");
                intent.putExtra("storename",ordersList.get(position).getStore_name());
                context.startActivity(intent);
            }
        });
        viewHold.lv_orders_detile.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(context, OrdersDetileActivity.class);
                intent.putExtra("position",(int)viewHold.lv_orders_detile.getTag());
                intent.putExtra("ordersList",(Serializable) ordersList);
                context.startActivity(intent);
            }
        });
        viewHold.tv_orders_delect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callback.onClickitemButton(convertView,parent,position,viewHold.tv_orders_delect.getId());
            }
        });
        viewHold.tv_orders_cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callback.onClickitemButton(convertView,parent,position,viewHold.tv_orders_cancle.getId());
            }
        });
        viewHold.tv_orders_topay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callback.onClickitemButton(convertView,parent,position,viewHold.tv_orders_topay.getId());
            }
        });
        viewHold.tv_orders_toevaluate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callback.onClickitemButton(convertView,parent,position,viewHold.tv_orders_toevaluate.getId());
            }
        });

        viewHold.tv_orders_toreback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callback.onClickitemButton(convertView,parent,position,viewHold.tv_orders_toreback.getId());
            }
        });
        return view;

    }

    private class ViewHold {
        TextView tv_orders_shopname;
        TextView tv_orders_paystadu;
        ListView lv_orders_detile;
        TextView tv_orders_product_total;
        TextView tv_orders_monney;
        TextView tv_orders_freight;
        TextView tv_orders_delect;
        TextView tv_orders_cancle;
        TextView tv_orders_topay;
        TextView tv_orders_toevaluate;
        TextView tv_orders_toreback;
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
