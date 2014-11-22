package com.deange.coffeerun.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.deange.coffeerun.R;
import com.melnykov.fab.FloatingActionButton;

public class GroupFragment
    extends Fragment {

    private FloatingActionButton mCheckinButton;

    public static Fragment newInstance(final int position) {
        return new GroupFragment();
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
    }
}
