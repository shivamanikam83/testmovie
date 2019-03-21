package com.test.mymovie.presenter.moviedetail;

import com.bumptech.glide.Glide;
import com.test.mymovie.model.json.MovieJson;
import com.test.mymovie.repository.local.Dbhelper;
import com.test.mymovie.repository.remote.RetrofitClient;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MovieDetailPresenter implements MovieDetailCallback{

    MovieDetailViewCallback viewCallback;
    public MovieDetailPresenter(MovieDetailViewCallback viewCallback) {
        this.viewCallback = viewCallback;
    }


    @Override
    public void fetchDetail(int movie_id) {
        Dbhelper.getInstance().getMovieDetail(movie_id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<MovieJson>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onSuccess(MovieJson movieJson) {
                        viewCallback.onDetailSuccess(movieJson);
                    }

                    @Override
                    public void onError(Throwable e) {
                        viewCallback.onDetailError();
                    }
                });
    }
}
