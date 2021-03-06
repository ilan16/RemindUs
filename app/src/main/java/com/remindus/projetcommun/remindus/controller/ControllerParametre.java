package com.remindus.projetcommun.remindus.controller;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.remindus.projetcommun.remindus.R;

/**
 * Created by samairi on 03/03/2015.
 */

public class ControllerParametre extends ControllerHeader {

    /**
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.vue_afficher_parametre);

        // redirige vers le menu langue
        final Button boutonLangues = (Button) findViewById(R.id.bouton_parametre_langues);
        boutonLangues.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ControllerParametre.this, ControllerLangues.class);
                startActivity(intent);
            }
        });

        // redirige vers le menu notification
        final Button boutonNotifications = (Button) findViewById(R.id.bouton_parametre_notifications);
        boutonNotifications.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ControllerParametre.this, ControllerNotifications.class);
                startActivity(intent);
            }
        });

        // redirige vers le menu copyright
        final Button boutonCopyright = (Button) findViewById(R.id.bouton_parametre_copyright);
        boutonCopyright.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ControllerParametre.this, ControllerCopyright.class);
                startActivity(intent);
            }
        });

        // redirige vers le menu mail
        final Button boutonMail = (Button) findViewById(R.id.bouton_parametre_mail);
        boutonMail.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ControllerParametre.this, ControllerMail.class);
                startActivity(intent);
            }
        });

    }




}
