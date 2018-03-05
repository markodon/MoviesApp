package com.movieapp.markodonovski.moviesapp.klasi;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by markodonovski on 2/12/18.
 */

public class People implements Serializable{

    ArrayList<Knownfor> known_for;

    int id;
    String name;
    String profile_path;



    public People(int id, String name, String profile_path, ArrayList<Knownfor> known_for) {
        this.id = id;
        this.name = name;
        this.profile_path = profile_path;
        this.known_for = known_for;
    }

    public ArrayList<Knownfor> getKnown_for() {
        return known_for;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getProfile_path() {
        return profile_path;
    }


}
