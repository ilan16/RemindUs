package com.remindus.projetcommun.remindus.controller;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.remindus.projetcommun.remindus.R;
import com.remindus.projetcommun.remindus.dao.DAOMsgProg;
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
                AlertDialog alertDialog = new AlertDialog.Builder(ControllerListerMsgProg.this).create();
                alertDialog.setTitle(valeurSelectionnee.getTitre());
                alertDialog.setButton(Dialog.BUTTON1, "Modifier", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(ControllerListerMsgProg.this, ControllerModifierModelMsg.class);
                        startActivity(intent);
                    }
                });
                alertDialog.setButton(Dialog.BUTTON2, "Supprimer", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        supprimerMsgProg(null);
                    }
                });
                alertDialog.show();

                Log.i("RDV A DELETE", "" + values.get(position).toString() + "");
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.global, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // TODO Auto-generated method stub
        switch (item.getItemId()) {
            case R.id.bouton_parametre:
                Intent intent = new Intent(ControllerListerMsgProg.this, ControllerParametre.class);
                startActivity(intent);
                break;

        }

        return false;
    }
}