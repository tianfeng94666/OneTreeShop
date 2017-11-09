package onetreeshopsapp.com.onetreeshops.adapter;

/**
 * Created by fiona on 2016/10/25.
 */

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import onetreeshopsapp.com.onetreeshops.R;
import onetreeshopsapp.com.onetreeshops.bean.GetShopsDataResult;
import onetreeshopsapp.com.onetreeshops.bean.SearchSortResult;


/**
 * Created by fiona on 2016/10/9.
 */
public class SortNameAdapter extends BaseAdapter {
    private ArrayList<SearchSortResult.ResultBean.ResBean> sorts;
    private Context context;
    private int currentPosition;
    private int currentSortId;

    public int getCurrentSortId() {
        return currentSortId;
    }

    public void setCurrentSortId(int currentSortId) {
        this.currentSortId = currentSortId;
    }

    public int getCurrentPosition() {
        return currentPosition;
    }

    public void setCurrentPosition(int currentPosition) {
        this.currentPosition = currentPosition;
        currentSortId =  sorts.get(currentPosition).getCategory_id();

    }

    public SortNameAdapter(Context context,ArrayList<SearchSortResult.ResultBean.ResBean> shops) {
        this.sorts = shops;
        this.context = context;
    }

    @Override
    public int getCount() {
        return sorts.size();
    }

    @Override
    public Object getItem(int position) {
        return sorts.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        ViewHold viewHold;
        SearchSortResult.ResultBean.ResBean resBean = sorts.get(position);
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
        viewHold.tvShopName.setText(resBean.getProduct_category());

        //选中颜色的修改
        if (currentPosition == position) {
            view.setBackgroundResource(R.color.maincolor);
//            viewHold.vCutOff.setVisibility(View.GONE);
        } else {
            view.setBackgroundResource(R.color.bg_graylight_list);
//            viewHold.vCutOff.setVisibility(View.VISIBLE);
        }
        //设置选中门店选取货物的数量
       /*if (resBean.getAmount() > 0) {
            viewHold.tvAmount.setVisibility(View.VISIBLE);
           viewHold.tvAmount.setText(resBean.getAmount() + "");
        }*/
        return view;
    }

    private class ViewHold {
        TextView tvShopName;
        TextView tvAmount;
        View vCutOff;
    }
}
