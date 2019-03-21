package com.test.mymovie.repository.local;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.util.Log;

import com.test.mymovie.MainActivity;
import com.test.mymovie.model.json.MovieJson;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.schedulers.Schedulers;

public class Dbhelper {

    private static Dbhelper dbhelper;
    private Context context;
    private MovieDb db;

    private Dbhelper(Context ctx)
    {
        context = ctx;
        db = Room.databaseBuilder(context.getApplicationContext(),
                MovieDb.class, "movie_db")
                .allowMainThreadQueries()
                .build();
    }

    public static Dbhelper getInstance()
    {
        if(dbhelper == null)
        {
            dbhelper = new Dbhelper(MainActivity.context);
        }
        return dbhelper;
    }

    public void updateMovie(final MovieJson movie) {
        Completable.fromAction(new Action() {
            @Override
            public void run() throws Exception {
                db.movieDao().update(movie);
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onComplete() {
                        Log.d("Roomdb", "Added movie");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("Roomdb", "Error while adding movie");
                    }

                });
    }

    public boolean isFavourite(final int movie_id) {
        try
        {
            return db.movieDao().getFavourite(movie_id);
        }
        catch (Exception e)
        {
            return false;
        }
    }

    public Single<List<MovieJson>> getAllFavourite()
    {
        return db.movieDao().getAllFavourite(true);
    }

    public Single<MovieJson> getMovieDetail(int movie_id)
    {
        return db.movieDao().getMovie(movie_id);
    }

    public void addAllMovie(final ArrayList<MovieJson> movielist) {
        Completable.fromAction(new Action() {
            @Override
            public void run() throws Exception {
                db.movieDao().insert(movielist);
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onComplete() {
                        Log.d("Roomdb", "Added all movies");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("Roomdb", "Error while adding all movies");
                    }
                });
    }

}
