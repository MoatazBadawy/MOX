package com.moataz.mox.ui.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.moataz.mox.data.model.article.Item;
import com.moataz.mox.data.model.article.MediumResponse;
import com.moataz.mox.data.repository.ArticlesRepository;
import com.moataz.mox.utils.Resource;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class AndroidViewModel extends ViewModel {

    private final CompositeDisposable disposables = new CompositeDisposable();
    final MutableLiveData<Resource<List<Item>>> mediumObjectsList = new MutableLiveData<>();
    private final ArticlesRepository articlesRepository = new ArticlesRepository();
    public LiveData<Resource<List<Item>>> makeApiCallAndroidArticles()  {
        disposables.add(articlesRepository.executeAndroidApi()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(result -> mediumObjectsList.setValue(Resource.success(result.getItems())),
                        throwable -> mediumObjectsList.setValue(null)));
        return mediumObjectsList;
    }

    @Override
    protected void onCleared() {
        disposables.clear();
    }
}