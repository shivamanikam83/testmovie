package com.test.mymovie.toprated.customview.paging;

import android.arch.lifecycle.MutableLiveData;
import android.arch.paging.DataSource;

public class MovieDataSourceFactory extends DataSource.Factory {

    private MutableLiveData<MovieDataSource> movieLiveData = new MutableLiveData<>();


    @Override
    public DataSource create() {
        MovieDataSource movieDataSource = new MovieDataSource();
        movieLiveData.postValue(movieDataSource);
        return movieDataSource;
    }

    public MutableLiveData<MovieDataSource> getMovieLiveData() {
        return movieLiveData;
    }
}
