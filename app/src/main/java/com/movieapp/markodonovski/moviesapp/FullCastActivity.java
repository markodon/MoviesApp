package com.movieapp.markodonovski.moviesapp;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.movieapp.markodonovski.moviesapp.adapter.CastAdapter;
import com.movieapp.markodonovski.moviesapp.adapter.PeopleAdapter;
import com.movieapp.markodonovski.moviesapp.adapter.RvAdapter;
import com.movieapp.markodonovski.moviesapp.api.RestApi;
import com.movieapp.markodonovski.moviesapp.klasi.Cast;
import com.movieapp.markodonovski.moviesapp.klasi.Credits;
import com.movieapp.markodonovski.moviesapp.klasi.Movies;
import com.movieapp.markodonovski.moviesapp.klasi.Moviesmodel;
import com.movieapp.markodonovski.moviesapp.klasi.Peoplemodel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FullCastActivity extends AppCompatActivity {

    @BindView(R.id.recyclerriew_cast)
    RecyclerView recyclerView_cast;

    Movies movies;
    RestApi api;
    CastAdapter adapter;
    int pozicija;
    Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_cast);
        ButterKnife.bind(this);

        final Intent intent = getIntent();
        if (intent.hasExtra("Cast")) {
            pozicija = intent.getIntExtra("Cast", 0);
            initialize();

        }




    }

    private void initialize() {

                api = new RestApi(FullCastActivity.this);

                recyclerView_cast.setLayoutManager (new LinearLayoutManager(this));

                Call<Movies> call = api.getMovie(pozicija, "credits");
                call.enqueue(new Callback<Movies>() {
                    @Override
                    public void onResponse(Call<Movies> call, Response<Movies> response) {
                        if (response.code() == 200){
                            movies = response.body();
                            adapter = new CastAdapter(FullCastActivity.this, movies.credits);
                            adapter.setItems(movies.credits.cast);
                            recyclerView_cast.setHasFixedSize(true);
                            recyclerView_cast.setAdapter(adapter);
                        } else if (response.code() == 401) {
                            Toast.makeText(FullCastActivity.this, "Wrong", Toast.LENGTH_LONG).show();
                        }

                    }

                    @Override
                    public void onFailure(Call<Movies> call, Throwable t) {

                    }
                });

            }

    }

