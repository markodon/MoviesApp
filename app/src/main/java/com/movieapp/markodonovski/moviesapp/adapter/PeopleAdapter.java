package com.movieapp.markodonovski.moviesapp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.movieapp.markodonovski.moviesapp.R;
import com.movieapp.markodonovski.moviesapp.klasi.Knownfor;
import com.movieapp.markodonovski.moviesapp.klasi.People;
import com.movieapp.markodonovski.moviesapp.klasi.Peoplemodel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by markodonovski on 2/15/18.
 */

public class PeopleAdapter extends RecyclerView.Adapter<PeopleAdapter.ViewHolder> {

    ArrayList<People> people = new ArrayList<>();
    Context context;


    public PeopleAdapter(Context context, Peoplemodel peopleModel){
        this.context = context;
        this.people = peopleModel.results;

    }
    public void setItems(List<People> peopleData){
        this.people = (ArrayList<People>) peopleData;
        notifyDataSetChanged();
    }


    @Override
    public PeopleAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.people_layout,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(PeopleAdapter.ViewHolder holder, int position) {

        final People filmovi = people.get(position);

        holder.name.setText(filmovi.getName());
        ArrayList knownFor = filmovi.getKnown_for();
        String a = "";
        for(int i=0; i < knownFor.size(); i++) {
            Knownfor nf = (Knownfor)knownFor.get(i);
            a = a+", " + nf.getTitle();
        }
        holder.film.setText(a);


//        Knownfor nf = (Knownfor)knownFor.get(0);
//        holder.film.setText(nf.getTitle());

        String path = "http://image.tmdb.org/t/p/w185" + filmovi.getProfile_path();

        Picasso.with(context).load(path).centerInside().fit().into(holder.slika);

    }

    @Override
    public int getItemCount() {
        return people.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.slika)
        ImageView slika;
        @BindView(R.id.name)
        TextView name;
        @BindView(R.id.film)
        TextView film;





        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}

