package com.meerwood.leonard.looktothestars;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.WindowManager;

import com.meerwood.leonard.looktothestars.fragments.CelestialObjectGridFragment;
import com.meerwood.leonard.looktothestars.fragments.ViewObjectActivityFragment;

public class ViewObjectActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_object);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        String name = getIntent().getExtras().getString("NAME");

        setTitle(name);

        ViewObjectActivityFragment voaf = new ViewObjectActivityFragment();
        voaf.setArguments(getIntent().getExtras());

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        transaction.replace(R.id.loading, voaf);

        transaction.commit();
    }

}
