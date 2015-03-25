package com.remindus.projetcommun.remindus.controller;

import android.content.Context;
import android.media.AudioManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.Toast;

import com.remindus.projetcommun.remindus.R;

/**
 * Created by samairi on 03/03/2015.
 */

public class ControllerNotifications extends ControllerHeader {

    private CheckBox normal, silencieux, vibreur;
    private Button button;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vue_afficher_notifications) ;

    }

    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch (view.getId()) {
            case R.id.notif_normal:
                if (checked) {
                    Toast.makeText(ControllerNotifications.this, "Mode normal par defaut activé !", Toast.LENGTH_LONG).show();
                    break;
                }
            case R.id.notif_silencieux:
                if (checked) {
                    Toast.makeText(ControllerNotifications.this, "Mode silencieux par defaut activé !", Toast.LENGTH_LONG).show();
                    break;
                }
            case R.id.notif_vibreur:
                if (checked) {
                    Toast.makeText(ControllerNotifications.this, "Mode vibreur par defaut activé !", Toast.LENGTH_LONG).show();
                    break;
                }
        }
    }



}