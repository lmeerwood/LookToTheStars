/*
 * Copyright (c) 2016. Created by Leonard Meerwood for ITC209.
 * No part of this may be used for your assignment.
 */

package com.meerwood.leonard.looktothestars.helpers;


import android.content.Context;
import android.util.Log;

import com.meerwood.leonard.looktothestars.objects.ManMadeObject;
import com.meerwood.leonard.looktothestars.objects.NaturalObject;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/*
    This class takes either a man-made or natural space object and returns
    the heading and elevation the user should be looking at. It uses the GPS
    and math to calculate the needed objects.
 */
public class CelestialObjectLocator {
    public static double[] findCelestialObject(NaturalObject naturalObject, double lat, double lon){
        //The formula to convert the ra and dec are found here:
        //http://www.convertalot.com/celestial_horizon_co-ordinates_calculator.html

        double dec = naturalObject.getDec();
        double ra = naturalObject.getRa();
        double raDegrees = ra * 15; //each hour is 15°

        double hrz_altitude;
        double hrz_azimuth;

        Calendar now = Calendar.getInstance();
        now.setTimeZone(TimeZone.getTimeZone("UTC"));


        //This section calculates the side real time based on longitude and current time.

        double millisSinceEpoch = System.currentTimeMillis();
        double daysSinceEpoch = millisSinceEpoch/(1000*60*60*24);

        // julianDate daysSinceLinuxEpoch + linuxEpochInJulianDay - J2000 offset
        double jd = daysSinceEpoch + 2440587.500000 - 2451546;

        Log.d("celObjLoc", "JulTime is: " + jd);

        //centuries since J2000.0
        double jt = jd/36525.0;

        //the mean sidereal time in degrees
        double mst = 280.46061837 + 360.98564736629 * jd + 0.000387933*jt*jt - jt*jt*jt/38710000 + lon;

        if (mst > 0){
            while (mst > 360.0) {
                mst -= 360;
            }
        } else {
            while (mst < 0) {
                mst += 360;
            }
        }

        //This section calulcates the altitude (alt) and azimuth (az) based on location and mean side real time

        double ha = mst - raDegrees;
        if (ha < 0) {
            ha += 360;
        }

        ha = ha*Math.PI/180;
        dec = dec*Math.PI/180;
        lat = lat*Math.PI/180;

        double sin_alt = Math.sin(dec)*Math.sin(lat) + Math.cos(dec)*Math.cos(lat)*Math.cos(ha);
        double alt = Math.asin(sin_alt);

        double cos_az = (Math.sin(dec) - Math.sin(alt) * Math.sin(lat))/(Math.cos(alt)*Math.cos(lat));
        double az = Math.acos(cos_az);

        hrz_altitude = alt*180/Math.PI;
        hrz_azimuth = az*180/Math.PI;

        if (Math.sin(ha) > 0) {
            hrz_azimuth = 360 - hrz_azimuth;
        }

        double [] results = new double[2];
        results[0] = hrz_altitude;
        results[1] = hrz_azimuth;
        return results;
    }

    public static double[] findCelestialObject(ManMadeObject manMadeObject){
        return new double[0];
    }

    public static PlanetCoordinates getCurrentPlanetCoordinates(Context context){
        PlanetCoordinates pc = new PlanetCoordinates(context);

        double millisSinceEpoch = System.currentTimeMillis();
        double daysSinceEpoch = millisSinceEpoch/(1000*60*60*24);
        double jd = daysSinceEpoch + 2440587.500000;

        pc.planetary_ephemeris(jd);

        return pc;
    }


    //This is to convert the planets' cartesian values to the heading and altitude. However, this
    //is completly wrong as it makes the assumption that earth is [0,0,0], when in fact the sun is.
    //It also doesn't take into account earth's "wobble" or its tilt. Due to time constraints I was
    //unfortunately unable to figure out how to conver these properly. I feel the proper way to convert
    //it lies in the madness of equations on this page somewhere: http://www.stjarnhimlen.se/comp/ppcomp.html
    public static double[] cartesianToRaAndDec(double[] cart){
        //https://en.wikipedia.org/wiki/Spherical_coordinate_system#Cartesian_coordinates
        //r = sqrt(x^2 + y^2 + z^2)
        Log.d("CelObjLoc.carToRaAndDec", "x = "  + cart[0] + ", y = " + cart[1] + ", z = " +cart[3]);
        double radius = Math.sqrt(cart[0]*cart[0] + cart[1]*cart[1] + cart[2]*cart[2]);

        double[] raAndDec = new double[2];
        raAndDec[0] = Math.atan2(cart[1],cart[0]);
        raAndDec[1] = Math.acos(cart[2]/radius);

        return raAndDec;
    }
}
