package com.defate.mac.common_android.widget.address;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.defate.mac.common_android.R;

import java.util.List;

/**
 * 地址控件适配器
 */
public class AddressAdapter extends BaseAdapter {

    private Context context;
    private ViewHolder viewHolder;
    private List<AddressItemData> mDBlist;

    public AddressAdapter(Context context, List<AddressItemData> mDBlist){
        this.context = context;
        this.mDBlist = mDBlist;
    }

    public void setData(List<AddressItemData> mDBlist){
        this.mDBlist = mDBlist;
    }

    @Override
    public int getCount() {
        return mDBlist.size();
    }

    @Override
    public Object getItem(int position) {
        return mDBlist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.widget_item_address, null);
            viewHolder.name_txt = convertView.findViewById(R.id.name_txt);
            viewHolder.choice_img = convertView.findViewById(R.id.choice_img);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }

        if(mDBlist != null && mDBlist.size() > 0){
            viewHolder.name_txt.setText(mDBlist.get(position).getAddressName());
            if(mDBlist.get(position).isState()){
                viewHolder.choice_img.setVisibility(View.VISIBLE);
                viewHolder.name_txt.setTextColor(context.getResources().getColor(R.color.widget_theme_color));
            }else{
                viewHolder.name_txt.setTextColor(context.getResources().getColor(R.color.widget_address_txt_color));
                viewHolder.choice_img.setVisibility(View.GONE);
            }
        }
        return convertView;
    }


    private class ViewHolder{
        TextView name_txt;
        ImageView choice_img;
    }
}
