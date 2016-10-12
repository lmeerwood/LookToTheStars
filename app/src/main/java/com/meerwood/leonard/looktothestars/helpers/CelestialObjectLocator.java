/*
 * Copyright (c) 2016. Created by Leonard Meerwood for ITC209.
 * No part of this may be used for your assignment.
 */

package com.meerwood.leonard.looktothestars.helpers;


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
        int year = now.get(Calendar.YEAR);
        int month = now.get(Calendar.MONTH);
        int day = now.get(Calendar.DAY_OF_MONTH);
        int hour = now.get(Calendar.HOUR);
        int minute = now.get(Calendar.MINUTE);
        int second = now.get(Calendar.SECOND);

        if ((month == 1) || (month == 2)) {
            year -= 1;
            month += 12;
        }

        double a =  Math.floor(year/100);
        double b = 2 - a + Math.floor(a/4);
        double c = Math.floor(365.25*year);
        double d = Math.floor(20.6001*(month+1));

        //days since J2000.0
        double jd = b + c + d - 730550.5 + day + (hour + minute/60.0 + second/3600.0)/24.0;

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
}
