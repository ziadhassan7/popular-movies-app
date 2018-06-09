package com.ziad.popularmovies.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Movie implements Parcelable{



    //Some local Variables
    private String title, originalTitle,
            posterPath, backdropPath,
            overview, releaseDate;

    private double voteAverage;
    //


    //Constructor used for instantiation
    public Movie() {
    }
    //

    public Movie(String title,String originalTitle
                ,double voteAverage
                ,String posterPath,String backdropPath
                ,String overview, String releaseDate){

        //
        this.title = title;
        this.originalTitle = originalTitle;

        this.voteAverage = voteAverage;

        this.posterPath = posterPath;
        this.backdropPath = backdropPath;

        this.overview = overview;
        this.releaseDate = releaseDate;
        //
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(title);
        parcel.writeString(originalTitle);
        parcel.writeString(posterPath);
        parcel.writeString(backdropPath);
        parcel.writeString(overview);
        parcel.writeString(releaseDate);
        parcel.writeDouble(voteAverage);
    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

    protected Movie(Parcel in) {
        this.title = in.readString();
        this.originalTitle = in.readString();
        this.posterPath = in.readString();
        this.backdropPath = in.readString();
        this.overview = in.readString();
        this.releaseDate = in.readString();
        this.voteAverage = in.readDouble();

        /*setTitle(in.readString());
        setOriginalTitle(in.readString());
        setPosterPath(in.readString());
        setBackdropPath(in.readString());
        setOverview(in.readString());
        setReleaseDate(in.readString());
        setVoteAverage(in.readDouble());*/
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public double getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(double voteAverage) {
        this.voteAverage = voteAverage;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    public void setBackdropPath(String backdropPath) {
        this.backdropPath = backdropPath;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }
}