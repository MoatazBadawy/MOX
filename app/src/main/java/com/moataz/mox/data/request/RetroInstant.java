package com.moataz.mox.data.request;

import android.app.Service;

import com.moataz.mox.data.api.APIService;

import java.util.concurrent.TimeUnit;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetroInstant {

    public static String BASE_URL = "https://api.rss2json.com/";

    private final static HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
    private static OkHttpClient okHttpClient;
    public static Retrofit getRetroMediumClient() {
        interceptor.level(HttpLoggingInterceptor.Level.BODY);
        interceptor.level(HttpLoggingInterceptor.Level.BASIC);
        interceptor.level(HttpLoggingInterceptor.Level.HEADERS);
        if (okHttpClient == null) {
            okHttpClient = new OkHttpClient.Builder()
                    .addInterceptor(interceptor)
                    .build();
        }
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();
        return retrofit;
    }
}