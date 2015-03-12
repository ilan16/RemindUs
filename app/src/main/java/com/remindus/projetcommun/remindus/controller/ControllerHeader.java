package com.remindus.projetcommun.remindus.controller;

import android.app.ListActivity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;

import com.remindus.projetcommun.remindus.MainActivity;
import com.remindus.projetcommun.remindus.R;

/**
 * Created by kevin on 11/03/2015.
 */
public class ControllerHeader extends ActionBarActivity {
    public void backHome(View view){
        final Button bouton = (Button) findViewById(R.id.bouton_home);
        bouton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
