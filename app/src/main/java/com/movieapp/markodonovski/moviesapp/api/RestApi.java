package com.movieapp.markodonovski.moviesapp.api;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.movieapp.markodonovski.moviesapp.klasi.FavouritesMoviePost;
import com.movieapp.markodonovski.moviesapp.klasi.Movies;
import com.movieapp.markodonovski.moviesapp.klasi.Moviesmodel;
import com.movieapp.markodonovski.moviesapp.klasi.Peoplemodel;
import com.movieapp.markodonovski.moviesapp.klasi.RatedMoviePost;
import com.movieapp.markodonovski.moviesapp.klasi.VideoModel;
import com.movieapp.markodonovski.moviesapp.klasi.WatchListMoviePost;
import com.movieapp.markodonovski.moviesapp.other.CheckInternetConection;
import com.movieapp.markodonovski.moviesapp.other.LoggingInterceptor;
import com.movieapp.markodonovski.moviesapp.other.PreferencesManager;
import com.movieapp.markodonovski.moviesapp.token.Token;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by markodonovski on 2/10/18.
 */

public class RestApi {

    public static final int request_max_time_in_seconds = 20;
    private Context activity;

    public RestApi(Context activity) {
        this.activity = activity;
    }

    public Retrofit getRetrofitInstance() {

        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(new LoggingInterceptor(activity, PreferencesManager.getToken(activity)))
                .readTimeout(request_max_time_in_seconds, TimeUnit.SECONDS)
                .connectTimeout(request_max_time_in_seconds, TimeUnit.SECONDS)
                .build();

        return new Retrofit.Builder()
                .baseUrl(ApiConstants.baseURL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();

    }

    public ApiService request() {

        return getRetrofitInstance().create(ApiService.class);

    }

    public void checkInternet(Runnable callback) {
        if (CheckInternetConection.CheckInternetConnectivity(activity, true, callback))
            callback.run();
        else {
            Toast.makeText(activity, "Connection failed, please check your connection settings", Toast.LENGTH_SHORT).show();
        }
    }

    public void checkInternet(Runnable callback, boolean showConnectionDialog, String massage) {
        if (CheckInternetConection.CheckInternetConnectivity(activity, showConnectionDialog, callback))
            callback.run();
        else {
            if (showConnectionDialog)
                Toast.makeText(activity, massage, Toast.LENGTH_SHORT).show();
            else
                Log.d("Connection failed", "" + massage);
        }
    }

    public Call<Moviesmodel> getPopular() {
        return request().getPopular();
    }

    public Call<Moviesmodel> getUpcoming() {
        return request().getUpcoming();
    }

    public Call<Moviesmodel> getNowPlaying() {
        return request().getNowPlaying();
    }

    public Call<Moviesmodel> getTopRated() {
        return request().getTopRated();
    }

    public Call<Peoplemodel> getPeople() {
        return request().getPeople();
    }

    public Call<Movies> getMovie(int id, String append_to_response) {
        return request().getMovie(id, append_to_response);
    }

    public Call<Token> getToken(String request_token) {
        return request().getToken(request_token);
    }

    public Call<Token> getLogin(String username, String password, String request_token) {

        return request().getLogin(username, password, request_token);
    }

    public Call<Token> getSesion(String request_token) {
        return request().getSesionId(request_token);
    }

    public Call<Movies> addRating(int id, String header, String sessionid, RatedMoviePost ratedMoviePost) {

        return request().addRating(id, header, sessionid, ratedMoviePost);

    }

    public Call<Movies> addWatchlist(String sessionid, String header, WatchListMoviePost watchListMoviePost) {


        return request().addWachlist(sessionid, header, watchListMoviePost);
    }

    public Call<Movies> addFavourites(String header, String sessionid, FavouritesMoviePost favouritesMoviePost) {
        return request().addFavourites(header, sessionid, favouritesMoviePost);
    }

    public Call<Moviesmodel> getRated(String sessionid) {
        return request().getRated(sessionid);
    }

    public Call<Moviesmodel> getWatchlist(String sessionid){
        return request().getWachlist(sessionid);
    }

    public Call<Moviesmodel> getFavorites(String sessionid){
        return request().getFavorites(sessionid);
    }

    public Call<VideoModel> getTrailer (int pozicija){
        return request().getVideo(pozicija);
    }

    public Call<Movies> checkAll (int id, String sessionid){
        return request().checkAll(id,sessionid);
    }

    public Call<Moviesmodel> getSearchMovie(String query) {

        return request().getSearchMovie(query);

    }

    public Call<Moviesmodel> getUserFavorites(String account_id,String session_id)

    {return request().getUserFavorites(account_id,session_id);}


}



