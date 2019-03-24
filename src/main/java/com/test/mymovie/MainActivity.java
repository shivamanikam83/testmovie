package com.test.mymovie;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.Toast;

import com.test.mymovie.customview.TabPagerAdapter;
import com.test.mymovie.fragments.FavouriteMovies;
import com.test.mymovie.fragments.TopMovies;
import com.test.mymovie.presenter.main.MainActivityPresenter;
import com.test.mymovie.presenter.main.MainActivityViewCallback;

public class MainActivity extends AppCompatActivity implements MainActivityViewCallback {

    public static Context context;
    Toolbar toolbar;
    TabLayout tabLayout;
    ViewPager viewPager;
    SearchView searchView;
    LinearLayout mainLayout;
    MainActivityPresenter presenter;

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
        presenter = new MainActivityPresenter(this);
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
                presenter.onSearchClick(searchtext.toString());
                hideKeyboard();

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

    //check if internet is available or not
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
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
                    presenter.showTopMovies();
                }
                else if(i==1)
                {
                    presenter.showFavouriteMovies();
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

    @Override
    public void onSearchSuccess(String sText) {
        TopMovies.getInstance().showSearchResult(sText);
        hideKeyboard();
        searchView.setQuery("", false);
    }

    @Override
    public void onSearchError() {
        hideKeyboard();
        Toast.makeText(MainActivity.this, "Enter valid input", Toast.LENGTH_SHORT).show();
    }
}
