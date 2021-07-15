package com.moataz.mox.data.repository;

import android.app.Service;

import com.moataz.mox.data.api.APIService;
import com.moataz.mox.data.model.article.MediumResponse;
import com.moataz.mox.data.model.news.CnnResponse;
import com.moataz.mox.data.request.RetroInstant;

import io.reactivex.Observable;
import io.reactivex.Single;

public class ArticlesRepository {

    /*private final APIService service;


    public Observable<CnnResponse> executeCnnApi(){
        return service.getNewsObjectsList("http://rss.cnn.com/rss/cnn_topstories.rss",
                "2wjlh0wtxhp4zriwj6segb8ohftkbx0swwxtyjwh",
                "50");
    }
    public Observable<MediumResponse> executeTechApi() {
        return service.getArticleObjectsList("https://www.theverge.com/tech/rss/index.xml",
                "2wjlh0wtxhp4zriwj6segb8ohftkbx0swwxtyjwh");
    }

    public Observable<MediumResponse> executeAndroidApi() {
        return service.getArticleObjectsList("https://medium.com/feed/topic/android-development",
                "2wjlh0wtxhp4zriwj6segb8ohftkbx0swwxtyjwh");
    }
    public Observable<MediumResponse> executeProApi() {
        return service.getArticleObjectsList("https://medium.com/feed/tag/coding",
                "2wjlh0wtxhp4zriwj6segb8ohftkbx0swwxtyjwh");
    }
    public Observable<MediumResponse> executeRemoteApi() {
        return service.getArticleObjectsList("https://medium.com/feed/topic/remote-work",
                "2wjlh0wtxhp4zriwj6segb8ohftkbx0swwxtyjwh");
    }
    public Observable<MediumResponse> executeUIApi() {
        return service.getArticleObjectsList("https://medium.com/feed/tag//ui-design",
                "2wjlh0wtxhp4zriwj6segb8ohftkbx0swwxtyjwh");
    }
    public Observable<MediumResponse> executeUXApi() {
        return service.getArticleObjectsList("https://uxplanet.org/feed",
                "2wjlh0wtxhp4zriwj6segb8ohftkbx0swwxtyjwh");
    }
    public Observable<MediumResponse> executeIosApi() {
        return service.getArticleObjectsList("https://medium.com/feed/topic/ios-development",
                "2wjlh0wtxhp4zriwj6segb8ohftkbx0swwxtyjwh");
    }
    public Observable<MediumResponse> executeWebApi() {
        return service.getArticleObjectsList("https://medium.com/feed/tag/web-development",
                "2wjlh0wtxhp4zriwj6segb8ohftkbx0swwxtyjwh");
    }
    public Observable<MediumResponse> executeCleanApi() {
        return service.getArticleObjectsList("https://medium.com/feed/topic/freelancing",
                "2wjlh0wtxhp4zriwj6segb8ohftkbx0swwxtyjwh");
    }*/
}