package com.deange.coffeerun.ui;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.deange.coffeerun.R;
import com.facebook.Session;
import com.facebook.SessionState;

public class MainActivity extends FacebookActivity
        implements NavigationDrawerFragment.OnDrawerItemSelectedListener {

    private static final String FRAGMENT_TAG = "FRAGMENT";

    private NavigationDrawerFragment mNavigationDrawerFragment;
    private String mTitle;
    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitleTextColor(getResources().getColor(R.color.coffee_text));
        setSupportActionBar(mToolbar);

        findNavigationDrawer();

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout),
                mToolbar);
    }

    @Override
    protected void onSessionStateChange(final Session session, final SessionState state, final Exception exception) {
        if (state.isClosed()) {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        }
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {

        findNavigationDrawer();
        if (mNavigationDrawerFragment != null) {
            setTitle(String.valueOf(mNavigationDrawerFragment.getItem(position)));
        }

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container, GroupFragment.newInstance(position), FRAGMENT_TAG)
                .commit();
    }

    private void findNavigationDrawer() {
        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
    }

    @Override
    public void showGlobalContextActionBar() {
        setTitle(R.string.app_name);
    }

    public void setTitle(final String title) {
        mTitle = title;
        refreshActionBar();
    }

    @Override
    public void setTitle(final int titleId) {
        mTitle = getString(titleId);
        refreshActionBar();
    }

    public void refreshActionBar() {
        if (mToolbar != null) {
            mToolbar.setTitle(mTitle);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            refreshActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

}
