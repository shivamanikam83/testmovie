package com.test.mymovie.customview.paging;

import android.arch.paging.PageKeyedDataSource;
import android.support.annotation.NonNull;

import com.test.mymovie.model.json.MovieJson;
import com.test.mymovie.model.json.MovieResponseJson;
import com.test.mymovie.repository.local.Dbhelper;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FavouriteDataSource extends PageKeyedDataSource<Integer, MovieJson> {

    public static final int TOTAL_PAGES = 50;
    private static final int FIRST_PAGE = 1;


    @Override
    public void loadInitial(@NonNull LoadInitialParams<Integer> params, @NonNull final LoadInitialCallback<Integer, MovieJson> callback) {

        Single<List<MovieJson>> single = Dbhelper.getInstance().getAllFavourite();
        single.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<List<MovieJson>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(List<MovieJson> movieJsons) {
                        callback.onResult(movieJsons, null, FIRST_PAGE + 1);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                });


    }

    @Override
    public void loadBefore(@NonNull final LoadParams<Integer> params, @NonNull final LoadCallback<Integer, MovieJson> callback) {
        final Integer key = (params.key > 1) ? params.key - 1 : null;

        Single<List<MovieJson>> single = Dbhelper.getInstance().getAllFavourite();
        single.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<List<MovieJson>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(List<MovieJson> movieJsons) {
                        callback.onResult(movieJsons, key);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                });

    }

    @Override
    public void loadAfter(@NonNull final LoadParams<Integer> params, @NonNull final LoadCallback<Integer, MovieJson> callback) {

        final Integer key = params.key < TOTAL_PAGES  ? params.key + 1 : null;
        Single<List<MovieJson>> single = Dbhelper.getInstance().getAllFavourite();
        single.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<List<MovieJson>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(List<MovieJson> movieJsons) {
                        callback.onResult(movieJsons, key);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                });
    }
}
