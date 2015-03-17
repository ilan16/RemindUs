package com.remindus.projetcommun.remindus.controller;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.inputmethodservice.Keyboard;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.format.Time;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TimePicker;
import android.widget.Toast;

import com.remindus.projetcommun.remindus.R;
import com.remindus.projetcommun.remindus.dao.DAOContact;
import com.remindus.projetcommun.remindus.dao.DAORDV;
import com.remindus.projetcommun.remindus.model.ModelContact;
import com.tyczj.extendedcalendarview.CalendarProvider;
import com.tyczj.extendedcalendarview.Event;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

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
    private UtilitaireDate utilitaireDate;
    private ListView lv;
    private Cursor cursor1;
    private DAOContact daoContact;

    public void listerContact(){
        daoContact = new DAOContact(this);
        daoContact.getCrud().open();

        final List<ModelContact> values = daoContact.getAllContacts();
        lv = (ListView) findViewById(R.id.sampleList);

        CustomAdapterContact adapter = new CustomAdapterContact(this, values);
        lv.setAdapter(adapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.i("Valeur sélectionnée", "" + lv.getId());
            }
        });

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vue_creer_rdv);

        nomEdit = (EditText) findViewById(R.id.nom_rdv);
        dateEdit = (EditText) findViewById(R.id.editDate);
        heureEdit = (EditText) findViewById(R.id.editHeure);
        lieuEdit = (EditText) findViewById(R.id.lieu_rdv);
        normal = (RadioButton) findViewById(R.id.radio_normal);
        silencieux = (RadioButton) findViewById(R.id.radio_silencieux);
        vibreur = (RadioButton) findViewById(R.id.radio_vibreur);

        this.listerContact();
    }


    public void creerRDV(View view) throws ParseException {

        ArrayList<String> contacts = new ArrayList<String>();

        lv = (ListView) findViewById(R.id.sampleList);

        SparseBooleanArray checked = lv.getCheckedItemPositions();

        for (int i = 0; i < lv.getCount(); i++) {
            if (checked.get(i)) {
                String text = lv.getItemAtPosition(i).toString();
                contacts.add(text);
                Log.i("LIST", ""+text);

            }
        }

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

        daordv = new DAORDV(this);

        int insert = daordv.insertRDV(nom, dateLong, date,lieu, mode);

        if (insert == 0) {
            ajouterEventCalendrier(date, heure);
            Intent intent = new Intent(ControllerCreerRDV.this, ControllerListerRDV.class);
            startActivity(intent);
        }else {
            Toast.makeText(this, R.string.erreur_insertion_rdv, Toast.LENGTH_SHORT);
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

    public void ajouterEventCalendrier(String date, String h){
        ContentValues values = new ContentValues();
        values.put(CalendarProvider.COLOR, Event.COLOR_RED);
        values.put(CalendarProvider.DESCRIPTION, "");
        values.put(CalendarProvider.LOCATION, "");
        values.put(CalendarProvider.EVENT, "RDV");

        Calendar cal = Calendar.getInstance();

        String[] tab_date = date.split("/");
        int annee = Integer.parseInt(tab_date[2]);
        int mois = Integer.parseInt(tab_date[1]);
        int jour = Integer.parseInt(tab_date[0]);
        System.out.println("année: "+ annee + " mois: "+ mois + " jour: "+jour);

        String[] tab_heure = h.split(":");

        int heure = Integer.parseInt(tab_heure[0]);
        int min = Integer.parseInt(tab_heure[1]);
        System.out.println("heure: "+ heure + " minute: "+ min);

        cal.set(annee, mois-1, jour, heure, min);
        values.put(CalendarProvider.START, cal.getTimeInMillis());

        TimeZone tz = TimeZone.getDefault();

        int endDayJulian = Time.getJulianDay(cal.getTimeInMillis(), TimeUnit.MILLISECONDS.toSeconds(tz.getOffset(cal.getTimeInMillis())));

        values.put(CalendarProvider.START_DAY, endDayJulian);
        cal.set(annee, mois-1, jour, heure, min);
        values.put(CalendarProvider.END, cal.getTimeInMillis());
        values.put(CalendarProvider.END_DAY, endDayJulian);

        Uri uri = getContentResolver().insert(CalendarProvider.CONTENT_URI, values);

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
