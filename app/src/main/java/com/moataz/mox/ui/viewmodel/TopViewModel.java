package com.moataz.mox.ui.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.moataz.mox.data.model.news.Item;
import com.moataz.mox.data.repository.ArticlesRepository;
import com.moataz.mox.utils.Resource;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class TopViewModel extends ViewModel {

    private final CompositeDisposable disposables = new CompositeDisposable();
    final MutableLiveData<Resource<List<Item>>> mediumObjectsList = new MutableLiveData<>();
    private final ArticlesRepository articlesRepository = new ArticlesRepository();
    public LiveData<Resource<List<Item>>> makeApiCallTopArticles() {
        disposables.add(articlesRepository.executeCnnApi()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(result -> mediumObjectsList.setValue(Resource.success(result.getItems())),
                        throwable -> mediumObjectsList.setValue(null)));
        return mediumObjectsList;
    }
}