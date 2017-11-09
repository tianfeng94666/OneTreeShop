package onetreeshopsapp.com.onetreeshops.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import onetreeshopsapp.com.onetreeshops.R;
import onetreeshopsapp.com.onetreeshops.bean.GetShopsDataResult;

/**
 * Created by fiona on 2016/10/9.
 */
public class ShopsNameAdapter extends BaseAdapter {
    private ArrayList<GetShopsDataResult.ResultBean.ResBean> shops;
    private Context context;
    private int currentPosition;

    public int getCurrentPosition() {
        return currentPosition;
    }

    public void setCurrentPosition(int currentPosition) {
        this.currentPosition = currentPosition;
    }

    public ShopsNameAdapter(ArrayList<GetShopsDataResult.ResultBean.ResBean> shops, Context context) {
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
        return shops.get(position).getStore_id();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        ViewHold viewHold;
        GetShopsDataResult.ResultBean.ResBean resBean = shops.get(position);
        if (view == null) {
            viewHold = new ViewHold();
            view = View.inflate(context, R.layout.item_shop_name, null);
            viewHold.tvShopName = (TextView) view.findViewById(R.id.item_shop_name);
            viewHold.tvAmount = (TextView) view.findViewById(R.id.tv_amount);
//            viewHold.vCutOff = (View)view.findViewById(R.id.v_cut_off);
            view.setTag(viewHold);
        } else {
            viewHold = (ViewHold) view.getTag();
        }
        viewHold.tvShopName.setText(resBean.getStore_name());

        //选中颜色的修改
        if (currentPosition == position) {
            view.setBackgroundResource(R.color.maincolor);
//            viewHold.vCutOff.setVisibility(View.GONE);
        } else {
            view.setBackgroundResource(R.color.bg_graylight_list);
//            viewHold.vCutOff.setVisibility(View.VISIBLE);
        }
        //设置选中门店选取货物的数量
        if (resBean.getOrder_num() > 0) {
            viewHold.tvAmount.setVisibility(View.VISIBLE);
            viewHold.tvAmount.setText(resBean.getOrder_num() + "");
        }
        return view;
    }

    private class ViewHold {
        TextView tvShopName;
        TextView tvAmount;
        View vCutOff;
    }
}
