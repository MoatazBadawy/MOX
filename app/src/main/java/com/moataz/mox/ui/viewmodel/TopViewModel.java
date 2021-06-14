package com.moataz.mox.ui.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.moataz.mox.data.api.APIServiceTop;
import com.moataz.mox.data.model.ArticlesModel;
import com.moataz.mox.data.request.RetroInstance;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TopViewModel extends ViewModel {

    private final MutableLiveData<List<ArticlesModel>> moviesList;

    public TopViewModel(){
        moviesList = new MutableLiveData<>();
    }

    public MutableLiveData<List<ArticlesModel>> getMoviesListObserver() {
        return moviesList;

    }

    public void makeApiCall() {
        APIServiceTop apiServiceHome = RetroInstance.getRetroClientTop().create(APIServiceTop.class);
        Call<List<ArticlesModel>> call = apiServiceHome.getTopObjectsList("us","2f183557c723441587950875002b2a83");
        call.enqueue(new Callback<List<ArticlesModel>>() {
            @Override
            public void onResponse(Call<List<ArticlesModel>> call, Response<List<ArticlesModel>> response) {
                moviesList.postValue(response.body());
            }

            @Override
            public void onFailure(Call<List<ArticlesModel>> call, Throwable t) {
                moviesList.postValue(null);
            }
        });
    }
}
