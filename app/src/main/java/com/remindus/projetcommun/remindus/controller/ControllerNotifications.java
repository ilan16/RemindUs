package com.remindus.projetcommun.remindus.controller;

import android.content.Context;
import android.media.AudioManager;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import com.remindus.projetcommun.remindus.R;

import java.util.Locale;

/**
 * Created by samairi on 03/03/2015.
 */

public class ControllerNotifications extends ActionBarActivity {

    private CheckBox normal, silencieux, vibreur;
    private Button button;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vue_afficher_notifications);

        addListenerOnChkNormal();
        addListenerOnButton();
    }

    public void addListenerOnChkNormal() {

        normal = (CheckBox) findViewById(R.id.notif_normal);
        normal.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if (((CheckBox) v).isChecked()) {

                    AudioManager audiomanage = (AudioManager)getSystemService(Context.AUDIO_SERVICE);
                    audiomanage.setRingerMode(AudioManager.RINGER_MODE_NORMAL);

                    Toast.makeText(ControllerNotifications.this, "Mode normal activé !", Toast.LENGTH_LONG).show();
                }

            }
        });

        silencieux = (CheckBox) findViewById(R.id.notif_silencieux);
        silencieux.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if (((CheckBox) v).isChecked()) {

                    AudioManager audiomanage = (AudioManager)getSystemService(Context.AUDIO_SERVICE);
                    audiomanage.setRingerMode(AudioManager.RINGER_MODE_SILENT);

                    Toast.makeText(ControllerNotifications.this, "Mode silencieux activé !", Toast.LENGTH_LONG).show();
                }

            }
        });

        vibreur = (CheckBox) findViewById(R.id.notif_vibreur);
        vibreur.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if (((CheckBox) v).isChecked()) {

                    AudioManager audiomanage = (AudioManager)getSystemService(Context.AUDIO_SERVICE);
                    audiomanage.setRingerMode(AudioManager.RINGER_MODE_VIBRATE);

                    Toast.makeText(ControllerNotifications.this, "Mode vibreur activé !", Toast.LENGTH_LONG).show();
                }

            }
        });

    }

    public void addListenerOnButton() {

        silencieux = (CheckBox) findViewById(R.id.notif_silencieux);
        vibreur = (CheckBox) findViewById(R.id.notif_vibreur);
        normal = (CheckBox) findViewById(R.id.notif_normal);
        button = (Button) findViewById(R.id.notif_button);

        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                StringBuffer result = new StringBuffer();
                result.append("Silencieux check : ").append(silencieux.isChecked());
                result.append("\nVibreur check : ").append(vibreur.isChecked());
                result.append("\nNormal check :").append(normal.isChecked());

                Toast.makeText(ControllerNotifications.this, result.toString(), Toast.LENGTH_LONG).show();

            }
        });

    }
}