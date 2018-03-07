package com.movieapp.markodonovski.moviesapp;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.movieapp.markodonovski.moviesapp.api.ApiConstants;
import com.movieapp.markodonovski.moviesapp.api.RestApi;
import com.movieapp.markodonovski.moviesapp.klasi.Cast;
import com.movieapp.markodonovski.moviesapp.klasi.Credits;
import com.movieapp.markodonovski.moviesapp.klasi.Crew;
import com.movieapp.markodonovski.moviesapp.klasi.FavouritesMoviePost;
import com.movieapp.markodonovski.moviesapp.klasi.Movies;
import com.movieapp.markodonovski.moviesapp.klasi.RatedList;
import com.movieapp.markodonovski.moviesapp.klasi.RatedMoviePost;
import com.movieapp.markodonovski.moviesapp.klasi.Video;
import com.movieapp.markodonovski.moviesapp.klasi.VideoModel;
import com.movieapp.markodonovski.moviesapp.klasi.WatchListMoviePost;
import com.movieapp.markodonovski.moviesapp.other.PreferencesManager;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailsActivity extends AppCompatActivity {

    Context context;
    int pozicija;

    Movies model;
    Crew crew;
    Cast cast;
    Video video;
    VideoModel videoModel;
    RatedList ratedList;



    @BindView(R.id.slika_detali)
    ImageView slika;


    @BindView(R.id.textReziser)
    TextView reziser;

    @BindView(R.id.textScenario)
    TextView scenario;

    @BindView(R.id.textSvezdi)
    TextView svezdi;

    @BindView(R.id.textGore)
    TextView tekstGore;


    @BindView(R.id.textOpis)
    TextView opis;

    @BindView(R.id.favo)
    ImageView favo;

    @BindView(R.id.star)
    ImageView star;

    @BindView(R.id.watch)
    ImageView watch;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        ButterKnife.bind(this);


        context = this;

        final Intent intent = getIntent();
        if (intent.hasExtra("DetailsActivity")) {
            pozicija = intent.getIntExtra("DetailsActivity", 0);

            Getmovie();

//            EdenFavorit();
        }
    }

    @OnClick(R.id.cast)
    public void klik (View view){
        Intent intent2 = new Intent(DetailsActivity.this,FullCastActivity.class);
        intent2.putExtra("Cast", pozicija);
        startActivity(intent2);

    }

    @OnClick(R.id.trailer)
    public void kliktrailer(View view){
          RestApi  api = new RestApi(context);
            final Call<VideoModel> videoModelCall = api.getTrailer(model.id);
            videoModelCall.enqueue(new Callback<VideoModel>() {
                @Override
                public void onResponse(Call<VideoModel> call, Response<VideoModel> response) {
                    if (response.isSuccessful()) {
                        videoModel = response.body();
                        video = videoModel.results.get(0);
                        if (video==null){
                            video=videoModel.results.get(1);
                        } else if (video==null){
                            video=videoModel.results.get(2);
                        }
                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        intent.setData(Uri.parse("https://www.youtube.com/watch?v=" + video.key));
                        startActivity(intent);

                    }
                }

                @Override
                public void onFailure(Call<VideoModel> call, Throwable t) {

                }
            });
        }





    public void Getmovie() {



        RestApi api = new RestApi(this);



        Call<Movies> call = api.getMovie(pozicija, "credits");




        call.enqueue(new Callback<Movies>() {

            @Override

            public void onResponse(Call<Movies> call, Response<Movies> response) {
//                EdenFavorit();

                if (response.code() == 200) {

                    ArrayList<Crew> writers = new ArrayList<>();

                    ArrayList<Cast> stars = new ArrayList<>();

                    model = response.body();
                    EdenFavorit();




                    if (model.overview != null) {

                        opis.setText("Overview: " + model.overview);

                    } else {

                        opis.setText("No overview available");

                    }

                    if (model.credits.crew.size() > 0) {

                        for (int i = 0; i < 1; i++) {

                            crew = model.credits.crew.get(i);

                            reziser.setText("Director: " + crew.getName());

                        }

                        for (Crew crew : model.credits.crew) {

                            if (crew.department.equals("Writing")) {

                                writers.add(crew);

                            } else {

                                tekstGore.setText("");

                            }

                        }

                        if (writers.size() == 1) {

                            crew = writers.get(0);

                            tekstGore.setText(crew.name);

                        } else if (writers.size() > 1) {

                            for (int i = 0; i < 2; i++) {

                                crew = writers.get(i);

                                if (writers.size() > 0) {

                                    tekstGore.setText(tekstGore.getText().toString() + crew.name + ", ");

                                }

                            }

                        }

                        tekstGore.setText("Writers: " + tekstGore.getText().toString());

                        if (model.credits.cast.size() >= 3) {

                            for (int i = 0; i < 3; i++) {

                                cast = model.credits.cast.get(i);

                                if (cast != null) {

                                    stars.add(cast);

                                }

                            }

                        } else if (model.credits.cast.size() == 2) {

                            for (int i = 0; i < 2; i++) {

                                cast = model.credits.cast.get(i);

                                if (cast != null) {

                                    stars.add(cast);

                                }

                            }

                        } else {

                            if (cast != null) {

                                stars.add(cast);

                            }

                        }


                        if (stars.size() > 0) {

                            for (int i = 0; i < stars.size(); i++) {

                                cast = stars.get(i);

                                if (stars.size() > 0) {

                                    svezdi.setText(svezdi.getText().toString() + cast.getName() + ", ");

                                }

                            }

                            svezdi.setText("Stars: " + svezdi.getText().toString());

                        } else {


                            svezdi.setText("Stars: ");

                        }

//                        toolbarLayout.setTitle(model.title);
//
//                        titleMovie.setText(model.title);

                        String path = "http://image.tmdb.org/t/p/w185" + model.getPoster_path();

                        Picasso.with(context).load(path).centerInside().fit().into(slika);

//                        genreAdapter = new RecyclerViewGenretAdapter(ScrollingMovieDetailActivity.this, new OnRowGenreClickListener() {
//
//                            @Override
//
//                            public void onRowClick(Genre genre, int position) {
//
//                                Intent intent = new Intent(ScrollingMovieDetailActivity.this, ExploreActivity.class);
//
//                                intent.putExtra("GID", genre.id);
//
//                                intent.putExtra("name", genre.name);
//
//                                startActivityForResult(intent, 1212);
//
//                            }
//
//                        });

//                        genreAdapter.setItems(model.genres);
//
//                        genresRV.setHasFixedSize(true);
//
//                        genresRV.setLayoutManager(new LinearLayoutManager(ScrollingMovieDetailActivity.this, LinearLayoutManager.HORIZONTAL, false));
//
//                        genresRV.setAdapter(genreAdapter);
//
//                        WatchlistListener();
//
//                        FavoriteListener();

                    }
                }


            }

            @Override
            public void onFailure(Call<Movies> call, Throwable t) {

            }

        });

        favo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                addFavourite();
                Toast.makeText(context, "successfully added to favorite ", Toast.LENGTH_SHORT).show();

            }
        });

        star.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addRated();
                Toast.makeText(context, "successfully rated ", Toast.LENGTH_SHORT).show();


            }
        });


        watch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                addWatchlist();
                Toast.makeText(context, "successfully added to watchlist ", Toast.LENGTH_SHORT).show();


            }
        });




    }

    public void addFavourite(){

        FavouritesMoviePost favouritesMoviePost = new FavouritesMoviePost();

        String sessionid = PreferencesManager.getSessionID(this);


        favouritesMoviePost.media_id = model.id;
        favouritesMoviePost.media_type = "movie";
        favouritesMoviePost.favorite = true;




        RestApi api2 = new RestApi(this);


        Call<Movies> call = api2.addFavourites(sessionid, "json/application", favouritesMoviePost);
        call.enqueue(new Callback<Movies>() {
            @Override
            public void onResponse(Call<Movies> call, Response<Movies> response) {

                if (response.code() == 200 ) {
                    model = response.body();
                    Toast.makeText(DetailsActivity.this, "OK", Toast.LENGTH_SHORT).show();
                } else if (response.code() == 401) {
                    Toast.makeText(DetailsActivity.this, "please login", Toast.LENGTH_LONG).show();

                }
            }

            @Override
            public void onFailure(Call<Movies> call, Throwable t) {

            }
        });






    }
    public void addRated(){

//        final RatedMoviePost rated = new RatedMoviePost();

        String sessionid = PreferencesManager.getSessionID(this);

        float rate = 5;
        final RatedMoviePost rated = new RatedMoviePost();
        rated.value = rate;

        int movieID = model.id;


        RestApi api2 = new RestApi(this);



        Call<Movies> call = api2.addRating(movieID, "json/application", sessionid,rated);
        call.enqueue(new Callback<Movies>() {
            @Override
            public void onResponse(Call<Movies> call, Response<Movies> response) {

                if (response.code() == 200){
                    model = response.body();
                    ratedList.ratedMovies.add(rated);

                    Toast.makeText(DetailsActivity.this, "yes", Toast.LENGTH_SHORT).show();
                }else if (response.code() == 401) {
                    Toast.makeText(DetailsActivity.this, "please login", Toast.LENGTH_LONG).show();

                }

            }

            @Override
            public void onFailure(Call<Movies> call, Throwable t) {


            }
        });





    }

    public void addWatchlist(){

       WatchListMoviePost watchListMoviePost = new WatchListMoviePost();

        String sessionid = PreferencesManager.getSessionID(this);


        watchListMoviePost.media_id = model.id;
        watchListMoviePost.media_type = "movie";
        watchListMoviePost.watchlist = true;



        RestApi api3 = new RestApi(this);


        Call<Movies> call = api3.addWatchlist(sessionid, "json/application", watchListMoviePost);
        call.enqueue(new Callback<Movies>() {
            @Override
            public void onResponse(Call<Movies> call, Response<Movies> response) {

                if (response.code() == 200){
                    Toast.makeText(DetailsActivity.this, "OK", Toast.LENGTH_SHORT).show();
                    model = response.body();

                }else if (response.code() == 401) {
                    Toast.makeText(DetailsActivity.this, "please login", Toast.LENGTH_LONG).show();

                }

            }

            @Override
            public void onFailure(Call<Movies> call, Throwable t) {

            }
        });




    }


    public void EdenFavorit() {
        RestApi api4 = new RestApi(this);
        final String aaa = PreferencesManager.getSessionID(this);

        Call<Movies> call2 = api4.checkAll(pozicija, aaa);
        call2.enqueue(new Callback<Movies>() {
            @Override
            public void onResponse(Call<Movies> call, Response<Movies> response) {
                if (response.code() == 200){
                    model = response.body();
                    if (model.favorite==true){
                        Picasso.with(context).load(R.mipmap.favourites_full_mdpi).fit().centerCrop().into(favo);
                    }
                    else {
                        Picasso.with(context).load(R.mipmap.favourites_empty_mdpi).fit().centerCrop().into(favo);

                    }

                    if (model.watchlist==true){
                        Picasso.with(context).load(R.mipmap.watchlist_remove_mdpi).fit().centerCrop().into(watch);
                    }
                }else if (response.code()==401){
                    Toast.makeText(context, "please log in !", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<Movies> call, Throwable t) {
                Toast.makeText(context, "some thing went wrong!", Toast.LENGTH_SHORT).show();

            }
        });

    }






}
