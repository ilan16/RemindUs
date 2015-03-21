package com.remindus.projetcommun.remindus.controller;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.remindus.projetcommun.remindus.R;
import com.remindus.projetcommun.remindus.dao.DAOGroupe;

/**
 * Created by ilanmalka on 10/03/15.
 */
public class ControllerModifierGroupe extends ControllerHeader {

    private DAOGroupe daoGroupe;
    private EditText nomGroupeEdit;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vue_modifier_groupe);
        nomGroupeEdit = (EditText) findViewById(R.id.nom_groupe_update);
        nomGroupeEdit.setText((CharSequence) ControllerListerGroupe.getValeurSelectionnee().getNomGroupe());
    }

    public void modifierGroupe(View view) {
        this.nomGroupeEdit = (EditText) findViewById(R.id.nom_groupe_update);
        this.daoGroupe = new DAOGroupe(this);
        int update = this.daoGroupe.updateGroupe(ControllerListerGroupe.getValeurSelectionnee(), this.nomGroupeEdit.getText().toString());
        if (update == 0) {
            Toast.makeText(getApplicationContext(), getResources().getString(R.string.groupe_modifie, ControllerListerGroupe.getValeurSelectionnee().getNomGroupe()), Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(ControllerModifierGroupe.this, ControllerListerGroupe.class);
            startActivity(intent);
        } else {
            Toast.makeText(getApplicationContext(), R.string.erreur_modifiction_groupe, Toast.LENGTH_SHORT).show();
        }
    }


}

