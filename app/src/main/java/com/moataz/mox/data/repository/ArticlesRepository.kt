package com.moataz.mox.data.repository

import com.moataz.mox.data.api.APIService
import com.moataz.mox.data.model.ArticleResponse
import com.moataz.mox.data.request.RetroInstant
import io.reactivex.Single

class ArticlesRepository {

    private val service: APIService = RetroInstant.getRetroMediumClient()

    fun executeRemoteApi(): Single<ArticleResponse> {
        return service.getArticleObjectsList(
            "https://medium.com/feed/topic/remote-work",
            "b7fnpiy39m1ntewj93105d5ukhpmifvmnqufyksq"
        )
    }

    fun executeUXApi(): Single<ArticleResponse> {
        return service.getArticleObjectsList(
            "https://uxplanet.org/feed",
            "b7fnpiy39m1ntewj93105d5ukhpmifvmnqufyksq"
        )
    }

    fun executeFrontEndApi(): Single<ArticleResponse> {
        return service.getArticleObjectsList(
            "https://medium.com/feed/tag/web-development",
            "b7fnpiy39m1ntewj93105d5ukhpmifvmnqufyksq"
        )
    }

    fun executeAndroidApi(): Single<ArticleResponse> {
        return service.getArticleObjectsList(
            "https://medium.com/feed/tag/androiddev",
            "b7fnpiy39m1ntewj93105d5ukhpmifvmnqufyksq"
        )
    }

    fun executeProApi(): Single<ArticleResponse> {
        return service.getArticleObjectsList(
            "https://betterprogramming.pub/feed",
            "b7fnpiy39m1ntewj93105d5ukhpmifvmnqufyksq"
        )
    }

    fun executeTechApi(): Single<ArticleResponse> {
        return service.getArticleObjectsList(
            "https://www.theverge.com/rss/full.xml",
            "b7fnpiy39m1ntewj93105d5ukhpmifvmnqufyksq"
        )
    }

    fun executeCnnApi(): Single<ArticleResponse> {
        return service.getNewsObjectsList(
            "http://rss.cnn.com/rss/cnn_tech.rss",
            "b7fnpiy39m1ntewj93105d5ukhpmifvmnqufyksq",
            "50"
        )
    }
}