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
 * Classe qui permet d'afficher un calendrier pour les RDV
 */
public class ControllerCalendrier extends ControllerHeader {

    private ExtendedCalendarView calendar = null;
    private DAORDV daordv;
    private long v_date;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.vue_afficher_calendrier);
        //Initialisation du calendrier
        calendar = (ExtendedCalendarView) findViewById(R.id.calendar);
        //Methode lorsque l'on clique sur un jour du calendrier
        calendar.setOnDayClickListener(new ExtendedCalendarView.OnDayClickListener() {
            @Override
            public void onDayClicked(AdapterView<?> adapter, View view, int position,
                                     long id, Day day) {
                //Affichage d'une popup
                AlertDialog alertDialog = new AlertDialog.Builder(ControllerCalendrier.this).create();
                //Affichage de la date en titre de la popup
                String date = day.getDay() + "/" + (day.getMonth() + 1) + "/" + day.getYear();
                alertDialog.setTitle(date);
                //On liste les RDV selon la date sélectionnée
                alertDialog.setMessage(listeRDV(date));
                //Ajout d'un bouton "Ajouter un RDV" et redirection vers le formulaire
                alertDialog.setButton(Dialog.BUTTON_NEGATIVE, "Ajouter un RDV", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(ControllerCalendrier.this, ControllerCreerRDV.class);
                        startActivity(intent);
                    }
                });
                //Ajout d'un bouton "OK" qui ferme la popup
                alertDialog.setButton(Dialog.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                alertDialog.show();
            }
        });

    }


    /*
    * @param date
    * @return String
    * Methode qui permet de lister les RDV lorsque l'on appuie sur un jour du calendrier
     */
    public String listeRDV(String date) {
        UtilitaireDate utilitaire_date = new UtilitaireDate();
        String s_date = "";

        daordv = new DAORDV(this);
        daordv.getCrud().open(); //On ouvre la connexion à la base de données

        //On récupère tous les RDV de la base de données
        List<ModelRDV> values = daordv.getAllRDV();
        if (values != null) {
            //On parcourt la liste
            for (int i = 0; i < values.size(); i++) {
                //Si la date parcourue correspond à la date selectionnée
                if (date.equals(values.get(i).getDateString())) {
                    //On garde en mémoire
                    s_date += "RDV " + values.get(i).getNom() + " à "
                            + values.get(i).getLieu()
                            + " de " + utilitaire_date.convertirLongDateString(
                            values.get(i).getDatedebut(), "dd/MM/yy à HH:mm") + "\n";
                }
            }
        }
        if (s_date.equals("")) { //Si malgré le parcours, aucune date de correspond
            s_date = "Pas de RDV ce jour";
        }
        daordv.getCrud().close(); //On ferme la connexion à la base de données
        return s_date;
    }
}
