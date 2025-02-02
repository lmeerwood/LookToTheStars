/*
 * Copyright (c) 2016. Created by Leonard Meerwood for ITC209.
 * No part of this may be used for your assignment.
 */

package com.meerwood.leonard.looktothestars.fragments;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.hardware.GeomagneticField;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.icu.text.DecimalFormat;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.meerwood.leonard.looktothestars.R;
import com.meerwood.leonard.looktothestars.helpers.CelestialObjectLocator;
import com.meerwood.leonard.looktothestars.helpers.PlanetCoordinates;
import com.meerwood.leonard.looktothestars.objects.NaturalObject;
import com.meerwood.leonard.looktothestars.view.CompassView;

import java.security.Permission;

import io.realm.Realm;

/**
 * A placeholder fragment containing a simple view.
 */
public class ViewObjectActivityFragment extends Fragment implements SensorEventListener, LocationListener {

    public static final String NA = "N/A";
    public static final String FIXED = "FIXED";

    public static final int MY_PERMISSIONS_REQUEST_FINE_LOCATION = 1;
    public static final int MY_PERMISSIONS_REQUEST_COARSE_LOCATION = 2;

    private static final int LOCATION_MIN_TIME = 30 * 1000;
    private static final int LOCATION_MIN_DISTANCE = 10;

    private float[] gravity = new float[3];
    private float[] geomagnetic = new float[3];
    private float[] rotation = new float[9];
    private float[] orientation = new float[3];
    private float[] smoothed = new float[3];

    private SensorManager sensorManager;

    private Sensor sensorGravity;
    private Sensor sensorMagnetic;
    private LocationManager locationManager;
    private Location currentLocation;
    private GeomagneticField geomagneticField;
    private double bearing = 0;
    private double lastBearing = 0;

    private TextView textViewObjectText;
    private CompassView compassView;
    private TextView elevationText;
    private TextView warningText;
    private FloatingActionButton fabFavourite;

    private double azimuthOffset = 0;

    private String name;
    private String type;


    public ViewObjectActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_view_object, container, false);
        compassView = (CompassView) view.findViewById(R.id.compass);
        elevationText = (TextView) view.findViewById(R.id.declination_text);
        warningText = (TextView) view.findViewById(R.id.warning_text);
        fabFavourite = (FloatingActionButton) view.findViewById(R.id.fab_favourite);

        name = getArguments().getString("NAME");
        type = getArguments().getString("TYPE");

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        setUp();
    }


    private void setUp(){

        final Context context = getContext();
        sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        sensorGravity = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorMagnetic = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);

        sensorManager.registerListener(this, sensorGravity,
                SensorManager.SENSOR_DELAY_GAME);
        sensorManager.registerListener(this, sensorMagnetic,
                SensorManager.SENSOR_DELAY_GAME);

        //Check for permissions
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            //Request permissions if we don't have them.
            requestPermissions(
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    MY_PERMISSIONS_REQUEST_FINE_LOCATION);
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                LOCATION_MIN_TIME, LOCATION_MIN_DISTANCE, this);

        Location gpsLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

        if (gpsLocation != null) {
            currentLocation = gpsLocation;
        } else {
            Location networkLocation = locationManager
                    .getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            if (networkLocation != null) {
                currentLocation = networkLocation;
            } else {
                currentLocation = new Location(FIXED);
                currentLocation.setAltitude(0);
                currentLocation.setLatitude(0);
                currentLocation.setLongitude(0);
            }

            onLocationChanged(currentLocation);
        }


        final Realm realm = Realm.getDefaultInstance();

        NaturalObject no = realm.where(NaturalObject.class)
                .equalTo("name", name)
                .findFirst();
        double [] coordinates = CelestialObjectLocator.findCelestialObject(no,
                currentLocation.getLatitude(),
                currentLocation.getLongitude());
        azimuthOffset = coordinates[1];
        double altitude = coordinates[0];

        //Display elevation. If elevation is below horizon, display warning.
        //If SDK is below 24 elevation shown with out deciaml places.

        elevationText.setText(String.format(getString(R.string.elevation_view_frag), altitude));

        if (type.equals(NaturalObject.Type.PLANET)){
            warningText.setText(R.string.error_planet);
        } else if (no.getDec() < 0) {
            warningText.setText(R.string.error_horizon);
        }

        //Show if celestial object is favourited.
        if (no.isFavourite()){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                fabFavourite.setImageDrawable(context.getDrawable(R.drawable.ic_star_black_24dp));
            } else {
                fabFavourite.setImageDrawable(getResources().getDrawable(R.drawable.ic_star_black_24dp));
            }
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                fabFavourite.setImageDrawable(context.getDrawable(R.drawable.ic_star_border_black_24dp));
            } else {
                fabFavourite.setImageDrawable(getResources().getDrawable(R.drawable.ic_star_border_black_24dp));
            }
        }

        fabFavourite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Retrieve Natural Object
                NaturalObject no = realm.where(NaturalObject.class)
                        .equalTo("name", name).findFirst();

                //Change favourite value;
                boolean isFavourite = no.isFavourite();
                realm.beginTransaction();
                no.setFavourite(!isFavourite);
                realm.commitTransaction();

                //Change icon
                if (!isFavourite){
                    Toast.makeText(context, "Added to Favourites!", Toast.LENGTH_SHORT).show();
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        fabFavourite.setImageDrawable(context.getDrawable(R.drawable.ic_star_black_24dp));
                    } else {
                        fabFavourite.setImageDrawable(getResources().getDrawable(R.drawable.ic_star_black_24dp));
                    }
                } else {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        fabFavourite.setImageDrawable(context.getDrawable(R.drawable.ic_star_border_black_24dp));
                    } else {
                        fabFavourite.setImageDrawable(getResources().getDrawable(R.drawable.ic_star_border_black_24dp));
                    }
                }
            }
        });
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            //If we got fine location permission, run setUp() again. Otherwise, request coarse location.
            case MY_PERMISSIONS_REQUEST_FINE_LOCATION:
                if (grantResults.length > 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    setUp();
                } else {
                    requestPermissions(
                            new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                            MY_PERMISSIONS_REQUEST_COARSE_LOCATION);
                }
                break;
            //If we recieve coarse location permission, run setUp() again. Otherwise display error message
            //and close program.
            case MY_PERMISSIONS_REQUEST_COARSE_LOCATION:
                if (grantResults.length > 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    setUp();
                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setTitle(R.string.need_gps_title)
                            .setMessage(R.string.need_gps_message);
                    builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            getActivity().onBackPressed();
                        }
                    });

                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        sensorManager.unregisterListener(this, sensorGravity);
        sensorManager.unregisterListener(this, sensorMagnetic);
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(
                    new String[]{
                            Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        locationManager.removeUpdates(this);
    }


    @Override
    public void onLocationChanged(Location location) {
        currentLocation = location;

        updateLocation(location);
        geomagneticField = new GeomagneticField(
                (float) currentLocation.getLatitude(),
                (float) currentLocation.getLongitude(),
                (float) currentLocation.getAltitude(),
                System.currentTimeMillis()
        );
    }

    private void updateLocation(Location location) {
        if (FIXED.equals(location.getProvider())) {

        }
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }


    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        boolean accelOrMagnetic = false;


        //get accelerometer data
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            smoothed = lowPassFilter(event.values, geomagnetic);
            gravity[0] = smoothed[0];
            gravity[1] = smoothed[1];
            gravity[2] = smoothed[2];
            accelOrMagnetic = true;
        } else if (event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) {
            smoothed = lowPassFilter(event.values, geomagnetic);
            geomagnetic[0] = smoothed[0];
            geomagnetic[1] = smoothed[1];
            geomagnetic[2] = smoothed[2];
            accelOrMagnetic = true;
        }

        SensorManager.getRotationMatrix(rotation, null, gravity, geomagnetic);

        SensorManager.getOrientation(rotation, orientation);

        bearing = orientation[0];



        bearing = Math.toDegrees(bearing);

        bearing += azimuthOffset;

        if (geomagneticField != null) {
            bearing -= geomagneticField.getDeclination();
        }



        if(bearing < 0) {
            bearing += 360;
        } else if (bearing > 360) {
            bearing -= 360;
        }

        if (Math.abs(bearing - lastBearing) > 1.5) {

            compassView.setBearing((float) bearing);

            lastBearing = bearing;
        }



        if (accelOrMagnetic) {
            compassView.postInvalidate();
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    private float[] lowPassFilter(float[] eventValues, float[] pastValues){
        //LPF: Y(n) = (1-ß)*Y(n-1) + (ß*X(n))) = Y(n-1) - (ß*(Y(n-1)-X(n)))
        float beta = (float) 0.995;
        int size = pastValues.length;
        float[] filtered = new float[size];
        for (int i = 0; i < size; i++){
            filtered[i] = pastValues[i] -(beta * (pastValues[i] - eventValues[i]));
        }

        return filtered;

    }
}
