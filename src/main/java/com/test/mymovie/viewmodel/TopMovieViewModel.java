package com.test.mymovie.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.paging.LivePagedListBuilder;
import android.arch.paging.PagedList;

import com.test.mymovie.toprated.customview.paging.MovieDataSource;
import com.test.mymovie.toprated.customview.paging.MovieDataSourceFactory;
import com.test.mymovie.model.json.MovieJson;

import java.util.ArrayList;

public class TopMovieViewModel extends ViewModel {

    public LiveData<PagedList<MovieJson>> itemPagedList;
    MutableLiveData<MovieDataSource> liveDataSource;
    MovieDataSourceFactory movieSourceFactory;
    PagedList.Config config;

    public TopMovieViewModel() {

        movieSourceFactory = new MovieDataSourceFactory();
        liveDataSource = movieSourceFactory.getMovieLiveData();

        config = (new PagedList.Config.Builder())
                        .setEnablePlaceholders(false)
                        .setPageSize(MovieDataSource.TOTAL_PAGES)
                        .build();

        itemPagedList = (new LivePagedListBuilder(movieSourceFactory, config)).build();

    }

    public void recreateList()
    {
        itemPagedList = (new LivePagedListBuilder(movieSourceFactory, config)).build();
    }
}
