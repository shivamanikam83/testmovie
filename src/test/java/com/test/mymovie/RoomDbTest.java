package com.test.mymovie;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.test.runner.AndroidJUnit4;

import com.test.mymovie.model.json.MovieJson;
import com.test.mymovie.presenter.main.MainActivityPresenter;
import com.test.mymovie.presenter.moviedetail.MovieDetailPresenter;
import com.test.mymovie.presenter.moviedetail.MovieDetailViewCallback;
import com.test.mymovie.repository.local.Dbhelper;
import com.test.mymovie.repository.local.MovieDao;
import com.test.mymovie.repository.local.MovieDb;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.configuration.DefaultMockitoConfiguration;
import org.mockito.internal.stubbing.defaultanswers.ReturnsEmptyValues;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

import java.io.IOException;
import java.util.List;

import io.reactivex.schedulers.TestScheduler;

import static io.reactivex.Single.just;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(AndroidJUnit4.class)
public class RoomDbTest {
    MovieDetailPresenter presenter;
    @Mock
    private Context context;
    private MovieDao movieDao;
    private MovieDb db;
    private int FAKE_ID;

    @Mock
    Dbhelper dbhelper;

    @Mock
    MovieDetailViewCallback view;

    @After
    public void closeDb() throws IOException {
        db.close();
    }

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        TestScheduler testScheduler = new TestScheduler();
        presenter = new MovieDetailPresenter(view, testScheduler, testScheduler);
        db = Room.inMemoryDatabaseBuilder(context, MovieDb.class).allowMainThreadQueries().build();
        movieDao = db.movieDao();
        FAKE_ID = 144;
    }
    /*@Test
    public void getDetails()
    {
        MovieJson movieJson = new MovieJson();
        movieJson.setId(FAKE_ID);
        presenter.fetchDetail(FAKE_ID);
        when(dbhelper.getMovieDetail(FAKE_ID)).thenReturn(just(movieJson));
        verify(view).onDetailSuccess(movieJson);
    }*/

    @Test
    public void getDetailWithValidIdTest()
    {
        MovieJson movieJson = new MovieJson();
        movieJson.setId(FAKE_ID);
        movieDao.insert(movieJson);

        MovieJson movieJsonSingle = movieDao.getMovie(144).blockingGet();
        when(movieDao.getMovie(144)).thenReturn(just(movieJson));
        Assert.assertEquals(movieJsonSingle.getId(), movieJson.getId());
    }

    @Test
    public void insertMovieDbTest()
    {
        MovieJson movieJson = new MovieJson();
        movieJson.setId(FAKE_ID);
        movieDao.insert(movieJson);
        List<MovieJson> movieJsons = movieDao.getAll();
        Assert.assertEquals(movieJsons.size(), 1);
    }

    @Test
    public void updateMovieDbTest()
    {
        MovieJson movieJson = new MovieJson();
        movieJson.setId(FAKE_ID);
        movieDao.insert(movieJson);
        movieJson.setFavourite(true);
        movieDao.update(movieJson);
        Boolean isFavourite = movieDao.getFavourite(FAKE_ID);
        Assert.assertEquals(isFavourite, true);
    }

    @Test
    public void getFavouriteDbTest()
    {
        //insert fake favourite movie
        MovieJson movieJson = new MovieJson();
        movieJson.setId(FAKE_ID);
        movieJson.setFavourite(true);
        movieDao.insert(movieJson);
        Boolean isFavourite = movieDao.getFavourite(FAKE_ID);
        Assert.assertEquals(isFavourite, true);
    }
}

