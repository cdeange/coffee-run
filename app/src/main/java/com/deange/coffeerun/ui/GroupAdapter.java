package com.deange.coffeerun.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.TextView;

import com.deange.coffeerun.R;
import com.deange.coffeerun.model.Group;

import java.util.List;

public class GroupAdapter extends BaseAdapter {

    private final Context mContext;
    private final List<Group> mGroups;

    public GroupAdapter(final Context context, final List<Group> groups) {
        mContext = context;
        mGroups = groups;
    }

    @Override
    public int getCount() {
        return mGroups.size();
    }

    @Override
    public Group getItem(final int position) {
        return mGroups.get(position);
    }

    @Override
    public long getItemId(final int position) {
        return mGroups.get(position).gid;
    }

    @Override
    public View getView(final int position, final View convertView, final ViewGroup parent) {

        final View view;
        if (convertView != null) {
            view = convertView;
        } else {
            view = LayoutInflater.from(mContext).inflate(R.layout.list_item_group, null);
        }

        final TextView nameView = (TextView) view.findViewById(android.R.id.text1);
        nameView.setText(getItem(position).name);

        return view;
    }
}
