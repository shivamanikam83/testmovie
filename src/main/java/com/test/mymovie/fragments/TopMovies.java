package com.test.mymovie.fragments;

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
import android.widget.ProgressBar;
import android.widget.Toast;

import com.test.mymovie.R;
import com.test.mymovie.model.json.MovieJson;
import com.test.mymovie.customview.MovieAdapter;
import com.test.mymovie.customview.SearchAdapter;
import com.test.mymovie.presenter.moviesearch.SearchPresenter;
import com.test.mymovie.presenter.moviesearch.SearchViewCallback;
import com.test.mymovie.viewmodel.TopMovieViewModel;

import java.util.ArrayList;

public class TopMovies extends Fragment implements SearchViewCallback {

    private RecyclerView recyclerView;
    private static TopMovies topMovies;
    private MovieAdapter adapter;
    TopMovieViewModel topViewModel;
    SearchPresenter searchPresenter;

    public static synchronized TopMovies getInstance()
    {
        return topMovies;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        topMovies = this;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.top_movies, container, false);

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));
        recyclerView.setHasFixedSize(true);
        //fetching dataobserver
        topViewModel = ViewModelProviders.of(this).get(TopMovieViewModel.class);
        adapter = new MovieAdapter(getContext());

        //notify adapter of paging data change
        topViewModel.topMoviePagedList.observe(this, new Observer<PagedList<MovieJson>>() {
            @Override
            public void onChanged(@Nullable PagedList<MovieJson> items) {
                adapter.submitList(items);
            }
        });
        //setting recyclerview for top movies
        recyclerView.setAdapter(adapter);
        searchPresenter = new SearchPresenter(this);
        return view;
    }

    public void updateFragment()
    {
        //refresing fragment when data change
        topViewModel.recreateList();
        if(recyclerView!=null && adapter !=null) {
            topViewModel.topMoviePagedList.observe(this, new Observer<PagedList<MovieJson>>() {
                @Override
                public void onChanged(@Nullable PagedList<MovieJson> items) {
                    adapter.submitList(items);
                }
            });
            recyclerView.setAdapter(adapter);
        }
    }

    public void showSearchResult(String search)
    {
        //fetching search result
        searchPresenter.showSearchResult(search);
    }

    @Override
    public void onSearchSuccess(ArrayList<MovieJson> allMovies) {
        SearchAdapter searchAdapter = new SearchAdapter(getContext(), allMovies);
        if (recyclerView != null && adapter != null) {
            recyclerView.setAdapter(searchAdapter);
        }
    }

    @Override
    public void onSearchError() {
        Toast.makeText(getContext(), "No Match Found", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onInvalidString() {
        Toast.makeText(getContext(), "Enter valid input", Toast.LENGTH_SHORT).show();
    }
}
