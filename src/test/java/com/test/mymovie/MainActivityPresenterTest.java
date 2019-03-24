package com.test.mymovie;

import android.content.Context;

import com.test.mymovie.presenter.main.MainActivityPresenter;
import com.test.mymovie.presenter.main.MainActivityViewCallback;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class MainActivityPresenterTest {

    @Mock
    Context mockContext;

    MainActivityPresenter presenter;

    @Mock
    MainActivityViewCallback viewCallback;


    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        presenter = new MainActivityPresenter(viewCallback);
    }

    @Test
    public void onSearchClickWithEmptyText() {
        presenter.onSearchClick("");
        verify(viewCallback).onSearchError();
    }

    @Test
    public void onSearchClickWithValidText()
    {
        presenter.onSearchClick("FAKE");
        verify(viewCallback).onSearchSuccess("FAKE");
    }
}