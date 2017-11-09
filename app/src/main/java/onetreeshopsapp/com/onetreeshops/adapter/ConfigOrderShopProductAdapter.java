package onetreeshopsapp.com.onetreeshops.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import onetreeshopsapp.com.onetreeshops.R;
import onetreeshopsapp.com.onetreeshops.base.BaseActivity;
import onetreeshopsapp.com.onetreeshops.bean.Orders;

/**
 * Created by fiona on 2016/10/19.
 */
public class ConfigOrderShopProductAdapter extends BaseAdapter {
    private Context context;
    private Orders orders;

    public ConfigOrderShopProductAdapter(Context context, Orders orders) {
        this.context = context;
        this.orders = orders;
    }

    @Override
    public int getCount() {
        return orders.getOrders_app().size();
    }

    @Override
    public Object getItem(int position) {
        return orders.getOrders_app().get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        final ViewHold viewHold;
        Orders.OrdersAppBean lists = orders.getOrders_app().get(position);
        if (view == null) {
            viewHold = new ViewHold();
            view = View.inflate(context, R.layout.item_config_shops, null);
            viewHold.tv_shop_name = (TextView) view.findViewById(R.id.tv_shop_name);
            viewHold.lv_item_product_list = (ListView) view.findViewById(R.id.lv_item_product_list);
            view.setTag(viewHold);
        } else {
            viewHold = (ViewHold) view.getTag();
        }
        if (lists.getLines().size() > 0) {
            viewHold.tv_shop_name.setText(lists.getStroes_name());
        }

        ConfigOrderProductsAdapter configOrderProductsAdapter = new ConfigOrderProductsAdapter(context, (Orders.OrdersAppBean) lists);
        viewHold.lv_item_product_list.setAdapter(configOrderProductsAdapter);
        setListViewHeightBasedOnChildren(viewHold.lv_item_product_list);
        return view;
    }

    private class ViewHold {

        TextView tv_shop_name;
        ListView lv_item_product_list;
    }

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
}
