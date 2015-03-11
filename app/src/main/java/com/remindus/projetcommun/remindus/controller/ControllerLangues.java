package com.remindus.projetcommun.remindus.controller;

import android.app.Application;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.remindus.projetcommun.remindus.MainActivity;
import com.remindus.projetcommun.remindus.R;

import java.util.Locale;

/**
 * Created by samairi on 03/03/2015.
 */

public class ControllerLangues extends ControllerHeader  {

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

        Intent intent = new Intent(this.getApplicationContext(), MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        this.startActivity(intent);

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.global, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // TODO Auto-generated method stub
        switch (item.getItemId()) {
            case R.id.bouton_parametre:
                Intent intent = new Intent(ControllerLangues.this, ControllerParametre.class);
                startActivity(intent);
                break;

        }

        return false;
    }


}
