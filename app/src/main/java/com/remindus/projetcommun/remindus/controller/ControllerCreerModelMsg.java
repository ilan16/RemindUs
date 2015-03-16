package com.remindus.projetcommun.remindus.controller;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.remindus.projetcommun.remindus.R;
import com.remindus.projetcommun.remindus.dao.DAOGroupe;
import com.remindus.projetcommun.remindus.dao.DAOModelMsg;

/**
 * Created by bahia on 23/02/2015.
 */
public class ControllerCreerModelMsg extends ControllerHeader {

    private DAOModelMsg daoModelMsg;
    private EditText titreEdit, contenuEdit;
    private UtilitaireDate utilitaireDate;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vue_creer_model_msg);

        titreEdit = (EditText) findViewById(R.id.titre_model_msg);
        contenuEdit = (EditText) findViewById(R.id.message_model_msg);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.global, menu);
        return true;
    }

    public void ajouterModelMsg(View view) {
        String titre = this.titreEdit.getText().toString();
        String contenu = this.contenuEdit.getText().toString();

        this.daoModelMsg = new DAOModelMsg(this);
        if (!titre.equals("") && !contenu.equals("")) {
            int insert = this.daoModelMsg.insertModelMsg(titre, contenu);
            if (insert == 0) {
                Intent intent = new Intent(ControllerCreerModelMsg.this, ControllerListerModelMsg.class);
                startActivity(intent);
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // TODO Auto-generated method stub
        switch (item.getItemId()) {
            case R.id.bouton_parametre:
                Intent intent = new Intent(ControllerCreerModelMsg.this, ControllerParametre.class);
                startActivity(intent);
                break;

        }

        return false;
    }

}