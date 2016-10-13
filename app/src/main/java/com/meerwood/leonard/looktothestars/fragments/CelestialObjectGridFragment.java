/*
 * Copyright (c) 2016. Created by Leonard Meerwood for ITC209.
 * No part of this may be used for your assignment.
 */

package com.meerwood.leonard.looktothestars.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.meerwood.leonard.looktothestars.R;
import com.meerwood.leonard.looktothestars.adapters.ManMadeAdapter;
import com.meerwood.leonard.looktothestars.adapters.NaturalAdapter;
import com.meerwood.leonard.looktothestars.objects.ManMadeObject;
import com.meerwood.leonard.looktothestars.objects.NaturalObject;

import co.moonmonkeylabs.realmrecyclerview.RealmRecyclerView;
import io.realm.Realm;
import io.realm.RealmResults;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link CelestialObjectGridFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CelestialObjectGridFragment extends Fragment {

    private static final String ARG_CELESTIAL_NAME = "name";
    private static final String ARG_CELESTIAL_TYPE = "type";


    private String mCelestialName;
    private String mCelestialType;

    //private OnFragmentInteractionListener mListener;

    public CelestialObjectGridFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CelestialObjectGridFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CelestialObjectGridFragment newInstance(String param1, String param2) {
        CelestialObjectGridFragment fragment = new CelestialObjectGridFragment();
        Bundle args = new Bundle();
        args.putString(ARG_CELESTIAL_NAME, param1);
        args.putString(ARG_CELESTIAL_TYPE, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mCelestialName = getArguments().getString(ARG_CELESTIAL_NAME);
            mCelestialType = getArguments().getString(ARG_CELESTIAL_TYPE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_celestial_object_grid, container, false);
        Realm realm = Realm.getDefaultInstance();
        NaturalAdapter naturalAdapter;
        ManMadeAdapter manMadeAdapter;
        RealmRecyclerView realmRecyclerView;
        switch (mCelestialType) {
            case "stars":
                RealmResults<NaturalObject> stars =
                        realm.where(NaturalObject.class)
                                .equalTo("type", NaturalObject.Type.STAR)
                                .findAll();
                stars = stars.sort("name");
                naturalAdapter = new NaturalAdapter(this.getContext(), stars, true, true);
                realmRecyclerView = (RealmRecyclerView) view.findViewById(R.id.celestial_object_view);
                realmRecyclerView.setAdapter(naturalAdapter);
                break;
            case "constellations":
                RealmResults<NaturalObject> constellations =
                        realm.where(NaturalObject.class)
                                .equalTo("type", NaturalObject.Type.CONSTELLATION)
                                .findAll();
                constellations = constellations.sort("name");
                naturalAdapter = new NaturalAdapter(this.getContext(), constellations, true, true);
                realmRecyclerView = (RealmRecyclerView) view.findViewById(R.id.celestial_object_view);
                realmRecyclerView.setAdapter(naturalAdapter);
                break;
            case "planets":
                RealmResults<NaturalObject> planets =
                        realm.where(NaturalObject.class)
                                .equalTo("type", NaturalObject.Type.PLANET)
                                .findAll();
                naturalAdapter = new NaturalAdapter(this.getContext(), planets, true, true);
                realmRecyclerView = (RealmRecyclerView) view.findViewById(R.id.celestial_object_view);
                realmRecyclerView.setAdapter(naturalAdapter);
                break;
            case "manmade":
                RealmResults<ManMadeObject> manMadeObjects =
                        realm.where(ManMadeObject.class)
                                .findAll();
                for(ManMadeObject manMadeObject : manMadeObjects) {
                    Log.d("Debug Man Made", manMadeObject.getName());
                }
                manMadeAdapter = new ManMadeAdapter(this.getContext(), manMadeObjects, true, true);
                realmRecyclerView = (RealmRecyclerView) view.findViewById(R.id.celestial_object_view);
                realmRecyclerView.setAdapter(manMadeAdapter);
                break;
        }
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        //mListener = null;
    }

    @Override
    public void onResume() {
        super.onResume();
    }
}
