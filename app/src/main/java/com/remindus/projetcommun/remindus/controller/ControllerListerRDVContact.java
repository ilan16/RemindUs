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
import com.remindus.projetcommun.remindus.dao.DAOContact;
import com.remindus.projetcommun.remindus.dao.DAORDV;
import com.remindus.projetcommun.remindus.dao.DAORDVxContacts;
import com.remindus.projetcommun.remindus.model.ModelContact;
import com.remindus.projetcommun.remindus.model.ModelRDV;
import com.remindus.projetcommun.remindus.model.ModelRDVxContacts;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ilanmalka on 19/03/15.
 */
public class ControllerListerRDVContact extends ControllerHeader {

    private static ModelRDV valeurSelectionnee;
    private static ModelContact modelContact;
    private DAORDVxContacts daordVxContacts;
    private ListView l;

    /**
     *
     * @return
     */
    public static ModelContact getModelContact() {
        return modelContact;
    }

    /**
     *
     * @param modelContact
     */
    public static void setModelContact(ModelContact modelContact) {
        ControllerListerRDVContact.modelContact = modelContact;
    }

    /**
     *
     * @param savedInstanceState
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vue_liste_contact);

        this.listerRDVContact();
    }

    /**
     * permet de lister les contacts d'un rdv
     */
    public void listerRDVContact() {
        daordVxContacts = new DAORDVxContacts(this);
        daordVxContacts.getCrud().open();

        DAORDV daordv = new DAORDV(this);
        List<ModelRDVxContacts> values = null;
        Log.i("lrdvc", ""+ControllerListerRDV.getValeurSelectionnee().getNom());
        if (!ControllerListerRDV.getValeurSelectionnee().getNom().equals("")) {
            values = daordVxContacts.getAllRDVxC(ControllerListerRDV.getValeurSelectionnee().getId());
        } else if (!ControllerCreerRDV.getNomRDVstatic().equals("")) {
            values = daordVxContacts.getAllRDVxC(daordv.getIdRDV(ControllerCreerRDV.getNomRDVstatic()).getId());
        }
        l = (ListView) findViewById(R.id.sampleList);

        List affiche = new ArrayList();

        DAOContact daoContact = new DAOContact(this);

        for (ModelRDVxContacts m : values) {
            ModelContact modelContact = daoContact.getContact(m.getIdcontact());
            String tel = modelContact.getTelephone();
            String nom = modelContact.getContact();
            affiche.add(nom + "\n" + tel);
        }


        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, affiche);
        l.setAdapter(adapter);

        l.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Log.i("test", "" + l.getAdapter().getItem(position));
                String[] split = l.getAdapter().getItem(position).toString().split("\n");
                DAOContact daoContact = new DAOContact(getBaseContext());
                ControllerListerRDVContact.setModelContact(daoContact.getContact(split[1]));
                AlertDialog alertDialog = new AlertDialog.Builder(ControllerListerRDVContact.this).create();
                alertDialog.setButton(Dialog.BUTTON2, "Supprimer", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        supprimer(null);
                    }
                });

                alertDialog.show();

                return false;
            }
        });
    }

    /**
     * permet de supprimer un contact d'un rdv
     * @param view
     */
    public void supprimer(View view){
        ArrayAdapter<ModelContact> adapter = (ArrayAdapter<ModelContact>) l.getAdapter();
        boolean delete = daordVxContacts.deleteRDVxC(ControllerListerRDVContact.getModelContact().getId());
        if(delete){
            Toast.makeText(getApplicationContext(), getResources().getString(R.string.rdv_supprime, getModelContact().getContact()), Toast.LENGTH_SHORT).show();
        }
        adapter.remove(getModelContact());
        adapter.notifyDataSetChanged();
    }

    /**
     * permet de faire une redirection vers la page des contacts
     * @param view
     */
    public void redirection(View view){
        Intent intent = new Intent(ControllerListerRDVContact.this, ControllerContact.class);
        startActivity(intent);
    }

}