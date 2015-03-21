package com.remindus.projetcommun.remindus.controller;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.remindus.projetcommun.remindus.R;

/**
 * Created by samairi on 03/03/2015.
 */

public class ControllerParametre extends ControllerHeader {


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
        getMenuInflater().inflate(R.menu.global, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // TODO Auto-generated method stub
        switch (item.getItemId()) {
            case R.id.bouton_parametre:
                Intent intent = new Intent(ControllerParametre.this, ControllerParametre.class);
                startActivity(intent);
                break;

        }

        return false;
    }


}
