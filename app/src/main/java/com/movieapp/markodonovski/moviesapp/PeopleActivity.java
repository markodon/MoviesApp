package com.movieapp.markodonovski.moviesapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.movieapp.markodonovski.moviesapp.adapter.PeopleAdapter;
import com.movieapp.markodonovski.moviesapp.api.RestApi;
import com.movieapp.markodonovski.moviesapp.klasi.People;
import com.movieapp.markodonovski.moviesapp.klasi.Peoplemodel;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PeopleActivity extends AppCompatActivity {

    @BindView(R.id.recyclerriew_people)
    RecyclerView recyclerView;

    PeopleAdapter adapter;
    RestApi api;
    Peoplemodel model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_people);
        ButterKnife.bind(this);

        api = new RestApi(PeopleActivity.this);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager (new LinearLayoutManager(this));
        initialize();
    }

    private void initialize() {
        api.checkInternet(new Runnable() {
            @Override
            public void run() {
                Call<Peoplemodel> call = api.getPeople();
                call.enqueue(new Callback<Peoplemodel>() {
                    @Override
                    public void onResponse(Call<Peoplemodel> call, Response<Peoplemodel> response) {
                        if (response.code() == 200){
                            model = response.body();
                            adapter = new PeopleAdapter(PeopleActivity.this, model);
                            adapter.setItems(model.results);
                            recyclerView.setHasFixedSize(true);
                            recyclerView.setAdapter(adapter);
                        } else if (response.code() == 401) {
                            Toast.makeText(PeopleActivity.this, "Wrong", Toast.LENGTH_LONG).show();
                        }

                    }

                    @Override
                    public void onFailure(Call<Peoplemodel> call, Throwable t) {

                    }
                });

            }
        });
    }
}

