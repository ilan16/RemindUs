package com.remindus.projetcommun.remindus.controller;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.remindus.projetcommun.remindus.R;
import com.remindus.projetcommun.remindus.dao.DAOModelMsg;

/**
 * Created by ilanmalka on 14/03/15.
 */
public class ControllerModifierModelMsg extends ControllerHeader {

    private DAOModelMsg daoModelMsg;
    private EditText titreEdit, contenuEdit;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vue_modifier_model_msg);
        titreEdit = (EditText) findViewById(R.id.titre_model_msg);
        contenuEdit = (EditText) findViewById(R.id.message_model_msg);
        titreEdit.setText((CharSequence) ControllerListerModelMsg.getValeurSelectionnee().getTitre());
        contenuEdit.setText((CharSequence) ControllerListerModelMsg.getValeurSelectionnee().getContenu());
    }

    public void modifierModelMsg(View view) {
        this.daoModelMsg = new DAOModelMsg(this);
        int update = this.daoModelMsg.updateModelMsg(ControllerListerModelMsg.getValeurSelectionnee(), this.titreEdit.getText().toString(), this.contenuEdit.getText().toString());
        Log.i("UPDATE MM:", "" + update + "");
        if (update == 0) {
            Toast.makeText(getApplicationContext(), getResources().getString(R.string.model_msg_modifie, ControllerListerModelMsg.getValeurSelectionnee().getTitre()), Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(ControllerModifierModelMsg.this, ControllerListerModelMsg.class);
            startActivity(intent);
        } else {
            Toast.makeText(getApplicationContext(), R.string.erreur_modifiction_model_msg, Toast.LENGTH_SHORT).show();
        }
    }


}

