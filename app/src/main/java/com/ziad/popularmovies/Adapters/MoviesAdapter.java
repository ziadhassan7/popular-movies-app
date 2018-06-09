package com.ziad.popularmovies.Adapters;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;
import com.ziad.popularmovies.Activities.DetailsActivity;
import com.ziad.popularmovies.R;
import com.ziad.popularmovies.databinding.MovieCardItemBinding;
import com.ziad.popularmovies.model.Movie;
import com.ziad.popularmovies.constants.Constants;

import java.util.ArrayList;


public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MovieViewHolder> {
    private ArrayList<Movie> mMoviesList;

    public MoviesAdapter(ArrayList<Movie> moviesList){
        mMoviesList = moviesList;
    }


    class MovieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        MovieCardItemBinding binding;
        MovieViewHolder(View itemView){
            super(itemView);

            binding = DataBindingUtil.bind(itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int  clickedItemPosition = getAdapterPosition();

            Intent intent = new Intent(view.getContext(), DetailsActivity.class);
            intent.putExtra("movie-item", mMoviesList.get(clickedItemPosition));
            view.getContext().startActivity(intent);
        }
    }


    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);

        MovieCardItemBinding binding = DataBindingUtil.inflate(layoutInflater, R.layout.movie_card_item, viewGroup, false);
        return new MovieViewHolder(binding.getRoot());
    }

    @Override
    public void onBindViewHolder(MovieViewHolder holder, int position) {
        Movie movieObj = mMoviesList.get(position);
        loadImage(holder, movieObj.getPosterPath());

    }

    public void loadImage(MovieViewHolder holder, String path){
        Context context = holder.binding.posterImage.getContext();

        Picasso
                .with(context)
                .load(Constants.POSTER_BASE_URL + path)
                .into(holder.binding.posterImage);
    }


    @Override
    public int getItemCount() {
        return mMoviesList.size();
    }

}
