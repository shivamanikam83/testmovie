package com.test.mymovie.toprated.customview.paging;

import android.arch.paging.PageKeyedDataSource;
import android.support.annotation.NonNull;
import android.util.Log;

import com.test.mymovie.model.json.MovieJson;
import com.test.mymovie.model.json.MovieResponseJson;
import com.test.mymovie.toprated.repository.local.Dbhelper;
import com.test.mymovie.toprated.repository.remote.RetrofitClient;

import java.util.ArrayList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieDataSource extends PageKeyedDataSource<Integer, MovieJson> {

    public static final int TOTAL_PAGES = 50;
    private static final int FIRST_PAGE = 1;


    @Override
    public void loadInitial(@NonNull LoadInitialParams<Integer> params, @NonNull final LoadInitialCallback<Integer, MovieJson> callback) {

        RetrofitClient.getInstance()
                .getApi()
                .getTopMovies(1, RetrofitClient.API_KEY, RetrofitClient.LANGUAGE)
                .enqueue(new Callback<MovieResponseJson>() {
                    @Override
                    public void onResponse(Call<MovieResponseJson> call, Response<MovieResponseJson> response) {

                            if(response.body()!=null) {
                                ArrayList<MovieJson> movieJsons = new ArrayList<>();
                                for (MovieJson movie : response.body().results) {
                                    if (Dbhelper.getInstance().isFavourite(movie.id)) {
                                        movie.setFavourite(true);
                                    }
                                    if(movie.getPoster_path()!=null)
                                    {
                                        movieJsons.add(movie);
                                    }
                                }
                                Dbhelper.getInstance().addAllMovie(movieJsons);
                                callback.onResult(movieJsons, null, FIRST_PAGE + 1);
                            }

                    }

                    @Override
                    public void onFailure(Call<MovieResponseJson> call, Throwable t) {

                    }
                });

    }

    @Override
    public void loadBefore(@NonNull final LoadParams<Integer> params, @NonNull final LoadCallback<Integer, MovieJson> callback) {

        RetrofitClient.getInstance()
                .getApi()
                .getTopMovies(params.key, RetrofitClient.API_KEY, RetrofitClient.LANGUAGE)
                .enqueue(new Callback<MovieResponseJson>() {
                    @Override
                    public void onResponse(Call<MovieResponseJson> call, Response<MovieResponseJson> response) {

                        if(response.body() != null){
                            Integer key = (params.key > 1) ? params.key - 1 : null;
                            ArrayList<MovieJson> movieJsons = new ArrayList<>();
                            for (MovieJson movie:response.body().results) {
                                if(Dbhelper.getInstance().isFavourite(movie.id))
                                {
                                    movie.setFavourite(true);
                                }
                                if(movie.getPoster_path()!=null)
                                {
                                    movieJsons.add(movie);
                                }
                            }
                            Dbhelper.getInstance().addAllMovie(movieJsons);
                            callback.onResult(movieJsons, key);
                        }
                    }

                    @Override
                    public void onFailure(Call<MovieResponseJson> call, Throwable t) {

                    }
                });

    }

    @Override
    public void loadAfter(@NonNull final LoadParams<Integer> params, @NonNull final LoadCallback<Integer, MovieJson> callback) {

        RetrofitClient.getInstance()
                .getApi()
                .getTopMovies(params.key, RetrofitClient.API_KEY, RetrofitClient.LANGUAGE)
                .enqueue(new Callback<MovieResponseJson>() {
                    @Override
                    public void onResponse(Call<MovieResponseJson> call, Response<MovieResponseJson> response) {

                        if(response.body() != null){
                            Integer key = response.body().page < TOTAL_PAGES  ? params.key + 1 : null;
                            ArrayList<MovieJson> movieJsons = new ArrayList<>();
                            for (MovieJson movie:response.body().results) {
                                if(Dbhelper.getInstance().isFavourite(movie.id))
                                {
                                    movie.setFavourite(true);
                                }
                                if(movie.getPoster_path()!=null)
                                {
                                    movieJsons.add(movie);
                                }

                            }
                            Dbhelper.getInstance().addAllMovie(movieJsons);
                            callback.onResult(movieJsons, key);
                        }
                    }

                    @Override
                    public void onFailure(Call<MovieResponseJson> call, Throwable t) {

                    }
                });
    }
}
