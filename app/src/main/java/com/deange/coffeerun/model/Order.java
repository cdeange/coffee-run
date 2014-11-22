package com.deange.coffeerun.model;

import com.google.gson.annotations.SerializedName;

public class Order {

    @SerializedName("orid")
    int orid;

    @SerializedName("item")
    String item;

    @SerializedName("size")
    String size;

    @SerializedName("price")
    double price;

    @SerializedName("details")
    String details;

}
