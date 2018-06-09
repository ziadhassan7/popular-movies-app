package com.ziad.popularmovies.Activities;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;
import com.ziad.popularmovies.R;
import com.ziad.popularmovies.constants.Constants;
import com.ziad.popularmovies.model.Movie;

public class DetailsActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //---
        final Movie movieItem = getIntent().getParcelableExtra("movie-item");
        //----------

        //---
        setContentView(R.layout.activity_details);
        final Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_round_arrow_back_24px);
        setSupportActionBar(toolbar);

        final CollapsingToolbarLayout collapsingToolbar = findViewById(R.id.collapsingToolbarLayout);
        AppBarLayout appBarLayout = findViewById(R.id.appBarLayout);
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = true;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    collapsingToolbar.setTitle(movieItem.getTitle());
                    isShow = true;
                    collapsingToolbar.setCollapsedTitleTextColor(Color.BLACK);
                } else if(isShow) {
                    collapsingToolbar.setTitle(" ");//careful there should a space between double quote otherwise it wont work
                    isShow = false;
                }
            }
        });

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Not implemented yet!", Snackbar.LENGTH_SHORT)
                        .setAction("Action", null).show();
            }
        });
        //----------


        //Setting the data to its views
        ImageView backdrop = findViewById(R.id.backdrop);
        Picasso
                .with(this)
                .load(Constants.BACKDROP_BASE_URL + movieItem.getBackdropPath())
                .into(backdrop);

        ImageView poster = findViewById(R.id.poster);
        Picasso
                .with(this)
                .load(Constants.POSTER_BASE_URL + movieItem.getPosterPath())
                .into(poster);


        TextView movieName = findViewById(R.id.movieName);
        movieName.setText(movieItem.getTitle());

        TextView releaseDate = findViewById(R.id.releaseDate);
        releaseDate.setText(movieItem.getReleaseDate());

        TextView ratingVar = findViewById(R.id.ratingVar);
        ratingVar.setText(String.valueOf(movieItem.getVoteAverage()));

        TextView movieDetails = findViewById(R.id.movieDetails);
        movieDetails.setText(movieItem.getOverview());
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
