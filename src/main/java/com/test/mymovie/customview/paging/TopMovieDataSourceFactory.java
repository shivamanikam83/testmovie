package com.test.mymovie.customview.paging;

import android.arch.lifecycle.MutableLiveData;
import android.arch.paging.DataSource;

public class TopMovieDataSourceFactory extends DataSource.Factory {

    private MutableLiveData<TopMovieDataSource> movieLiveData = new MutableLiveData<>();

    @Override
    public DataSource create() {
        TopMovieDataSource topMovieDataSource = new TopMovieDataSource();
        movieLiveData.postValue(topMovieDataSource);
        return topMovieDataSource;
    }

    public MutableLiveData<TopMovieDataSource> getMovieLiveData() {
        return movieLiveData;
    }
}
