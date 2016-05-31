package com.sridevi.sample2.customlist;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.sridevi.sample2.customlist.model.Item;

import com.sridevi.sample2.R;

import java.util.List;

/**
 * Created by sridevi on 3/27/15.
 */
public class CustomListAdapter extends BaseAdapter {

    private Activity activity;
    private List<Item> items;
    private LayoutInflater inflater;

    public CustomListAdapter(Activity activity, List<Item> items){
        this.activity = activity;
        this.items = items;
    }

    private class ViewHolder {
        TextView title;
        TextView description;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (inflater == null)
            inflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null){
            convertView = inflater.inflate(R.layout.customlist_item, null);
            holder = new ViewHolder();
            holder.title = (TextView) convertView.findViewById(R.id.title);
            holder.description = (TextView) convertView.findViewById(R.id.description);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        Item item = items.get(position);
        holder.title.setText(item.getTitle());
        holder.description.setText(item.getDescription());
        return convertView;
    }

    public void removeAll() {
        int count = getCount();
        for (int i = 0; i < count; i++) {
            items.remove(0);
        }
        notifyDataSetChanged();
    }
}

