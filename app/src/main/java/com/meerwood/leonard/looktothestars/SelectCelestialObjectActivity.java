package com.meerwood.leonard.looktothestars;

import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.meerwood.leonard.looktothestars.adapters.PagerAdapter;
import com.meerwood.leonard.looktothestars.helpers.CelestialObjectLocator;
import com.meerwood.leonard.looktothestars.helpers.PlanetCoordinates;
import com.meerwood.leonard.looktothestars.objects.ManMadeObject;
import com.meerwood.leonard.looktothestars.objects.NaturalObject;
import com.meerwood.leonard.looktothestars.objects.NaturalObject.Type;

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
                .deleteRealmIfMigrationNeeded() //Note, this is a bit hacky and should only be used for debugging
                .build();
        Realm.setDefaultConfiguration(config);
    }


    //This populates the database on the first time the app is run.
    private class initialTransaction implements Realm.Transaction {

        @Override
        public void execute(Realm realm) {
            //Man made objects
            ManMadeObject mmo = new ManMadeObject("ISS");
            realm.copyToRealm(mmo);

            //Stars
            //List found from space.com
            //http://www.space.com/21640-star-luminosity-and-magnitude.html
            ArrayList<NaturalObject> stars = new ArrayList<>();
            stars.add(new NaturalObject("Sirius",Type.STAR, 6.75, -16.7));
            stars.add(new NaturalObject("Canopus",Type.STAR, 6.4, -52.7));
            stars.add(new NaturalObject("Rigil Kent.",Type.STAR, 14.6666666666667, -60.8));
            stars.add(new NaturalObject("Arcturus",Type.STAR, 14.2666666666667, +19.2));
            stars.add(new NaturalObject("Vega",Type.STAR, 18.6166666666667, +38.8));
            stars.add(new NaturalObject("Capella",Type.STAR, 5.28333333333333, +46.0));
            stars.add(new NaturalObject("Rigel",Type.STAR, 5.25, -8.2));
            stars.add(new NaturalObject("Procyon",Type.STAR, 7.65, +5.2));
            stars.add(new NaturalObject("Betelgeuse",Type.STAR, 5.91666666666667, +7.4));
            stars.add(new NaturalObject("Achernar",Type.STAR, 1.63333333333333, -57.2));
            stars.add(new NaturalObject("Hadar",Type.STAR, 14.0666666666667, -60.4));
            stars.add(new NaturalObject("Altair",Type.STAR, 19.85, +8.9));
            stars.add(new NaturalObject("Acrux",Type.STAR, 12.45, -63.1));
            stars.add(new NaturalObject("Aldebaran",Type.STAR, 4.6, +16.5));
            stars.add(new NaturalObject("Spica",Type.STAR, 13.4166666666667, -11.2));
            stars.add(new NaturalObject("Antares",Type.STAR, 16.4833333333333, -26.4));
            stars.add(new NaturalObject("Pollux",Type.STAR, 7.75, +28.0));
            stars.add(new NaturalObject("Fomalhaut",Type.STAR, 22.9666666666667, -29.6));
            stars.add(new NaturalObject("Deneb",Type.STAR, 20.6833333333333, +45.3));
            stars.add(new NaturalObject("Mimosa",Type.STAR, 12.8, -59.7));
            stars.add(new NaturalObject("Regulus",Type.STAR, 10.1333333333333, +12.0));
            stars.add(new NaturalObject("Adhara",Type.STAR, 6.98333333333333, -29.0));
            stars.add(new NaturalObject("Castor",Type.STAR, 7.58333333333333, +31.9));
            stars.add(new NaturalObject("Gacrux",Type.STAR, 12.5166666666667, -57.1));
            stars.add(new NaturalObject("Shaula",Type.STAR, 17.5666666666667, -37.1));
            stars.add(new NaturalObject("Bellatrix",Type.STAR, 5.41666666666667, +6.3));
            stars.add(new NaturalObject("Elnath",Type.STAR, 5.43333333333333, +28.6));
            stars.add(new NaturalObject("Miaplacidus",Type.STAR, 9.21666666666667, -69.7));
            stars.add(new NaturalObject("Alnilam",Type.STAR, 5.6, -1.2));
            stars.add(new NaturalObject("Alnair",Type.STAR, 22.1333333333333, -47.0));
            stars.add(new NaturalObject("Alnitak",Type.STAR, 5.68333333333333, -1.9));
            stars.add(new NaturalObject("Regor",Type.STAR, 8.16666666666667, -47.3));
            stars.add(new NaturalObject("Alioth",Type.STAR, 12.9, +56.0));
            stars.add(new NaturalObject("Kaus Aust.",Type.STAR, 18.4, -34.4));
            stars.add(new NaturalObject("Mirfak",Type.STAR, 3.4, +49.9));
            stars.add(new NaturalObject("Dubhe",Type.STAR, 11.0666666666667, +61.8));
            stars.add(new NaturalObject("Wezen",Type.STAR, 7.13333333333333, -26.4));
            stars.add(new NaturalObject("Alkaid",Type.STAR, 13.8, +49.3));
            stars.add(new NaturalObject("Sargas",Type.STAR, 17.6166666666667, -43.0));
            stars.add(new NaturalObject("Avior",Type.STAR, 8.38333333333333, -59.5));
            stars.add(new NaturalObject("Menkalinan",Type.STAR, 6, +44.9));
            stars.add(new NaturalObject("Atria",Type.STAR, 16.8166666666667, -69.0));
            stars.add(new NaturalObject("Koo She",Type.STAR, 8.75, -54.7));
            stars.add(new NaturalObject("Alhena",Type.STAR, 6.63333333333333, +16.4));
            stars.add(new NaturalObject("Peacock",Type.STAR, 20.4333333333333, -56.7));
            stars.add(new NaturalObject("Polaris",Type.STAR, 2.53333333333333, +89.3));
            stars.add(new NaturalObject("Mirzam",Type.STAR, 6.38333333333333, -18.0));
            stars.add(new NaturalObject("Alphard",Type.STAR, 9.46666666666667, -8.7));
            stars.add(new NaturalObject("Algieba",Type.STAR, 10.3333333333333, +19.8));
            stars.add(new NaturalObject("Hamal",Type.STAR, 2.11666666666667, +23.5));
            for(NaturalObject star: stars){
                realm.copyToRealm(star);
            }

            //Constellations
            //List found from University of Wisconson Madison
            //http://www.astro.wisc.edu/~dolan/constellations/constellation_list.html
            //ra and dec found from wikipedia: https://en.wikipedia.org/wiki/88_modern_constellations_by_area
            //Instead of typing all these out, a little bit of Excel wizardry was used

            ArrayList<NaturalObject> constellations = new ArrayList<>();

            constellations.add(new NaturalObject("Andromeda",Type.CONSTELLATION, 0.807666666666667, 37.4318333333333));
            constellations.add(new NaturalObject("Antlia",Type.CONSTELLATION, 10.2738333333333, -32.4835));
            constellations.add(new NaturalObject("Apus",Type.CONSTELLATION, 16.1441666666667, -75.3));
            constellations.add(new NaturalObject("Aquarius",Type.CONSTELLATION, 22.2896666666667, -10.7891666666667));
            constellations.add(new NaturalObject("Aquila",Type.CONSTELLATION, 19.667, 3.41083333333333));
            constellations.add(new NaturalObject("Ara",Type.CONSTELLATION, 17.3748333333333, -56.5883333333333));
            constellations.add(new NaturalObject("Aries",Type.CONSTELLATION, 2.636, 20.7923333333333));
            constellations.add(new NaturalObject("Auriga",Type.CONSTELLATION, 6.07366666666667, 42.028));
            constellations.add(new NaturalObject("Boötes",Type.CONSTELLATION, 14.7106666666667, 31.2026666666667));
            constellations.add(new NaturalObject("Caelum",Type.CONSTELLATION, 4.7045, -37.8816666666667));
            constellations.add(new NaturalObject("Camelopardalis",Type.CONSTELLATION, 8.85616666666667, 69.3815));
            constellations.add(new NaturalObject("Cancer",Type.CONSTELLATION, 8.64933333333333, 19.8058333333333));
            constellations.add(new NaturalObject("Canes Venatici",Type.CONSTELLATION, 13.116, 40.1018333333333));
            constellations.add(new NaturalObject("Canis Major",Type.CONSTELLATION, 6.829, -22.1403333333333));
            constellations.add(new NaturalObject("Canis Minor",Type.CONSTELLATION, 7.65283333333333, 6.42716666666667));
            constellations.add(new NaturalObject("Capricornus",Type.CONSTELLATION, 21.0488333333333, -18.0231666666667));
            constellations.add(new NaturalObject("Carina",Type.CONSTELLATION, 8.695, -63.2193333333333));
            constellations.add(new NaturalObject("Cassiopeia",Type.CONSTELLATION, 1.31933333333333, 62.184));
            constellations.add(new NaturalObject("Centaurus",Type.CONSTELLATION, 13.0711666666667, -47.3453333333333));
            constellations.add(new NaturalObject("Cepheus",Type.CONSTELLATION, 2.544, 71.0085));
            constellations.add(new NaturalObject("Cetus",Type.CONSTELLATION, 1.66833333333333, -7.17933333333333));
            constellations.add(new NaturalObject("Chamaeleon",Type.CONSTELLATION, 10.6921666666667, -79.205));
            constellations.add(new NaturalObject("Circinus",Type.CONSTELLATION, 14.5756666666667, -63.0303333333333));
            constellations.add(new NaturalObject("Columba",Type.CONSTELLATION, 5.86266666666667, -35.0945));
            constellations.add(new NaturalObject("Coma Berenices",Type.CONSTELLATION, 12.7878333333333, 23.3056666666667));
            constellations.add(new NaturalObject("Corona Australis",Type.CONSTELLATION, 18.6465, -41.1475));
            constellations.add(new NaturalObject("Corona Borealis",Type.CONSTELLATION, 15.8431666666667, 32.6248333333333));
            constellations.add(new NaturalObject("Corvus",Type.CONSTELLATION, 12.442, -18.4366666666667));
            constellations.add(new NaturalObject("Crater",Type.CONSTELLATION, 11.3958333333333, -15.929));
            constellations.add(new NaturalObject("Crux",Type.CONSTELLATION, 12.4498333333333, -60.1865));
            constellations.add(new NaturalObject("Cygnus",Type.CONSTELLATION, 20.588, 44.545));
            constellations.add(new NaturalObject("Delphinus",Type.CONSTELLATION, 20.6935, 11.671));
            constellations.add(new NaturalObject("Dorado",Type.CONSTELLATION, 5.24183333333333, -59.387));
            constellations.add(new NaturalObject("Draco",Type.CONSTELLATION, 15.144, 67.0066666666667));
            constellations.add(new NaturalObject("Equuleus",Type.CONSTELLATION, 21.1876666666667, 7.75816666666667));
            constellations.add(new NaturalObject("Eridanus",Type.CONSTELLATION, 3.30033333333333, -28.7561666666667));
            constellations.add(new NaturalObject("Fornax",Type.CONSTELLATION, 2.798, -31.6345));
            constellations.add(new NaturalObject("Gemini",Type.CONSTELLATION, 7.07066666666667, 22.6001666666667));
            constellations.add(new NaturalObject("Grus",Type.CONSTELLATION, 22.4565, -46.3518333333333));
            constellations.add(new NaturalObject("Hercules",Type.CONSTELLATION, 17.386, 27.4988333333333));
            constellations.add(new NaturalObject("Horologium",Type.CONSTELLATION, 3.276, -53.3363333333333));
            constellations.add(new NaturalObject("Hydra",Type.CONSTELLATION, 11.6121666666667, -14.5318333333333));
            constellations.add(new NaturalObject("Hydrus",Type.CONSTELLATION, 2.34416666666667, -69.9565));
            constellations.add(new NaturalObject("Indus",Type.CONSTELLATION, 21.9721666666667, -59.7066666666667));
            constellations.add(new NaturalObject("Lacerta",Type.CONSTELLATION, 22.4613333333333, 46.0418333333333));
            constellations.add(new NaturalObject("Leo",Type.CONSTELLATION, 10.6671666666667, 13.1386666666667));
            constellations.add(new NaturalObject("Leo Minor",Type.CONSTELLATION, 10.2453333333333, 32.1346666666667));
            constellations.add(new NaturalObject("Lepus",Type.CONSTELLATION, 5.56583333333333, -19.0463333333333));
            constellations.add(new NaturalObject("Libra",Type.CONSTELLATION, 15.1993333333333, -15.2346666666667));
            constellations.add(new NaturalObject("Lupus",Type.CONSTELLATION, 15.2201666666667, -42.7088333333333));
            constellations.add(new NaturalObject("Lynx",Type.CONSTELLATION, 7.99216666666667, 47.4666666666667));
            constellations.add(new NaturalObject("Lyra",Type.CONSTELLATION, 18.8528333333333, 36.6893333333333));
            constellations.add(new NaturalObject("Mensa",Type.CONSTELLATION, 5.415, -77.504));
            constellations.add(new NaturalObject("Microscopium",Type.CONSTELLATION, 20.9646666666667, -36.2748333333333));
            constellations.add(new NaturalObject("Monoceros",Type.CONSTELLATION, 7.0605, 0.282166666666667));
            constellations.add(new NaturalObject("Musca",Type.CONSTELLATION, 12.588, -70.161));
            constellations.add(new NaturalObject("Norma",Type.CONSTELLATION, 15.903, -51.3515));
            constellations.add(new NaturalObject("Octans",Type.CONSTELLATION, 23, -82.152));
            constellations.add(new NaturalObject("Ophiuchus",Type.CONSTELLATION, 17.3948333333333, -7.91233333333333));
            constellations.add(new NaturalObject("Orion",Type.CONSTELLATION, 5.5765, 5.949));
            constellations.add(new NaturalObject("Pavo",Type.CONSTELLATION, 19.6118333333333, -65.7815));
            constellations.add(new NaturalObject("Pegasus",Type.CONSTELLATION, 22.6973333333333, 19.4663333333333));
            constellations.add(new NaturalObject("Perseus",Type.CONSTELLATION, 3.175, 45.0131666666667));
            constellations.add(new NaturalObject("Phoenix",Type.CONSTELLATION, 0.931833333333333, -48.5806666666667));
            constellations.add(new NaturalObject("Pictor",Type.CONSTELLATION, 5.70766666666667, -53.4741666666667));
            constellations.add(new NaturalObject("Pisces",Type.CONSTELLATION, 0.482833333333333, 13.6871666666667));
            constellations.add(new NaturalObject("Piscis Austrinus",Type.CONSTELLATION, 22.2845, -30.6421666666667));
            constellations.add(new NaturalObject("Puppis",Type.CONSTELLATION, 7.258, -31.1773333333333));
            constellations.add(new NaturalObject("Pyxis",Type.CONSTELLATION, 8.95266666666667, -27.3516666666667));
            constellations.add(new NaturalObject("Reticulum",Type.CONSTELLATION, 3.92116666666667, -59.9975));
            constellations.add(new NaturalObject("Sagitta",Type.CONSTELLATION, 19.6508333333333, 18.8613333333333));
            constellations.add(new NaturalObject("Sagittarius",Type.CONSTELLATION, 19.099, -28.4768333333333));
            constellations.add(new NaturalObject("Scorpius",Type.CONSTELLATION, 16.8873333333333, -27.0315));
            constellations.add(new NaturalObject("Sculptor",Type.CONSTELLATION, 0.438, -32.0883333333333));
            constellations.add(new NaturalObject("Scutum",Type.CONSTELLATION, 18.6731666666667, -9.88866666666667));
            constellations.add(new NaturalObject("Serpens [6]",Type.CONSTELLATION, 16.9506666666667, 6.122));
            constellations.add(new NaturalObject("Sextans",Type.CONSTELLATION, 10.2715, -2.61466666666667));
            constellations.add(new NaturalObject("Taurus",Type.CONSTELLATION, 4.70216666666667, 14.8771666666667));
            constellations.add(new NaturalObject("Telescopium",Type.CONSTELLATION, 19.3256666666667, -51.0368333333333));
            constellations.add(new NaturalObject("Triangulum",Type.CONSTELLATION, 2.1845, 31.476));
            constellations.add(new NaturalObject("Triangulum Australe",Type.CONSTELLATION, 16.0825, -65.388));
            constellations.add(new NaturalObject("Tucana",Type.CONSTELLATION, 23.7773333333333, -65.83));
            constellations.add(new NaturalObject("Ursa Major",Type.CONSTELLATION, 11.3126666666667, 50.7211666666667));
            constellations.add(new NaturalObject("Ursa Minor",Type.CONSTELLATION, 15, 77.6998333333333));
            constellations.add(new NaturalObject("Vela",Type.CONSTELLATION, 9.57733333333333, -47.1671666666667));
            constellations.add(new NaturalObject("Virgo",Type.CONSTELLATION, 13.4065, -4.1585));
            constellations.add(new NaturalObject("Volans",Type.CONSTELLATION, 7.7955, -69.8011666666667));
            constellations.add(new NaturalObject("Vulpecula",Type.CONSTELLATION, 20.2313333333333, 24.4426666666667));



            for(NaturalObject constellation: constellations){
                realm.copyToRealm(constellation);


            }

            //Planets
            //List taught to me by Mr. Stewart in the ninth grade science class.
            //Also, I've included Pluto because in my heart, Pluto will always be a planet.
            List<String> planets =
                    Arrays.asList(
                            "Mercury",
                            "Venus",
                            "Earth",
                            "Mars",
                            "Jupiter",
                            "Saturn",
                            "Uranus",
                            "Neptune",
                            "Pluto",
                            "Moon",
                            "Sun"
                    );

            PlanetCoordinates pc = CelestialObjectLocator
                    .getCurrentPlanetCoordinates(getApplicationContext());
            NaturalObject p;
            int i = 0;
            for(String planet: planets){
                double[] raAndDec;
                raAndDec = CelestialObjectLocator.cartesianToRaAndDec(pc.planet_r[i++]);
                p = new NaturalObject(planet,Type.PLANET,raAndDec[0], raAndDec[1]);
                realm.copyToRealm(p);
            }
        }
    }
}
