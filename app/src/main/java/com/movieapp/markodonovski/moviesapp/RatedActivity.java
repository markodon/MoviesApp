package com.movieapp.markodonovski.moviesapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.movieapp.markodonovski.moviesapp.adapter.RvAdapter;
import com.movieapp.markodonovski.moviesapp.api.RestApi;
import com.movieapp.markodonovski.moviesapp.klasi.Movies;
import com.movieapp.markodonovski.moviesapp.klasi.Moviesmodel;
import com.movieapp.markodonovski.moviesapp.klasi.RatedMoviePost;
import com.movieapp.markodonovski.moviesapp.other.OnRowClick;
import com.movieapp.markodonovski.moviesapp.other.PreferencesManager;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RatedActivity extends AppCompatActivity {

    @BindView(R.id.recyclerriew_favo)
    RecyclerView recyclerView_favo;

    Moviesmodel model;
    RestApi api;
    RvAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rated);
        ButterKnife.bind(this);

        api = new RestApi(RatedActivity.this);

        final String sessionID = PreferencesManager.getSessionID(this);


        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        recyclerView_favo.setLayoutManager(layoutManager);


        Call<Moviesmodel> call = api.getRated(sessionID);
        call.enqueue(new Callback<Moviesmodel>() {
            @Override
            public void onResponse(Call<Moviesmodel> call, Response<Moviesmodel> response) {
                if (response.code() == 200){
                    model = response.body();
                    adapter = new RvAdapter(RatedActivity.this, model);
                    adapter.setItems(model.results);
                    recyclerView_favo.setHasFixedSize(true);
                    recyclerView_favo.setAdapter(adapter);
                } else if (response.code() == 401) {
                    Toast.makeText(RatedActivity.this, "please login", Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(Call<Moviesmodel> call, Throwable t) {

            }
        });
    }



}

