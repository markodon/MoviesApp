package com.movieapp.markodonovski.moviesapp.klasi;

import java.io.Serializable;

/**
 * Created by markodonovski on 2/10/18.
 */

public class Movies implements Serializable{

    public int id;
    public String title;
    public String poster_path;
    public String vote_average;

    public String overview;
    public Credits credits;
    public boolean watchlist;
    public boolean favorite;


    public Movies(int id, String title, String poster_path, String vote_average) {
        this.id = id;
        this.title = title;
        this.poster_path = poster_path;
        this.vote_average = vote_average;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public String getVote_average() {
        return vote_average;
    }
}
