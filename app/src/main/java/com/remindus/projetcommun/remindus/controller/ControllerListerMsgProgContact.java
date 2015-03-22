package com.remindus.projetcommun.remindus.controller;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.remindus.projetcommun.remindus.R;
import com.remindus.projetcommun.remindus.dao.DAOContact;
import com.remindus.projetcommun.remindus.dao.DAOMsgProg;
import com.remindus.projetcommun.remindus.dao.DAOMsgProgxContacts;
import com.remindus.projetcommun.remindus.model.ModelContact;
import com.remindus.projetcommun.remindus.model.ModelMsgProg;
import com.remindus.projetcommun.remindus.model.ModelMsgProgxContacts;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ilanmalka on 22/03/15.
 */
public class ControllerListerMsgProgContact extends ControllerHeader {

    private static ModelMsgProg valeurSelectionnee;
    private DAOMsgProgxContacts daoMsgProgxContacts;
    private ListView l;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vue_liste_contact);

        this.lister();
    }

    public void lister() {
        daoMsgProgxContacts = new DAOMsgProgxContacts(this);
        daoMsgProgxContacts.getCrud().open();

        final List<ModelMsgProgxContacts> values = daoMsgProgxContacts.getAllMsgProgxC(ControllerListerMsgProg.getValeurSelectionnee().getIdMsgProg());
        l = (ListView) findViewById(R.id.sampleList);

        List affiche = new ArrayList();

        DAOMsgProg daoMsgProg = new DAOMsgProg(this);
        DAOContact daoContact = new DAOContact(this);

        for (ModelMsgProgxContacts m : values) {
            ModelContact modelContact = daoContact.getContact(m.getIdContact());
            String tel = modelContact.getTelephone();
            String nom = modelContact.getContact();
            affiche.add(nom + "\n" + tel);
        }


        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, affiche);
        l.setAdapter(adapter);

        /*l.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                valeurSelectionnee = (ModelRDV) l.getAdapter().getItem(position);
                ControllerListerRDV.setValeurSelectionnee(valeurSelectionnee);
                AlertDialog alertDialog = new AlertDialog.Builder(ControllerListerRDVContact.this).create();
                alertDialog.setTitle(valeurSelectionnee.getNom());
                alertDialog.setButton(Dialog.BUTTON1, "Modifier", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(ControllerListerRDVContact.this, ControllerModifierRDV.class);
                        startActivity(intent);
                    }
                });
                alertDialog.setButton(Dialog.BUTTON2, "Supprimer", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        supprimerRDV(null);
                    }
                });
                alertDialog.show();

                Log.i("RDV A DELETE", "" + values.get(position).toString() + "");
                return false;
            }
        });*/
    }}