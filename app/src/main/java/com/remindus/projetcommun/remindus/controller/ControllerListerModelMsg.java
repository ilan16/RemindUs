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

    public static ModelModelMsg getValeurSelectionnee() {
        return valeurSelectionnee;
    }

    public static void setValeurSelectionnee(ModelModelMsg valeurSelectionnee) {
        ControllerListerModelMsg.valeurSelectionnee = valeurSelectionnee;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vue_liste_model_msg);

        this.listerModelMsg();
    }

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
                AlertDialog alertDialog = new AlertDialog.Builder(ControllerListerModelMsg.this).create();
                alertDialog.setTitle(valeurSelectionnee.getTitre());
                alertDialog.setButton(Dialog.BUTTON1, "Modifier", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(ControllerListerModelMsg.this, ControllerModifierModelMsg.class);
                        startActivity(intent);
                    }
                });
                alertDialog.setButton(Dialog.BUTTON2, "Supprimer", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        supprimerModelMsg(null);
                    }
                });
                alertDialog.show();

                Log.i("MODEL MSG A DELETE", "" + values.get(position).toString() + "");
                return false;
            }
        });
    }

    public void redirection(View view) {
        Intent intent = null;
        switch (view.getId()) {
            case R.id.bouton_ajouter_model_msg:
                intent = new Intent(ControllerListerModelMsg.this, ControllerCreerModelMsg.class);
                startActivity(intent);
                break;
        }
    }

    public void supprimerModelMsg(View view) {
        ArrayAdapter<ModelModelMsg> adapter = (ArrayAdapter<ModelModelMsg>) l.getAdapter();
        boolean delete = daoModelMsg.deleteModelMsg(valeurSelectionnee);
        if (delete) {
            Toast.makeText(getApplicationContext(), getResources().getString(R.string.model_msg_supprime, valeurSelectionnee.getTitre()), Toast.LENGTH_SHORT).show();
        }
        adapter.remove(valeurSelectionnee);
        adapter.notifyDataSetChanged();
    }



}
