package com.remindus.projetcommun.remindus.controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CalendarView;
import android.widget.Toast;

import com.remindus.projetcommun.remindus.R;
import com.tyczj.extendedcalendarview.ExtendedCalendarView;

/**
 * Created by bahia on 23/02/2015.
 */
public class ControllerCalendrier extends ControllerHeader /*implements CalendarView.OnDateChangeListener*/ {

    private ExtendedCalendarView calendar = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.vue_afficher_calendrier);
        calendar = (ExtendedCalendarView)findViewById(R.id.calendar);

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
                Intent intent = new Intent(ControllerCalendrier.this, ControllerParametre.class);
                startActivity(intent);
                break;

        }

        return false;
    }
}
