package com.remindus.projetcommun.remindus;

/**
 * Created by bahia on 26/02/2015.
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;

import com.remindus.projetcommun.remindus.Service.DeclencheurSms;
import com.remindus.projetcommun.remindus.controller.ControllerCalendrier;
import com.remindus.projetcommun.remindus.controller.ControllerContact;
import com.remindus.projetcommun.remindus.controller.ControllerCreerMsgProg;
import com.remindus.projetcommun.remindus.controller.ControllerCreerRDV;
import com.remindus.projetcommun.remindus.controller.ControllerListerGroupe;
import com.remindus.projetcommun.remindus.controller.ControllerListerModelMsg;
import com.remindus.projetcommun.remindus.controller.ControllerListerMsgProg;
import com.remindus.projetcommun.remindus.controller.ControllerListerRDV;
import com.remindus.projetcommun.remindus.controller.ControllerParametre;


public class MainActivity extends ActionBarActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ControllerCreerRDV.setNomRDVstatic("");
        ControllerCreerMsgProg.setTitreMsgProgStatic("");


        //debut du service declencheurSms pour savoir quand envoyer un sms
        Intent myIntent = new Intent(MainActivity.this, DeclencheurSms.class);

        MainActivity.this.startService(myIntent);

        //redirection vers le calendrier
        final Button boutonAccueil = (Button) findViewById(R.id.bouton_calendrier);
        boutonAccueil.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ControllerCalendrier.class);
                startActivity(intent);
            }
        });
        //redirection vers la liste des RDV
        final Button boutonRDV = (Button) findViewById(R.id.bouton_rdv);
        boutonRDV.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ControllerListerRDV.class);
                startActivity(intent);
            }
        });
        //redirection vers liste des messages programmé
        final Button boutonMsgProg = (Button) findViewById(R.id.bouton_msg_prog);
        boutonMsgProg.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ControllerListerMsgProg.class);
                startActivity(intent);
            }
        });
        //redirection vers la liste de groupe
        final Button boutonGroupe = (Button) findViewById(R.id.bouton_groupe);
        boutonGroupe.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ControllerListerGroupe.class);
                startActivity(intent);
            }
        });
        //redirection vers la liste des contacts
        final Button boutonContact = (Button) findViewById(R.id.bouton_contact);
        boutonContact.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ControllerContact.class);
                startActivity(intent);
            }
        });
        //redirection vers liste des modele messages
        final Button boutonModelMsg = (Button) findViewById(R.id.bouton_model_msg);
        boutonModelMsg.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ControllerListerModelMsg.class);
                startActivity(intent);
            }
        });

        //redirection vers les parametres
        final Button boutonParametre = (Button) findViewById(R.id.bouton_parametre);
        boutonParametre.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ControllerParametre.class);
                startActivity(intent);
            }
        });


    }

}