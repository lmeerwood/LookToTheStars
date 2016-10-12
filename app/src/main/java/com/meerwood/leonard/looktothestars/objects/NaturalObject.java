/*
 * Copyright (c) 2016. Created by Leonard Meerwood for ITC209.
 * No part of this may be used for your assignment.
 */

package com.meerwood.leonard.looktothestars.objects;

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
    private String id;
    private CelestialObject celestialObject;
    private double ra;      //Right ascension
    private double dec;     //declination
    private String type;      //Type of natural object

    public NaturalObject(){
        celestialObject = null;
    }

    public NaturalObject (String name, String type){
        celestialObject = new CelestialObject();
        celestialObject.setName(name);
        setType(type);
        id = name + "-" + type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    //All the basic getters and setters for NaturalObject
    public CelestialObject getCelestialObject() {
        return celestialObject;
    }

    public void setCelestialObject(CelestialObject celestialObject) {
        this.celestialObject = celestialObject;
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
}
