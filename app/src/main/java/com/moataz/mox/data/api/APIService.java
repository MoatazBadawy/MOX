package com.moataz.mox.data.api;


import com.moataz.mox.data.model.response.ArticleResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface APIService {

    @GET("v2/top-headlines")
    Call<ArticleResponse> getTopObjectsList(
            @Query("country")
            String countryCode,
            @Query("apikey")
            String API_KEY
    );

    @GET("v2/top-headlines")
    Call<ArticleResponse> getCategoryObjectsList(
            @Query("category")
            String category,
            @Query("language")
            String language,
            @Query("apikey")
            String API_KEY
    );
}