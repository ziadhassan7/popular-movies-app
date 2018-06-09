package com.ziad.popularmovies.utilities;

import android.net.Uri;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;


public class NetworkUtils {

    private final static String MOVIES_BASE_URL = "http://api.themoviedb.org/3/";
    private final static String TYPE_PARAM = "movie";
    private final static String sortByPopular = "popular";
    private final static String sortByTopRated = "top_rated";
    private final static String API_Query_PARAM = "api_key";
    //------API KEY------
    private final static String api_key= "";

    //return Movies Url
    public static URL buildUrl(boolean userHaveChosenSortByPopular){
        Uri builtUri;

        //check if the user have chosen sort by popular movies or not
        if(userHaveChosenSortByPopular) {
            builtUri = Uri.parse(MOVIES_BASE_URL).buildUpon()
                    .appendPath(TYPE_PARAM)
                    .appendPath(sortByPopular)
                    .appendQueryParameter(API_Query_PARAM, api_key)
                    .build();
        }else{
            builtUri = Uri.parse(MOVIES_BASE_URL).buildUpon()
                    .appendPath(TYPE_PARAM)
                    .appendPath(sortByTopRated)
                    .appendQueryParameter(API_Query_PARAM, api_key)
                    .build();
        }

        URL url = null;

        try{
            url = new URL(builtUri.toString());
        }catch (MalformedURLException e){
            e.printStackTrace();
        }

        return url;
    }


    public static String getResponseFromHttpUrl(URL url) throws IOException{
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

        try{
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if(hasInput){
                return scanner.next();
            }else{
                return null;
            }

        }finally {
            urlConnection.disconnect();
        }
    }
}
