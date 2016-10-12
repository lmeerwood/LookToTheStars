/*
 * Copyright (c) 2016. Created by Leonard Meerwood for ITC209.
 * No part of this may be used for your assignment.
 */

package com.meerwood.leonard.looktothestars.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.meerwood.leonard.looktothestars.R;
import com.meerwood.leonard.looktothestars.objects.ManMadeObject;
import com.meerwood.leonard.looktothestars.objects.NaturalObject;
import com.squareup.picasso.Picasso;

import io.realm.RealmBasedRecyclerViewAdapter;
import io.realm.RealmResults;
import io.realm.RealmViewHolder;


public class NaturalAdapter extends RealmBasedRecyclerViewAdapter<NaturalObject, NaturalAdapter.ViewHolder> {
    public class ViewHolder extends RealmViewHolder {
        public TextView celestialName;
        public ImageView celestialImage;

        public ViewHolder(View view) {
            super(view);
            celestialName = (TextView) view.findViewById(R.id.celestialName);
            celestialImage = (ImageView) view.findViewById(R.id.celestialImage);
        }
    }

    public NaturalAdapter(
            Context context,
            RealmResults<NaturalObject> realmResults,
            boolean automaticUpdate,
            boolean animateResults) {
        super(context, realmResults, automaticUpdate, animateResults);
    }

    @Override
    public NaturalAdapter.ViewHolder onCreateRealmViewHolder(ViewGroup viewGroup, int viewType) {
        View v = inflater.inflate(R.layout.celestial_object_entry, viewGroup, false);
        NaturalAdapter.ViewHolder vh = new NaturalAdapter.ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindRealmViewHolder(NaturalAdapter.ViewHolder viewHolder, int position) {
        final NaturalObject no = realmResults.get(position);
        viewHolder.celestialName.setText(no.getCelestialObject().getName());
    }
}