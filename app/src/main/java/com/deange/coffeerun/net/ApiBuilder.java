package com.deange.coffeerun.net;

import retrofit.RequestInterceptor;
import retrofit.RestAdapter;

public class ApiBuilder {

    public static final int VERSION = 2;
    public static final String BASE_URL = "http://6e88941e.ngrok.com/api/tims";

    /* package */ static ApiModelConverter sConverter = ApiModelConverter.newInstance();

    public static <T> T build(final Class<T> clazz) {

        return new RestAdapter.Builder()
                .setEndpoint(BASE_URL)
                .setConverter(sConverter)
                .build()
                .create(clazz);
    }
}
