package com.remindus.projetcommun.remindus.controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.remindus.projetcommun.remindus.R;

/**
 * Created by samairi on 03/03/2015.
 */

public class ControllerParametre extends ActionBarActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.vue_afficher_parametre);


        final Button boutonLangues = (Button) findViewById(R.id.bouton_parametre_langues);
        boutonLangues.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ControllerParametre.this, ControllerLangues.class);
                startActivity(intent);
            }
        });

        final Button boutonNotifications = (Button) findViewById(R.id.bouton_parametre_notifications);
        boutonNotifications.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ControllerParametre.this, ControllerNotifications.class);
                startActivity(intent);
            }
        });

        final Button boutonCopyright = (Button) findViewById(R.id.bouton_parametre_copyright);
        boutonCopyright.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ControllerParametre.this, ControllerCopyright.class);
                startActivity(intent);
            }
        });

        final Button boutonMail = (Button) findViewById(R.id.bouton_parametre_mail);
        boutonMail.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ControllerParametre.this, ControllerMail.class);
                startActivity(intent);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_controller_parametre, menu);
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
