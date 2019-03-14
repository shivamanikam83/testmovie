package com.test.mymovie.toprated.customview;

import android.arch.paging.PagedListAdapter;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.test.mymovie.DetailActivity;
import com.test.mymovie.R;
import com.test.mymovie.model.json.MovieJson;
import com.test.mymovie.toprated.repository.local.Dbhelper;
import com.test.mymovie.toprated.repository.remote.RetrofitClient;

import java.util.List;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.MovieViewHolder> {

    private List<MovieJson> movielist;
    private Context mCtx;

    public SearchAdapter(Context mCtx, List<MovieJson> list) {
        this.mCtx = mCtx;
        this.movielist = list;
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mCtx).inflate(R.layout.recyclerview_item, parent, false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MovieViewHolder holder, int position) {

        final MovieJson movie = movielist.get(position);

        if (movie != null) {

            Glide.with(mCtx)
                    .load(RetrofitClient.IMAGE_BASE_URL+movie.poster_path)
                    .listener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                            holder.imageView.setImageResource(R.drawable.ic_sentiment_dissatisfied_black_24dp);
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                            return false;
                        }
                    })
                    .into(holder.imageView);

            holder.favouritebg.setVisibility(View.GONE);
            if(movie.isFavourite())
            {
                movie.setFavourite(true);
                holder.favouritebg.setVisibility(View.VISIBLE);
            }

            holder.imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(movie.getPoster_path()!=null) {
                        Intent intent = new Intent(v.getContext(), DetailActivity.class);
                        intent.putExtra("MOVIE_ID", movie.id);
                        v.getContext().startActivity(intent);
                    }
                }
            });

            holder.favourite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(movie.isFavourite())
                    {
                        movie.setFavourite(false);
                        holder.favouritebg.setVisibility(View.GONE);
                    }
                    else
                    {
                        movie.setFavourite(true);
                        holder.favouritebg.setVisibility(View.VISIBLE);
                    }
                    Dbhelper.getInstance().updateMovie(movie);

                }
            });

        }

    }

    @Override
    public int getItemCount() {
        return movielist.size();
    }

    class MovieViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        RelativeLayout favourite;
        LinearLayout favouritebg;

        public MovieViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
            favourite = itemView.findViewById(R.id.favcontainer);
            favouritebg = itemView.findViewById(R.id.favouritebg);
        }
    }
}
