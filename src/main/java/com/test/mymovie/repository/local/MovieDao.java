package com.test.mymovie.repository.local;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.test.mymovie.model.json.MovieJson;

import java.util.ArrayList;
import java.util.List;
import io.reactivex.Single;

@Dao
public interface MovieDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(MovieJson movie);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(ArrayList<MovieJson> movielist);

    @Update
    void update(MovieJson movie);

    @Query("SELECT * FROM movie_table")
    List<MovieJson> getAll();

    @Query("SELECT favourite FROM movie_table WHERE id LIKE :movieid")
    boolean getFavourite(int movieid);

    @Query("SELECT * FROM movie_table WHERE id LIKE :movieid")
    Single<MovieJson> getMovie(int movieid);

    @Query("SELECT * FROM movie_table WHERE favourite LIKE :fav")
    Single<List<MovieJson>> getAllFavourite(boolean fav);
}
