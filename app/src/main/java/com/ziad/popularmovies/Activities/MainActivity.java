package com.ziad.popularmovies.Activities;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.ziad.popularmovies.AsyncTask.MovieFetchingTask;
import com.ziad.popularmovies.Adapters.MoviesAdapter;
import com.ziad.popularmovies.R;
import com.ziad.popularmovies.interfaces.AsyncTaskCallback;
import com.ziad.popularmovies.model.Movie;
import com.ziad.popularmovies.utilities.NetworkUtils;

import java.net.URL;
import java.util.ArrayList;

//----------------------------------------------------------------------------------------------------------------------

public class MainActivity extends AppCompatActivity implements AsyncTaskCallback {

    public RecyclerView mMoviesRecycler;
    public ProgressBar mLoadingIndicator;
    public TextView mNetworkErrorText;
    private ArrayList<Movie> mMoviesList;
    boolean userHaveChosenSortByPopularity;
    SwipeRefreshLayout mSwipeRefreshLayout;


    // Callback methods (get the MovieList - Switch the loading indicator on & off) ----------

    @Override
    public void onPreExecute() {
        mNetworkErrorText.setVisibility(View.INVISIBLE);
        mMoviesRecycler.setVisibility(View.INVISIBLE);
        mLoadingIndicator.setVisibility(View.VISIBLE);
    }

    @Override
    public void onPostExecute(ArrayList<Movie> moviesList) {
        mMoviesList = moviesList;
        MoviesAdapter mAdapter = new MoviesAdapter(mMoviesList);
        mMoviesRecycler.setAdapter(mAdapter);

        //---

        mNetworkErrorText.setVisibility(View.INVISIBLE);
        mLoadingIndicator.setVisibility(View.INVISIBLE);
        mMoviesRecycler.setVisibility(View.VISIBLE);
    }

    //OnCreate() -----------------------------------------------------------------------------

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userHaveChosenSortByPopularity = true;


        mMoviesRecycler = findViewById(R.id.rv_movies);
        mLoadingIndicator =  findViewById(R.id.loading_indicator);
        mNetworkErrorText = findViewById(R.id.network_error_text);

        //Start the AsyncTask!
        if(isNetworkAvailable()){
        startAsyncTask(userHaveChosenSortByPopularity);
        }else{
            mNetworkErrorText.setVisibility(View.VISIBLE);
        }

        //Initiate & Set the LayoutManager
        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        mMoviesRecycler.setLayoutManager(layoutManager);


        //Swipe to refresh button
        mSwipeRefreshLayout = findViewById(R.id.swiperefresh);
        mSwipeRefreshLayout.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {

                        // This method performs the actual data-refresh operation.
                        // The method calls setRefreshing(false) when it's finished.
                        myUpdateOperation();
                    }
                }
        );

    }

    private boolean isNetworkAvailable() {

        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();

        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }


    private void myUpdateOperation() {
        startAsyncTask(userHaveChosenSortByPopularity);
        mSwipeRefreshLayout.setRefreshing(false);

    }


//Start the AsyncTask! ----------------------------------------------------------------------

    private void startAsyncTask(boolean userHaveChosenSortByPopularity){
        URL builtUrl = NetworkUtils.buildUrl(userHaveChosenSortByPopularity);
        new MovieFetchingTask(this).execute(builtUrl);
    }




    //Option Menu methods -----------------------------------------------------------------------

    Dialog onCreateDialogSingleChoice(){

        CharSequence[] array = {"Popularity", "Rating"};

        final AlertDialog.Builder builder  = new AlertDialog.Builder(this);

        builder.setTitle("Sort Movies By");
        builder.setSingleChoiceItems(array, -1, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int chosenItem) {
                switch(chosenItem){
                    case 0:
                        //boolean
                        userHaveChosenSortByPopularity = true;
                        break;
                    case 1:
                        userHaveChosenSortByPopularity = false;
                        break;
                }

            }
        })

                .setPositiveButton("Sort", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        // User clicked OK, so save the result somewhere
                        // or return them to the component that opened the dialog

                        //pass boolean to startActivity
                        startAsyncTask(userHaveChosenSortByPopularity);

                    }
                });

        return builder.create();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

            // Check if user triggered a refresh:
        if (id == R.id.refresh) {

            // Start the refresh background task.
            // This method calls setRefreshing(false) when it's finished.
            if(isNetworkAvailable()){
                startAsyncTask(userHaveChosenSortByPopularity);
            }else{
                Toast.makeText(this, "There is no internet connection", Toast.LENGTH_SHORT).show();
            }


            return true;

        }else if (id == R.id.action_sort_by){
            Dialog dialog = onCreateDialogSingleChoice();
            dialog.show();

            return true;
        }

        // User didn't trigger a refresh, let the superclass handle this action
        return super.onOptionsItemSelected(item);
    }

}

//-----------------------------------------------------------------------------------------------------------------------