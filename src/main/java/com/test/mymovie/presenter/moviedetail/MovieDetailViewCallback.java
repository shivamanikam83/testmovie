package com.test.mymovie.presenter.moviedetail;

import com.test.mymovie.model.json.MovieJson;

import java.util.ArrayList;

public interface MovieDetailViewCallback {
    public void onDetailSuccess(MovieJson movie);
    public void onDetailError();
}
