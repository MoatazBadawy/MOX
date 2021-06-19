package com.moataz.mox.ui.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.moataz.mox.data.api.APIService;
import com.moataz.mox.data.model.Article;
import com.moataz.mox.data.model.response.ArticleResponse;
import com.moataz.mox.data.request.RetroInstance;
import com.moataz.mox.utils.Resource;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HealthViewModel extends ViewModel {

    public LiveData<Resource<List<Article>>> makeApiCallHealth() {

        final MutableLiveData<Resource<List<Article>>> healthObjectsList = new MutableLiveData<>();
        healthObjectsList.setValue(Resource.loading());
        APIService apiService = RetroInstance.getRetroClien().create(APIService.class);
        Call<ArticleResponse> call = apiService.getCategoryObjectsList("health","en","229b8268919c430b8c00bf0fff104926");
        call.enqueue(new Callback<ArticleResponse>() {
            @Override
            public void onResponse(@NotNull Call<ArticleResponse> call, @NotNull Response<ArticleResponse> response) {
                List<Article> articles = new ArrayList<>();
                assert response.body() != null;
                List<Article> responce = response.body().getArticles();
                for (int i = 0; i < responce.size(); i ++) {
                    if (responce.get(i).getDescription() != null && responce.get(i).getAuthor() != null && responce.get(i).getUrlToImage() != null && responce.get(i).getSource().getId() != null && responce.get(i).getTitle() != null) {
                        articles.add(responce.get(i));
                    }
                }
                healthObjectsList.postValue(Resource.success(articles));

            }

            @Override
            public void onFailure(@NotNull Call<ArticleResponse> call, @NotNull Throwable t) {
                healthObjectsList.setValue(Resource.error(t.getMessage() != null ? t.getMessage() : "Unknown Error"));
            }
        });

        return healthObjectsList;
    }
}