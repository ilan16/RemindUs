package com.remindus.projetcommun.remindus.controller;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

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
    private DAORDVxContacts daordVxContacts;
    private ListView l;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vue_liste_rdv_contact);

        this.listerRDVContact();
    }

    public void listerRDVContact() {
        daordVxContacts = new DAORDVxContacts(this);
        daordVxContacts.getCrud().open();

        final List<ModelRDVxContacts> values = daordVxContacts.getAllRDVxC(ControllerListerRDV.getValeurSelectionnee().getId());
        l = (ListView) findViewById(R.id.sampleList);

        List affiche = new ArrayList();

        DAORDV daordv = new DAORDV(this);
        DAOContact daoContact = new DAOContact(this);

        for (ModelRDVxContacts m : values) {
            ModelContact modelContact = daoContact.getContact(m.getIdcontact());
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
    }

    /*public void supprimerRDV(View view){
        ArrayAdapter<ModelRDV> adapter = (ArrayAdapter<ModelRDV>) l.getAdapter();
        boolean delete = daordVxContacts.deleteRDV(valeurSelectionnee);
        if(delete){
            Toast.makeText(getApplicationContext(), getResources().getString(R.string.rdv_supprime, valeurSelectionnee.getNom()), Toast.LENGTH_SHORT).show();
        }
        Log.i("RDV A DELETE", ""+valeurSelectionnee+"");
        adapter.remove(valeurSelectionnee);
        adapter.notifyDataSetChanged();
    }
*/


}