package com.movieapp.markodonovski.moviesapp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.movieapp.markodonovski.moviesapp.R;
import com.movieapp.markodonovski.moviesapp.klasi.Cast;
import com.movieapp.markodonovski.moviesapp.klasi.Credits;
import com.movieapp.markodonovski.moviesapp.klasi.Movies;
import com.movieapp.markodonovski.moviesapp.klasi.People;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by markodonovski on 2/19/18.
 */

public class CastAdapter extends RecyclerView.Adapter<CastAdapter.ViewHolder> {

    ArrayList<Cast> casts = new ArrayList<>();
    Context context;

    public CastAdapter(Context context, Credits credits){
        this.context = context;
        this.casts = credits.cast;
    }

    public void setItems(List<Cast> castData){
        this.casts = (ArrayList<Cast>) castData;
        notifyDataSetChanged();
    }




    @Override
    public CastAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context= parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.full_cast_layout,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(CastAdapter.ViewHolder holder, int position) {
        final Cast glumci = casts.get(position);
        holder.namecast.setText(glumci.getName());
        holder.charactercast.setText(glumci.getCharacter());

        String path = "http://image.tmdb.org/t/p/w185" + glumci.getProfile_path();
        Picasso.with(context).load(path).centerInside().fit().into(holder.slikacast);




    }

    @Override
    public int getItemCount() {
        return casts.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.slikacast)
        ImageView slikacast;
        @BindView(R.id.namecast)
        TextView namecast;
        @BindView(R.id.charactercast)
        TextView charactercast;

        public ViewHolder(View itemView) {

            super(itemView);
            ButterKnife.bind(this,itemView);


        }
    }
}

