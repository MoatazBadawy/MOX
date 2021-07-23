package com.moataz.mox.data.repository;

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

    public Single<MediumResponse> executeRemoteApi() {
        return service.getArticleObjectsList("https://medium.com/feed/topic/remote-work",
                "b7fnpiy39m1ntewj93105d5ukhpmifvmnqufyksq");
    }

    public Single<MediumResponse> executeUXApi() {
        return service.getArticleObjectsList("https://uxplanet.org/feed",
                "b7fnpiy39m1ntewj93105d5ukhpmifvmnqufyksq");
    }

    public Single<MediumResponse> executeFrontEndApi() {
        return service.getArticleObjectsList("https://medium.com/feed/tag/web-development",
                "b7fnpiy39m1ntewj93105d5ukhpmifvmnqufyksq");
    }

    public Single<MediumResponse> executeAndroidApi() {
        return service.getArticleObjectsList("https://medium.com/feed/tag/android-development",
                "b7fnpiy39m1ntewj93105d5ukhpmifvmnqufyksq");
    }

    public Single<MediumResponse> executeProApi() {
        return service.getArticleObjectsList("https://betterprogramming.pub/feed",
                "b7fnpiy39m1ntewj93105d5ukhpmifvmnqufyksq");
    }

    public Single<MediumResponse> executeTechApi() {
        return service.getArticleObjectsList("https://www.theverge.com/rss/full.xml",
                "b7fnpiy39m1ntewj93105d5ukhpmifvmnqufyksq");
    }

    public Single<CnnResponse> executeCnnApi(){
        return service.getNewsObjectsList("http://rss.cnn.com/rss/cnn_latest.rss",
                "b7fnpiy39m1ntewj93105d5ukhpmifvmnqufyksq",
                "50");
    }
}