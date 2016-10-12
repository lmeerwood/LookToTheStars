/*
 * Copyright (c) 2016. Created by Leonard Meerwood for ITC209.
 * No part of this may be used for your assignment.
 */

package com.meerwood.leonard.looktothestars.objects;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.RealmClass;

/*
    Man Made objects store the details for satellites and space stations.
 */

@RealmClass
public class ManMadeObject extends RealmObject {
    @PrimaryKey
    private String name;
    private String image;
    private boolean favourite;
    private String twoLineElement;

    public ManMadeObject() {
        name = "error";
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

    public boolean isFavourite() {
        return favourite;
    }

    public void setFavourite(boolean favourite) {
        this.favourite = favourite;
    }

    public String getTwoLineElement() {
        return twoLineElement;
    }

    public void setTwoLineElement(String twoLineElement) {
        this.twoLineElement = twoLineElement;
    }

    public ManMadeObject (String name){
        this.name = name ;

    }
}
