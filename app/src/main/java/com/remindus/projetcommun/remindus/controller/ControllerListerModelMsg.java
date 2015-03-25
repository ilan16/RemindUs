package com.remindus.projetcommun.remindus.controller;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.remindus.projetcommun.remindus.R;
import com.remindus.projetcommun.remindus.dao.DAOModelMsg;
import com.remindus.projetcommun.remindus.model.ModelModelMsg;

import java.util.List;

/**
 * Created by ilanmalka on 14/03/15.
 */
public class ControllerListerModelMsg extends ControllerHeader {

    private static ModelModelMsg valeurSelectionnee;
    private DAOModelMsg daoModelMsg;
    private ListView l;

    /**
     *
     * @return
     */
    public static ModelModelMsg getValeurSelectionnee() {
        return valeurSelectionnee;
    }

    /**
     *
     * @param valeurSelectionnee
     */
    public static void setValeurSelectionnee(ModelModelMsg valeurSelectionnee) {
        ControllerListerModelMsg.valeurSelectionnee = valeurSelectionnee;
    }

    /**
     *
     * @param savedInstanceState
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vue_liste_model_msg);

        this.listerModelMsg();
    }

    /**
     * permet de lister l'ensemble des modeles messages
     */
    public void listerModelMsg() {
        daoModelMsg = new DAOModelMsg(this);
        daoModelMsg.getCrud().open();

        final List<ModelModelMsg> values = daoModelMsg.getAllModelMsg();
        l = (ListView) findViewById(R.id.sampleList);

        ArrayAdapter<ModelModelMsg> adapter = new ArrayAdapter<ModelModelMsg>(this, android.R.layout.simple_list_item_1, values);
        l.setAdapter(adapter);

        l.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                valeurSelectionnee = (ModelModelMsg) l.getAdapter().getItem(position);
                ControllerListerModelMsg.setValeurSelectionnee(valeurSelectionnee);

                final String[] option = {getResources().getString(R.string.dialogue_modifier),getResources().getString(R.string.dialogue_supprimer)};

                AlertDialog.Builder myDialog =new AlertDialog.Builder(ControllerListerModelMsg.this);
                myDialog.setTitle(valeurSelectionnee.getTitre());
                myDialog.setItems(option, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        switch (which) {
                            case 0:
                                Intent intent = new Intent(ControllerListerModelMsg.this, ControllerModifierModelMsg.class);
                                startActivity(intent);
                                break;
                            case 1:
                                supprimerModelMsg(null);
                                break;
                        }
                    }
                });

                myDialog.setNegativeButton("Cancel", null);

                myDialog.show();


                return false;
            }
        });
    }

    /**
     * permet de faire une redirection vers le menu créer un modele message
     * @param view
     */
    public void redirection(View view) {
        Intent intent = null;
        switch (view.getId()) {
            case R.id.bouton_ajouter_model_msg:
                intent = new Intent(ControllerListerModelMsg.this, ControllerCreerModelMsg.class);
                startActivity(intent);
                break;
        }
    }

    /**
     * permet de supprimer un élément de la bdd et de la liste visuel
     * @param view
     */
    public void supprimerModelMsg(View view) {
        ArrayAdapter<ModelModelMsg> adapter = (ArrayAdapter<ModelModelMsg>) l.getAdapter();
        boolean delete = daoModelMsg.deleteModelMsg(valeurSelectionnee);
        if (delete) {
            Toast.makeText(getApplicationContext(), getResources().getString(R.string.model_msg_supprime, valeurSelectionnee.getTitre()), Toast.LENGTH_SHORT).show();
        }
        adapter.remove(valeurSelectionnee);
        adapter.notifyDataSetChanged();//permet de mettre à jour la liste niveau design
    }



}
