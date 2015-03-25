package com.remindus.projetcommun.remindus.controller;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.remindus.projetcommun.remindus.R;
import com.remindus.projetcommun.remindus.Service.DeclencheurModeService;
import com.remindus.projetcommun.remindus.dao.DAORDV;
import com.remindus.projetcommun.remindus.model.ModelRDV;
import com.tyczj.extendedcalendarview.CalendarProvider;

import java.util.List;

/**
 * Created by ilanmalka on 12/03/15.
 */
public class ControllerListerRDV extends ControllerHeader {

    private static ModelRDV valeurSelectionnee; //permettra de récupérer les valeurs à delete ou à update
    // c'est en static car on en a besoin pour garder le contenu de cette variable lorsquon changera de classe
    private DAORDV daordv;
    private ListView l;

    /**
     *
     * @return
     */
    public static ModelRDV getValeurSelectionnee() {
        return valeurSelectionnee;
    }

    /**
     *
     * @param valeurSelectionnee
     */
    public static void setValeurSelectionnee(ModelRDV valeurSelectionnee) {
        ControllerListerRDV.valeurSelectionnee = valeurSelectionnee;
    }

    /**
     *
     * @param savedInstanceState
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vue_liste_rdv);
        //debut du service declencheurMode pour savoir quand changer le mode
        Intent myIntent2 = new Intent(ControllerListerRDV.this, DeclencheurModeService.class);
        ControllerListerRDV.this.startService(myIntent2);

        // on remet titre msg prg vide dans le cas ou l'utilisateur veut creer un rdv et un msg prog dans la mm session
        ControllerCreerMsgProg.setTitreMsgProgStatic("");
        ControllerListerMsgProg.setValeurSelectionnee(null);

        this.listerRDV();
    }

    /**
     * permet de lister l'ensemble des rdv
     */
    public void listerRDV() {
        daordv = new DAORDV(this);
        daordv.getCrud().open();

        final List<ModelRDV> values = daordv.getAllRDV();
        l = (ListView) findViewById(R.id.afficherRDV);

        final ArrayAdapter<ModelRDV> adapter = new ArrayAdapter<ModelRDV>(this,
                android.R.layout.simple_list_item_1, values);
        l.setAdapter(adapter);

        // qd l'utilisateur reste longtemps appuié, une boite dialogue s'ouvre
        l.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                valeurSelectionnee = (ModelRDV) l.getAdapter().getItem(position);
                ControllerListerRDV.setValeurSelectionnee(valeurSelectionnee);


        final String[] option = {getResources().getString(R.string.dialogue_modifier),getResources().getString(R.string.dialogue_contacts), getResources().getString(R.string.dialogue_supprimer)};

        AlertDialog.Builder myDialog =new AlertDialog.Builder(ControllerListerRDV.this);
        myDialog.setTitle(valeurSelectionnee.getNom());
        myDialog.setItems(option, new DialogInterface.OnClickListener(){

            @Override
            public void onClick(DialogInterface dialog, int which) {

                switch (which){
                    case 0:
                        Intent intent = new Intent(ControllerListerRDV.this, ControllerModifierRDV.class);
                        startActivity(intent);
                        break;
                    case 1:
                        supprimerRDV(null);
                        break;
                    case 2:
                        Intent intent2 = new Intent(ControllerListerRDV.this, ControllerListerRDVContact.class);
                        startActivity(intent2);
                        break;
                }


            }});

        myDialog.setNegativeButton("Cancel", null);

        myDialog.show();

                return false;
            }
        });
    }

    /**
     * peremet de supprimer un rdv
     * @param view
     */
    public void supprimerRDV(View view) {
        ArrayAdapter<ModelRDV> adapter = (ArrayAdapter<ModelRDV>) l.getAdapter();
        boolean delete = daordv.deleteRDV(valeurSelectionnee);
        if (delete) {
            supprimerEventCalendrier(valeurSelectionnee);
            Toast.makeText(getApplicationContext(), getResources().getString(R.string.rdv_supprime, valeurSelectionnee.getNom()), Toast.LENGTH_SHORT).show();
        }
        Log.i("RDV A DELETE", "" + valeurSelectionnee + "");
        adapter.remove(valeurSelectionnee);
        adapter.notifyDataSetChanged();
    }

    /**
     * permet de faire une redirection vers la page créer un rdv
     * @param view
     */
    public void redirectionCreerRDV(View view) {
        Intent intent = new Intent(ControllerListerRDV.this, ControllerCreerRDV.class);
        startActivity(intent);
    }


    /*
    * @param rdv
    * Méthode qui permet de supprimer un rdv du calendrier
    * */
    public void supprimerEventCalendrier(ModelRDV rdv) {
        String[] tab_date = rdv.getDateString().split("/");
        int annee = Integer.parseInt(tab_date[2]);
        int mois = Integer.parseInt(tab_date[1]);
        int jour = Integer.parseInt(tab_date[0]);

        UtilitaireDate util_date = new UtilitaireDate();
        String h = util_date.convertirLongDateString(rdv.getDatedebut(),"HH:mm:ss");
        System.out.println(h);
        String[] tab_heure = h.split(":");

        int heure = Integer.parseInt(tab_heure[0]);
        int min = Integer.parseInt(tab_heure[1]);


        // Définit les critères de selection pour les lignes que l'on souhaite supprimer
        String mSelectionClause = CalendarProvider.LOCATION + " = ? AND "+ CalendarProvider.EVENT +" = ? AND "+CalendarProvider.START + " = ?";
        //Les parametres de la requête
        String[] mSelectionArgs = {rdv.getLieu(), rdv.getNom(), String.valueOf(rdv.getDatedebut())};

        // Variable qui montrera le  nombre de ligne supprimer
        int mRowsDeleted = 0;

        // Execution de la requête
        mRowsDeleted = getContentResolver().delete(
                CalendarProvider.CONTENT_URI,   //la table
                mSelectionClause,
                mSelectionArgs
        );
        System.out.println("rows deleted :"+mRowsDeleted);
    }
}