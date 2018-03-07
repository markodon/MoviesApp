package com.movieapp.markodonovski.moviesapp.api;


import com.movieapp.markodonovski.moviesapp.klasi.FavouritesMoviePost;
import com.movieapp.markodonovski.moviesapp.klasi.Movies;
import com.movieapp.markodonovski.moviesapp.klasi.Moviesmodel;
import com.movieapp.markodonovski.moviesapp.klasi.Peoplemodel;
import com.movieapp.markodonovski.moviesapp.klasi.RatedMoviePost;
import com.movieapp.markodonovski.moviesapp.klasi.User;
import com.movieapp.markodonovski.moviesapp.klasi.VideoModel;
import com.movieapp.markodonovski.moviesapp.klasi.WatchListMoviePost;
import com.movieapp.markodonovski.moviesapp.token.Token;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Anti on 1/16/2018.
 */

public interface ApiService {


    @GET("movie/popular?" + ApiConstants.api_key)
    Call<Moviesmodel> getPopular();

    @GET("movie/top_rated?" + ApiConstants.api_key)
    Call<Moviesmodel> getTopRated();

    @GET("movie/upcoming?" + ApiConstants.api_key)
    Call<Moviesmodel> getUpcoming();

    @GET("movie/now_playing?" + ApiConstants.api_key)
    Call<Moviesmodel> getNowPlaying();

    @GET("person/popular?" + ApiConstants.api_key)
    Call<Peoplemodel> getPeople();

    @GET("movie/{movie_id}?" + ApiConstants.api_key)

    Call<Movies> getMovie(@Path("movie_id") int link,@Query("append_to_response") String credits);

    @GET("authentication/token/new?" + ApiConstants.api_key)
    Call<Token> getToken(@Query ("request_token") String request_code);

    @GET("authentication/token/validate_with_login?" + ApiConstants.api_key)
    Call<Token> getLogin (@Query("username")String username, @Query ("password") String password, @Query ("request_token") String request_code) ;

    @GET("authentication/session/new?" + ApiConstants.api_key)
    Call<Token> getSesionId (@Query ("request_token") String request_code) ;

    @POST("movie/{movie_id}/rating?" + ApiConstants.api_key)
    Call<Movies> addRating (@Path("movie_id") int id, @Header("json/application") String contentHeader, @Query("session_id") String sessionid, @Body RatedMoviePost ratedMoviePost);

    @GET("account/account_id/rated/movies?" + ApiConstants.api_key)
    Call<Moviesmodel> getRated (@Query("session_id") String sessionid);

    @POST("account/account_id/watchlist?" + ApiConstants.api_key)
    Call<Movies> addWachlist (@Query("session_id") String sessionid , @Header("json/application") String contentHeader, @Body WatchListMoviePost watchListMoviePost);

    @GET("account/account_id/watchlist/movies?" + ApiConstants.api_key)
    Call<Moviesmodel> getWachlist (@Query("session_id") String sessionid);

    @POST("account/account_id/favorite?" + ApiConstants.api_key)
    Call<Movies> addFavourites (@Query("session_id") String sessionid,@Header("json/application") String contentHeader, @Body FavouritesMoviePost favouritesMoviePost);

    @GET("account/account_id/favorite/movies?" + ApiConstants.api_key)
    Call<Moviesmodel> getFavorites (@Query ("session_id") String sessionId) ;

    @GET("movie/{movie_id}/videos?" + ApiConstants.api_key)
    Call<VideoModel> getVideo(@Path("movie_id") int pozicija);

    @GET("movie/{movie_id}/account_states?" + ApiConstants.api_key)
    Call<Movies> checkAll (@Path("movie_id") int id, @Query("session_id") String sessionid);

    @GET("search/movie?" + ApiConstants.api_key)

    Call<Moviesmodel> getSearchMovie (@Query ("query") String query) ;

    @GET("account/{account_id}/favorite/movies?" + ApiConstants.api_key)

    Call<Moviesmodel> getUserFavorites(@Path("account_id") String account_id,@Query("session_id") String session_id);

    @GET("account?" + ApiConstants.api_key)

    Call<User> getAccountDetails(@Query("session_id")String session_id);


}
