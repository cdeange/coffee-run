package com.deange.coffeerun.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.deange.coffeerun.R;
import com.deange.coffeerun.model.Order;

import java.util.List;

public class OrdersAdapter extends BaseAdapter {

    private final Context mContext;
    private final List<Order> mOrders;

    public OrdersAdapter(final Context context, final List<Order> orders) {
        mContext = context;
        mOrders = orders;
    }

    @Override
    public int getCount() {
        return mOrders.size();
    }

    @Override
    public Order getItem(final int position) {
        return mOrders.get(position);
    }

    @Override
    public long getItemId(final int position) {
        return position;
    }

    @Override
    public View getView(final int position, final View convertView, final ViewGroup parent) {
        final View view;
        if (convertView != null) {
            view = convertView;
        } else {
            view = LayoutInflater.from(mContext).inflate(R.layout.list_item_order, null);
        }

        final Order order = getItem(position);
        ((TextView) view.findViewById(R.id.item_name)).setText(order.item);
        ((TextView) view.findViewById(R.id.item_details)).setText(order.details);
        ((TextView) view.findViewById(R.id.item_info)).setText(order.size + " ($"
                + String.format("%.02f", order.price) + ")");

        return view;
    }
}
