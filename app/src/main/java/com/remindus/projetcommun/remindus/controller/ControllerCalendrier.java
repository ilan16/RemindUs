package com.remindus.projetcommun.remindus.controller;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import com.remindus.projetcommun.remindus.R;
import com.remindus.projetcommun.remindus.dao.DAORDV;
import com.remindus.projetcommun.remindus.model.ModelRDV;
import com.tyczj.extendedcalendarview.Day;
import com.tyczj.extendedcalendarview.ExtendedCalendarView;

import java.util.List;

/**
 * Created by bahia on 23/02/2015.
 */
public class ControllerCalendrier extends ControllerHeader /*implements CalendarView.OnDateChangeListener*/ {

    private ExtendedCalendarView calendar = null;
    private DAORDV daordv;
    private long v_date;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.vue_afficher_calendrier);
        calendar = (ExtendedCalendarView) findViewById(R.id.calendar);
        calendar.setOnDayClickListener(new ExtendedCalendarView.OnDayClickListener() {
            @Override
            public void onDayClicked(AdapterView<?> adapter, View view, int position,
                                     long id, Day day) {
                AlertDialog alertDialog = new AlertDialog.Builder(ControllerCalendrier.this).create();
                String date = day.getDay() + "/" + (day.getMonth() + 1) + "/" + day.getYear();
                alertDialog.setTitle(date);
                alertDialog.setMessage(listeRDV(date));
                alertDialog.setButton(Dialog.BUTTON_NEGATIVE, "Ajouter un RDV", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(ControllerCalendrier.this, ControllerCreerRDV.class);
                        startActivity(intent);
                    }
                });
                alertDialog.setButton(Dialog.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                alertDialog.show();
            }
        });

    }



    public String listeRDV(String date) {
        UtilitaireDate utilitaire_date = new UtilitaireDate();
        String s_date = "";

        daordv = new DAORDV(this);
        daordv.getCrud().open();

        List<ModelRDV> values = daordv.getAllRDV();
        if (values != null) {
            for (int i = 0; i < values.size(); i++) {
                if (date.equals(values.get(i).getDateString())) {
                    s_date += "RDV " + values.get(i).getNom() + " à "
                            + values.get(i).getLieu()
                            + " de " + utilitaire_date.convertirLongDateString(
                            values.get(i).getDatedebut(), "dd/MM/yy à HH:mm") + "\n";
                }
            }
        }
        if (s_date.equals("")) {
            s_date = "Pas de RDV ce jour";
        }
        daordv.getCrud().close();
        return s_date;
    }
}
