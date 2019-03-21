package com.test.mymovie.customview;

import android.arch.paging.PagedListAdapter;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.test.mymovie.DetailActivity;
import com.test.mymovie.R;
import com.test.mymovie.model.json.MovieJson;
import com.test.mymovie.repository.local.Dbhelper;
import com.test.mymovie.repository.remote.RetrofitClient;

public class MovieAdapter extends PagedListAdapter<MovieJson, MovieAdapter.MovieViewHolder> {

    private Context mCtx;

    public MovieAdapter(Context mCtx) {
        super(DIFF_CALLBACK);
        this.mCtx = mCtx;
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mCtx).inflate(R.layout.recyclerview_item, parent, false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MovieViewHolder holder, int position) {

        final MovieJson movie = getItem(position);

        if (movie != null) {

            Glide.with(mCtx)
                    .load(RetrofitClient.IMAGE_BASE_URL+movie.poster_path)
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
                    Intent intent = new Intent(v.getContext(), DetailActivity.class);
                    intent.putExtra("MOVIE_ID", movie.id);
                    v.getContext().startActivity(intent);
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

    private static DiffUtil.ItemCallback<MovieJson> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<MovieJson>() {
                @Override
                public boolean areItemsTheSame(MovieJson oldItem, MovieJson newItem) {
                    return oldItem.id == newItem.id;
                }

                @Override
                public boolean areContentsTheSame(MovieJson oldItem, MovieJson newItem) {
                    return oldItem.equals(newItem);
                }
            };


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
