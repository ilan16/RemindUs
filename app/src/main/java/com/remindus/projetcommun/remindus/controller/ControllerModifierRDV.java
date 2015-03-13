package com.remindus.projetcommun.remindus.controller;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
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

import java.util.Calendar;

/**
 * Created by ilanmalka on 12/03/15.
 */
public class ControllerModifierRDV  extends ControllerHeader {

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
        setContentView(R.layout.vue_modifier_groupe);
        nomEdit = (EditText) findViewById(R.id.nom_rdv);
        dateEdit = (EditText) findViewById(R.id.editDate);
        heureEdit = (EditText) findViewById(R.id.editHeure);
        lieuEdit = (EditText) findViewById(R.id.lieu_rdv);
        normal = (RadioButton) findViewById(R.id.radio_normal);
        silencieux = (RadioButton) findViewById(R.id.radio_silencieux);
        vibreur = (RadioButton) findViewById(R.id.radio_vibreur);

        nomEdit.setText((CharSequence) ControllerListerRDV.getValeurSelectionnee().getNom());
        //dateEdit.setText((CharSequence) ControllerListerRDV.getValeurSelectionnee().get);
        lieuEdit.setText((CharSequence) ControllerListerRDV.getValeurSelectionnee().getLieu());

    }

    /*
    public void modifierGroupe(View view){
        this.nomGroupeEdit = (EditText) findViewById(R.id.nom_groupe_update);
        this.daoGroupe = new DAOGroupe(this);
        int update = this.daoGroupe.updateGroupe(ControllerListerGroupe.getValeurSelectionnee(), this.nomGroupeEdit.getText().toString());
        if(update == 0){
            Toast.makeText(getApplicationContext(), getResources().getString(R.string.groupe_modifie, ControllerListerGroupe.getValeurSelectionnee().getNomGroupe()), Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(ControllerModifierRDV.this, ControllerListerRDV.class);
            startActivity(intent);
        }else{
            Toast.makeText(getApplicationContext(), R.string.erreur_modifiction_groupe, Toast.LENGTH_SHORT).show();
        }
    }
    */

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

