package onetreeshopsapp.com.onetreeshops.adapter.recycleAdapter;


import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import onetreeshopsapp.com.onetreeshops.R;
import onetreeshopsapp.com.onetreeshops.bean.SearchSortResult;
import onetreeshopsapp.com.onetreeshops.interfaces.MyItemClickListenerInterface;

/**
 * Created by 田丰 on 2016/12/12.
 */

public class SortAdapter extends RecyclerView.Adapter<SortAdapter.MyViewHolder> {
    private MyItemClickListenerInterface myItemClickListenerInterface = null;
    private ArrayList<SearchSortResult.ResultBean.ResBean> sorts;
    private Context context;
    private int currentPosition;
    private int currentSortId;
    private ArrayList<Boolean> isClicks;

    public SortAdapter(Context context, ArrayList<SearchSortResult.ResultBean.ResBean> shops) {
        this.sorts = shops;
        this.context = context;
        isClicks = new ArrayList<>();
        for (int i = 0; i < sorts.size(); i++) {
            isClicks.add(false);
        }
    }

    @Override
    public SortAdapter.MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_type,
                viewGroup, false);
        return new SortAdapter.MyViewHolder(view, myItemClickListenerInterface);
    }

    @Override
    public void onBindViewHolder(SortAdapter.MyViewHolder myViewHolder, int i) {
        SearchSortResult.ResultBean.ResBean resBean = sorts.get(i);
        myViewHolder.tvShopName.setText(resBean.getProduct_category());
        /*if (isClicks.get(i)) {
            myViewHolder.llItemSort.setBackgroundResource(R.color.maincolor);
        } else {
            myViewHolder.llItemSort.setBackgroundResource(R.color.bg_graylight_list);
        }*/
        if (isClicks.get(i)){
            myViewHolder.itemView.setBackgroundColor(Color.WHITE);
        }else {
            myViewHolder.itemView.setBackgroundColor(Color.TRANSPARENT);
        }
        if(resBean.getAmount()>0){
            myViewHolder.tvAmount.setText(resBean.getAmount()+"");
            myViewHolder.tvAmount.setVisibility(View.VISIBLE);
        }else {
            myViewHolder.tvAmount.setVisibility(View.GONE);
        }
    }

    /**
     * 设置Item点击监听
     *
     * @param listener
     */
    public void setOnItemClickListener(MyItemClickListenerInterface listener) {
        this.myItemClickListenerInterface = listener;
    }

    @Override
    public int getItemCount() {
        return sorts == null ? 0 : sorts.size();
    }

    public int getCurrentPosition() {
        return currentPosition;
    }

    public void setCurrentPosition(int currentPosition) {
        this.currentPosition = currentPosition;
        currentSortId = sorts.get(currentPosition).getCategory_id();
    }

    public int getCurrentSortId() {
        return currentSortId;
    }

    public void setCurrentSortId(int currentSortId) {
        this.currentSortId = currentSortId;
    }

    public void setSortAmount(int category_id, int j) {
        for(int i=0;i<sorts.size();i++){
            SearchSortResult.ResultBean.ResBean resBean = sorts.get(i);
            if(resBean.getCategory_id()==category_id){
                resBean.setAmount(resBean.getAmount()+j);
                break;
            }
        }
        notifyDataSetChanged();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tvShopName;
        TextView tvAmount;
        //LinearLayout llItemSort;
        private MyItemClickListenerInterface mmyItemClickListenerInterface = null;
        View vCutOff;

        public MyViewHolder(View itemView, MyItemClickListenerInterface myItemClickListenerInterface) {
            super(itemView);
            //llItemSort = (LinearLayout) itemView.findViewById(R.id.ll_item_sort);
            tvAmount = (TextView) itemView.findViewById(R.id.tv_amount);
            tvShopName = (TextView) itemView.findViewById(R.id.item_shop_name);
            this.mmyItemClickListenerInterface = myItemClickListenerInterface;
            itemView.setOnClickListener(this);
        }


        @Override
        public void onClick(View v) {
            if (mmyItemClickListenerInterface != null) {
                mmyItemClickListenerInterface.onItemClick(v, getAdapterPosition());
            }
        }
    }

    public void setClick(int positin) {
        for (int i = 0; i < isClicks.size(); i++) {
            isClicks.set(i, false);
        }
        isClicks.set(positin, true);
    }
}

