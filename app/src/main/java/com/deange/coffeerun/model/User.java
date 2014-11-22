package com.deange.coffeerun.model;

import android.content.Context;
import android.content.SharedPreferences;

import com.deange.coffeerun.util.GsonController;
import com.google.gson.annotations.SerializedName;

public class User {

    private static final String KEY_USER = "USER";

    private static SharedPreferences sPrefs;
    private static User sUser;

    public static void init(final Context context) {
        sPrefs = context.getSharedPreferences("current_user", Context.MODE_PRIVATE);
    }

    public User(final String name) {
        this.name = name;
    }

    @SerializedName("uid")
    public int uid;

    @SerializedName("name")
    public String name;

    @SerializedName("email")
    public String email;

    @SerializedName("token")
    public String token;

    @SerializedName("reg_id")
    public String registrationId;

    public static void setUser(final User user) {
        sUser = user;
        sPrefs.edit().putString(KEY_USER,
                GsonController.getInstance().toJson(user))
                .apply();
    }

    public static User getUser() {
        if (sUser == null) {
            final String userJson = sPrefs.getString(KEY_USER, null);
            if (userJson == null) {
                return null;
            }
            sUser = GsonController.getInstance().fromJson(userJson, User.class);
        }

        return sUser;
    }
}
