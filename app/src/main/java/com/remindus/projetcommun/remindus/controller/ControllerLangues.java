package com.remindus.projetcommun.remindus.controller;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;

import com.remindus.projetcommun.remindus.R;

import java.util.Locale;

/**
 * Created by samairi on 03/03/2015.
 */

public class ControllerLangues extends ControllerHeader {

    private Locale myLocale;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.vue_afficher_langues);


        final Button boutonFR = (Button) findViewById(R.id.bouton_fr);
        boutonFR.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                changeLangue("fr");

            }
        });

        final Button boutonEN = (Button) findViewById(R.id.bouton_en);
        boutonEN.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {


                changeLangue("en");

            }
        });

        final Button boutonAR = (Button) findViewById(R.id.bouton_ar);
        boutonAR.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {


                changeLangue("ar");

            }
        });


        final Button boutonHW = (Button) findViewById(R.id.bouton_hw);
        boutonHW.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                changeLangue("iw");

            }
        });

    }


    public void changeLangue(String langue) {

        myLocale = new Locale(langue);
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = myLocale;
        res.updateConfiguration(conf, dm);

        Intent intent = getIntent();
        startActivity(intent);
        finish();

    }




}
