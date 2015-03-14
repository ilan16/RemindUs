package com.remindus.projetcommun.remindus.controller;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TimePicker;
import android.widget.Toast;

import com.remindus.projetcommun.remindus.R;
import com.remindus.projetcommun.remindus.dao.DAOGroupe;
import com.remindus.projetcommun.remindus.dao.DAORDV;

import java.text.ParseException;
import java.util.Calendar;

/**
 * Created by ilanmalka on 12/03/15.
 */
public class ControllerModifierRDV extends ControllerHeader {

    private EditText nomEdit, dateEdit, heureEdit, lieuEdit;
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
        heureEdit = (EditText) findViewById(R.id.editHeure);
        lieuEdit = (EditText) findViewById(R.id.lieu_rdv);
        normal = (RadioButton) findViewById(R.id.radio_normal);
        silencieux = (RadioButton) findViewById(R.id.radio_silencieux);
        vibreur = (RadioButton) findViewById(R.id.radio_vibreur);

        nomEdit.setText((CharSequence) ControllerListerRDV.getValeurSelectionnee().getNom());
        dateEdit.setText((CharSequence) ControllerListerRDV.getValeurSelectionnee().getDateString());
        heureEdit.setText((CharSequence) utilitaireDate.convertirLongDateString(ControllerListerRDV.getValeurSelectionnee().getDate(), "HH:mm"));
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
        String heure = heureEdit.getText().toString();
        String lieu = lieuEdit.getText().toString();
        long mode = 0;

        if (normal.isChecked()) {
            mode = 0;
        } else if (silencieux.isChecked()) {
            mode = 1;
        } else if (vibreur.isChecked()) {
            mode = 2;
        }

        String dateRDV = date + "-" + heure;
        utilitaireDate = new UtilitaireDate();
        long dateLong = utilitaireDate.convertirStringDateEnLong(dateRDV);

        int update = this.daordv.updateRDV(ControllerListerRDV.getValeurSelectionnee(), nom, dateLong, date, lieu, mode);

        if(update == 0){
            Toast.makeText(getApplicationContext(), getResources().getString(R.string.rdv_modifie, ControllerListerRDV.getValeurSelectionnee().getNom()), Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(ControllerModifierRDV.this, ControllerListerRDV.class);
            startActivity(intent);
        }else{
            Toast.makeText(getApplicationContext(), R.string.erreur_modifiction_rdv, Toast.LENGTH_SHORT).show();
        }
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
                Intent intent = new Intent(ControllerModifierRDV.this, ControllerParametre.class);
                startActivity(intent);
                break;

        }

        return false;
    }

}

