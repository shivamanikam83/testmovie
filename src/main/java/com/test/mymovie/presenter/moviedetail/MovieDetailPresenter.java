package com.test.mymovie.presenter.moviedetail;

import com.bumptech.glide.Glide;
import com.test.mymovie.model.json.MovieJson;
import com.test.mymovie.repository.local.Dbhelper;
import com.test.mymovie.repository.remote.RetrofitClient;

import io.reactivex.Scheduler;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MovieDetailPresenter implements MovieDetailCallback{

    MovieDetailViewCallback viewCallback;
    Scheduler bgSchedular;
    Scheduler uiSchedular;
    public MovieDetailPresenter(MovieDetailViewCallback viewCallback) {
        this.viewCallback = viewCallback;
        bgSchedular = Schedulers.newThread();
        uiSchedular = AndroidSchedulers.mainThread();
    }

    public MovieDetailPresenter(MovieDetailViewCallback viewCallback, Scheduler sch1, Scheduler sch2) {
        this.viewCallback = viewCallback;
        bgSchedular = Schedulers.newThread();
        uiSchedular = AndroidSchedulers.mainThread();
        if(sch1 !=null)
        {
            bgSchedular = sch1;
        }
        if(sch2 != null)
        {
            uiSchedular = sch2;
        }
    }

    //getting movie details from database for movie id
    @Override
    public void fetchDetail(int movie_id) {
                getDetails(movie_id)
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

    public Single<MovieJson> getDetails(int movie_id)
    {
        return Dbhelper.getInstance().getMovieDetail(movie_id);
    }
}
