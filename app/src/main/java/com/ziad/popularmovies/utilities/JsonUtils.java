package com.ziad.popularmovies.utilities;

import android.util.Log;

import com.ziad.popularmovies.model.Movie;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class JsonUtils {
//---------------------------------------------------
    //Movie Name
    private static String title;
    private static String originalTitle;
    //How good is the movie?
    private static double voteAverage;
    //Movie Posters
    private static String posterPath;
    private static String backdropPath;
    //The Description of the Movie
    private static String overview;
    private static String releaseDate;

//---------------------------------------------------

    public static ArrayList<Movie> parseJsonData(String json){
        ArrayList<Movie> moviesList = null;

        if (json != null){
            try {
                //
                JSONObject moviesData = new JSONObject(json);

                JSONArray results = moviesData.getJSONArray("results");

                //An ArrayList of Movie Instances
                moviesList = new ArrayList<>();

                //


                for (int i = 0; i < results.length(); i++){
                    //get object from json array
                    JSONObject jsonObject = results.getJSONObject(i);

                    //Title of the Movie
                    title = jsonObject.getString("title");

                    //Original Title of the movie
                    originalTitle = jsonObject.getString("original_title");

                    //People vote Average
                    voteAverage = jsonObject.getDouble("vote_average");

                    //Movie Posters
                    posterPath = jsonObject.getString("poster_path");
                    backdropPath = jsonObject.getString("backdrop_path");

                    //The Description of the movie
                    overview = jsonObject.getString("overview");
                    releaseDate = jsonObject.getString("release_date");

                    //Store each Movie instance into the ArrayList
                    Movie movieInstance = new Movie(title,originalTitle,voteAverage,posterPath,backdropPath,overview,releaseDate);
                    moviesList.add(movieInstance);

                }


            //Catch any possible exception
            }catch (JSONException e){
                e.printStackTrace();
            }catch (Exception e){
                e.printStackTrace();
            }
            //

        }
        //return an ArrayList of movie instances
        return moviesList;
    }
}