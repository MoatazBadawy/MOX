package com.moataz.mox.data.api;

import com.moataz.mox.data.model.ArticleResponse;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface APIService {
    @GET("v1/api.json")
    Single<ArticleResponse> getArticleObjectsList(
            @Query("rss_url")
            String rssURL,
            @Query("api_key")
            String apiKey
    );

    @GET("v1/api.json")
    Single<ArticleResponse> getNewsObjectsList(
            @Query("rss_url")
            String rssURL,
            @Query("api_key")
            String apiKey,
            @Query("count")
            String count
    );
}