package com.test.mymovie.toprated.repository.remote;

import com.test.mymovie.model.json.MovieResponseJson;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MovieService {

    @GET("movie/top_rated")
    Call<MovieResponseJson> getTopMovies(
            @Query("page") int page,
            @Query("api_key") String key,
            @Query("language") String lang
    );

    @GET("search/movie")
    Call<MovieResponseJson> getSearchedMovies(
            @Query("api_key") String key,
            @Query("query") String searchTxt
    );
}
