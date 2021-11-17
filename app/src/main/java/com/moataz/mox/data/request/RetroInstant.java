package com.moataz.mox.data.request;

import com.moataz.mox.data.api.APIService;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetroInstant {

    public static String BASE_URL = "https://api.rss2json.com/";
    private static Retrofit retrofit;

    public static APIService getRetroMediumClient() {

        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();
        }
        return retrofit.create(APIService.class);
    }
}