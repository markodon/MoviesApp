package com.movieapp.markodonovski.moviesapp.klasi;

import java.io.Serializable;

/**
 * Created by markodonovski on 2/20/18.
 */

public class User implements Serializable{
    public String name;
    public String username;
    public String password;
    public String avatar;


    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getAvatar() {
        return avatar;
    }
}
