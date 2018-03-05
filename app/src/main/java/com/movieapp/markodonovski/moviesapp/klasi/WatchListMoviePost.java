package com.movieapp.markodonovski.moviesapp.klasi;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by markodonovski on 2/27/18.
 */

public class WatchListMoviePost implements Serializable {

    @SerializedName("media_type")

    public String media_type;

    @SerializedName("media_id")

    public int media_id;

    @SerializedName("watchlist")

    public boolean watchlist;
}
