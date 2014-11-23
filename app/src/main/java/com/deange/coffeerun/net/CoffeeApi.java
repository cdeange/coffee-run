package com.deange.coffeerun.net;

import com.deange.coffeerun.model.Group;
import com.deange.coffeerun.model.Order;
import com.deange.coffeerun.model.User;

import java.util.List;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Query;

public interface CoffeeApi {

    @POST("/user/create")
    public void createUser(@Query("name") final String name,
                           @Query("email") final String email,
                           @Query("token") final String token,
                           final Callback<User> callback);

    @POST("/group/create")
    public void createGroup(@Query("uid") final int uid,
                            @Query("name") final String name,
                            final Callback<Group> callback);

    @GET("/user/find_groups")
    public void getGroups(@Query("uid") final int uid,
                          final Callback<List<Group>> callback);

    @POST("/group/request")
    public void makeRequest(@Query("uid") final int uid,
                            @Query("gid") final int gid,
                            @Query("item") final String item,
                            @Query("size") final String size,
                            @Query("price") final double price,
                            @Query("details") final String details,
                            final Callback<Order> callback);

    @GET("/group/requests")
    public void getOrders(@Query("gid") final int gid,
                          final Callback<List<Order>> callback);

}
