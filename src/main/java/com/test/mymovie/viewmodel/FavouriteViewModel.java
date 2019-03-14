package com.test.mymovie.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.paging.LivePagedListBuilder;
import android.arch.paging.PagedList;

import com.test.mymovie.model.json.MovieJson;
import com.test.mymovie.toprated.customview.paging.FavouriteDataSource;
import com.test.mymovie.toprated.customview.paging.FavouriteDataSourceFactory;
import com.test.mymovie.toprated.customview.paging.MovieDataSource;
import com.test.mymovie.toprated.customview.paging.MovieDataSourceFactory;

import java.util.ArrayList;

public class FavouriteViewModel extends ViewModel {

    public LiveData<PagedList<MovieJson>> itemPagedList;
    MutableLiveData<FavouriteDataSource> liveDataSource;
    public MutableLiveData<ArrayList<Integer>> favouriteSource;
    FavouriteDataSourceFactory movieSourceFactory;
    PagedList.Config config;

    public FavouriteViewModel() {

        movieSourceFactory = new FavouriteDataSourceFactory();
        liveDataSource = movieSourceFactory.getFavouriteLiveData();

        config = (new PagedList.Config.Builder())
                        .setEnablePlaceholders(false)
                        .setPageSize(1)
                        .build();

        itemPagedList = (new LivePagedListBuilder(movieSourceFactory, config)).build();

    }

    public void recreateList()
    {
        itemPagedList = (new LivePagedListBuilder(movieSourceFactory, config)).build();
    }
}
