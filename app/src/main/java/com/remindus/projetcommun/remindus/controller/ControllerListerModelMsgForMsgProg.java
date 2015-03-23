package com.remindus.projetcommun.remindus.controller;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
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
    private ArrayAdapter<ModelModelMsg> adapter = null;
    private static ModelModelMsg modelModelMsg;

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
        lv = (ListView) findViewById(R.id.sampleListRadio);

        adapter = new ArrayAdapter<ModelModelMsg>(this, android.R.layout.simple_list_item_single_choice, values);
        lv.setAdapter(adapter);

        adapter.notifyDataSetChanged();

    }

    public void valider(View view) {
        int idSelectionne = lv.getCheckedItemPosition();
        setModelModelMsg(adapter.getItem(idSelectionne));
        ControllerListerModelMsgForMsgProg.this.finish();
        Intent intent = new Intent(ControllerListerModelMsgForMsgProg.this,ControllerCreerMsgProg.class);
        startActivity(intent);
    }

    public static ModelModelMsg getModelModelMsg() {
        return modelModelMsg;
    }

    public static void setModelModelMsg(ModelModelMsg modelModelMsg) {
        ControllerListerModelMsgForMsgProg.modelModelMsg = modelModelMsg;
    }
}
