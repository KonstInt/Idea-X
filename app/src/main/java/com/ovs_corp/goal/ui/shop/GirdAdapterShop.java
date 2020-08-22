package com.ovs_corp.goal.ui.shop;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ovs_corp.goal.R;

import java.util.ArrayList;

public class GirdAdapterShop extends BaseAdapter {
    private ArrayList<String> data;
    private Context context;
    private LayoutInflater inflater;

    public GirdAdapterShop(Context context, ArrayList<String> data) {

        this.data = data;
        this.context = context;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View girdViev = convertView;
        if (convertView == null)
        {
            inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            girdViev = inflater.inflate(R.layout.shop_item, null);
        }

        TextView btn = girdViev.findViewById(R.id.shop_item_ds);
        btn.setText(data.get(position));
        return girdViev;
    }
}
