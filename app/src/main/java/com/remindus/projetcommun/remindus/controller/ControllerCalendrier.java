package com.remindus.projetcommun.remindus.controller;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CalendarView;
import android.widget.Toast;

import com.remindus.projetcommun.remindus.R;
import com.tyczj.extendedcalendarview.Day;
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
        calendar.setOnDayClickListener(new ExtendedCalendarView.OnDayClickListener() {
            @Override
            public void onDayClicked(AdapterView<?> adapter, View view, int position,
                                     long id, Day day) {
                AlertDialog alertDialog = new AlertDialog.Builder(ControllerCalendrier.this).create();
                alertDialog.setTitle(day.getDay()+"/"+(day.getMonth()+1)+"/"+day.getYear());
                alertDialog.setMessage("Futur liste des RDV");
                alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Add your code for the button here.
                        //Toast.makeText(getApplicationContext(), "OK", Toast.LENGTH_SHORT).show();
                    }
                });
                alertDialog.show();
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
                Intent intent = new Intent(ControllerCalendrier.this, ControllerParametre.class);
                startActivity(intent);
                break;

        }

        return false;
    }
}
