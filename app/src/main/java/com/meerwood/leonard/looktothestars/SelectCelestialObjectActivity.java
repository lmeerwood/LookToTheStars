package com.meerwood.leonard.looktothestars;

import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.meerwood.leonard.looktothestars.adapters.PagerAdapter;
import com.meerwood.leonard.looktothestars.objects.ManMadeObject;
import com.meerwood.leonard.looktothestars.objects.NaturalObject;
import com.meerwood.leonard.looktothestars.objects.NaturalObject.Type;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class SelectCelestialObjectActivity extends AppCompatActivity {
    FragmentPagerAdapter adapterViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_celestial_object);

        ViewPager celObjViewPager = (ViewPager) findViewById(R.id.celestialObjectPager);
        adapterViewPager = new PagerAdapter(getSupportFragmentManager());
        celObjViewPager.setAdapter(adapterViewPager);

        RealmConfiguration config = new RealmConfiguration.Builder(getApplicationContext())
                .name("looktothestars.realm")
                .schemaVersion(1)
                .initialData(new initialTransaction())
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(config);
    }

    private class initialTransaction implements Realm.Transaction {

        @Override
        public void execute(Realm realm) {
            //Man made objects
            ManMadeObject mmo = new ManMadeObject("ISS");
            realm.copyToRealm(mmo);

            //Stars
            //List found from space.com
            //http://www.space.com/21640-star-luminosity-and-magnitude.html
            List<String> stars =
                    Arrays.asList(
                            "Sirius",
                            "Canopus",
                            "Alpha Centauri",
                            "Vega",
                            "Capella",
                            "Rigel",
                            "Procyon",
                            "Achernar",
                            "Betelgeuse",
                            "Hadar");
            NaturalObject no;
            for(String star: stars){
                Log.d("STARS", "Adding a star");
                no = new NaturalObject(star, Type.STAR);
                realm.copyToRealm(no);
            }

            //Constellations
            //List found from University of Wisconson Madison
            //http://www.astro.wisc.edu/~dolan/constellations/constellation_list.html
            List<String> constellations =
                    Arrays.asList(
                            "Andromeda",
                            "Antlia",
                            "Apus",
                            "Aquarius",
                            "Aquila",
                            "Ara",
                            "Aries",
                            "Auriga",
                            "Bootes",
                            "Caelum",
                            "Camelopardalis",
                            "Carina",
                            "Cancer",
                            "Canes Venatici",
                            "Canis Major",
                            "Canis Minor",
                            "Capricornus",
                            "Cassiopeia",
                            "Centaurus",
                            "Cepheus",
                            "Cetus",
                            "Chamaeloen",
                            "Circinus",
                            "Columba",
                            "Coma Berenices",
                            "Corona Austrina",
                            "Corona Borealis",
                            "Corvus",
                            "Crater",
                            "Crux",
                            "Cygnus",
                            "Delphinus",
                            "Dorado",
                            "Draco",
                            "Equuleus",
                            "Eridanus",
                            "Fornax",
                            "Gemini",
                            "Grus",
                            "Hercules",
                            "Horologium",
                            "Hydra",
                            "Hydrus",
                            "Indus",
                            "Lacerta",
                            "Leo",
                            "Leo Minor",
                            "Lepus",
                            "Libra",
                            "Lupus",
                            "Lynx",
                            "Lyra",
                            "Mensa",
                            "Microscopium",
                            "Monoceros",
                            "Musca",
                            "Norma",
                            "Octans",
                            "Ophiuchus",
                            "Orion",
                            "Pavo",
                            "Pegasus",
                            "Perseus",
                            "Phoenix",
                            "Pictor",
                            "Pisces",
                            "Piscis Austrinus",
                            "Puppis",
                            "Pyxis",
                            "Reticulum",
                            "Sagitta",
                            "Sagittarius",
                            "Scorpius",
                            "Sculptor",
                            "Scutum",
                            "Serpens",
                            "Sextans",
                            "Taurus",
                            "Telescopium",
                            "Triangulum",
                            "Triangulum Australe",
                            "Tucana",
                            "Ursa Major",
                            "Ursa Minor",
                            "Vela",
                            "Virgo",
                            "Volans",
                            "Vulpecula"
                            );

            for(String constellation: constellations){
                no = new NaturalObject(constellation, Type.CONSTELLATION);
                realm.copyToRealm(no);
            }


        }
    }
}
