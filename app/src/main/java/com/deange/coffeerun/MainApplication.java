package com.deange.coffeerun;

import android.app.Application;

import com.deange.coffeerun.model.User;

public class MainApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        User.init(getApplicationContext());

    }
}
