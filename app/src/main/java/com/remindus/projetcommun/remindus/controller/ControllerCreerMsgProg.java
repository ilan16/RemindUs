package com.remindus.projetcommun.remindus.controller;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TimePicker;
import android.widget.Toast;

import com.remindus.projetcommun.remindus.R;
import com.remindus.projetcommun.remindus.controller.validator.ValidatorDate;
import com.remindus.projetcommun.remindus.controller.validator.ValidatorHeure;
import com.remindus.projetcommun.remindus.dao.DAOMsgProg;

import java.text.ParseException;
import java.util.Calendar;

/**
 * Created by bahia on 23/02/2015.
 */
public class ControllerCreerMsgProg extends ControllerHeader {

    // Widget GUI
    private ImageButton buttonDate, buttonHeure;
    private EditText editDate, editHeure, editTitre, editContenu;
    private UtilitaireDate utilitaireDate;
    private DAOMsgProg daoMsgProg;
    // Variable for storing current date and time
    private int mYear, mMonth, mDay, mHour, mMinute;
    private static String titreMsgProgStatic;

    public static String getTitreMsgProgStatic() {
        return titreMsgProgStatic;
    }

    public static void setTitreMsgProgStatic(String titreMsgProgStatic) {
        ControllerCreerMsgProg.titreMsgProgStatic = titreMsgProgStatic;
    }

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vue_creer_msg_prog);

        buttonDate = (ImageButton) findViewById(R.id.date);
        buttonHeure = (ImageButton) findViewById(R.id.heure);

        editDate = (EditText) findViewById(R.id.editDate);
        editHeure = (EditText) findViewById(R.id.editHeure);
        editTitre = (EditText) findViewById(R.id.titre_msg_prog);
        editContenu = (EditText) findViewById(R.id.message_msg_prog);
        //buttonDate.setOnClickListener(this);
        //buttonHeure.setOnClickListener(this);
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
                        // Display Selected time in textboxi
                        String hourString = null;
                        String minuteString = null;
                        if(hourOfDay<10){
                            hourString = "0"+hourOfDay;
                        }else {
                            hourString = ""+hourOfDay;
                        }
                        if(minute<10){
                            minuteString = "0"+minute;
                        }else{
                            minuteString = ""+minute;
                        }
                        editHeure.setText(hourString + ":" + minuteString);
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
                        editDate.setText(dayOfMonth + "/"
                                + (monthOfYear + 1) + "/" + year);

                    }
                }, mYear, mMonth, mDay);
        dpd.show();
    }

    public void creerMsgProg(View view) throws ParseException {
        String titre = editTitre.getText().toString();
        String date = editDate.getText().toString();
        String heure = editHeure.getText().toString();
        String contenu = editContenu.getText().toString();

        if (this.verifierDate()) {

            if (this.verifierHeure()) {

                if (!titre.equals("")) {

                    if (!contenu.equals("")) {

                        String dateRDV = date + "-" + heure;
                        utilitaireDate = new UtilitaireDate();
                        long dateLong = utilitaireDate.convertirStringDateEnLong(dateRDV);

                        daoMsgProg = new DAOMsgProg(this);
                        int insert = daoMsgProg.insertMsgProg(titre, dateLong, date, contenu);

                        if (insert == 0) {
                            ControllerCreerMsgProg.setTitreMsgProgStatic(titre);
                            Intent intent = new Intent(ControllerCreerMsgProg.this, ControllerContact.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(this, R.string.erreur_insertion_msg_prog, Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(this, getResources().getString(R.string.champs_vide, "contenu"), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(this, getResources().getString(R.string.champs_vide, "titre"), Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, getResources().getString(R.string.erreur_format, "HH:MM"), Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, getResources().getString(R.string.erreur_format, "MM/DD/YYYY"), Toast.LENGTH_SHORT).show();
        }
    }

    public boolean verifierDate() {
        ValidatorDate validatorDate = new ValidatorDate();
        return validatorDate.validate(editDate.getText().toString());
    }

    public boolean verifierHeure() {
        ValidatorHeure validatorHeure = new ValidatorHeure();
        return validatorHeure.validate(editHeure.getText().toString());
    }




}
