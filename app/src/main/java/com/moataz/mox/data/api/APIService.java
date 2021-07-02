package com.moataz.mox.data.api;

import com.moataz.mox.data.model.news.CnnResponse;
import com.moataz.mox.data.model.article.MediumResponse;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface APIService {
    @GET("v1/api.json")
    Observable<MediumResponse> getArticleObjectsList(
            @Query("rss_url")
            String rssURL,
            @Query("api_key")
            String apiKey
    );

    @GET("v1/api.json")
    Observable<CnnResponse> getNewsObjectsList(
            @Query("rss_url")
            String rssURL,
            @Query("api_key")
            String apiKey,
            @Query("count")
            String count
    );
}