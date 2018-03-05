package com.movieapp.markodonovski.moviesapp.klasi;

import java.io.Serializable;

/**
 * Created by markodonovski on 2/15/18.
 */

public class Knownfor implements Serializable {

    String title;
    String poster_path;

    public Knownfor(String title, String poster_path) {
        this.title = title;
        this.poster_path = poster_path;
    }

    public String getTitle() {
        return title;
    }

    public String getPoster_path() {
        return poster_path;
    }
}
