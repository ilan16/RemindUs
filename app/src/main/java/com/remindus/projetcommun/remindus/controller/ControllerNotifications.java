package com.remindus.projetcommun.remindus.controller;

import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.Toast;

import com.remindus.projetcommun.remindus.R;

import java.util.Locale;

/**
 * Created by samairi on 03/03/2015.
 */

public class ControllerNotifications extends ControllerHeader {

    private CheckBox normal, silencieux, vibreur;
    private Button button;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vue_afficher_notifications);

    }

    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.notif_normal:
                if (checked) {
                    AudioManager audiomanage = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
                    audiomanage.setRingerMode(AudioManager.RINGER_MODE_NORMAL);

                    Toast.makeText(ControllerNotifications.this, "Mode normal activé !", Toast.LENGTH_LONG).show();
                    break;
                }
            case R.id.notif_silencieux:
                if (checked) {
                    AudioManager audiomanage = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
                    audiomanage.setRingerMode(AudioManager.RINGER_MODE_SILENT);

                    Toast.makeText(ControllerNotifications.this, "Mode silencieux activé !", Toast.LENGTH_LONG).show();
                    break;
                }case R.id.notif_vibreur:
                if (checked) {

                    AudioManager audiomanage = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
                    audiomanage.setRingerMode(AudioManager.RINGER_MODE_VIBRATE);

                    Toast.makeText(ControllerNotifications.this, "Mode vibreur activé !", Toast.LENGTH_LONG).show();

                }break;
        }
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
                Intent intent = new Intent(ControllerNotifications.this, ControllerParametre.class);
                startActivity(intent);
                break;

        }

        return false;
    }


}