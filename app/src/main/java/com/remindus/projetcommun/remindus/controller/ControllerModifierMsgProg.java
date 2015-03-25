package com.remindus.projetcommun.remindus.controller;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TimePicker;
import android.widget.Toast;

import com.remindus.projetcommun.remindus.R;
import com.remindus.projetcommun.remindus.dao.DAOModelMsg;
import com.remindus.projetcommun.remindus.dao.DAOMsgProg;

import java.text.ParseException;
import java.util.Calendar;

/**
 * Created by ilanmalka on 18/03/15.
 */
public class ControllerModifierMsgProg extends ControllerHeader {

    private ImageButton buttonDate, buttonHeure;
    private EditText editDate, editHeure, editTitre, editContenu;
    private UtilitaireDate utilitaireDate;
    private DAOMsgProg daoMsgProg;
    // Variable for storing current date and time
    private int mYear, mMonth, mDay, mHour, mMinute;

    /**
     *
     * @param savedInstanceState
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vue_modifier_msg_prog);

        utilitaireDate = new UtilitaireDate();

        buttonDate = (ImageButton) findViewById(R.id.date);
        buttonHeure = (ImageButton) findViewById(R.id.heure);

        editDate = (EditText) findViewById(R.id.editDate);
        editHeure = (EditText) findViewById(R.id.editHeure);
        editTitre = (EditText) findViewById(R.id.titre_msg_prog);
        editContenu = (EditText) findViewById(R.id.message_msg_prog);
        //permet de remplir l'intégralité des champs
        editTitre.setText((CharSequence) ControllerListerMsgProg.getValeurSelectionnee().getTitre());
        editContenu.setText((CharSequence) ControllerListerMsgProg.getValeurSelectionnee().getMsgProg());
        editDate.setText((CharSequence) ControllerListerMsgProg.getValeurSelectionnee().getDatestring());
        editHeure.setText((CharSequence) utilitaireDate.convertirLongDateString(ControllerListerMsgProg.getValeurSelectionnee().getDate(), "HH:mm"));
    }

    /**
     * permet de modifier un msg prg (il appelle les DAO qu'il faut...)
     * @param view
     * @throws ParseException
     */
    public void modifierMsgProg(View view) throws ParseException {
        this.daoMsgProg = new DAOMsgProg(this);

        String titre = editTitre.getText().toString();
        String date = editDate.getText().toString();
        String heure = editHeure.getText().toString();
        String message = editContenu.getText().toString();

        String dateMSG = date + "-" + heure;
        utilitaireDate = new UtilitaireDate();
        long dateLong = utilitaireDate.convertirStringDateEnLong(dateMSG);

        int update = this.daoMsgProg.updateMsgProg(ControllerListerMsgProg.getValeurSelectionnee(), titre, dateLong, date, message);
        Log.i("UPDATE MM:", "" + update + "");
        if (update == 0) {
            Toast.makeText(getApplicationContext(), getResources().getString(R.string.model_msg_modifie, ControllerListerMsgProg.getValeurSelectionnee().getTitre()), Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getApplicationContext(), ControllerListerMsgProg.class);
            startActivity(intent);
        } else {
            Toast.makeText(getApplicationContext(), R.string.erreur_modifiction_model_msg, Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * permet d'ajouter l'heure du msg prog avec heurePicker
     * @param v
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

    /**
     * permet d'ajouter la date avec datePicker
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
                        editDate.setText(dayOfMonth + "/"
                                + (monthOfYear + 1) + "/" + year);

                    }
                }, mYear, mMonth, mDay);
        dpd.show();
    }

}
