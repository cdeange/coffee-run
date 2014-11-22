package com.deange.coffeerun.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.deange.coffeerun.R;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.widget.LoginButton;


public class LoginActivity extends FacebookActivity {

    private static final String TAG = LoginActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final LoginButton authButton = (LoginButton) findViewById(R.id.facebook_login_button);
        authButton.setPublishPermissions("email", "public_profile");
    }

    @Override
    protected void onSessionStateChange(
            final Session session, final SessionState state, final Exception exception) {
        if (state.isOpened()) {
            Log.i(TAG, "Logged in...");
            onUserLoggedIn();
        }
    }

    private void onUserLoggedIn() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

}



