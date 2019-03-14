package com.test.mymovie.toprated.customview.paging;

import android.arch.lifecycle.MutableLiveData;
import android.arch.paging.DataSource;

public class FavouriteDataSourceFactory extends DataSource.Factory {

    private MutableLiveData<FavouriteDataSource> movieLiveData = new MutableLiveData<>();


    @Override
    public DataSource create() {
        FavouriteDataSource movieDataSource = new FavouriteDataSource();
        movieLiveData.postValue(movieDataSource);
        return movieDataSource;
    }

    public MutableLiveData<FavouriteDataSource> getFavouriteLiveData() {
        return movieLiveData;
    }
}
