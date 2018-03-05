package com.movieapp.markodonovski.moviesapp.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

/**
 * Created by markodonovski on 2/6/18.
 */

public class PagerAdapterHome extends FragmentPagerAdapter {

    ArrayList<Fragment> fragments = new ArrayList<>();
    ArrayList<String> titles = new ArrayList<>();
    public void addFragment(Fragment fragment, String title){
        titles.add(title);
        fragments.add(fragment);
    }




    public PagerAdapterHome(FragmentManager fm) {

        super(fm);
    }

    @Override
    public Fragment getItem(int position) {

        return fragments.get(position);
    }

    @Override
    public int getCount() {

        return fragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {

        return titles.get(position);
    }


}
