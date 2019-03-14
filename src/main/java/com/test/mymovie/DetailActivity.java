package com.test.mymovie;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.test.mymovie.model.json.MovieJson;
import com.test.mymovie.toprated.repository.local.Dbhelper;
import com.test.mymovie.toprated.repository.remote.RetrofitClient;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class DetailActivity extends Activity {


    ImageView movieImage, backBtn;
    TextView headTxt, movieHead, movieDesc, detailMore;
    MovieJson movie;
    int movie_id;
    Context context;
    CompositeDisposable compositeDisposable;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Intent intent = getIntent();
        movie_id = intent.getIntExtra("MOVIE_ID", -1);
        initView();
    }

    private void initView() {
        context = this.getApplicationContext();
        compositeDisposable = new CompositeDisposable();
        movieImage = findViewById(R.id.detail_movie_img);
        movieHead = findViewById(R.id.detail_movie_name);
        movieDesc = findViewById(R.id.detail_movie_desc);
        detailMore = findViewById(R.id.detail_movie_more);
        backBtn = findViewById(R.id.back_btn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        headTxt = findViewById(R.id.head_txt);
        headTxt.setText("Detail Screen");
        Dbhelper.getInstance().getMovieDetail(movie_id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<MovieJson>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onSuccess(MovieJson movieJson) {
                        movie = movieJson;
                        if (movie != null) {
                            Glide.with(context)
                                    .load(RetrofitClient.IMAGE_BASE_URL + movie.poster_path)
                                    .into(movieImage);

                            movieHead.setText(movie.title);
                            movieDesc.setText(movie.getOverview());
                            detailMore.setText("Rating : " + String.valueOf(movie.vote_average));
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        compositeDisposable.clear();
    }
}
