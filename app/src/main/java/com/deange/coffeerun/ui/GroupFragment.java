package com.deange.coffeerun.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.deange.coffeerun.R;
import com.deange.coffeerun.model.Order;
import com.deange.coffeerun.net.ApiBuilder;
import com.deange.coffeerun.net.CoffeeApi;
import com.melnykov.fab.FloatingActionButton;

import org.apache.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class GroupFragment
    extends ListFragment {

    private static final String KEY_GID = "GID";

    private FloatingActionButton mCheckinButton;

    private final CoffeeApi mApi = ApiBuilder.build(CoffeeApi.class);

    public static Fragment newInstance(final int gid) {
        final GroupFragment fragment = new GroupFragment();
        final Bundle args = new Bundle();
        args.putInt(KEY_GID, gid);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable final ViewGroup container,
            @Nullable final Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onViewCreated(final View view, @Nullable final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mCheckinButton = (FloatingActionButton) view.findViewById(R.id.floating_action_button);

        final int gid = getArguments().getInt(KEY_GID);
        mApi.getOrders(gid, new Callback<List<Order>>() {
            @Override
            public void success(final List<Order> orders, final Response response) {
                onOrdersLoaded(orders);
            }

            @Override
            public void failure(final RetrofitError error) {
                if (error.getResponse() != null
                        && error.getResponse().getStatus() == HttpStatus.SC_NOT_FOUND) {
                    onOrdersLoaded(new ArrayList<Order>());
                } else {
                    Toast.makeText(getActivity(),
                            "Could not fetch orders", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void onOrdersLoaded(final List<Order> orders) {
        setListAdapter(new OrdersAdapter(getActivity(), orders));
    }
}
