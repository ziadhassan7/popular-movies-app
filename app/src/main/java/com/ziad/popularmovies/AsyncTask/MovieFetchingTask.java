package com.ziad.popularmovies.AsyncTask;


import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import com.ziad.popularmovies.interfaces.AsyncTaskCallback;
import com.ziad.popularmovies.model.Movie;
import com.ziad.popularmovies.utilities.JsonUtils;
import com.ziad.popularmovies.utilities.NetworkUtils;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;


public class MovieFetchingTask extends AsyncTask<URL, Void, String>{
    private final AsyncTaskCallback mAsyncTaskCallback;


    public MovieFetchingTask(AsyncTaskCallback mAsyncTaskCallback) {
        this.mAsyncTaskCallback = mAsyncTaskCallback;
    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        mAsyncTaskCallback.onPreExecute();
    }

    @Override
    protected String doInBackground(URL... params) {
        URL passedUrlParam = params[0];
        String moviesJsonData = null;

        try{
            moviesJsonData = NetworkUtils.getResponseFromHttpUrl(passedUrlParam);
        }catch (IOException e){
            e.printStackTrace();
        }

        return moviesJsonData;
    }

    @Override
    protected void onPostExecute(String moviesJsonData){

        ArrayList<Movie> mMoviesList  = JsonUtils.parseJsonData(moviesJsonData);
        mAsyncTaskCallback.onPostExecute(mMoviesList);
    }

}