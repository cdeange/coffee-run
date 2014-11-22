package com.deange.coffeerun.net;

import retrofit.RequestInterceptor;
import retrofit.RestAdapter;

public class ApiBuilder {

    public static final int VERSION = 2;
    public static final String BASE_URL = "https://api.yourdomainhere.ca/v" + VERSION;
    private static final String DATA_FORMAT = "json";

    public static final String API_KEY = "key";
    public static final String FORMAT = "format";

    /* package */ static ApiModelConverter sConverter = ApiModelConverter.newInstance();

    public static <T> T build(final Class<T> clazz) {

        return new RestAdapter.Builder()
                .setEndpoint(BASE_URL)
                .setRequestInterceptor(new ApiInterceptor())
                .setConverter(sConverter)
                .build()
                .create(clazz);
    }

    private static class ApiInterceptor implements RequestInterceptor {

        private ApiInterceptor() {

        }

        @Override
        public void intercept(final RequestFacade requestFacade) {

            // Ensure the API has been properly initialized
            requestFacade.addQueryParam(API_KEY, "API_KEY");
            requestFacade.addEncodedPathParam(FORMAT, DATA_FORMAT);
        }

    }

}
