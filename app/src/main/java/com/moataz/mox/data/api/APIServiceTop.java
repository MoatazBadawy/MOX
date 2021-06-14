package com.moataz.mox.data.api;

import com.moataz.mox.data.model.ArticlesModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface APIServiceTop {

    @GET("v2/top-headlines")
    Call<List<ArticlesModel>> getTopObjectsList(
            @Query("country")
            String countryCode,
            @Query("apikey")
            String API_KEY
    );
}