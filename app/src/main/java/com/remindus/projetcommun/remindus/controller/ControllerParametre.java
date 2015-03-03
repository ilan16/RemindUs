package com.remindus.projetcommun.remindus.controller;

/**
 * Created by samairi on 03/03/2015.
 */

import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import com.remindus.projetcommun.remindus.R;

public class ControllerParametre extends ListActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


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



}
