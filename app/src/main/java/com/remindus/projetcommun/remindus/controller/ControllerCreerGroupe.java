package com.remindus.projetcommun.remindus.controller;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.remindus.projetcommun.remindus.R;
import com.remindus.projetcommun.remindus.dao.DAOGroupe;

/**
 * Created by bahia on 23/02/2015.
 */
public class ControllerCreerGroupe extends ControllerHeader {

    private EditText nomGroupeEdit;
    private DAOGroupe daoGroupe;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vue_creer_groupe);
    }

    public void ajouterGroupe(View view) {
        this.nomGroupeEdit = (EditText) findViewById(R.id.nom_groupe);
        String nomGroupe = this.nomGroupeEdit.getText().toString();

        this.daoGroupe = new DAOGroupe(this);
        if (!nomGroupe.equals("")) {
            int insert = this.daoGroupe.insertGroupe(nomGroupe);
            if (insert == 0) {
                Intent intent = new Intent(ControllerCreerGroupe.this, ControllerListerGroupe.class);
                startActivity(intent);
            }
        } else {
            Toast.makeText(this, R.string.groupe_nom_vide, Toast.LENGTH_SHORT).show();
        }

    }




    public EditText getNomGroupeEdit() {
        return nomGroupeEdit;
    }

    public void setNomGroupeEdit(EditText nomGroupeEdit) {
        this.nomGroupeEdit = nomGroupeEdit;
    }
}
