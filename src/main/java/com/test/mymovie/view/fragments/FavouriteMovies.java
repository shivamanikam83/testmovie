package com.test.mymovie.view.fragments;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.arch.paging.PagedList;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.test.mymovie.R;
import com.test.mymovie.model.json.MovieJson;
import com.test.mymovie.toprated.customview.MovieAdapter;
import com.test.mymovie.viewmodel.FavouriteViewModel;

public class FavouriteMovies extends Fragment {

    private RecyclerView recyclerView;
    private MovieAdapter movieAdapter;

    private static FavouriteMovies favouriteMovies;
    public FavouriteViewModel favViewModel;

    public static FavouriteMovies getInstance()
    {
        return favouriteMovies;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        favouriteMovies = this;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.top_movies, container, false);

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));
        recyclerView.setHasFixedSize(true);

        favViewModel = ViewModelProviders.of(this).get(FavouriteViewModel.class);
        movieAdapter = new MovieAdapter(getContext());

        favViewModel.itemPagedList.observe(this, new Observer<PagedList<MovieJson>>() {
            @Override
            public void onChanged(@Nullable PagedList<MovieJson> items) {
                movieAdapter.submitList(items);
            }
        });

        recyclerView.setAdapter(movieAdapter);
        return view;
    }

    public void updateFragment()
    {
        favViewModel.recreateList();

        favViewModel.itemPagedList.observe(this, new Observer<PagedList<MovieJson>>() {
            @Override
            public void onChanged(@Nullable PagedList<MovieJson> items) {
                movieAdapter.submitList(items);
            }
        });

        if(recyclerView!=null && movieAdapter != null)
            recyclerView.setAdapter(movieAdapter);
    }

    @Override
    public void onResume() {
        super.onResume();
    }
}
