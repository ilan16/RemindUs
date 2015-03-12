package com.remindus.projetcommun.remindus.controller;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.inputmethodservice.Keyboard;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TimePicker;

import com.remindus.projetcommun.remindus.R;
import com.remindus.projetcommun.remindus.dao.DAORDV;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by bahia on 23/02/2015.
 */
public class ControllerCreerRDV extends ControllerHeader {

    private EditText nomEdit;
    private EditText dateEdit;
    private EditText heureEdit;
    private EditText lieuEdit;
    private RadioButton normal, silencieux, vibreur;
    private DAORDV daordv;
    private int mYear, mMonth, mDay, mHour, mMinute;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vue_afficher_rdv);

        nomEdit = (EditText) findViewById(R.id.nom_rdv);
        dateEdit = (EditText) findViewById(R.id.editDate);
        heureEdit = (EditText) findViewById(R.id.editHeure);
        lieuEdit = (EditText) findViewById(R.id.lieu_rdv);
        normal = (RadioButton) findViewById(R.id.radio_normal);
        silencieux = (RadioButton) findViewById(R.id.radio_silencieux);
        vibreur = (RadioButton) findViewById(R.id.radio_vibreur);
    }

    public void creerRDV(View view) throws ParseException {

        String nom = nomEdit.getText().toString();
        String date = dateEdit.getText().toString();
        String heure = heureEdit.getText().toString();
        String lieu = lieuEdit.getText().toString();
        long mode = 0;

        if(normal.isChecked()){
            mode = 0;
        }else if(silencieux.isChecked()){
            mode = 1;
        }else if(vibreur.isChecked()){
            mode = 2;
        }
        String dateRDV = date + "-"+heure;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy-HH:mm");
        Date d = (Date)simpleDateFormat.parse(dateRDV);
        long dateLong = d.getTime();

        DateFormat df = new SimpleDateFormat("dd/MM/yy à HH:mm:ss");
        String doo =df.format(dateLong);
        System.out.println("date : "+date+ " dateMinute : "+dateLong + " reconversion :" +doo);

        daordv = new DAORDV(this);

        int insert = daordv.insertRDV(nom, dateLong, lieu, mode);

    }

    public void ajouterHeure(View v) {

        // Process to get Current Time
        final Calendar c = Calendar.getInstance();
        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);

        // Launch Time Picker Dialog
        TimePickerDialog tpd = new TimePickerDialog(this,
                new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay,
                                          int minute) {
                        // Display Selected time in textbox
                        heureEdit.setText(hourOfDay + ":" + minute);
                    }
                }, mHour, mMinute, true);
        tpd.show();
    }

    public void ajouterDate(View view) {
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
                        dateEdit.setText(dayOfMonth + "/"
                                + (monthOfYear + 1) + "/" + year);

                    }
                }, mYear, mMonth, mDay);
        dpd.show();
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
                Intent intent = new Intent(ControllerCreerRDV.this, ControllerParametre.class);
                startActivity(intent);
                break;

        }

        return false;
    }
}
