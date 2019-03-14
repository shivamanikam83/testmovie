package com.test.mymovie.view;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.test.mymovie.view.fragments.FavouriteMovies;
import com.test.mymovie.view.fragments.TopMovies;

public class CustomPagerAdapter extends FragmentPagerAdapter {
    final int PAGE_COUNT = 2;
    private String tabTitles[] = new String[] { "TopRated", "Favourites"};
    private Context context;

    public CustomPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public Fragment getItem(int position) {
        if(position==0)
        {
            return new TopMovies();
        }
        else
        {
            return new FavouriteMovies();
        }
    }



    @Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position
        return tabTitles[position];
    }
}
