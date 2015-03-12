package com.remindus.projetcommun.remindus.controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.remindus.projetcommun.remindus.R;
import com.remindus.projetcommun.remindus.dao.DAOGroupe;
import com.remindus.projetcommun.remindus.model.ModelGroupe;

import java.util.List;

/**
 * Created by ilanmalka on 10/03/15.
 */
public class ControllerModifierGroupe  extends ControllerHeader {

    private DAOGroupe daoGroupe;
    private EditText nomGroupeEdit;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vue_modifier_groupe);
        nomGroupeEdit = (EditText) findViewById(R.id.nom_groupe_update);
        nomGroupeEdit.setText((CharSequence) ControllerListerGroupe.getValeurSelectionnee().getNomGroupe());
    }

    public void modifierGroupe(View view){
        this.nomGroupeEdit = (EditText) findViewById(R.id.nom_groupe_update);
        this.daoGroupe = new DAOGroupe(this);
        int update = this.daoGroupe.updateGroupe(ControllerListerGroupe.getValeurSelectionnee(), this.nomGroupeEdit.getText().toString());
        if(update == 0){
            Toast.makeText(getApplicationContext(), getResources().getString(R.string.groupe_modifie, ControllerListerGroupe.getValeurSelectionnee().getNomGroupe()), Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(ControllerModifierGroupe.this, ControllerListerGroupe.class);
            startActivity(intent);
        }else{
            Toast.makeText(getApplicationContext(), R.string.erreur_modifiction_groupe, Toast.LENGTH_SHORT).show();
        }
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
                Intent intent = new Intent(ControllerModifierGroupe.this, ControllerParametre.class);
                startActivity(intent);
                break;

        }

        return false;
    }

}

