package com.moataz.mox.data.repository;

import android.app.Service;

import com.moataz.mox.data.api.APIService;
import com.moataz.mox.data.model.article.MediumResponse;
import com.moataz.mox.data.model.news.CnnResponse;
import com.moataz.mox.data.request.RetroInstant;

import io.reactivex.Single;

public class ArticlesRepository {

    private final APIService service;

    public ArticlesRepository() {
        this.service = RetroInstant.getRetroMediumClient();
    }
    
    public Single<MediumResponse> executeAndroidApi() {
        return service.getArticleObjectsList("https://medium.com/feed/tag/android-app-development",
                "gemq2i32mdg0aaye65jvvzguuzhxuuj3aqkn3dig");
    }
    public Single<MediumResponse> executeProApi() {
        return service.getArticleObjectsList("https://medium.com/feed/tag/development",
                "gemq2i32mdg0aaye65jvvzguuzhxuuj3aqkn3dig");
    }
    public Single<MediumResponse> executeRemoteApi() {
        return service.getArticleObjectsList("https://medium.com/feed/tag/remote",
                "gemq2i32mdg0aaye65jvvzguuzhxuuj3aqkn3dig");
    }
    public Single<MediumResponse> executeUIApi() {
        return service.getArticleObjectsList("https://medium.com/feed/tag/ui",
                "gemq2i32mdg0aaye65jvvzguuzhxuuj3aqkn3dig");
    }
    public Single<MediumResponse> executeUXApi() {
        return service.getArticleObjectsList("https://uxplanet.org/feed",
                "gemq2i32mdg0aaye65jvvzguuzhxuuj3aqkn3dig");
    }

    public Single<MediumResponse> executeTechApi() {
        return service.getArticleObjectsList("https://www.theverge.com/tech/rss/index.xml",
                "gemq2i32mdg0aaye65jvvzguuzhxuuj3aqkn3dig");
    }
    public Single<CnnResponse> executeCnnApi(){
        return service.getNewsObjectsList("http://rss.cnn.com/rss/cnn_topstories.rss",
                "gemq2i32mdg0aaye65jvvzguuzhxuuj3aqkn3dig",
                "50");
    }
}