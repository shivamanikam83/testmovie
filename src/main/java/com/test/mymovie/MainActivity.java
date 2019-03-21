package com.test.mymovie;

import android.content.Context;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;
import android.widget.SearchView;

import com.test.mymovie.customview.TabPagerAdapter;
import com.test.mymovie.fragments.FavouriteMovies;
import com.test.mymovie.fragments.TopMovies;

public class MainActivity extends AppCompatActivity {

    public static Context context;
    Toolbar toolbar;
    TabLayout tabLayout;
    ViewPager viewPager;
    SearchView searchView;
    LinearLayout mainLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initview();
    }

    //initialise view of activity
    private void initview()
    {
        context = this.getApplicationContext();
        mainLayout = findViewById(R.id.main_layout);
        toolbar = findViewById(R.id.toolbar);
        tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        initToolbar();
        initTab();
        initSearch();
    }

    //setup search
    private void initSearch()
    {
        searchView = findViewById(R.id.search_movie);
        searchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CharSequence searchtext = searchView.getQuery();
                hideKeyboard();
                TopMovies.getInstance().showSearchResult(searchtext.toString());
                searchView.setQuery("", false);
            }
        });
        searchView.setFocusable(false);
    }

    //initalise toolbar
    private void initToolbar()
    {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle(R.string.app_title);
    }

    //initialise tabs - TopMovies & Favourite
    private void initTab()
    {
        viewPager.setAdapter(new TabPagerAdapter(getSupportFragmentManager(), context));
        tabLayout.setupWithViewPager(viewPager);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {
            }

            @Override
            public void onPageSelected(int i) {
                if(i == 0)
                {
                    TopMovies.getInstance().updateFragment();
                }
                else if(i==1)
                {
                    FavouriteMovies.getInstance().updateFragment();
                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
    }

    //hide device keyboard
    private void hideKeyboard()
    {
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(mainLayout.getWindowToken(), 0);
        }
    }
}
