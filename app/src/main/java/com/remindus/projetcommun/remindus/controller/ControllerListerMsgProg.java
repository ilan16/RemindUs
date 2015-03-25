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
import com.remindus.projetcommun.remindus.Service.DeclencheurSms;
import com.remindus.projetcommun.remindus.Service.EnvoiSms;
import com.remindus.projetcommun.remindus.dao.DAOMsgProg;
import com.remindus.projetcommun.remindus.dao.DAOMsgProgxContacts;
import com.remindus.projetcommun.remindus.model.ModelMsgProg;

import java.util.List;

/**
 * Created by ilanmalka on 18/03/15.
 */
public class ControllerListerMsgProg extends ControllerHeader {

    private static ModelMsgProg valeurSelectionnee;
    private DAOMsgProg daoMsgProg;
    private ListView l;



    public static ModelMsgProg getValeurSelectionnee() {
        return valeurSelectionnee;
    }

    public static void setValeurSelectionnee(ModelMsgProg valeurSelectionnee) {
        ControllerListerMsgProg.valeurSelectionnee = valeurSelectionnee;
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vue_liste_msg_prog);

        //debut du service declencheurSms pour savoir quand envoyer un sms
        Intent myIntent = new Intent(ControllerListerMsgProg.this, DeclencheurSms.class);

        ControllerListerMsgProg.this.startService(myIntent);

        // on remet nom rdv vide dans le cas ou l'utilisateur veut creer un rdv et un msg prog dans la mm session
        ControllerCreerRDV.setNomRDVstatic("");
        ControllerListerRDV.setValeurSelectionnee(null);

        this.listerRDV();
    }

    public void listerRDV() {
        daoMsgProg = new DAOMsgProg(this);
        daoMsgProg.getCrud().open();

        final List<ModelMsgProg> values = daoMsgProg.getAllMsgProg();
        l = (ListView) findViewById(R.id.sampleList);

        ArrayAdapter<ModelMsgProg> adapter = new ArrayAdapter<ModelMsgProg>(this,
                android.R.layout.simple_list_item_1, values);
        l.setAdapter(adapter);

        l.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                valeurSelectionnee = (ModelMsgProg) l.getAdapter().getItem(position);
                ControllerListerMsgProg.setValeurSelectionnee(valeurSelectionnee);


                final String[] option = {"Modifier", "Contacts", "Supprimer","Envoyer manuellement"};

                AlertDialog.Builder myDialog =new AlertDialog.Builder(ControllerListerMsgProg.this);
                myDialog.setTitle(valeurSelectionnee.getTitre());
                myDialog.setItems(option, new DialogInterface.OnClickListener(){

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        String item = option[which];

                        switch (item){
                            case "Modifier":
                                Intent intent = new Intent(ControllerListerMsgProg.this, ControllerModifierMsgProg.class);
                                startActivity(intent);
                                break;
                            case "Supprimer":
                                supprimerMsgProg(null);
                                break;
                            case "Contacts":
                                Intent intent2 = new Intent(ControllerListerMsgProg.this, ControllerListerMsgProgContact.class);
                                startActivity(intent2);
                                break;
                            case "Envoyer manuellement":
                                int idInt  = (int) valeurSelectionnee.getId();
                                long idLong = valeurSelectionnee.getId();
                                ModelMsgProg modelMsgProg = daoMsgProg.getMsgProg(idInt);
                                String message = modelMsgProg.getMsgProg();
                                DAOMsgProgxContacts daoMsgProgxContacts = new DAOMsgProgxContacts(getBaseContext());
                                String numero = daoMsgProgxContacts.getAllNumero(idLong, getBaseContext());
                                EnvoiSms envoiSms = new EnvoiSms(numero, message);
                                envoiSms.envoi_texto();
                                break;
                        }


                    }});

                myDialog.setNegativeButton("Cancel", null);

                myDialog.show();

                return false;
            }
        });
    }

    public void supprimerMsgProg(View view) {
        ArrayAdapter<ModelMsgProg> adapter = (ArrayAdapter<ModelMsgProg>) l.getAdapter();
        boolean delete = daoMsgProg.deleteMsgProg(valeurSelectionnee);
        if (delete) {
            Toast.makeText(getApplicationContext(), getResources().getString(R.string.rdv_supprime, valeurSelectionnee.getTitre()), Toast.LENGTH_SHORT).show();
        }
        Log.i("MSG PROG A DELETE", "" + valeurSelectionnee + "");
        adapter.remove(valeurSelectionnee);
        adapter.notifyDataSetChanged();
    }

    public void redirection(View view) {
        Intent intent = new Intent(ControllerListerMsgProg.this, ControllerCreerMsgProg.class);
        startActivity(intent);
    }


}