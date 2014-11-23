package com.deange.coffeerun.model;

import com.google.gson.annotations.SerializedName;

public class Order {

    @SerializedName("orid")
    public int orid;

    @SerializedName("item")
    public String item;

    @SerializedName("size")
    public String size;

    @SerializedName("price")
    public double price;

    @SerializedName("details")
    public String details;

}
