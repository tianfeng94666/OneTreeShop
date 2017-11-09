package onetreeshopsapp.com.onetreeshops.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import onetreeshopsapp.com.onetreeshops.R;
import onetreeshopsapp.com.onetreeshops.bean.GetShopsDataResult;
import onetreeshopsapp.com.onetreeshops.utils.HttpValue;

/**
 * Created by fiona on 2016/10/9.
 */
public class ShopsInfodapter extends BaseAdapter {
    private ArrayList<GetShopsDataResult.ResultBean.ResBean> shops;
    private Context context;
    private int currentPosition;

    public int getCurrentPosition() {
        return currentPosition;
    }

    public void setCurrentPosition(int currentPosition) {
        this.currentPosition = currentPosition;
    }

    public ShopsInfodapter(Context context) {
        this.context = context;
    }
    public void setList(ArrayList<GetShopsDataResult.ResultBean.ResBean> shops){
        this.shops = shops;
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
            view = View.inflate(context, R.layout.item_shop_info, null);
            viewHold.shop_info_image = (ImageView) view.findViewById(R.id.shop_info_image);
            viewHold.shop_info_shopname = (TextView) view.findViewById(R.id.shop_info_shopname);
            viewHold.shop_info_rb = (RatingBar) view.findViewById(R.id.shop_info_rb);
            viewHold.shop_info_pingfen = (TextView) view.findViewById(R.id.shop_info_pingfen);
            viewHold.shop_info_saled = (TextView) view.findViewById(R.id.shop_info_saled);
            viewHold.shop_info_peisong_cost = (TextView) view.findViewById(R.id.shop_info_peisong_cost);
            viewHold.shop_info_juli = (TextView) view.findViewById(R.id.shop_info_juli);
            viewHold.shop_info_huodong_viewline = view.findViewById(R.id.shop_info_huodong_viewline);
            viewHold.shop_info_huodong = (ListView) view.findViewById(R.id.shop_info_huodong);
            view.setTag(viewHold);
        } else {
            viewHold = (ViewHold) view.getTag();
        }
        Glide.with(context)
                .load(HttpValue.getHttp() + resBean.getStore_pic())
                .error(R.mipmap.icon)
                .centerCrop()
                .into(viewHold.shop_info_image);
        viewHold.shop_info_shopname.setText(resBean.getStore_name());
        viewHold.shop_info_rb.setRating(resBean.getScore());
        viewHold.shop_info_pingfen.setText(resBean.getScore()+"");
        viewHold.shop_info_saled.setText("月售"+resBean.getOrder_num()+"单");
        viewHold.shop_info_peisong_cost.setText("￥30起送/配送费5元,满100免");
        int distance_to_storen = resBean.getDistance_to_store();
        if (distance_to_storen<1000&&distance_to_storen!=0){
            viewHold.shop_info_juli.setText(distance_to_storen+"m");
        }else if (distance_to_storen>=1000){
            viewHold.shop_info_juli.setText(distance_to_storen/1000+"km");
        }else {
            viewHold.shop_info_juli.setText("未知");
        }


        return view;
    }

    private class ViewHold {
        ImageView shop_info_image;
        TextView shop_info_shopname;
        RatingBar shop_info_rb;
        TextView shop_info_pingfen;
        TextView shop_info_saled;
        TextView shop_info_peisong_cost;
        TextView shop_info_juli;
        View shop_info_huodong_viewline;
        ListView shop_info_huodong;

    }
}
