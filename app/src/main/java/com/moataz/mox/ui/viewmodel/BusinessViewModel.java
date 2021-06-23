package com.moataz.mox.ui.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.moataz.mox.data.api.APIService;
import com.moataz.mox.data.model.Article.Article;
import com.moataz.mox.data.model.Article.Article.ArticleResponse;
import com.moataz.mox.data.request.article.RetroInstance;
import com.moataz.mox.utils.Resource;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BusinessViewModel extends ViewModel {

    public LiveData<Resource<List<Article>>> makeApiCallBusiness() {

        final MutableLiveData<Resource<List<Article>>> businessObjectsList = new MutableLiveData<>();
        businessObjectsList.setValue(Resource.loading());
        APIService apiService = RetroInstance.getRetroClien().create(APIService.class);
        Call<ArticleResponse> call = apiService.getCategoryObjectsList("business","en","35a114dfffb84d77b102065f5333765d");
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
                businessObjectsList.postValue(Resource.success(articles));
            }

            @Override
            public void onFailure(@NotNull Call<ArticleResponse> call, @NotNull Throwable t) {
                businessObjectsList.setValue(Resource.error(t.getMessage() != null ? t.getMessage() : "Unknown Error"));
            }
        });

        return businessObjectsList;
    }
}
