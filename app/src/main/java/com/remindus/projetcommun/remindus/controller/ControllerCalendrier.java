package com.remindus.projetcommun.remindus.controller;

import android.app.Activity;
import android.os.Bundle;
import android.widget.CalendarView;
import android.widget.Toast;

import com.remindus.projetcommun.remindus.R;

/**
 * Created by bahia on 23/02/2015.
 */
public class ControllerCalendrier extends Activity implements CalendarView.OnDateChangeListener {

    private CalendarView calenderview=null;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vue_afficher_accueil);
        calenderview=(CalendarView)findViewById(R.id.calendarView_cl);
        calenderview.setOnDateChangeListener(this);
    }

    @Override
    public void onSelectedDayChange(CalendarView arg0, int arg1, int arg2, int arg3) {

        Toast.makeText(getBaseContext(), arg1 + "/" + arg2 + "/" + arg3, Toast.LENGTH_LONG).show();
    }
}
