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
import android.widget.Toast;

import com.test.mymovie.R;
import com.test.mymovie.model.json.MovieJson;
import com.test.mymovie.model.json.MovieResponseJson;
import com.test.mymovie.toprated.customview.MovieAdapter;
import com.test.mymovie.toprated.customview.SearchAdapter;
import com.test.mymovie.toprated.repository.local.Dbhelper;
import com.test.mymovie.toprated.repository.remote.RetrofitClient;
import com.test.mymovie.viewmodel.TopMovieViewModel;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TopMovies extends Fragment {

    private RecyclerView recyclerView;
    private static TopMovies topMovies;
    private MovieAdapter adapter;
    TopMovieViewModel topViewModel;

    public static TopMovies getInstance()
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
        topViewModel.itemPagedList.observe(this, new Observer<PagedList<MovieJson>>() {
            @Override
            public void onChanged(@Nullable PagedList<MovieJson> items) {
                adapter.submitList(items);
            }
        });
        //setting recyclerview for top movies
        recyclerView.setAdapter(adapter);
        return view;
    }

    public void updateFragment()
    {
        //refresing fragment when data change
        topViewModel.recreateList();
        topViewModel.itemPagedList.observe(this, new Observer<PagedList<MovieJson>>() {
            @Override
            public void onChanged(@Nullable PagedList<MovieJson> items) {
                adapter.submitList(items);
            }
        });

        if(recyclerView!=null && adapter != null)
        recyclerView.setAdapter(adapter);
    }

    public void showSearchResult(String search)
    {
        //fetching search result
        RetrofitClient.getInstance()
                .getApi()
                .getSearchedMovies(RetrofitClient.API_KEY, search)
                .enqueue(new Callback<MovieResponseJson>() {

                    @Override
                    public void onResponse(Call<MovieResponseJson> call, Response<MovieResponseJson> response) {
                        if(response.body()!=null)
                        {
                            if(response.body().results.size()>0) {
                                ArrayList<MovieJson> allMovies = new ArrayList();
                                for(MovieJson movie:response.body().results)
                                {
                                    if(movie.getPoster_path()!= null)
                                    {
                                        allMovies.add(movie);
                                    }
                                }
                                Dbhelper.getInstance().addAllMovie(allMovies);
                                SearchAdapter searchAdapter = new SearchAdapter(getContext(), allMovies);
                                if (recyclerView != null && adapter != null) {

                                    recyclerView.setAdapter(searchAdapter);
                                    //recyclerView.getAdapter().notifyDataSetChanged();
                                }
                            }
                            else
                            {
                                Toast.makeText(getContext(), "No Match Found", Toast.LENGTH_SHORT).show();
                            }

                        }
                    }

                    @Override
                    public void onFailure(Call<MovieResponseJson> call, Throwable t) {

                    }
                });
    }
}
