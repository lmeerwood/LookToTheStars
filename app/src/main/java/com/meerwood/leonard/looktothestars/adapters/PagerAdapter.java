/*
 * Copyright (c) 2016. Created by Leonard Meerwood for ITC209.
 * No part of this may be used for your assignment.
 */

package com.meerwood.leonard.looktothestars.adapters;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.meerwood.leonard.looktothestars.fragments.CelestialObjectGridFragment;

public class PagerAdapter extends FragmentPagerAdapter {
    private static int NUM_ITEMS = 4;

    public PagerAdapter(FragmentManager fragmentManager)  {
        super(fragmentManager);
    }

    @Override
    public int getCount(){
        return NUM_ITEMS;
    }

    @Override
    public Fragment getItem(int position){
        switch(position) {
            case 0:
                return CelestialObjectGridFragment.newInstance("Stars","stars");
            case 1:
                return CelestialObjectGridFragment.newInstance("Constellations","constellations");
            case 2:
                return CelestialObjectGridFragment.newInstance("Planets","planets");
            case 3:
                return CelestialObjectGridFragment.newInstance("Favourites","favourites");
            default:
                return null;
        }
    }

    @Override
    public CharSequence getPageTitle(int position){
        switch(position) {
            case 0:
                return "Stars";
            case 1:
                return "Constellations";
            case 2:
                return "Planets";
            case 3:
                return "Favourites";
            default:
                return null;
        }
    }
}

