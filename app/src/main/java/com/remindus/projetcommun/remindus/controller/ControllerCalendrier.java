package com.remindus.projetcommun.remindus.controller;

import android.app.Activity;
import android.os.Bundle;
import android.widget.CalendarView;
import android.widget.Toast;

import com.remindus.projetcommun.remindus.R;

/**
 * Created by bahia on 23/02/2015.
 */
public class ControllerCalendrier extends Activity /*implements CalendarView.OnDateChangeListener*/ {

    private CalendarView calenderview=null;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vue_afficher_accueil);
        /*calenderview=(CalendarView)findViewById(R.id.calendarView_cl);
        calenderview.setOnDateChangeListener(this);*/
        initializeCalendar();

    }

    /*@Override
    public void onSelectedDayChange(CalendarView arg0, int arg1, int arg2, int arg3) {

        Toast.makeText(getBaseContext(), arg1 + "/" + arg2 + "/" + arg3, Toast.LENGTH_LONG).show();
    }*/

    public void initializeCalendar() {
        calenderview=(CalendarView)findViewById(R.id.calendarView_cl);

        // sets whether to show the week number.
        calenderview.setShowWeekNumber(false);

        // sets the first day of week according to Calendar.
        // here we set Monday as the first day of the Calendar
        calenderview.setFirstDayOfWeek(2);

        //The background color for the selected week.
        calenderview.setSelectedWeekBackgroundColor(getResources().getColor(R.color.green));

        //sets the color for the dates of an unfocused month.
        calenderview.setUnfocusedMonthDateColor(getResources().getColor(R.color.transparent));

        //sets the color for the separator line between weeks.
        calenderview.setWeekSeparatorLineColor(getResources().getColor(R.color.transparent));

        //sets the color for the vertical bar shown at the beginning and at the end of the selected date.
        calenderview.setSelectedDateVerticalBar(R.color.darkgreen);

        //sets the listener to be notified upon selected date change.
        calenderview.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            //show the selected date as a toast
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int day) {
                Toast.makeText(getApplicationContext(), day + "/" + (month+1) + "/" + year, Toast.LENGTH_LONG).show();
            }
        });
    }
}
