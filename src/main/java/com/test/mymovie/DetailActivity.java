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
import com.test.mymovie.presenter.moviedetail.MovieDetailPresenter;
import com.test.mymovie.presenter.moviedetail.MovieDetailViewCallback;
import com.test.mymovie.repository.remote.RetrofitClient;


public class DetailActivity extends Activity implements MovieDetailViewCallback {


    ImageView movieImage, backBtn;
    TextView headTxt, movieHead, movieDesc, detailMore;
    MovieJson movie;
    int movie_id;
    Context context;
    MovieDetailPresenter presenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Intent intent = getIntent();
        movie_id = intent.getIntExtra("MOVIE_ID", -1);
        initView();
    }

    //initialise view of detail activity
    private void initView() {
        context = this.getApplicationContext();
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
        presenter = new MovieDetailPresenter(this);
        presenter.fetchDetail(movie_id);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    //on success of dbcall for movie details (event dispatched by presenter)
    @Override
    public void onDetailSuccess(MovieJson moviejson) {
        movie = moviejson;
        if (movie != null) {
            Glide.with(context)
                    .load(RetrofitClient.IMAGE_BASE_URL + movie.poster_path)
                    .into(movieImage);

            movieHead.setText(movie.title);
            movieDesc.setText(movie.getOverview());
            detailMore.setText("Rating : " + String.valueOf(movie.vote_average));
        }

    }

    //on error of dbcall for movie details api
    @Override
    public void onDetailError() {

    }
}
