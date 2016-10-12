/*
 * Copyright (c) 2016. Created by Leonard Meerwood for ITC209.
 * No part of this may be used for your assignment.
 */

package com.meerwood.leonard.looktothestars.adapters;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.meerwood.leonard.looktothestars.R;
import com.meerwood.leonard.looktothestars.ViewObjectActivity;
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
        viewHolder.celestialName.setText(no.getName());

        Uri uri = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE
            + "://"+ getContext().getPackageName()
            + "/drawable/"
            + no.getImage());
        Picasso.with(getContext())
            .load(uri)
            .resize(150,150)
            .centerInside()
            .error(R.drawable.ara)
            .into(viewHolder.celestialImage);
        viewHolder.celestialImage.setOnClickListener(
            new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Context context = v.getContext();
                    Bundle bundle = new Bundle();
                    bundle.putString("NAME", no.getName());
                    bundle.putString("TYPE", no.getType());
                    Intent intent = new Intent(context, ViewObjectActivity.class);
                    intent.putExtras(bundle);
                    context.startActivity(intent);
                }
            }
        );
    }
}