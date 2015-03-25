package com.remindus.projetcommun.remindus.controller;

import android.app.AlertDialog;
import android.app.Dialog;
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

    private static ModelRDV valeurSelectionnee;
    private DAORDV daordv;
    private ListView l;

    public static ModelRDV getValeurSelectionnee() {
        return valeurSelectionnee;
    }

    public static void setValeurSelectionnee(ModelRDV valeurSelectionnee) {
        ControllerListerRDV.valeurSelectionnee = valeurSelectionnee;
    }

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

    public void listerRDV() {
        daordv = new DAORDV(this);
        daordv.getCrud().open();

        final List<ModelRDV> values = daordv.getAllRDV();
        l = (ListView) findViewById(R.id.afficherRDV);

        ArrayAdapter<ModelRDV> adapter = new ArrayAdapter<ModelRDV>(this,
                android.R.layout.simple_list_item_1, values);
        l.setAdapter(adapter);

        l.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                valeurSelectionnee = (ModelRDV) l.getAdapter().getItem(position);
                ControllerListerRDV.setValeurSelectionnee(valeurSelectionnee);
                AlertDialog alertDialog = new AlertDialog.Builder(ControllerListerRDV.this).create();
                alertDialog.setTitle(valeurSelectionnee.getNom());
                alertDialog.setButton(Dialog.BUTTON1, "Modifier", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(ControllerListerRDV.this, ControllerModifierRDV.class);
                        startActivity(intent);
                    }
                });
                alertDialog.setButton(Dialog.BUTTON2, "Supprimer", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        supprimerRDV(null);
                    }
                });
                alertDialog.setButton(Dialog.BUTTON3, "Contacts", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(ControllerListerRDV.this, ControllerListerRDVContact.class);
                        startActivity(intent);
                    }
                });
                alertDialog.show();

                return false;
            }
        });
    }

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

    public void redirectionCreerRDV(View view) {
        Intent intent = new Intent(ControllerListerRDV.this, ControllerCreerRDV.class);
        startActivity(intent);
    }


    public void supprimerEventCalendrier(ModelRDV rdv) {
        String[] tab_date = rdv.getDateString().split("/");
        int annee = Integer.parseInt(tab_date[2]);
        int mois = Integer.parseInt(tab_date[1]);
        int jour = Integer.parseInt(tab_date[0]);
        System.out.println("année: " + annee + " mois: " + mois + " jour: " + jour);

        UtilitaireDate util_date = new UtilitaireDate();
        String h = util_date.convertirLongDateString(rdv.getDatedebut(),"HH:mm:ss");
        System.out.println(h);
        String[] tab_heure = h.split(":");

        int heure = Integer.parseInt(tab_heure[0]);
        int min = Integer.parseInt(tab_heure[1]);

        System.out.println("heure: "+ heure + " minute: "+ min);
        //Calendar cal = Calendar.getInstance();
        //cal.set(annee, mois-1, jour, heure, min);
        //cal.set(2015, 2, 2, 18, 30);
        // Defines selection criteria for the rows you want to delete
        String mSelectionClause = CalendarProvider.LOCATION + " = ? AND "+ CalendarProvider.EVENT +" = ? AND "+CalendarProvider.START + " = ?";
        //String time = String.valueOf(cal.getTimeInMillis());
        String[] mSelectionArgs = {rdv.getLieu(), rdv.getNom(), String.valueOf(rdv.getDatedebut())};

        // Defines a variable to contain the number of rows deleted
        int mRowsDeleted = 0;

        // Deletes the words that match the selection criteria
        mRowsDeleted = getContentResolver().delete(
                CalendarProvider.CONTENT_URI,   // the user dictionary content URI
                mSelectionClause,                    // the column to select on
                mSelectionArgs                      // the value to compare to
        );
        System.out.println("rows deleted :"+mRowsDeleted);
    }
}