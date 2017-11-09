package onetreeshopsapp.com.onetreeshops.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import onetreeshopsapp.com.onetreeshops.R;
import onetreeshopsapp.com.onetreeshops.activity.ShopActivity;
import onetreeshopsapp.com.onetreeshops.bean.Orders;
import onetreeshopsapp.com.onetreeshops.bean.SearchShopsResult;
import onetreeshopsapp.com.onetreeshops.utils.HttpValue;

/**
 * Created by fiona on 2016/10/25.
 */
public class SearchShopsResultAdapter extends BaseAdapter {
    private Context context;
    private List<SearchShopsResult.ResultBean.ResBean> list;
    public SearchShopsResultAdapter(Context context, List<SearchShopsResult.ResultBean.ResBean> list){
        this.context =context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final SearchShopsResult.ResultBean.ResBean resBean =  list.get(position);
        View view = convertView;
        final ViewHold viewHold;
        if (view == null) {
            viewHold = new ViewHold();
            view = View.inflate(context, R.layout.item_search_shops, null);
            viewHold.ivShopImage = (ImageView) view.findViewById(R.id.iv_shop_image);
            viewHold.tvShopName = (TextView)view.findViewById(R.id.tv_shop_name);
            viewHold.tvShopProductAmount = (TextView)view.findViewById(R.id.tv_shop_product_amount);
            viewHold.ivGotoShop = (ImageView)view.findViewById(R.id.iv_goto_shop);
            view.setTag(viewHold);
        } else {
            viewHold = (ViewHold) view.getTag();
        }

        Glide.with(context)
                .load(HttpValue.getHttp() + resBean.getImage())
                .into(viewHold.ivShopImage);
        viewHold.tvShopName.setText(resBean.getStore_name());
        viewHold.tvShopProductAmount.setText("店铺中有"+resBean.getProduct_num()+"种宝贝");
        viewHold.ivGotoShop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ShopActivity.class);
                intent.putExtra("stote_id",resBean.getStore_id()+"");
                context.startActivity(intent);
//                ((Activity)context).overridePendingTransition(R.anim.scale_rotate,R.anim.fade);
            }
        });
        return view;
    }
    private class ViewHold {
        ImageView ivShopImage;
        TextView tvShopName;
        TextView tvShopProductAmount;
        ImageView ivGotoShop;
    }
}
