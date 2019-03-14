package com.test.mymovie.toprated.repository.local;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.test.mymovie.model.json.MovieJson;

@Database(entities = MovieJson.class, version = 1)
public abstract class MovieDb extends RoomDatabase {
    public abstract MovieDao movieDao();
}
