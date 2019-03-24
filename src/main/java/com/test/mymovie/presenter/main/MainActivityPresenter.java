package com.test.mymovie.presenter.main;

import com.test.mymovie.fragments.FavouriteMovies;
import com.test.mymovie.fragments.TopMovies;

public class MainActivityPresenter {

    MainActivityViewCallback viewCallback;

    public MainActivityPresenter(MainActivityViewCallback view)
    {
        viewCallback = view;
    }

    public void onSearchClick(String searchText) {
        if(isSearchTextValid(searchText))
        {
            viewCallback.onSearchSuccess(searchText);
        }
        else
        {
            viewCallback.onSearchError();
        }
    }

    private boolean isSearchTextValid(String searchText)
    {
        if(searchText != null && !searchText.isEmpty())
        {
            return true;
        }
        return false;
    }

    public void showTopMovies() {
        TopMovies.getInstance().updateFragment();
    }

    public void showFavouriteMovies() {
        FavouriteMovies.getInstance().updateFragment();
    }
}
