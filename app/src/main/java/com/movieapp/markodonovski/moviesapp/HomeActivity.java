package com.movieapp.markodonovski.moviesapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.movieapp.markodonovski.moviesapp.adapter.PagerAdapterHome;
import com.movieapp.markodonovski.moviesapp.adapter.RvAdapter;
import com.movieapp.markodonovski.moviesapp.api.RestApi;
import com.movieapp.markodonovski.moviesapp.fragment.FragmentNowPlaying;
import com.movieapp.markodonovski.moviesapp.fragment.FragmentPopular;
import com.movieapp.markodonovski.moviesapp.fragment.FragmentTopRated;
import com.movieapp.markodonovski.moviesapp.fragment.FragmentUpcoming;
import com.movieapp.markodonovski.moviesapp.klasi.Movies;
import com.movieapp.markodonovski.moviesapp.klasi.Moviesmodel;
import com.movieapp.markodonovski.moviesapp.other.OnRowClick;
import com.movieapp.markodonovski.moviesapp.other.PreferencesManager;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.vpager)
    ViewPager pager;
    @BindView(R.id.tablayout)
    TabLayout tab;

    @BindView(R.id.editSearch)

    EditText search;


    Movies film;
    Moviesmodel model;

    Handler handler;

    @BindView(R.id.MyRV)

    RecyclerView rv;

    RvAdapter recyclerViewAdapter;











    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ButterKnife.bind(this);

        setPager(pager);
        tab.setupWithViewPager(pager);








//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        loginLogoutVisibility();
    }

    private void loginLogoutVisibility() {
        SharedPreferences sharedPreferences = getSharedPreferences("MySharedPreffsFile", Context.MODE_PRIVATE);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        Menu nav_Menu = navigationView.getMenu();

        if(sharedPreferences.getBoolean("Logged", false)){
            nav_Menu.findItem(R.id.login).setVisible(false);
            nav_Menu.findItem(R.id.logout).setVisible(true);
        }
        else {
            nav_Menu.findItem(R.id.login).setVisible(true);
            nav_Menu.findItem(R.id.logout).setVisible(false);
        }
    }

    public void setPager(ViewPager pager) {
        PagerAdapterHome adapter = new PagerAdapterHome(this.getSupportFragmentManager());
        adapter.addFragment(new FragmentPopular(), "popular");
        adapter.addFragment(new FragmentTopRated(), "top rated");
        adapter.addFragment(new FragmentUpcoming(), "upcoming");
        adapter.addFragment(new FragmentNowPlaying(),"now playing");

        pager.setAdapter(adapter);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();



        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.explore) {

        } else if (id == R.id.favourites) {
            Intent intent = new Intent(HomeActivity.this,FavouritesActivity.class);
            startActivity(intent);

        } else if (id == R.id.rated) {
            Intent intent = new Intent(HomeActivity.this, RatedActivity.class);
            startActivity(intent);

        } else if (id == R.id.watchlist) {

            Intent intent = new Intent(HomeActivity.this,WatchListActivity.class);
            startActivity(intent);

        } else if (id == R.id.people) {
            Intent intent = new Intent(HomeActivity.this,PeopleActivity.class);
            startActivity(intent);

        } else if (id == R.id.login) {
            SharedPreferences sharedPreferences = getSharedPreferences("MySharedPreffsFile",Context.MODE_PRIVATE);

            NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
            Menu nav_Menu = navigationView.getMenu();

            if(sharedPreferences.getBoolean("Logged", false)){
                nav_Menu.findItem(R.id.login).setVisible(false);
                nav_Menu.findItem(R.id.logout).setVisible(true);
            }
            else {
                nav_Menu.findItem(R.id.login).setVisible(true);
                nav_Menu.findItem(R.id.logout).setVisible(false);
            }

            Intent intent2 = new Intent(HomeActivity.this,LoginActivity.class);
            startActivity(intent2);
        }
       else if (id == R.id.logout) {

            SharedPreferences preferences =getSharedPreferences("MySharedPreffsFile",Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.clear();
            editor.commit();

            item.setVisible(false);
            NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
            Menu nav_Menu = navigationView.getMenu();
            nav_Menu.findItem(R.id.login).setVisible(true);
    }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    /**
     * Created by markodonovski on 2/10/18.
     */

//    public void MovieSearch(String link) {
//
//        RestApi api = new RestApi(HomeActivity.this);
//
//        Call<Moviesmodel> call = api.getSearchMovie(link);
//
//        call.enqueue(new Callback<Moviesmodel>() {
//
//            @Override
//
//            public void onResponse(Call<Moviesmodel> call, Response<Moviesmodel> response) {
//
//                if (response.code() == 200) {
//
//                    model = response.body();
//
//                    recyclerViewAdapter = new RvAdapter(HomeActivity.this, model, new OnRowClick() {
//
//                        @Override
//                        public void OnRowClick1(Movies film, int pozicija) {
//                            Intent intent = new Intent(HomeActivity.this, DetailsActivity.class);
//
//                            intent.putExtra("details", film.id);
//
//                            startActivityForResult(intent, 1111);
//                        }
//                    });
//
//                    recyclerViewAdapter.setItems(model.results);
//
//                    rv.setHasFixedSize(true);
//
//                    rv.setLayoutManager(new GridLayoutManager(HomeActivity.this, 2));
//
//                    rv.setAdapter(recyclerViewAdapter);
//                }
//            }
//
//            @Override
//
//            public void onFailure(Call<Moviesmodel> call, Throwable t) {
//
//            }
//        });
//
//
//
//    }
//
//    @Override
//
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//
//        if (resultCode == RESULT_OK && requestCode == 1111){
//
//            String session_id = PreferencesManager.getSessionID(this);
//
//
//
//            RestApi api = new RestApi(HomeActivity.this);
//
//            Call<Moviesmodel> call = api.getUserFavorites("account_id",session_id);
//
//            call.enqueue(new Callback<Moviesmodel>() {
//
//                @Override
//
//                public void onResponse(Call<Moviesmodel> call, Response<Moviesmodel> response) {
//
//                    if (response.code() == 200) {
//
//                        model = response.body();
//
//                        recyclerViewAdapter = new RvAdapter(HomeActivity.this, new OnRowClick() {
//
//                            @Override
//                            public void OnRowClick(Movies movies, int position) {
//
//                            }
//
//
//                            @Override
//
//                            public void onRatedClick(Movies film, int id) {
//
//                            }
//
//                            @Override
//
//                            public void onWatchClick(Movies film, int position) {
//
//                            }
//
//                        }, new OnRowClick() {
//                            @Override
//                            public void OnRowClick1(Movies film, int pozicija) {
//                                Intent intent = new Intent(HomeActivity.this, DetailsActivity.class);
//                                intent.putExtra("details", film.id);
//                                startActivityForResult(intent, 1111);
//                            }
//                        });
//
//                        recyclerViewAdapter.setItems(model.results);
//
//                        rv.setHasFixedSize(true);
//
//                        rv.setLayoutManager(new GridLayoutManager(HomeActivity.this,2));
//
//                        rv.setAdapter(recyclerViewAdapter);
//
//
//
//                    }}
//
//                @Override
//                public void onFailure(Call<Moviesmodel> call, Throwable t) {
//
//                }});}}
//

}
