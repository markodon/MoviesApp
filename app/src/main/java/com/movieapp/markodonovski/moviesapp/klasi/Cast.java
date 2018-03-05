package com.movieapp.markodonovski.moviesapp.klasi;

import java.io.Serializable;

/**
 * Created by markodonovski on 2/19/18.
 */

public class Cast implements Serializable{
    String name;
    String character;
    String profile_path;

    public Cast(String name, String character, String profile_path) {
        this.name = name;
        this.character = character;
        this.profile_path = profile_path;
    }

    public String getName() {
        return name;
    }

    public String getCharacter() {
        return character;
    }

    public String getProfile_path() {
        return profile_path;
    }
}

