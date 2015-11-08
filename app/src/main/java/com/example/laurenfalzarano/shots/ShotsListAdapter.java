package com.example.laurenfalzarano.shots;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by laurenfalzarano on 11/6/15.
 */
public class ShotsListAdapter extends BaseAdapter {

    private Context context;
    private List<String> names = new ArrayList<>();

    public ShotsListAdapter(Context context) {
        this.context = context;
        this.names = PreferencesManager.get().getShotsArray();
    }

    @Override
    public int getCount() {
        return names.size();
    }

    @Override
    public String getItem(int position) {
        return names.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public static class ViewHolder {
        @Bind(R.id.count) public TextView shotsCount;
        @Bind(R.id.list_name)TextView listName;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    public View getView(int position, View view, ViewGroup parent) {
        final ViewHolder holder;
        if (view == null) {
            LayoutInflater vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = vi.inflate(R.layout.shots_list_cell, parent, false);
            holder = new ViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        String count = Integer.toString(PreferencesManager.get().getShots(names.get(position)));

        holder.listName.setText(names.get(position));
        holder.shotsCount.setText(count);

        return view;
    }
}
