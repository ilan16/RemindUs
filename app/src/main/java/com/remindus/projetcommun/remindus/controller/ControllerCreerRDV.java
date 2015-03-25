package com.remindus.projetcommun.remindus.controller;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.text.format.Time;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.remindus.projetcommun.remindus.R;
import com.remindus.projetcommun.remindus.controller.validator.ValidatorDate;
import com.remindus.projetcommun.remindus.controller.validator.ValidatorHeure;
import com.remindus.projetcommun.remindus.dao.DAOContact;
import com.remindus.projetcommun.remindus.dao.DAORDV;
import com.remindus.projetcommun.remindus.dao.DAORDVxContacts;
import com.remindus.projetcommun.remindus.model.ModelContact;
import com.remindus.projetcommun.remindus.model.ModelRDV;
import com.tyczj.extendedcalendarview.CalendarProvider;
import com.tyczj.extendedcalendarview.Event;

import java.text.ParseException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

/**
 * Created by bahia on 23/02/2015.
 */
public class ControllerCreerRDV extends ControllerHeader {

    private static String nomRDVstatic;
    private EditText nomEdit;
    private EditText dateEdit;
    private EditText heureDebutEdit, heureFinEdit;
    private EditText lieuEdit;
    private RadioButton normal, silencieux, vibreur;
    private DAORDV daordv;
    private DAORDVxContacts daordVxContacts;
    private int mYear, mMonth, mDay, mHour, mMinute;
    private UtilitaireDate utilitaireDate;
    private ListView lv;
    private Cursor cursor1;
    private DAOContact daoContact;
    private CustomAdapterContact adapter = null;

    public static String getNomRDVstatic() {
        return nomRDVstatic;
    }

    public static void setNomRDVstatic(String nomRDVstatic) {
        ControllerCreerRDV.nomRDVstatic = nomRDVstatic;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vue_creer_rdv);

        nomEdit = (EditText) findViewById(R.id.nom_rdv);
        dateEdit = (EditText) findViewById(R.id.editDate);
        heureDebutEdit = (EditText) findViewById(R.id.editHeureDebut);
        heureFinEdit = (EditText) findViewById(R.id.editHeureFin);
        lieuEdit = (EditText) findViewById(R.id.lieu_rdv);
        normal = (RadioButton) findViewById(R.id.radio_normal);
        silencieux = (RadioButton) findViewById(R.id.radio_silencieux);
        vibreur = (RadioButton) findViewById(R.id.radio_vibreur);


        // on remet titre msg prg vide dans le cas ou l'utilisateur veut creer un rdv et un msg prog dans la mm session
        ControllerCreerMsgProg.setTitreMsgProgStatic("");
        ControllerListerMsgProg.setValeurSelectionnee(null);
    }


    /**
     *
     */
    private void checkButtonClick() {

        Button myButton = (Button) findViewById(R.id.valider);

        myButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                StringBuffer responseText = new StringBuffer();
                responseText.append("Contacts selectionnés ...\n");


                final HashMap<CheckBox, TextView> values = adapter.getListChecked();

                for (HashMap.Entry<CheckBox, TextView> hash : values.entrySet()) {
                    if (hash.getKey().isChecked()) {
                        responseText.append("\n - " + hash.getValue().getText());
                        String[] split = hash.getValue().getText().toString().split("\n");
                        daoContact = new DAOContact(getBaseContext());
                        ModelContact modelContact = daoContact.getContact(split[1]);
                        Log.i("contact", "" + modelContact.getId() + " " + modelContact.getContact());

                    }
                }

                Toast.makeText(getApplicationContext(),
                        responseText, Toast.LENGTH_LONG).show();
            }
        });
    }

    /**
     *
     * @param nomRDV
     */
    public void ajouterContactRDV(String nomRDV) {
        final HashMap<CheckBox, TextView> values = adapter.getListChecked();
        for (HashMap.Entry<CheckBox, TextView> hash : values.entrySet()) {
            if (hash.getKey().isChecked()) {
                String[] split = hash.getValue().getText().toString().split("\n");
                this.daoContact = new DAOContact(this);
                ModelContact modelContact = this.daoContact.getContact(split[1]);
                Log.i("contact", "" + modelContact.getId() + " " + modelContact.getContact());
                this.daordVxContacts = new DAORDVxContacts(this);
                this.daordv = new DAORDV(this);
                ModelRDV modelRDV = new ModelRDV();
                modelRDV = this.daordv.getIdRDV(nomRDV);
                long idcontact = Long.parseLong(split[1]);
                this.daordVxContacts.insertRDVxC(modelRDV.getId(), idcontact);
            }
        }
    }

    /**
     * permet de créer un rdv
     * @param view
     * @throws ParseException
     */
    public void creerRDV(View view) throws ParseException {

        String nom = nomEdit.getText().toString();
        String date = dateEdit.getText().toString();
        String heuredebut = heureDebutEdit.getText().toString();
        String heurefin = heureFinEdit.getText().toString();
        String lieu = lieuEdit.getText().toString();
        long mode = 0;
//on vérifie l'ensemble des champs
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
                        if(dateDebutLong <= dateFinLong) {
                            daordv = new DAORDV(this);
                            int insert = daordv.insertRDV(nom, dateDebutLong, dateFinLong, date, lieu, mode);

                            if (insert == 0) {
                                ajouterEventCalendrier(date, heuredebut, lieu, nom, dateDebutLong);
                                ControllerCreerRDV.setNomRDVstatic(nom);
                                Intent intent = new Intent(ControllerCreerRDV.this, ControllerContact.class);
                                startActivity(intent);
                            } else {
                                Toast.makeText(this, R.string.erreur_insertion_rdv, Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(this,"L'heure de fin doit être supérieure à l'heure de début", Toast.LENGTH_SHORT).show();
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

    /**
     *
     * @return
     */
    public boolean verifierDate() {
        ValidatorDate validatorDate = new ValidatorDate();
        return validatorDate.validate(dateEdit.getText().toString());
    }

    /**
     *
     * @param heure
     * @return
     */
    public boolean verifierHeure(EditText heure) {
        ValidatorHeure validatorHeure = new ValidatorHeure();
        return validatorHeure.validate(heure.getText().toString());
    }

    /**
     *
     * @param v
     */
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

    /**
     *
     * @param v
     */
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

    /**
     *
     * @param view
     */
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

    /**
     * permet de rajouter un marqueur dans le calendrier
     * @param date
     * @param h
     * @param lieu
     * @param nom
     * @param dateLong
     */
    public void ajouterEventCalendrier(String date, String h, String lieu, String nom, long dateLong) {
        ContentValues values = new ContentValues();
        values.put(CalendarProvider.COLOR, Event.COLOR_RED);
        values.put(CalendarProvider.DESCRIPTION, "");
        values.put(CalendarProvider.LOCATION, lieu);
        values.put(CalendarProvider.EVENT, nom);

        Calendar cal = Calendar.getInstance();

        String[] tab_date = date.split("/");
        int annee = Integer.parseInt(tab_date[2]);
        int mois = Integer.parseInt(tab_date[1]);
        int jour = Integer.parseInt(tab_date[0]);
        System.out.println("année: " + annee + " mois: " + mois + " jour: " + jour);

        String[] tab_heure = h.split(":");

        int heure = Integer.parseInt(tab_heure[0]);
        int min = Integer.parseInt(tab_heure[1]);
        System.out.println("heure: " + heure + " minute: " + min);

        cal.set(annee, mois - 1, jour, heure, min);
        values.put(CalendarProvider.START, String.valueOf(dateLong));
        TimeZone tz = TimeZone.getDefault();

        int endDayJulian = Time.getJulianDay(cal.getTimeInMillis(), TimeUnit.MILLISECONDS.toSeconds(tz.getOffset(cal.getTimeInMillis())));

        values.put(CalendarProvider.START_DAY, endDayJulian);
        cal.set(annee, mois-1, jour, heure, min);
        values.put(CalendarProvider.END, cal.getTimeInMillis());
        values.put(CalendarProvider.END_DAY, endDayJulian);

        Uri uri = getContentResolver().insert(CalendarProvider.CONTENT_URI, values);

    }

}
