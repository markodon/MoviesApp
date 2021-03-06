package com.movieapp.markodonovski.moviesapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.movieapp.markodonovski.moviesapp.adapter.RvAdapter;
import com.movieapp.markodonovski.moviesapp.api.RestApi;
import com.movieapp.markodonovski.moviesapp.klasi.Movies;
import com.movieapp.markodonovski.moviesapp.klasi.Moviesmodel;
import com.movieapp.markodonovski.moviesapp.other.OnRowClickListener;
import com.movieapp.markodonovski.moviesapp.other.PreferencesManager;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WatchListActivity extends AppCompatActivity {
    @BindView(R.id.recyclerriew_favo)
    RecyclerView recyclerView_favo;

    Moviesmodel moviesmodel;
    RestApi api;
    RvAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_watch_list);
        ButterKnife.bind(this);


        api = new RestApi(WatchListActivity.this);

        final String sessionID = PreferencesManager.getSessionID(this);


        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        recyclerView_favo.setLayoutManager(layoutManager);


        Call<Moviesmodel> call = api.getWatchlist(sessionID);
        call.enqueue(new Callback<Moviesmodel>() {
            @Override
            public void onResponse(Call<Moviesmodel> call, Response<Moviesmodel> response) {
                if (response.code() == 200){
                    moviesmodel = response.body();
                    adapter = new RvAdapter(WatchListActivity.this, moviesmodel, new OnRowClickListener() {
                        @Override
                        public void OnRowClick(Movies movies, int position) {

                        }
                    });
                    adapter.setItems(moviesmodel.results);
                    recyclerView_favo.setHasFixedSize(true);
                    recyclerView_favo.setAdapter(adapter);
                } else if (response.code() == 401) {
                    Toast.makeText(WatchListActivity.this, "please login", Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(Call<Moviesmodel> call, Throwable t) {

            }
        });
    }



}
