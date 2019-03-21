package com.test.mymovie.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.paging.LivePagedListBuilder;
import android.arch.paging.PagedList;

import com.test.mymovie.model.json.MovieJson;
import com.test.mymovie.customview.paging.FavouriteDataSource;
import com.test.mymovie.customview.paging.FavouriteDataSourceFactory;

import java.util.ArrayList;

public class FavouriteViewModel extends ViewModel {

    public LiveData<PagedList<MovieJson>> favouritePagedList;
    MutableLiveData<FavouriteDataSource> favouriteDataSource;
    FavouriteDataSourceFactory favouriteDataSourceFactory;
    PagedList.Config config;

    public FavouriteViewModel() {

        favouriteDataSourceFactory = new FavouriteDataSourceFactory();
        favouriteDataSource = favouriteDataSourceFactory.getFavouriteLiveData();

        config = (new PagedList.Config.Builder())
                        .setEnablePlaceholders(false)
                        .setPageSize(1)
                        .build();

        favouritePagedList = (new LivePagedListBuilder(favouriteDataSourceFactory, config)).build();

    }

    public void recreateList()
    {
        //for refreshing list
        favouriteDataSource.getValue().invalidate();
    }
}
