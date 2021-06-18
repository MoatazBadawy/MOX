package com.moataz.mox.ui.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.moataz.mox.data.api.APIService;
import com.moataz.mox.data.model.response.ArticleResponse;
import com.moataz.mox.data.request.RetroInstance;
import com.moataz.mox.utils.Resource;

import org.jetbrains.annotations.NotNull;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ScienceViewModel extends ViewModel {

    public LiveData<Resource<ArticleResponse>> makeApiCallScience() {
        final MutableLiveData<Resource<ArticleResponse>> scienceObjectsList = new MutableLiveData<>();

        scienceObjectsList.setValue(Resource.loading());
        APIService apiService = RetroInstance.getRetroClien().create(APIService.class);
        Call<ArticleResponse> call = apiService.getCategoryObjectsList("science","en","f97f99ed437f4f01a2076254fc625fcf");
        call.enqueue(new Callback<ArticleResponse>() {
            @Override
            public void onResponse(@NotNull Call<ArticleResponse> call, @NotNull Response<ArticleResponse> response) {
                scienceObjectsList.postValue(Resource.success(response.body()));

            }

            @Override
            public void onFailure(@NotNull Call<ArticleResponse> call, @NotNull Throwable t) {
                scienceObjectsList.setValue(Resource.error(t.getMessage() != null ? t.getMessage() : "Unknown Error"));
            }
        });

        return scienceObjectsList;
    }
}