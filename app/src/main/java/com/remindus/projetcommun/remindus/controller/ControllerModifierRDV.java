package com.remindus.projetcommun.remindus.controller;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TimePicker;
import android.widget.Toast;

import com.remindus.projetcommun.remindus.R;
import com.remindus.projetcommun.remindus.controller.validator.ValidatorDate;
import com.remindus.projetcommun.remindus.controller.validator.ValidatorHeure;
import com.remindus.projetcommun.remindus.dao.DAORDV;

import java.text.ParseException;
import java.util.Calendar;

/**
 * Created by ilanmalka on 12/03/15.
 */
public class ControllerModifierRDV extends ControllerHeader {

    private EditText nomEdit, dateEdit, heureDebutEdit, heureFinEdit, lieuEdit;
    private RadioButton normal, silencieux, vibreur;
    private int mYear, mMonth, mDay, mHour, mMinute;
    private UtilitaireDate utilitaireDate;
    private DAORDV daordv;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vue_modifier_rdv);

        this.utilitaireDate = new UtilitaireDate();

        nomEdit = (EditText) findViewById(R.id.nom_rdv);
        dateEdit = (EditText) findViewById(R.id.editDate);
        heureDebutEdit = (EditText) findViewById(R.id.editHeureDebut);
        heureFinEdit = (EditText) findViewById(R.id.editHeureFin);
        lieuEdit = (EditText) findViewById(R.id.lieu_rdv);
        normal = (RadioButton) findViewById(R.id.radio_normal);
        silencieux = (RadioButton) findViewById(R.id.radio_silencieux);
        vibreur = (RadioButton) findViewById(R.id.radio_vibreur);

        nomEdit.setText((CharSequence) ControllerListerRDV.getValeurSelectionnee().getNom());
        dateEdit.setText((CharSequence) ControllerListerRDV.getValeurSelectionnee().getDateString());
        heureDebutEdit.setText((CharSequence) utilitaireDate.convertirLongDateString(ControllerListerRDV.getValeurSelectionnee().getDatedebut(), "HH:mm"));
        heureFinEdit.setText((CharSequence) utilitaireDate.convertirLongDateString(ControllerListerRDV.getValeurSelectionnee().getDatefin(), "HH:mm"));
        lieuEdit.setText((CharSequence) ControllerListerRDV.getValeurSelectionnee().getLieu());

        if (ControllerListerRDV.getValeurSelectionnee().getMode() == 0) {
            normal.setChecked(true);
            silencieux.setChecked(false);
            vibreur.setChecked(false);
        } else if (ControllerListerRDV.getValeurSelectionnee().getMode() == 1) {
            normal.setChecked(false);
            silencieux.setChecked(true);
            vibreur.setChecked(false);
        } else if (ControllerListerRDV.getValeurSelectionnee().getMode() == 2) {
            normal.setChecked(false);
            silencieux.setChecked(false);
            vibreur.setChecked(true);
        }
    }


    public void modifierRDV(View view) throws ParseException {

        this.daordv = new DAORDV(this);
        String nom = nomEdit.getText().toString();
        String date = dateEdit.getText().toString();
        String heuredebut = heureDebutEdit.getText().toString();
        String heurefin = heureFinEdit.getText().toString();
        String lieu = lieuEdit.getText().toString();
        long mode = 0;

        if (this.verifierDate()) {

            if (this.verifierHeure(heureDebutEdit) && this.verifierHeure(heureFinEdit)) {

                if (!nom.equals("")) {

                    if (!lieu.equals("")) {

                        if (normal.isChecked()) {
                            mode = 0;
                        } else if (silencieux.isChecked()) {
                            mode = 1;
                        } else if (vibreur.isChecked()) {
                            mode = 2;
                        }

                        utilitaireDate = new UtilitaireDate();
                        String dateRDVdebut = date + "-" + heuredebut;
                        long dateDebutLong = utilitaireDate.convertirStringDateEnLong(dateRDVdebut);
                        String dateRDVfin = date + "-" + heurefin;
                        long dateFinLong = utilitaireDate.convertirStringDateEnLong(dateRDVfin);

                        int update = this.daordv.updateRDV(ControllerListerRDV.getValeurSelectionnee(), nom, dateDebutLong, dateFinLong, date, lieu, mode);

                        if (update == 0) {
                            Toast.makeText(getApplicationContext(), getResources().getString(R.string.rdv_modifie, ControllerListerRDV.getValeurSelectionnee().getNom()), Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(ControllerModifierRDV.this, ControllerListerRDV.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(getApplicationContext(), R.string.erreur_modifiction_rdv, Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(this, getResources().getString(R.string.champs_vide, "lieu"), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(this, getResources().getString(R.string.champs_vide, "nom"), Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, getResources().getString(R.string.erreur_format, "HH:MM"), Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, getResources().getString(R.string.erreur_format, "MM/DD/YYYY"), Toast.LENGTH_SHORT).show();
        }
    }


    public boolean verifierHeure(EditText heure) {
        ValidatorHeure validatorHeure = new ValidatorHeure();
        return validatorHeure.validate(heure.getText().toString());
    }


    public void ajouterHeureDebut(View v) {

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
                        heureDebutEdit.setText(hourString + ":" + minuteString);
                    }
                }, mHour, mMinute, true);
        tpd.show();
    }

    public void ajouterHeureFin(View v) {

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
                        heureFinEdit.setText(hourString + ":" + minuteString);
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

    public boolean verifierDate() {
        ValidatorDate validatorDate = new ValidatorDate();
        return validatorDate.validate(dateEdit.getText().toString());
    }

}

