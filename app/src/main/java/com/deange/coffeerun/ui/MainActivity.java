package com.deange.coffeerun.ui;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.deange.coffeerun.R;
import com.deange.coffeerun.model.Group;
import com.deange.coffeerun.model.User;
import com.deange.coffeerun.net.ApiBuilder;
import com.deange.coffeerun.net.CoffeeApi;
import com.deange.coffeerun.util.GsonController;
import com.deange.coffeerun.util.Utils;
import com.facebook.Session;
import com.facebook.SessionState;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class MainActivity extends FacebookActivity
        implements NavigationDrawerFragment.OnDrawerItemSelectedListener {

    private static final String FRAGMENT_TAG = "FRAGMENT";

    private NavigationDrawerFragment mNavigationDrawerFragment;
    private String mTitle;
    private Toolbar mToolbar;

    private final CoffeeApi mApi = ApiBuilder.build(CoffeeApi.class);

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

        getGroupsForUser();
    }

    @Override
    protected void onSessionStateChange(final Session session, final SessionState state, final Exception exception) {
        if (state.isClosed()) {
            User.setUser(null);
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
            getMenuInflater().inflate(R.menu.main, menu);
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    private void createGroup(final String name) {
        mApi.createGroup(User.getUser().uid, name, new Callback<Group>() {
            @Override
            public void success(final Group group, final Response response) {
                getGroupsForUser();
            }

            @Override
            public void failure(final RetrofitError error) {
                Toast.makeText(
                        MainActivity.this, "Could not create group", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getGroupsForUser() {
        mApi.getGroups(User.getUser().uid, new Callback<List<Group>>() {
            @Override
            public void success(final List<Group> groups, final Response response) {
                String body = null;
                try {
                    body = Utils.streamToString(response.getBody().in());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                mNavigationDrawerFragment.setGroups(groups);
            }

            @Override
            public void failure(final RetrofitError error) {
                Toast.makeText(MainActivity.this,
                        "Could not download group list", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.menu_create_group) {

            final View root = getLayoutInflater().inflate(R.layout.dialog_create_group, null);
            new AlertDialog.Builder(this)
                    .setView(root)
                    .setTitle(R.string.menu_add_group)
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(final DialogInterface dialog, final int which) {
                            createGroup(((EditText)
                                    root.findViewById(R.id.group_name_edit)).getText().toString());
                        }
                    })
                    .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(final DialogInterface dialog, final int which) {
                            dialog.dismiss();
                        }
                    })
                    .show();
        }
        return super.onOptionsItemSelected(item);
    }

}
