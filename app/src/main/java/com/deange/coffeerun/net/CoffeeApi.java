package com.deange.coffeerun.net;

import com.deange.coffeerun.model.User;

import retrofit.Callback;
import retrofit.http.POST;
import retrofit.http.Query;

public interface CoffeeApi {

    @POST("/tims/user/create")
    public void createUser(@Query("name") final String name,
                           @Query("email") final String email,
                           @Query("token") final String token,
                           final Callback<User> callback);

}
