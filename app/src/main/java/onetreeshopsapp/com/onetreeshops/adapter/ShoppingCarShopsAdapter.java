package onetreeshopsapp.com.onetreeshops.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import de.greenrobot.event.EventBus;
import onetreeshopsapp.com.onetreeshops.R;
import onetreeshopsapp.com.onetreeshops.bean.EventProduct;
import onetreeshopsapp.com.onetreeshops.bean.GetProductDateResult;
import onetreeshopsapp.com.onetreeshops.bean.ProductInfo;
import onetreeshopsapp.com.onetreeshops.utils.HttpValue;

/**
 * Created by fiona on 2016/10/14.
 */
public class ShoppingCarShopsAdapter extends BaseAdapter {
    private final Context context;
    List<ProductInfo> chooseProducts;
    private final HashMap<String, List<ProductInfo>> productLists = new HashMap<>();
    ArrayList<List<ProductInfo>> listproductlist = new ArrayList<List<ProductInfo>>();
    ArrayList<Object> listkey = new ArrayList<Object>();

    public ShoppingCarShopsAdapter(Context context, List<ProductInfo> chooseProducts) {
        this.context = context;
        this.chooseProducts = chooseProducts;
        settingChooseProducts(chooseProducts);
        for (String key : productLists.keySet()) {
            listproductlist.add(productLists.get(key));
            listkey.add(key);
        }
    }
    /**
     *将门店数据和产品数据分割开来
     * @param chooseProducts
     */
    private void settingChooseProducts(List<ProductInfo> chooseProducts) {
        for (int i = 0; i < chooseProducts.size(); i++) {
            ProductInfo resBean = chooseProducts.get(i);
            if (productLists.containsKey(resBean.getStore_name())) {
                List<ProductInfo> shopProductsList = productLists.get(resBean.getStore_name());
                shopProductsList.add(resBean);
            } else {
                List<ProductInfo> shopProductsList = new ArrayList<>();
                shopProductsList.add(resBean);
                productLists.put(resBean.getStore_name(), shopProductsList);
            }
        }
    }
    @Override
    public int getCount() {
        return listproductlist.size();
    }

    @Override
    public Object getItem(int position) {
        return listproductlist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        final ViewHold viewHold;
        boolean shopIsCheck;
        final List<ProductInfo> list = (List<ProductInfo>) listproductlist.get(position);
        if (view == null) {
            viewHold = new ViewHold();
            view = View.inflate(context, R.layout.item_shoppingcar_list, null);
            viewHold.cb_shop = (CheckBox) view.findViewById(R.id.cb_shop);
            viewHold.tv_shop_name = (TextView) view.findViewById(R.id.tv_shop_name);
            viewHold.lv_item_product_list = (ListView) view.findViewById(R.id.lv_item_product_list);
            view.setTag(viewHold);
        } else {
            viewHold = (ViewHold) view.getTag();
        }
        final ShoppingCarAdapter shoppingCarAdapter = new ShoppingCarAdapter(context, list);
        viewHold.lv_item_product_list.setAdapter(shoppingCarAdapter);
        setListViewHeightBasedOnChildren(viewHold.lv_item_product_list);
        viewHold.tv_shop_name.setText((CharSequence) listkey.get(position));
        viewHold.cb_shop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeState(list,viewHold.cb_shop.isChecked());
                shoppingCarAdapter.notifyDataSetChanged();
                EventBus.getDefault().post("sumMoney");
            }
        });
        if(list.size()>0){
            viewHold.cb_shop.setChecked(isAll(list));
        }

        return view;

    }

    public boolean isNoListAllChecked(List<ProductInfo> list) {
        boolean ischeck = true;
        for(int i = 0 ;i<list.size();i++){
           ProductInfo product = list.get(i);
            if(!product.isSelect()){
                ischeck = false;
                continue;
            }
        }
        if(ischeck){
            setListproductlist(new ArrayList<List<ProductInfo>>(getListproductlist()));
            notifyDataSetChanged();
        }
        return ischeck;
    }


    private boolean isAll(List<ProductInfo> list) {
        boolean isAll = true;
        for (int i = 0;i<list.size();i++){
            ProductInfo resBean = list.get(i);
            if(!resBean.isSelect()){
                isAll= false;
            }
        }
        return isAll;
    }

    private class ViewHold {
        CheckBox cb_shop;
        TextView tv_shop_name;
        ListView lv_item_product_list;
    }

    public void changeState(List<ProductInfo> list,boolean isSelct) {
        for(int i =0;i<list.size();i++){
            ProductInfo resBean = list.get(i);
            resBean.setSelect(isSelct);
        }
    }


    public void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            // pre-condition
            return;
        }

        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }


    public ArrayList<List<ProductInfo>> getListproductlist() {
        return listproductlist;
    }

    public void setListproductlist(ArrayList<List<ProductInfo>> listproductlist) {
        this.listproductlist = listproductlist;
    }
}
