package onetreeshopsapp.com.onetreeshops.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import onetreeshopsapp.com.onetreeshops.R;
import onetreeshopsapp.com.onetreeshops.base.BaseActivity;
import onetreeshopsapp.com.onetreeshops.bean.LinesBean;
import onetreeshopsapp.com.onetreeshops.bean.OrderProduct;
import onetreeshopsapp.com.onetreeshops.bean.Orders;
import onetreeshopsapp.com.onetreeshops.bean.ProductInfo;
import onetreeshopsapp.com.onetreeshops.utils.HttpValue;

/**
 * Created by fiona on 2016/10/19.
 */
public class ConfigOrderProductsAdapter extends BaseAdapter{
    private  Context context;
    private  Orders.OrdersAppBean list;
    public ConfigOrderProductsAdapter(Context context, Orders.OrdersAppBean list){
        this.list = list;
        this.context = context;
    }
    @Override
    public int getCount() {
        return list.getLines().size();
    }

    @Override
    public Object getItem(int position) {
        return list.getLines().get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        final ViewHold viewHold;
        OrderProduct linesBean =  list.getLines().get(position);

        if (view == null) {
            viewHold = new ViewHold();
            view = View.inflate(context, R.layout.item_config_product, null);
            viewHold.tv_product_name = (TextView) view.findViewById(R.id.tv_product_name);
            viewHold.iv_product_image = (ImageView) view.findViewById(R.id.iv_product_image);
            viewHold.tv_product_unit = (TextView) view.findViewById(R.id.tv_product_unit);
            viewHold.tv_product_price = (TextView) view.findViewById(R.id.tv_product_price);
            viewHold.tv_product_amount = (TextView) view.findViewById(R.id.tv_product_amount);
            view.setTag(viewHold);
        } else {
            viewHold = (ViewHold) view.getTag();
        }

        Glide.with(context)
                .load(HttpValue.getHttp() + linesBean.getImage())
                .into(viewHold.iv_product_image);
        viewHold.tv_product_name.setText(linesBean.getName());
        viewHold.tv_product_price.setText(linesBean.getPrice() + "");
        viewHold.tv_product_unit.setText("/" + linesBean.getUom());
viewHold.tv_product_amount.setText("*"+linesBean.getAmount());
        return view;
    }

    private class ViewHold {
        ImageView iv_product_image;
        TextView tv_product_name;
        TextView tv_product_unit;
        TextView tv_product_price;
        TextView tv_product_amount;
    }
}
