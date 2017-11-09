package onetreeshopsapp.com.onetreeshops.adapter.recycleAdapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import onetreeshopsapp.com.onetreeshops.R;
import onetreeshopsapp.com.onetreeshops.bean.GetShopEvaluateResult;
import onetreeshopsapp.com.onetreeshops.utils.HttpValue;
import onetreeshopsapp.com.onetreeshops.utils.view.CircleImageView;


public class ShopEvaluateAdapter extends RecyclerView.Adapter<ShopEvaluateAdapter.MyViewHolder> {
    private List<GetShopEvaluateResult.ResultBean.ResBean.DataBean> resBeen;
    private Context context;

    public ShopEvaluateAdapter(Context context, List<GetShopEvaluateResult.ResultBean.ResBean.DataBean> resBeen) {
        this.resBeen = resBeen;
        this.context = context;
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_shop_pingjia,
                parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder viewHold, final int position) {

        Glide.with(context)
                .load(HttpValue.getHttp() +resBeen.get(position).getUser_image())
                .into(viewHold.iv_guke_image);
        String username = resBeen.get(position).getUser_name().toString();
        viewHold.tv_shop_pingjia_gukename.setText(username.replaceAll("(\\d{3})\\d{4}(\\d{4})","$1****$2"));
        viewHold.tv_shop_pingjia_time.setText(resBeen.get(position).getCreate_time());
        float score = ((float) resBeen.get(position).getPro_score() + (float) resBeen.get(position).getService_score())/2;
        viewHold.rb_guke_pingfen.setRating(score);
        viewHold.tv_guke_pinjianeiyong.setText(resBeen.get(position).getContent());
        viewHold.tv_shangjia_huifh.setText("商家回复：亲，欢迎再次光临！");
    }

    public Object getItem(int position) {
        return resBeen.get(position);
    }

    @Override
    public int getItemCount() {
       return resBeen.size();
    }
    class MyViewHolder extends RecyclerView.ViewHolder{
        CircleImageView iv_guke_image;
        TextView tv_shop_pingjia_gukename;
        RatingBar rb_guke_pingfen;
        TextView tv_guke_pinjianeiyong;
        TextView tv_shangjia_huifh;
        TextView tv_shop_pingjia_time;

        public MyViewHolder(View itemView) {
            super(itemView);
            iv_guke_image = (CircleImageView) itemView.findViewById(R.id.iv_guke_image);
            tv_shop_pingjia_gukename = (TextView) itemView.findViewById(R.id.tv_shop_pingjia_gukename);
            rb_guke_pingfen = (RatingBar) itemView.findViewById(R.id.rb_guke_pingfen);
            tv_guke_pinjianeiyong = (TextView) itemView.findViewById(R.id.tv_guke_pinjianeiyong);
            tv_shangjia_huifh = (TextView) itemView.findViewById(R.id.tv_shangjia_huifh);
            tv_shop_pingjia_time = (TextView) itemView.findViewById(R.id.tv_shop_pingjia_time);

        }

    }

}
