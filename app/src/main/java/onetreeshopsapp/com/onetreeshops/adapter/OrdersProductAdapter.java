package onetreeshopsapp.com.onetreeshops.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import onetreeshopsapp.com.onetreeshops.R;
import onetreeshopsapp.com.onetreeshops.bean.GetOrdersResult;
import onetreeshopsapp.com.onetreeshops.bean.OrdersProductBean2;
import onetreeshopsapp.com.onetreeshops.utils.HttpValue;

/**
 * Created by fiona on 2016/10/22.
 */
public class OrdersProductAdapter extends BaseAdapter{
    private  List<GetOrdersResult.ResultBean.ResBean.OrdersProductBean> chooseProducts;
    private Context context;
    public OrdersProductAdapter(Context context, List<GetOrdersResult.ResultBean.ResBean.OrdersProductBean> chooseProducts){
            this.chooseProducts = chooseProducts;
            this.context =  context;
    }
    @Override
    public int getCount() {
        return chooseProducts.size();
    }

    @Override
    public Object getItem(int position) {
        return chooseProducts.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = convertView;
        final ViewHold viewHold;
        final GetOrdersResult.ResultBean.ResBean.OrdersProductBean resBean = chooseProducts.get(position);
        if (view == null) {
            viewHold = new ViewHold();
            view = View.inflate(context, R.layout.item_orders_detile, null);
            viewHold.orders_detile_product_image = (ImageView) view.findViewById(R.id.orders_detile_product_image);
            viewHold.orders_detile_product_name = (TextView) view.findViewById(R.id.orders_detile_product_name);
            viewHold.orders_detile_product_price = (TextView) view.findViewById(R.id.orders_detile_product_price);
            viewHold.orders_detile_product_num = (TextView) view.findViewById(R.id.orders_detile_product_num);
            view.setTag(viewHold);
        } else {
            viewHold = (ViewHold) view.getTag();
        }
        Glide.with(context)
                .load(HttpValue.getHttp() + resBean.getImage())
                .into(viewHold.orders_detile_product_image);
        viewHold.orders_detile_product_name.setText(resBean.getName());
        viewHold.orders_detile_product_price.setText("￥"+resBean.getPrice() + "/" + resBean.getUom());//￥15.6/kg
        viewHold.orders_detile_product_num.setText("x" + resBean.getAmount());
        return view;
    }
    private class ViewHold {
        ImageView orders_detile_product_image;
        TextView orders_detile_product_name;
        TextView orders_detile_product_price;
        TextView orders_detile_product_num;
    }
}
