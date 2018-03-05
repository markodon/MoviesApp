package com.movieapp.markodonovski.moviesapp.token;

import java.io.Serializable;

/**
 * Created by markodonovski on 2/20/18.
 */

public class Token implements Serializable {


    public String session_id;

    public String request_token;

    public Token(String request_token) {
        this.request_token = request_token;
    }

    public String getRequest_token() {
        return request_token;
    }
}
