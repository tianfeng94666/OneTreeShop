package onetreeshopsapp.com.onetreeshops.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Switch;
import android.widget.TextView;

import java.util.List;

import onetreeshopsapp.com.onetreeshops.R;
import onetreeshopsapp.com.onetreeshops.bean.MyAddresRecord;


/**
 * Created by Admin on 2016/6/17.
 */
public class MyAddresInfoAdapter extends BaseAdapter {

    private Context context;
    private List<MyAddresRecord.Result.Res> list;

    public MyAddresInfoAdapter(Context context, List<MyAddresRecord.Result.Res> list) {
        this.context = context;
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = convertView;
        ViewHolder holder = new ViewHolder();
        if (view == null) {
            view = View.inflate(context, R.layout.item_myaddres_info, null);
            holder.tv_myaddres_name = (TextView) view.findViewById(R.id.tv_myaddres_name);
            holder.tv_myaddres_phone = (TextView) view.findViewById(R.id.tv_myaddres_phone);
            holder.tv_myaddres_info = (TextView) view.findViewById(R.id.tv_myaddres_info);
            holder.tv_myaddres_default = (TextView) view.findViewById(R.id.tv_myaddres_default);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        holder.tv_myaddres_name.setText(list.get(position).getName());
        holder.tv_myaddres_phone.setText(list.get(position).getTelephone());
        holder.tv_myaddres_info.setText(list.get(position).getAddress());
        if (list.get(position).isDefault_addr()){
            holder.tv_myaddres_default.setVisibility(View.VISIBLE);
        }else {
            holder.tv_myaddres_default.setVisibility(View.GONE);
        }
        return view;
    }
    class ViewHolder {
        TextView tv_myaddres_name;
        TextView tv_myaddres_phone;
        TextView tv_myaddres_info;
        TextView tv_myaddres_default;
    }
}
