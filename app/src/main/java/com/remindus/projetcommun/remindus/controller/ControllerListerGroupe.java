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
import com.remindus.projetcommun.remindus.dao.DAOGroupe;
import com.remindus.projetcommun.remindus.model.ModelGroupe;

import java.util.List;

/**
 * Created by ilanmalka on 09/03/15.
 */
public class ControllerListerGroupe extends ControllerHeader {

    private static ModelGroupe valeurSelectionnee;
    private DAOGroupe daoGroupe;
    private ListView l;

    public static ModelGroupe getValeurSelectionnee() {
        return valeurSelectionnee;
    }

    public static void setValeurSelectionnee(ModelGroupe valeurSelectionnee) {
        ControllerListerGroupe.valeurSelectionnee = valeurSelectionnee;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vue_liste_groupe);

        this.listerGroupe();
    }

    public void listerGroupe() {
        daoGroupe = new DAOGroupe(this);
        daoGroupe.getCrud().open();

        final List<ModelGroupe> values = daoGroupe.getAllGroupe();
        l = (ListView) findViewById(R.id.sampleList);

        ArrayAdapter<ModelGroupe> adapter = new ArrayAdapter<ModelGroupe>(this,
                android.R.layout.simple_list_item_1, values);
        l.setAdapter(adapter);

        l.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                valeurSelectionnee = (ModelGroupe) l.getAdapter().getItem(position);
                ControllerListerGroupe.setValeurSelectionnee(valeurSelectionnee);
                AlertDialog alertDialog = new AlertDialog.Builder(ControllerListerGroupe.this).create();
                alertDialog.setTitle(valeurSelectionnee.getNomGroupe());
                alertDialog.setButton(Dialog.BUTTON1, "Modifier", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(ControllerListerGroupe.this, ControllerModifierGroupe.class);
                        startActivity(intent);
                    }
                });
                alertDialog.setButton(Dialog.BUTTON2, "Supprimer", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        supprimerGroupe(null);
                    }
                });
                alertDialog.show();

                Log.i("GROUPE A DELETE", "" + values.get(position).toString() + "");
                return false;
            }
        });
    }

    public void supprimerGroupe(View view) {
        ArrayAdapter<ModelGroupe> adapter = (ArrayAdapter<ModelGroupe>) l.getAdapter();
        boolean delete = daoGroupe.deleteGroupe(valeurSelectionnee, this);
        if (delete) {
            Toast.makeText(getApplicationContext(), getResources().getString(R.string.groupe_supprime, valeurSelectionnee.getNomGroupe()), Toast.LENGTH_SHORT).show();
        }
        Log.i("GROUPE A DELETE", "" + valeurSelectionnee + "");
        adapter.remove(valeurSelectionnee);
        adapter.notifyDataSetChanged();
    }

    public void redirectionGroupe(View view) {
        Intent intent = null;
        switch (view.getId()) {
            case R.id.bouton_ajouter_groupe:
                intent = new Intent(ControllerListerGroupe.this, ControllerCreerGroupe.class);
                startActivity(intent);
                break;
        }
    }


}
