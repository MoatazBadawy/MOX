package com.moataz.mox.ui.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.moataz.mox.data.model.news.Item;
import com.moataz.mox.data.model.news.CnnResponse;
import com.moataz.mox.data.repository.ArticlesRepository;
import com.moataz.mox.utils.Resource;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class TopViewModel extends ViewModel {

    private final CompositeDisposable disposables = new CompositeDisposable();
    private final MutableLiveData<Resource<List<Item>>> newsObjectsList = new MutableLiveData<>();
    private final ArticlesRepository articlesRepository = new ArticlesRepository();

    public LiveData<Resource<List<Item>>> makeApiCallTopArticles() {
        disposables.add(articlesRepository.executeCnnApi()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flattenAsObservable(CnnResponse::getItems)
                .filter(item -> item.getEnclosure().getLink()!=null && !item.getEnclosure().getLink().isEmpty())
                .toList()
                .subscribe(result -> newsObjectsList.postValue(Resource.success(result)),
                        throwable -> newsObjectsList.postValue(null)));
        return newsObjectsList;
    }

    @Override
    protected void onCleared() {
        disposables.clear();
    }
}