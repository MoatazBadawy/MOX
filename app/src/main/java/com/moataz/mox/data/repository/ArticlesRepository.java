package com.moataz.mox.data.repository;

import com.moataz.mox.data.api.APIService;
import com.moataz.mox.data.model.article.ArticleResponse;
import com.moataz.mox.data.request.RetroInstant;

import javax.inject.Inject;

import io.reactivex.Single;

public class ArticlesRepository {

    private final APIService service;

    @Inject
    public ArticlesRepository() {
        this.service = RetroInstant.getRetroMediumClient();
    }

    public Single<ArticleResponse> executeRemoteApi() {
        return service.getArticleObjectsList("https://medium.com/feed/topic/remote-work",
                "b7fnpiy39m1ntewj93105d5ukhpmifvmnqufyksq");
    }

    public Single<ArticleResponse> executeUXApi() {
        return service.getArticleObjectsList("https://uxplanet.org/feed",
                "b7fnpiy39m1ntewj93105d5ukhpmifvmnqufyksq");
    }

    public Single<ArticleResponse> executeFrontEndApi() {
        return service.getArticleObjectsList("https://medium.com/feed/tag/web-development",
                "b7fnpiy39m1ntewj93105d5ukhpmifvmnqufyksq");
    }

    public Single<ArticleResponse> executeAndroidApi() {
        return service.getArticleObjectsList("https://medium.com/feed/tag/androiddev",
                "b7fnpiy39m1ntewj93105d5ukhpmifvmnqufyksq");
    }

    public Single<ArticleResponse> executeProApi() {
        return service.getArticleObjectsList("https://betterprogramming.pub/feed",
                "b7fnpiy39m1ntewj93105d5ukhpmifvmnqufyksq");
    }

    public Single<ArticleResponse> executeTechApi() {
        return service.getArticleObjectsList("https://www.theverge.com/rss/full.xml",
                "b7fnpiy39m1ntewj93105d5ukhpmifvmnqufyksq");
    }

    public Single<ArticleResponse> executeCnnApi() {
        return service.getNewsObjectsList("http://rss.cnn.com/rss/cnn_latest.rss",
                "b7fnpiy39m1ntewj93105d5ukhpmifvmnqufyksq",
                "50");
    }
}