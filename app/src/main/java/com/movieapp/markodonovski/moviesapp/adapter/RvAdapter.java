package com.movieapp.markodonovski.moviesapp.adapter;


import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.movieapp.markodonovski.moviesapp.DetailsActivity;
import com.movieapp.markodonovski.moviesapp.R;
import com.movieapp.markodonovski.moviesapp.klasi.Movies;
import com.movieapp.markodonovski.moviesapp.klasi.Moviesmodel;
import com.movieapp.markodonovski.moviesapp.other.OnRowClickListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by markodonovski on 2/10/18.
 */

public class RvAdapter extends RecyclerView.Adapter<RvAdapter.ViewHolder> {

    ArrayList<Movies> movies = new ArrayList<>();
    Context context;

    OnRowClickListener onRowClick;



    public RvAdapter(Context context, Moviesmodel movieModel, OnRowClickListener onRowClick) {

        this.context = context;

        this.movies = movieModel.results;

        this.onRowClick = onRowClick;
    }

    public void setItems(List<Movies> movieData) {

        this.movies = (ArrayList<Movies>) movieData;
        notifyDataSetChanged();
    }


    @Override
    public RvAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.movies_layout,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);

          return viewHolder;
    }



    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Movies filmovi = movies.get(position);


        holder.tilte.setText("Name: " + filmovi.getTitle());
        holder.vote.setText("Rating: " + filmovi.getVote_average());
//        holder.watcjlist.setText(filmovi.getId() + "");
        String path = "http://image.tmdb.org/t/p/w185" + filmovi.getPoster_path();

        Picasso.with(context).load(path).centerInside().fit().into(holder.slika);



    }


    @Override
    public int getItemCount() {
        return movies.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.title)
        TextView tilte;
        @BindView(R.id.vote)
        TextView vote;
        @BindView(R.id.watchlist)
        TextView watcjlist;

        @BindView(R.id.slika)
        ImageView slika;
        @BindView(R.id.movies_layout_click)
        RelativeLayout click;




        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    Movies clickedDataItem = movies.get(pos);
                    Intent intent = new Intent(context, DetailsActivity.class);

                    intent.putExtra("DetailsActivity", movies.get(pos).getId());
                    context.startActivity(intent);


                }
            });
        }


    }
}
