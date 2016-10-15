/*
 * Copyright (c) 2016. Created by Leonard Meerwood for ITC209.
 * No part of this may be used for your assignment.
 */

package com.meerwood.leonard.looktothestars.objects;

import android.util.Log;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.RealmClass;

/*
    Natural Objects store information for stars, constellations and planets.
    While it could be argued that constellations are man made concepts, the stars
    that make them up are natural, therefore they fall under the natural category.
 */

@RealmClass
public class NaturalObject extends RealmObject {
    public interface Type{
        String CONSTELLATION = "constellation";
        String STAR = "star";
        String PLANET = "planet";
    }


    @PrimaryKey
    private String name;
    private String image;
    private double ra;      //Right ascension
    private double dec;     //declination
    private String type;      //Type of natural object
    private boolean favourite;

    public NaturalObject(){

    }

    public NaturalObject (String name, String type){
        this.name = name;
        String fileName = name.toLowerCase().replace(" ","_");
        this.image = fileName;
        this.type = type;
        ra=0;
        dec=0;
        favourite = false;
    }

    public NaturalObject (String name, String type, double ra, double dec){
        this.name = name;
        String fileName = name.toLowerCase().replace(" ","_");
        this.image = fileName;
        this.type = type;
        this.ra = ra;
        this.dec = dec;
        favourite = false;

    }

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

    public double getRa() {
        return ra;
    }

    public void setRa(double ra) {
        this.ra = ra;
    }

    public double getDec() {
        return dec;
    }

    public void setDec(double dec) {
        this.dec = dec;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isFavourite() {
        return favourite;
    }

    public void setFavourite(boolean favourite) {
        this.favourite = favourite;
    }
}
