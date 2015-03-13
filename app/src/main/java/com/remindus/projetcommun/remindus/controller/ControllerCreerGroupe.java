package com.remindus.projetcommun.remindus.controller;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

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

    public void ajouterGroupe(View view){
        this.nomGroupeEdit = (EditText) findViewById(R.id.nom_groupe);
        String nomGroupe = this.nomGroupeEdit.getText().toString();

        this.daoGroupe = new DAOGroupe(this);

        int insert = this.daoGroupe.insertGroupe(nomGroupe);
        Log.i("INSERT GROUP", ""+insert+"");
        if(insert == 0){
            Intent intent = new Intent(ControllerCreerGroupe.this, ControllerListerGroupe.class);
            startActivity(intent);
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
                Intent intent = new Intent(ControllerCreerGroupe.this, ControllerParametre.class);
                startActivity(intent);
                break;

        }

        return false;
    }

    public EditText getNomGroupeEdit() {
        return nomGroupeEdit;
    }

    public void setNomGroupeEdit(EditText nomGroupeEdit) {
        this.nomGroupeEdit = nomGroupeEdit;
    }
}
