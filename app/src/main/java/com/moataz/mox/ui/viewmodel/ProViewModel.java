package com.moataz.mox.ui.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.moataz.mox.data.model.article.Item;
import com.moataz.mox.data.model.article.MediumResponse;
import com.moataz.mox.data.repository.ArticlesRepository;
import com.moataz.mox.utils.Resource;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class ProViewModel extends ViewModel {

    private final CompositeDisposable disposables = new CompositeDisposable();
    private final MutableLiveData<Resource<List<Item>>> mediumObjectsList = new MutableLiveData<>();
    private final ArticlesRepository articlesRepository = new ArticlesRepository();

    public LiveData<Resource<List<Item>>> makeApiCallDevArticles() {
        disposables.add(articlesRepository.executeProApi()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flattenAsObservable(MediumResponse::getItems)
                .filter(item -> item.getThumbnail()!=null && !item.getThumbnail().isEmpty())
                .toList()
                .subscribe(result -> mediumObjectsList.postValue(Resource.success(result)),
                        throwable -> mediumObjectsList.postValue(null)));
        return mediumObjectsList;
    }

    @Override
    protected void onCleared() {
        disposables.clear();
    }
}