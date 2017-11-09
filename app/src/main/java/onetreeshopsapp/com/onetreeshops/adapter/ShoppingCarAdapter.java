package onetreeshopsapp.com.onetreeshops.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import de.greenrobot.event.EventBus;
import onetreeshopsapp.com.onetreeshops.R;
import onetreeshopsapp.com.onetreeshops.bean.EventProduct;
import onetreeshopsapp.com.onetreeshops.bean.GetProductDateResult;
import onetreeshopsapp.com.onetreeshops.bean.ProductInfo;
import onetreeshopsapp.com.onetreeshops.utils.HttpValue;

/**
 * Created by fiona on 2016/10/13.
 */
public class ShoppingCarAdapter extends BaseAdapter{
    private  List<ProductInfo> chooseProducts;
    private Context context;
    public ShoppingCarAdapter(Context context, List<ProductInfo> chooseProducts){
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
        final ProductInfo resBean = chooseProducts.get(position);
        if (view == null) {
            viewHold = new ViewHold();
            view = View.inflate(context, R.layout.item_shoppingcar_product, null);
            viewHold.tv_product_name = (TextView) view.findViewById(R.id.tv_product_name);
            viewHold.iv_product_image = (ImageView) view.findViewById(R.id.iv_product_image);
            viewHold.tv_product_unit = (TextView) view.findViewById(R.id.tv_product_unit);
            viewHold.tv_product_price = (TextView) view.findViewById(R.id.tv_product_price);
            viewHold.ib_product_add = (ImageView) view.findViewById(R.id.ib_product_add);
            viewHold.ib_product_reduce = (ImageView) view.findViewById(R.id.ib_product_reduce);
            viewHold.tv_product_amount = (TextView) view.findViewById(R.id.tv_product_amount);
            viewHold.cb_isselect = (CheckBox) view.findViewById(R.id.cb_isselect);
            view.setTag(viewHold);
        } else {
            viewHold = (ViewHold) view.getTag();
        }

        Glide.with(context)
                .load(HttpValue.getHttp() + resBean.getImage())
                .into(viewHold.iv_product_image);
        viewHold.tv_product_name.setText(resBean.getName());
        viewHold.tv_product_price.setText(resBean.getPrice() + "");
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
                EventBus.getDefault().post(new EventProduct("shoppingCarAdd", (ProductInfo) getItem(position)));
                EventBus.getDefault().post("sumMoney");
            }
        });
        viewHold.ib_product_reduce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int amount = Integer.parseInt(viewHold.tv_product_amount.getText().toString());
                if (amount > 2) {
                    --amount;
                    viewHold.tv_product_amount.setVisibility(View.VISIBLE);
                    viewHold.ib_product_reduce.setVisibility(View.VISIBLE);
                }  else if(amount == 2){
                    --amount;
                    viewHold.ib_product_reduce.setVisibility(View.INVISIBLE);
                }
                resBean.setAmount(amount);
                viewHold.tv_product_amount.setText(amount + "");
                EventBus.getDefault().post(new EventProduct("shoppingCarReduce", (ProductInfo) getItem(position)));
                EventBus.getDefault().post("sumMoney");
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
        if(viewHold.tv_product_amount.getText().toString().equals("1")){
            viewHold.ib_product_reduce.setVisibility(View.INVISIBLE);
        }
        viewHold.cb_isselect.setChecked(resBean.isSelect());
        viewHold.cb_isselect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resBean.setSelect(viewHold.cb_isselect.isChecked());
                EventBus.getDefault().post("sumMoney");
            }
        });
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
        CheckBox cb_isselect;

    }
}
