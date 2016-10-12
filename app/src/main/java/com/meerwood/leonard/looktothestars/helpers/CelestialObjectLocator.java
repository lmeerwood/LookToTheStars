/*
 * Copyright (c) 2016. Created by Leonard Meerwood for ITC209.
 * No part of this may be used for your assignment.
 */

package com.meerwood.leonard.looktothestars.helpers;


import com.meerwood.leonard.looktothestars.objects.ManMadeObject;
import com.meerwood.leonard.looktothestars.objects.NaturalObject;

/*
    This class takes either a man-made or natural space object and returns
    the heading and elevation the user should be looking at. It uses the GPS
    and math to calculate the needed objects.
 */
public class CelestialObjectLocator {
    public static double[] findCelestialObject(NaturalObject naturalObject){
        //The formula to convert the ra and dec are found here:
        //http://www.convertalot.com/celestial_horizon_co-ordinates_calculator.html
        return new double[0];
    }

    public static double[] findCelestialObject(ManMadeObject manMadeObject){
        return new double[0];
    }
}
