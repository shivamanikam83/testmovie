package com.test.mymovie.presenter.moviesearch;

import com.test.mymovie.model.json.MovieJson;

import java.util.ArrayList;

public interface SearchViewCallback {
    public void onSearchSuccess(ArrayList<MovieJson> movielist);
    public void onSearchError();
    public void onInvalidString();
}
