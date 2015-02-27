package com.remindus.projetcommun.remindus;

/**
 * Created by bahia on 26/02/2015.
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;

import com.remindus.projetcommun.remindus.controller.ControllerContact;
import com.remindus.projetcommun.remindus.controller.ControllerGroupe;
import com.remindus.projetcommun.remindus.controller.ControllerModelMsg;
import com.remindus.projetcommun.remindus.controller.ControllerMsgProg;
import com.remindus.projetcommun.remindus.controller.ControllerCalendrier;
import com.remindus.projetcommun.remindus.controller.ControllerRdv;

public class MainActivity extends ActionBarActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Button boutonAccueil = (Button) findViewById(R.id.bouton_calendrier);
        boutonAccueil.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ControllerCalendrier.class);
                startActivity(intent);
            }
        });

        final Button boutonRDV = (Button) findViewById(R.id.bouton_rdv);
        boutonRDV.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ControllerRdv.class);
                startActivity(intent);
            }
        });

        final Button boutonMsgProg = (Button) findViewById(R.id.bouton_msg_prog);
        boutonMsgProg.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ControllerMsgProg.class);
                startActivity(intent);
            }
        });

        final Button boutonGroupe = (Button) findViewById(R.id.bouton_groupe);
        boutonGroupe.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ControllerGroupe.class);
                startActivity(intent);
            }
        });

        final Button boutonContact = (Button) findViewById(R.id.bouton_contact);
        boutonContact.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ControllerContact.class);
                startActivity(intent);
            }
        });

        final Button boutonModelMsg = (Button) findViewById(R.id.bouton_model_msg);
        boutonModelMsg.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ControllerModelMsg.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.global, menu);
        return true;
    }
}