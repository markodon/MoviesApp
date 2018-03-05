package com.movieapp.markodonovski.moviesapp.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.movieapp.markodonovski.moviesapp.R;
import com.movieapp.markodonovski.moviesapp.adapter.RvAdapter;
import com.movieapp.markodonovski.moviesapp.api.RestApi;
import com.movieapp.markodonovski.moviesapp.klasi.Moviesmodel;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by markodonovski on 2/7/18.
 */

public class FragmentNowPlaying extends Fragment {
    private Unbinder mUnbind;
    @BindView(R.id.recyclerriew_now)
    RecyclerView recyclerView_now;

    Moviesmodel movies;
    RestApi api;
    RvAdapter adapter;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_now_playing, null);
        mUnbind = ButterKnife.bind(this, view);

        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 2);
        recyclerView_now.setLayoutManager(layoutManager);

        api = new RestApi(getActivity());


        api.checkInternet(new Runnable() {
            @Override
            public void run() {

                Call<Moviesmodel> call = api.getNowPlaying();

                call.enqueue(new Callback<Moviesmodel>() {
                    @Override
                    public void onResponse(Call<Moviesmodel> call, Response<Moviesmodel> response) {
                        if (response.code() == 200) {
                            movies = response.body();
                            adapter = new RvAdapter(getActivity(), movies);
                            adapter.setItems(movies.results);
                            recyclerView_now.setHasFixedSize(true);
                            recyclerView_now.setAdapter(adapter);

                        } else if (response.code() == 401) {
                            Toast.makeText(getActivity(), "Wrong", Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Moviesmodel> call, Throwable t) {

                    }
                });

            }
        });

        return view;

    }
}
