package com.test.mymovie.customview.paging;

import android.arch.lifecycle.MutableLiveData;
import android.arch.paging.DataSource;

public class FavouriteDataSourceFactory extends DataSource.Factory {

    private MutableLiveData<FavouriteDataSource> favouriteLiveData = new MutableLiveData<>();

    @Override
    public DataSource create() {
        FavouriteDataSource favDataSource = new FavouriteDataSource();
        favouriteLiveData.postValue(favDataSource);
        return favDataSource;
    }

    public MutableLiveData<FavouriteDataSource> getFavouriteLiveData() {
        return favouriteLiveData;
    }
}
