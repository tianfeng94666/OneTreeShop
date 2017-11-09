package onetreeshopsapp.com.onetreeshops.adapter.recycleAdapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import de.greenrobot.event.EventBus;
import onetreeshopsapp.com.onetreeshops.R;
import onetreeshopsapp.com.onetreeshops.bean.EventProduct;
import onetreeshopsapp.com.onetreeshops.bean.ProductInfo;
import onetreeshopsapp.com.onetreeshops.interfaces.MyItemClickListenerInterface;
import onetreeshopsapp.com.onetreeshops.utils.HttpValue;

/**
 * Created by 田丰 on 2016/12/13.
 */

public class SortProductAdapter extends RecyclerView.Adapter<SortProductAdapter.MyViewHolder> {
    private List<ProductInfo> shops;
    private Context context;
    private FoodActionCallback callback;

    public SortProductAdapter( Context context, List<ProductInfo> shops) {
        this.shops = shops;
        this.context = context;
    }
    public void setCallback(FoodActionCallback callback){
        this.callback =callback;
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_shop_product,
                parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder viewHold, final int position) {
       final ProductInfo resBean = shops.get(position);
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
                amount = resBean.getAmount();
                String amountstring = viewHold.tv_product_amount.getText().toString();
                if(callback!=null) {
                    callback.addAction(v);
                }
                if (amount==0) {
                    amount = 0;
                } else {
                    amount = Integer.parseInt(amountstring);
                }
                ++amount;
                if (amount > 0) {
                    viewHold.ib_product_reduce.setAnimation(getShowAnimation());
                    viewHold.tv_product_amount.setVisibility(View.VISIBLE);
                    viewHold.ib_product_reduce.setVisibility(View.VISIBLE);
                } else {
                    viewHold.tv_product_amount.setVisibility(View.INVISIBLE);
                    viewHold.ib_product_reduce.setVisibility(View.INVISIBLE);
                }
                resBean.setAmount(amount);
                viewHold.tv_product_amount.setText(amount+"");
                EventBus.getDefault().post(new EventProduct("sortAdd", (ProductInfo) getItem(position)));
            }
        });
        viewHold.ib_product_reduce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //int amount = Integer.parseInt(viewHold.tv_product_amount.getText().toString());
               int amount = resBean.getAmount();
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
                EventBus.getDefault().post(new EventProduct("sortReduce", (ProductInfo) getItem(position)));
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
    }

    public Object getItem(int position) {
        return shops.get(position);
    }

    @Override
    public int getItemCount() {
       return shops.size();
    }
    class MyViewHolder extends RecyclerView.ViewHolder{
        ImageView iv_product_image;
        TextView tv_product_name;
        TextView tv_product_unit;
        TextView tv_product_price;
        ImageView ib_product_reduce;
        ImageView ib_product_add;
        TextView tv_product_amount;
        private MyItemClickListenerInterface mmyItemClickListenerInterface = null;
        View vCutOff;

        public MyViewHolder(View itemView) {
            super(itemView);
            tv_product_name = (TextView) itemView.findViewById(R.id.tv_product_name);
            iv_product_image = (ImageView) itemView.findViewById(R.id.iv_product_image);
            tv_product_unit = (TextView) itemView.findViewById(R.id.tv_product_unit);
            tv_product_price = (TextView) itemView.findViewById(R.id.tv_product_price);
            ib_product_add = (ImageView) itemView.findViewById(R.id.ib_product_add);
            ib_product_reduce = (ImageView) itemView.findViewById(R.id.ib_product_reduce);
            tv_product_amount = (TextView) itemView.findViewById(R.id.tv_product_amount);

        }

    }
    public interface FoodActionCallback {
        void addAction(View view);
    }
    private Animation getShowAnimation(){
        AnimationSet set = new AnimationSet(true);
        RotateAnimation rotate = new RotateAnimation(0,720,RotateAnimation.RELATIVE_TO_SELF,0.5f,RotateAnimation.RELATIVE_TO_SELF,0.5f);
        set.addAnimation(rotate);
        TranslateAnimation translate = new TranslateAnimation(
                TranslateAnimation.RELATIVE_TO_SELF,2f
                ,TranslateAnimation.RELATIVE_TO_SELF,0
                ,TranslateAnimation.RELATIVE_TO_SELF,0
                ,TranslateAnimation.RELATIVE_TO_SELF,0);
        set.addAnimation(translate);
        AlphaAnimation alpha = new AlphaAnimation(0,1);
        set.addAnimation(alpha);
        set.setDuration(500);
        return set;
    }

}
