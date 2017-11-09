package onetreeshopsapp.com.onetreeshops.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;
import onetreeshopsapp.com.onetreeshops.R;
import onetreeshopsapp.com.onetreeshops.bean.EventProduct;
import onetreeshopsapp.com.onetreeshops.bean.GetProductDateResult;
import onetreeshopsapp.com.onetreeshops.bean.GetShopsDataResult;
import onetreeshopsapp.com.onetreeshops.bean.ProductInfo;
import onetreeshopsapp.com.onetreeshops.utils.Const;
import onetreeshopsapp.com.onetreeshops.utils.HttpValue;

/**
 * Created by fiona on 2016/10/9.
 */
public class ShopsProductAdapter extends BaseAdapter {
    private List<ProductInfo> shops;
    private Context context;

    public ShopsProductAdapter(List<ProductInfo> shops, Context context) {
        this.shops = shops;
        this.context = context;
    }

    @Override
    public int getCount() {
        return shops.size();
    }

    @Override
    public Object getItem(int position) {
        return shops.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = convertView;
        final ViewHold viewHold;
        final ProductInfo resBean = shops.get(position);
        if (view == null) {
            viewHold = new ViewHold();
            view = View.inflate(context, R.layout.item_shop_product, null);
            viewHold.tv_product_name = (TextView) view.findViewById(R.id.tv_product_name);
            viewHold.iv_product_image = (ImageView) view.findViewById(R.id.iv_product_image);
            viewHold.tv_product_unit = (TextView) view.findViewById(R.id.tv_product_unit);
            viewHold.tv_product_price = (TextView) view.findViewById(R.id.tv_product_price);
            viewHold.ib_product_add = (ImageView) view.findViewById(R.id.ib_product_add);
            viewHold.ib_product_reduce = (ImageView) view.findViewById(R.id.ib_product_reduce);
            viewHold.tv_product_amount = (TextView) view.findViewById(R.id.tv_product_amount);
            viewHold.tv_saledNumber = (TextView)view.findViewById(R.id.tv_saledNumber);
            view.setTag(viewHold);
        } else {
            viewHold = (ViewHold) view.getTag();
        }

        Glide.with(context)
                .load(HttpValue.getHttp() + resBean.getImage())
                .into(viewHold.iv_product_image);
        viewHold.tv_product_name.setText(resBean.getName());
        viewHold.tv_product_price.setText("￥"+resBean.getPrice() + "");
        viewHold.tv_product_unit.setText("/" + resBean.getUom());
        viewHold.ib_product_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int amount;
                String amountstring = viewHold.tv_product_amount.getText().toString();
                if (amountstring.equals("")) {
                    amount = 0;
                } else {
                    amount = Integer.parseInt(amountstring);
                }
                ++amount;
                if (amount > 0) {
                    viewHold.tv_product_amount.setVisibility(View.VISIBLE);
                    viewHold.ib_product_reduce.setVisibility(View.VISIBLE);
                } else {
                    viewHold.tv_product_amount.setVisibility(View.INVISIBLE);
                    viewHold.ib_product_reduce.setVisibility(View.INVISIBLE);
                }
                resBean.setAmount(amount);
                viewHold.tv_product_amount.setText(amount + "");
                EventBus.getDefault().post(new EventProduct("add", (ProductInfo) getItem(position)));
            }
        });
        viewHold.ib_product_reduce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int amount = Integer.parseInt(viewHold.tv_product_amount.getText().toString());
                if (amount > 1) {
                    --amount;
                    viewHold.tv_product_amount.setVisibility(View.VISIBLE);
                    viewHold.ib_product_reduce.setVisibility(View.VISIBLE);
                }  else {
                    --amount;
                    viewHold.tv_product_amount.setVisibility(View.INVISIBLE);
                    viewHold.ib_product_reduce.setVisibility(View.INVISIBLE);
                }
                resBean.setAmount(amount);
                viewHold.tv_product_amount.setText(amount + "");
                EventBus.getDefault().post(new EventProduct("reduce", (ProductInfo) getItem(position)));
            }
        });
        if (resBean.getAmount() > 0) {
            viewHold.tv_product_amount.setVisibility(View.VISIBLE);
            viewHold.tv_product_amount.setText(resBean.getAmount() + "");
            viewHold.ib_product_reduce.setVisibility(View.VISIBLE);
        } else {
            viewHold.tv_product_amount.setVisibility(View.INVISIBLE);
            viewHold.ib_product_reduce.setVisibility(View.INVISIBLE);
        }
        viewHold.tv_saledNumber.setText(resBean.getSaled_num()+"");
        return view;
    }

    private class ViewHold {
        ImageView iv_product_image;
        TextView tv_product_name;
        TextView tv_product_unit;
        TextView tv_product_price;
        ImageView ib_product_reduce;
        ImageView ib_product_add;
        TextView tv_product_amount;
        TextView tv_saledNumber;
    }

    public List<ProductInfo> getShops() {
        return shops;
    }

    public void setShops(List<ProductInfo> shops) {
        this.shops = shops;
    }
}
