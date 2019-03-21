package com.test.mymovie.repository.remote;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    private static final String BASE_URL = "https://api.themoviedb.org/3/";
    public static final String API_KEY = "b7eb626bad6e67ce0b04cdc33bd0030c";
    public static final String LANGUAGE = "en-US";
    public static final String IMAGE_BASE_URL = "http://image.tmdb.org/t/p/w185/";
    private static RetrofitClient mInstance;
    private Retrofit retrofit;

    private RetrofitClient(){
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static synchronized RetrofitClient getInstance(){
        if(mInstance == null){
            mInstance = new RetrofitClient();
        }
        return mInstance;
    }

    public MovieService getApi(){
        return retrofit.create(MovieService.class);
    }
}
