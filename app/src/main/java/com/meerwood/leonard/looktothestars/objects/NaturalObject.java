/*
 * Copyright (c) 2016. Created by Leonard Meerwood for ITC209.
 * No part of this may be used for your assignment.
 */

package com.meerwood.leonard.looktothestars.objects;

import io.realm.RealmObject;

/*
    Natural Objects store information for stars, constellations and planets.
    While it could be argued that constellations are man made concepts, the stars
    that make them up are natural, therefore they fall under the natural category.
 */

public class NaturalObject extends RealmObject {
    public interface Type{
        String CONSTELLATION = "constellation";
        String STAR = "star";
        String PLANET = "planet";
    }


    private CelestialObject celestialObject;
    private double ra;      //Right ascension
    private double dec;     //declination
    private Type type;      //Type of natural object

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
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
