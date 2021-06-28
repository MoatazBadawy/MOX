package com.moataz.mox.ui.viewmodel;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.moataz.mox.data.api.APIService;
import com.moataz.mox.data.model.article.Item;
import com.moataz.mox.data.model.article.MediumResponse;
import com.moataz.mox.data.model.news.CnnResponse;
import com.moataz.mox.data.request.RetroInstant;
import com.moataz.mox.utils.status.Resource;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AndroidViewModel extends ViewModel {

    private static final String TAG = "AndroidViewModel";

    public LiveData<Resource<List<Item>>> makeApiCallAndroidArticles() {
        final MutableLiveData<Resource<List<Item>>> mediumObjectsList = new MutableLiveData<>();
        mediumObjectsList.setValue(Resource.loading());
        APIService apiService = RetroInstant.getRetroMediumClient().create(APIService.class);
        Observable<MediumResponse> observable = apiService.getArticleObjectsList(
                "https://medium.com/feed/tag/android-app-development",
                "gemq2i32mdg0aaye65jvvzguuzhxuuj3aqkn3dig");

        Observer<MediumResponse> observer = new Observer<MediumResponse>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onNext(MediumResponse value) {
                List<Item> articles = new ArrayList<>();
                assert value != null;
                List<Item> responce = value.getItems();
                for (int i = 0; i < Objects.requireNonNull(responce).size(); i ++) {
                    if (!Objects.equals(responce.get(i).getAuthor(), "")
                            && !Objects.equals(responce.get(i).getThumbnail(), "")
                            && !Objects.equals(responce.get(i).getTitle(), "")) {
                        articles.add(responce.get(i));
                    }
                }
                mediumObjectsList.postValue(Resource.success(articles));
            }

            @Override
            public void onError(@NonNull Throwable e) {
                Log.e(TAG, "onError: message",e);
            }

            @Override
            public void onComplete() {

            }
        };

        observable.observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(observer);

        return mediumObjectsList;
    }
}