package com.moataz.mox.ui.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.moataz.mox.data.db.Data;
import com.moataz.mox.utils.Resource;

import java.util.ArrayList;
import java.util.List;

public class FirstRecyViewModel extends ViewModel {

    private final MutableLiveData<Resource<List<Data>>> firstRecyLiveData = new MutableLiveData<>();

    private List<Data> topArrayList;
    private List<Data> devArrayList;


    public LiveData<Resource<List<Data>>> getTopMutableLiveData() {
        firstRecyLiveData.postValue(Resource.success(topArrayList));
        populateList();
        return firstRecyLiveData;
    }

    public LiveData<Resource<List<Data>>> getDevMutableLiveData() {
        firstRecyLiveData.postValue(Resource.success(devArrayList));
        populateList();
        return firstRecyLiveData;
    }

    public void populateList(){
        // top data
        topArrayList = new ArrayList<>();
        topArrayList.add(new Data("https://i.pinimg.com/originals/af/8d/63/af8d63a477078732b79ff9d9fc60873f.jpg","bood Booster"));
        topArrayList.add(new Data("https://i.pinimg.com/originals/af/8d/63/af8d63a477078732b79ff9d9fc60873f.jpg", "Confidence Boost"));
        topArrayList.add(new Data("https://i.pinimg.com/originals/af/8d/63/af8d63a477078732b79ff9d9fc60873f.jpg", "Happy Beats"));
        topArrayList.add(new Data("https://i.pinimg.com/originals/af/8d/63/af8d63a477078732b79ff9d9fc60873f.jpg", "Feelin' Myself"));
        topArrayList.add(new Data("https://i.pinimg.com/originals/af/8d/63/af8d63a477078732b79ff9d9fc60873f.jpg","bood Booster"));
        topArrayList.add(new Data("https://i.pinimg.com/originals/af/8d/63/af8d63a477078732b79ff9d9fc60873f.jpg", "Confidence Boost"));
        topArrayList.add(new Data("https://i.pinimg.com/originals/af/8d/63/af8d63a477078732b79ff9d9fc60873f.jpg", "Happy Beats"));
        topArrayList.add(new Data("https://i.pinimg.com/originals/af/8d/63/af8d63a477078732b79ff9d9fc60873f.jpg", "Feelin' Myself"));
        // dev data
        devArrayList = new ArrayList<>();
        devArrayList.add(new Data("https://i.pinimg.com/originals/af/8d/63/af8d63a477078732b79ff9d9fc60873f.jpg","ghghgh"));
        devArrayList.add(new Data("https://i.pinimg.com/originals/af/8d/63/af8d63a477078732b79ff9d9fc60873f.jpg", "ghghgh"));
        devArrayList.add(new Data("https://i.pinimg.com/originals/af/8d/63/af8d63a477078732b79ff9d9fc60873f.jpg", "ghghgh"));
        devArrayList.add(new Data("https://i.pinimg.com/originals/af/8d/63/af8d63a477078732b79ff9d9fc60873f.jpg", "ytty"));
        devArrayList.add(new Data("https://i.pinimg.com/originals/af/8d/63/af8d63a477078732b79ff9d9fc60873f.jpg","bwrer"));
        devArrayList.add(new Data("https://i.pinimg.com/originals/af/8d/63/af8d63a477078732b79ff9d9fc60873f.jpg", "Confidence Boost"));
        devArrayList.add(new Data("https://i.pinimg.com/originals/af/8d/63/af8d63a477078732b79ff9d9fc60873f.jpg", "Happy Beats"));
        devArrayList.add(new Data("https://i.pinimg.com/originals/af/8d/63/af8d63a477078732b79ff9d9fc60873f.jpg", "Feelin' Myself"));
    }
}