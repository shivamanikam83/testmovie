package com.test.mymovie.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.paging.LivePagedListBuilder;
import android.arch.paging.PagedList;

import com.test.mymovie.model.json.MovieJson;
import com.test.mymovie.customview.paging.TopMovieDataSource;
import com.test.mymovie.customview.paging.TopMovieDataSourceFactory;

public class TopMovieViewModel extends ViewModel {

    public LiveData<PagedList<MovieJson>> topMoviePagedList;
    MutableLiveData<TopMovieDataSource> topMovieDataSource;
    TopMovieDataSourceFactory topMovieDataSourceFactory;
    PagedList.Config config;

    public TopMovieViewModel() {

        topMovieDataSourceFactory = new TopMovieDataSourceFactory();
        topMovieDataSource = topMovieDataSourceFactory.getMovieLiveData();

        config = (new PagedList.Config.Builder())
                        .setEnablePlaceholders(false)
                        .setPageSize(TopMovieDataSource.TOTAL_PAGES)
                        .build();

        topMoviePagedList = (new LivePagedListBuilder(topMovieDataSourceFactory, config)).build();

    }


    public void recreateList()
    {
        //topMovieDataSource.getValue().invalidate();
        topMoviePagedList = (new LivePagedListBuilder(topMovieDataSourceFactory, config)).build();
    }
}
