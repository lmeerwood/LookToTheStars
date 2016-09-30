/*
 * Copyright (c) 2016. Created by Leonard Meerwood for ITC209.
 * No part of this may be used for your assignment.
 */

package com.meerwood.leonard.looktothestars.objects;

import io.realm.RealmObject;

/*
    Man Made objects store the details for satellites and space stations.
 */
public class ManMadeObject extends RealmObject {
    private CelestialObject celestialObject;
    private String twoLineElement;

    //Getter and setters for Man Made Objects
    public CelestialObject getCelestialObject() {
        return celestialObject;
    }

    public void setCelestialObject(CelestialObject celestialObject) {
        this.celestialObject = celestialObject;
    }

    public String getTwoLineElement() {
        return twoLineElement;
    }

    public void setTwoLineElement(String twoLineElement) {
        this.twoLineElement = twoLineElement;
    }
}
