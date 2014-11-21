package com.deange.coffeerun.ui;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.deange.coffeerun.R;
import com.facebook.AppEventsLogger;


public class LoginActivity
        extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

    }

    @Override
    protected void onResume() {
        super.onResume();
        AppEventsLogger.activateApp(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        AppEventsLogger.deactivateApp(this);
    }

    public void attemptLogin() {

    }

}



