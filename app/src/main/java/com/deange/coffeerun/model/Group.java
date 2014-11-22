package com.deange.coffeerun.model;

import com.google.gson.annotations.SerializedName;

public class Group {

    @SerializedName("gid")
    public int gid;

    @SerializedName("name")
    public String name;

    @Override
    public String toString() {
        return name;
    }
}
