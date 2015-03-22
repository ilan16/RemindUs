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
import com.remindus.projetcommun.remindus.dao.DAOMsgProg;
import com.remindus.projetcommun.remindus.dao.DAOMsgProgxContacts;
import com.remindus.projetcommun.remindus.model.ModelContact;
import com.remindus.projetcommun.remindus.model.ModelMsgProgxContacts;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ilanmalka on 22/03/15.
 */
public class ControllerListerMsgProgContact extends ControllerHeader {

    private DAOMsgProgxContacts daoMsgProgxContacts;
    private static ModelContact modelContact;
    private ListView l;

    public static ModelContact getModelContact() {
        return modelContact;
    }

    public static void setModelContact(ModelContact modelContact) {
        ControllerListerMsgProgContact.modelContact = modelContact;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vue_liste_contact);

        this.lister();
    }

    public void lister() {
        daoMsgProgxContacts = new DAOMsgProgxContacts(this);
        daoMsgProgxContacts.getCrud().open();

        final List<ModelMsgProgxContacts> values = daoMsgProgxContacts.getAllMsgProgxC(ControllerListerMsgProg.getValeurSelectionnee().getId());
        l = (ListView) findViewById(R.id.sampleList);

        List affiche = new ArrayList();

        final DAOMsgProg daoMsgProg = new DAOMsgProg(this);
        DAOContact daoContact = new DAOContact(this);

        for (ModelMsgProgxContacts m : values) {
            ModelContact modelContact = daoContact.getContact(m.getIdContact());
            String tel = modelContact.getTelephone();
            String nom = modelContact.getContact();
            affiche.add(nom + "\n" + tel);
        }


        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, affiche);
        l.setAdapter(adapter);

        l.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Log.i("test", ""+ l.getAdapter().getItem(position));
                String[] split = l.getAdapter().getItem(position).toString().split("\n");
                DAOContact daoContact = new DAOContact(getBaseContext());
                ControllerListerMsgProgContact.setModelContact(daoContact.getContact(split[1]));
                AlertDialog alertDialog = new AlertDialog.Builder(ControllerListerMsgProgContact.this).create();
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

      public void supprimer(View view){
        ArrayAdapter<ModelContact> adapter = (ArrayAdapter<ModelContact>) l.getAdapter();
        boolean delete = daoMsgProgxContacts.deleteMsgProgxC(ControllerListerMsgProgContact.getModelContact().getId());
        if(delete){
            Toast.makeText(getApplicationContext(), getResources().getString(R.string.rdv_supprime, getModelContact().getContact()), Toast.LENGTH_SHORT).show();
        }
        adapter.remove(getModelContact());
        adapter.notifyDataSetChanged();
    }

    public void redirection(View view){
        Intent intent = new Intent(ControllerListerMsgProgContact.this, ControllerContact.class);
        startActivity(intent);
    }
}