package com.deange.coffeerun.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.deange.coffeerun.R;
import com.deange.coffeerun.model.User;
import com.deange.coffeerun.net.ApiBuilder;
import com.deange.coffeerun.net.CoffeeApi;
import com.deange.coffeerun.util.Utils;
import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.model.GraphUser;
import com.facebook.widget.LoginButton;

import java.io.IOException;

import retrofit.Callback;
import retrofit.RetrofitError;


public class LoginActivity
        extends FacebookActivity
        implements Request.GraphUserCallback, Callback<User> {

    private static final String TAG = LoginActivity.class.getSimpleName();

    private final CoffeeApi mApi = ApiBuilder.build(CoffeeApi.class);
    private View mProgressView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final LoginButton authButton = (LoginButton) findViewById(R.id.facebook_login_button);
        authButton.setPublishPermissions("email", "public_profile");

        mProgressView = findViewById(R.id.progress_view);
    }

    @Override
    protected void onResume() {
        super.onResume();
        final Session session = Session.getActiveSession();
        if (session != null && session.isOpened() && User.getUser() != null) {
            startMainActivity();
        }
    }

    @Override
    protected void onSessionStateChange(
            final Session session, final SessionState state, final Exception exception) {
        if (state.isOpened()) {
            Log.i(TAG, "Logged in...");
            onUserLoggedInOnFacebook();
        }
    }

    private void setLoading(final boolean isLoading) {
        mProgressView.setVisibility(isLoading ? View.VISIBLE : View.GONE);
    }

    private boolean isLoading() {
        return mProgressView.getVisibility() == View.VISIBLE;
    }

    private void onUserLoggedInOnFacebook() {

        // Only allow 1 sign in flow at a time
        if (isLoading()) return;

        setLoading(true);

        Request.newMeRequest(Session.getActiveSession(), this).executeAsync();
    }

    @Override
    public void onCompleted(final GraphUser graphUser, final Response response) {

        if (User.getUser() != null) return;

        final User user = new User(graphUser.getName());
        user.email = String.valueOf(graphUser.getProperty("email"));
        user.token = Session.getActiveSession().getAccessToken();

        mApi.createUser(user.name, user.email, user.token, this);
    }

    @Override
    public void success(final User user, final retrofit.client.Response response) {
        // User was created!
        setLoading(false);

        String body = null;
        try {
            body = Utils.streamToString(response.getBody().in());
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Save the current user now
        User.setUser(user);

        startMainActivity();
    }

    private void startMainActivity() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    @Override
    public void failure(final RetrofitError error) {

        // User creation failed
        setLoading(false);

        Session.getActiveSession().closeAndClearTokenInformation();
        User.setUser(null);

        error.printStackTrace();
        Toast.makeText(this, "Could not log in. Please try again.", Toast.LENGTH_LONG).show();

    }
}
