package com.remindus.projetcommun.remindus.controller;

import android.app.ActionBar;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TimePicker;

import com.remindus.projetcommun.remindus.R;

import java.util.Calendar;

/**
 * Created by bahia on 23/02/2015.
 */
public class ControllerMsgProg extends ActionBarActivity {

    // Widget GUI
    private ImageButton buttonDate, buttonHeure;
    private EditText editDate, editHeure;

    // Variable for storing current date and time
    private int mYear, mMonth, mDay, mHour, mMinute;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vue_afficher_msg_prog);

        buttonDate = (ImageButton) findViewById(R.id.date);
        buttonHeure = (ImageButton) findViewById(R.id.heure);

        editDate = (EditText) findViewById(R.id.editDate);
        editHeure = (EditText) findViewById(R.id.editHeure);

        //buttonDate.setOnClickListener(this);
        //buttonHeure.setOnClickListener(this);
    }

    public void ajouterHeure(View v) {

        // Process to get Current Time
        final Calendar c = Calendar.getInstance();
        mHour = c.get(Calendar.HOUR_OF_DAY)+6;
        mMinute = c.get(Calendar.MINUTE);

        // Launch Time Picker Dialog
        TimePickerDialog tpd = new TimePickerDialog(this,
            new TimePickerDialog.OnTimeSetListener() {

                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay,
                                              int minute) {
                // Display Selected time in textbox
                editHeure.setText(hourOfDay + ":" + minute);
                }
                              }, mHour, mMinute, true);
        tpd.show();
    }

    public void ajouterDate(View view){
        // Process to get Current Date
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);

        // Launch Date Picker Dialog
        DatePickerDialog dpd = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        // Display Selected date in textbox
                        editDate.setText(dayOfMonth + "/"
                                + (monthOfYear + 1) + "/" + year);

                    }
                }, mYear, mMonth, mDay);
        dpd.show();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
