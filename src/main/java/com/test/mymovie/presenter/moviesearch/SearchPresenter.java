package com.test.mymovie.presenter.moviesearch;

import com.test.mymovie.model.json.MovieJson;
import com.test.mymovie.model.json.MovieResponseJson;
import com.test.mymovie.repository.local.Dbhelper;
import com.test.mymovie.repository.remote.RetrofitClient;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchPresenter implements SearchPresenterCallback {

    SearchViewCallback searchCallback;
    public SearchPresenter(SearchViewCallback callback) {
        this.searchCallback = callback;
    }

    @Override
    public void showSearchResult(String searchtxt) {
        if (searchtxt != null && !searchtxt.isEmpty()) {
            RetrofitClient.getInstance()
                    .getApi()
                    .getSearchedMovies(RetrofitClient.API_KEY, searchtxt)
                    .enqueue(new Callback<MovieResponseJson>() {

                        @Override
                        public void onResponse(Call<MovieResponseJson> call, Response<MovieResponseJson> response) {
                            if (response.body() != null) {
                                if (response.body().results.size() > 0) {
                                    ArrayList<MovieJson> allMovies = new ArrayList();
                                    for (MovieJson movie : response.body().results) {
                                        if (movie.getPoster_path() != null) {
                                            allMovies.add(movie);
                                        }
                                    }
                                    Dbhelper.getInstance().addAllMovie(allMovies);
                                    searchCallback.onSearchSuccess(allMovies);
                                } else {
                                    searchCallback.onSearchError();

                                }

                            }
                        }

                        @Override
                        public void onFailure(Call<MovieResponseJson> call, Throwable t) {
                            searchCallback.onSearchError();
                        }
                    });
        }
        else
        {
            searchCallback.onInvalidString();
        }
    }
}
