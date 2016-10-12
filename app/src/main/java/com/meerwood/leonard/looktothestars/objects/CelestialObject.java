/*
 * Copyright (c) 2016. Created by Leonard Meerwood for ITC209.
 * No part of this may be used for your assignment.
 */

package com.meerwood.leonard.looktothestars.objects;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;

import java.net.URI;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.RealmClass;

@RealmClass
public class CelestialObject extends RealmObject {
    @PrimaryKey
    private String name;
    private String image;
    private boolean favourite;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public boolean isFavourite() {
        return favourite;
    }

    public void setFavourite(boolean favourite) {
        this.favourite = favourite;
    }
}
