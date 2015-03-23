package com.remindus.projetcommun.remindus.controller;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.remindus.projetcommun.remindus.R;
import com.remindus.projetcommun.remindus.dao.DAOContact;
import com.remindus.projetcommun.remindus.dao.DAOModelMsg;
import com.remindus.projetcommun.remindus.dao.DAOMsgProg;
import com.remindus.projetcommun.remindus.dao.DAOMsgProgxContacts;
import com.remindus.projetcommun.remindus.dao.DAORDV;
import com.remindus.projetcommun.remindus.dao.DAORDVxContacts;
import com.remindus.projetcommun.remindus.model.ModelContact;
import com.remindus.projetcommun.remindus.model.ModelModelMsg;
import com.remindus.projetcommun.remindus.model.ModelMsgProg;
import com.remindus.projetcommun.remindus.model.ModelRDV;

import java.util.HashMap;
import java.util.List;

/**
 * Created by ilanmalka on 23/03/15.
 */
public class ControllerListerModelMsgForMsgProg extends ControllerHeader {
    private ListView lv;
    private Cursor cursor1;
    private DAOModelMsg daoModelMsg;
    private CustomAdapterMsgModele adapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vue_afficher_msg_modele);

        this.listerContact();
    }

    public void listerContact() {
        daoModelMsg = new DAOModelMsg(this);
        daoModelMsg.getCrud().open();

        final List<ModelModelMsg> values = daoModelMsg.getAllModelMsg();
        lv = (ListView) findViewById(R.id.sampleList);

        adapter = new CustomAdapterMsgModele(this, R.layout.vue_afficher_msg_modele, values);
        lv.setAdapter(adapter);

        adapter.notifyDataSetChanged();

    }


    /*public void ajouterContact(View view) {
        final HashMap<RadioButton, TextView> values = adapter.getListChecked();
        int count = 0;
        for (HashMap.Entry<RadioButton, TextView> hash : values.entrySet()) {

            if (hash.getKey().isChecked()) {
                count++;
                String[] split = hash.getValue().getText().toString().split(" - ");
                this.daoModelMsg = new DAOModelMsg(this);
                ModelModelMsg modelModelMsg = this.daoModelMsg.getModelMsg(split[0]);

                DAORDVxContacts daordVxContacts = new DAORDVxContacts(this);
                DAORDV daordv = new DAORDV(this);
                ModelRDV modelRDV = new ModelRDV();
                if (!ControllerCreerRDV.getNomRDVstatic().equals("")) {
                    modelRDV = daordv.getIdRDV(ControllerCreerRDV.getNomRDVstatic());
                } else if (!ControllerListerRDV.getValeurSelectionnee().getNom().equals("")) {
                    modelRDV = daordv.getIdRDV(ControllerListerRDV.getValeurSelectionnee().getNom());
                }
                long idcontact = modelModelMsg.getId();
                int insert = daordVxContacts.insertRDVxC(modelRDV.getId(), idcontact);
                if (count == values.size()) {
                    Intent intent = new Intent(ControllerContact.this, ControllerListerRDV.class);
                    startActivity(intent);
                }
            } else {
                Intent intent = new Intent(ControllerContact.this, ControllerListerRDV.class);
                startActivity(intent);
            }
        }
    }*/
}
