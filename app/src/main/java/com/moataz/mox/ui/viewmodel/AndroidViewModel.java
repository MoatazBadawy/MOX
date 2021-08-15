package com.moataz.mox.ui.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.moataz.mox.data.model.article.ArticleResponse;
import com.moataz.mox.data.model.article.Item;
import com.moataz.mox.data.repository.ArticlesRepository;
import com.moataz.mox.utils.Resource;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class AndroidViewModel extends ViewModel {

    private final CompositeDisposable disposables = new CompositeDisposable();
    private final MutableLiveData<Resource<List<Item>>> mediumObjectsList = new MutableLiveData<>();
    private final ArticlesRepository articlesRepository = new ArticlesRepository();

    public LiveData<Resource<List<Item>>> makeApiCallAndroidArticles() {
        disposables.add(articlesRepository.executeAndroidApi()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flattenAsObservable(ArticleResponse::getItems)
                .filter(item -> item.getThumbnail() != null && !item.getThumbnail().isEmpty())
                .toList()
                .subscribe(result -> mediumObjectsList.postValue(Resource.success(result)),
                        throwable -> mediumObjectsList.postValue(Resource.error("error"))));
        return mediumObjectsList;
    }

    @Override
    protected void onCleared() {
        disposables.clear();
    }
}