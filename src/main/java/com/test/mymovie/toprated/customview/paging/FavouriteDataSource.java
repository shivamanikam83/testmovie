package com.test.mymovie.toprated.customview.paging;

import android.arch.paging.PageKeyedDataSource;
import android.support.annotation.NonNull;

import com.test.mymovie.model.json.MovieJson;
import com.test.mymovie.model.json.MovieResponseJson;
import com.test.mymovie.toprated.repository.local.Dbhelper;
import com.test.mymovie.toprated.repository.remote.RetrofitClient;

import java.util.ArrayList;

import io.reactivex.Flowable;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FavouriteDataSource extends PageKeyedDataSource<Integer, MovieJson> {

    public static final int TOTAL_PAGES = 50;
    private static final int FIRST_PAGE = 1;


    @Override
    public void loadInitial(@NonNull LoadInitialParams<Integer> params, @NonNull final LoadInitialCallback<Integer, MovieJson> callback) {

        ArrayList<MovieJson> movielist = (ArrayList<MovieJson>) Dbhelper.getInstance().getAllFavourite();
        callback.onResult(movielist, null, FIRST_PAGE + 1);

    }

    @Override
    public void loadBefore(@NonNull final LoadParams<Integer> params, @NonNull final LoadCallback<Integer, MovieJson> callback) {
        Integer key = (params.key > 1) ? params.key - 1 : null;
        ArrayList<MovieJson> movielist = (ArrayList<MovieJson>) Dbhelper.getInstance().getAllFavourite();
        callback.onResult(movielist, key);

    }

    @Override
    public void loadAfter(@NonNull final LoadParams<Integer> params, @NonNull final LoadCallback<Integer, MovieJson> callback) {

        Integer key = params.key < TOTAL_PAGES  ? params.key + 1 : null;
        ArrayList<MovieJson> movielist = (ArrayList<MovieJson>) Dbhelper.getInstance().getAllFavourite();
        callback.onResult(movielist, key);
    }
}
