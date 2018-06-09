package com.ziad.popularmovies.interfaces;

import com.ziad.popularmovies.model.Movie;
import java.util.ArrayList;

public interface AsyncTaskCallback {
    void onPreExecute();
    void onPostExecute(ArrayList<Movie> moviesList);
}