package com.remindus.projetcommun.remindus.controller;

import java.util.Locale;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.app.Activity;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
/**
 * Created by samairi on 03/03/2015.
 */

import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import com.remindus.projetcommun.remindus.MainActivity;
import com.remindus.projetcommun.remindus.R;

import java.util.Locale;

public class ControllerLangues extends ActionBarActivity {

    private Locale myLocale;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.vue_afficher_langues);


        final Button boutonFR = (Button) findViewById(R.id.bouton_fr);
        boutonFR.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                myLocale = new Locale("fr");
                Resources res = getResources();
                DisplayMetrics dm = res.getDisplayMetrics();
                Configuration conf = res.getConfiguration();
                conf.locale = myLocale;
                res.updateConfiguration(conf, dm);
                Intent refresh = new Intent(ControllerLangues.this, MainActivity.class);
                startActivity(refresh);
            }
        });

        final Button boutonAR= (Button) findViewById(R.id.bouton_ar);
        boutonAR.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                myLocale = new Locale("ar");
                Resources res = getResources();
                DisplayMetrics dm = res.getDisplayMetrics();
                Configuration conf = res.getConfiguration();
                conf.locale = myLocale;
                res.updateConfiguration(conf, dm);
                Intent refresh = new Intent(ControllerLangues.this, MainActivity.class);
                startActivity(refresh);


            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_controller_langues, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }



}
