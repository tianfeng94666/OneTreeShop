package onetreeshopsapp.com.onetreeshops.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.opengl.GLDebugHelper;
import android.util.DisplayMetrics;
import android.view.ContextThemeWrapper;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import onetreeshopsapp.com.onetreeshops.R;
import onetreeshopsapp.com.onetreeshops.activity.SearchproductResultActivity;
import onetreeshopsapp.com.onetreeshops.activity.SelectAdressActivity;
import onetreeshopsapp.com.onetreeshops.bean.MyAddresRecord;
import onetreeshopsapp.com.onetreeshops.bean.ProductInfo;
import onetreeshopsapp.com.onetreeshops.utils.HttpValue;
import onetreeshopsapp.com.onetreeshops.utils.ToastUtils;

/**
 * Created by fiona on 2016/10/27.
 */
public class SearchProductAdapter extends BaseAdapter {
    private Context context;
    private List<ProductInfo> productInfos;;
    private int CHOOSE_CODE = 3;


    public SearchProductAdapter(Context context, List<ProductInfo> productInfos) {
        this.context = context;
        this.productInfos = productInfos;
    }

    @Override
    public int getCount() {
        return productInfos.size();
    }

    @Override
    public Object getItem(int position) {
        return productInfos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHold viewHold;
        final ProductInfo productInfo = productInfos.get(position);
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.item_search_product, null);
            viewHold = new ViewHold();
            viewHold.ivProductImage = (ImageView) convertView.findViewById(R.id.iv_product_image);
            viewHold.tvProductName = (TextView) convertView.findViewById(R.id.tv_product_name);
            viewHold.tvProductPrice = (TextView) convertView.findViewById(R.id.tv_product_price);
            viewHold.tvProductUnit = (TextView) convertView.findViewById(R.id.tv_product_unit);
            viewHold.ivShoppingcar = (ImageView) convertView.findViewById(R.id.iv_shoppingcar);
            convertView.setTag(viewHold);
        } else {
            viewHold = (ViewHold) convertView.getTag();
        }

        Glide.with(context)
                .load(HttpValue.getHttp() + productInfo.getImage())
                .into(viewHold.ivProductImage);
        viewHold.tvProductName.setText(productInfo.getName());
        viewHold.tvProductPrice.setText(productInfo.getPrice() + "");
        viewHold.tvProductUnit.setText("/" + productInfo.getUom());
        viewHold.ivShoppingcar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               PopupWindow popupWindow = ((SearchproductResultActivity)context).popwindow(productInfo);
                popupWindow.showAtLocation(v, Gravity.BOTTOM, 0, 0);
            }
        });
        return convertView;
    }

    private class ViewHold {
        ImageView ivProductImage;
        TextView tvProductName;
        TextView tvProductPrice;
        TextView tvProductUnit;
        ImageView ivShoppingcar;
    }






}
