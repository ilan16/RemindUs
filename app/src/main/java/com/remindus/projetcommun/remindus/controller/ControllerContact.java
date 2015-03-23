package com.remindus.projetcommun.remindus.controller;

/**
 * Created by bahia on 26/02/2015.
 */

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

import com.remindus.projetcommun.remindus.MainActivity;
import com.remindus.projetcommun.remindus.R;
import com.remindus.projetcommun.remindus.dao.DAOContact;
import com.remindus.projetcommun.remindus.dao.DAOMsgProg;
import com.remindus.projetcommun.remindus.dao.DAOMsgProgxContacts;
import com.remindus.projetcommun.remindus.dao.DAORDV;
import com.remindus.projetcommun.remindus.dao.DAORDVxContacts;
import com.remindus.projetcommun.remindus.model.ModelContact;
import com.remindus.projetcommun.remindus.model.ModelMsgProg;
import com.remindus.projetcommun.remindus.model.ModelRDV;

import java.util.HashMap;
import java.util.List;

public class ControllerContact extends ControllerHeader {

    private ListView lv;
    private Cursor cursor1;
    private DAOContact daoContact;
    private CustomAdapterContact adapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vue_afficher_contact);

        this.listerContact();
    }

    public void listerContact() {
        daoContact = new DAOContact(this);
        daoContact.getCrud().open();

        final List<ModelContact> values = daoContact.getAllContacts();
        lv = (ListView) findViewById(R.id.sampleList);

        adapter = new CustomAdapterContact(this, R.layout.vue_afficher_contact, values);
        lv.setAdapter(adapter);

        adapter.notifyDataSetChanged();

    }


    public void choisirCheckBox(View view) {

        CheckBox cb = (CheckBox) view;
        int position = Integer.parseInt(cb.getTag().toString());
        Log.i("POSITION: ", "" + position);
    }

    public void refreshContact(View view) {
        daoContact = new DAOContact(this);
        cursor1 = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " ASC");

        while (cursor1.moveToNext()) {
            String contactId = cursor1.getString(cursor1.getColumnIndex(ContactsContract.Contacts._ID));
            String name = cursor1.getString(cursor1.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
            String number = cursor1.getString(cursor1.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
            daoContact.insertContact(name, number);
        }

        adapter.notifyDataSetChanged();

    }


    public void ajouterContact(View view) {
        final HashMap<CheckBox, TextView> values = adapter.getListChecked();
        int count = 0;
        for (HashMap.Entry<CheckBox, TextView> hash : values.entrySet()) {

            //Pour savoir quel insert choisir, on vérifie quel page a appelé cette classe
            if (!ControllerCreerRDV.getNomRDVstatic().equals("") || !ControllerListerRDV.getValeurSelectionnee().getNom().equals("")) { // sil s'agit de la classe RDV

                if (hash.getKey().isChecked()) {
                    count++;
                    String[] split = hash.getValue().getText().toString().split(" - ");
                    this.daoContact = new DAOContact(this);
                    ModelContact modelContact = this.daoContact.getContact(split[1]);

                    Log.i("contact rdv", "" + modelContact.getId() + " " + modelContact.getContact());
                    DAORDVxContacts daordVxContacts = new DAORDVxContacts(this);
                    DAORDV daordv = new DAORDV(this);
                    ModelRDV modelRDV = new ModelRDV();
                    if (!ControllerCreerRDV.getNomRDVstatic().equals("")) {
                        modelRDV = daordv.getIdRDV(ControllerCreerRDV.getNomRDVstatic());
                    } else if (!ControllerListerRDV.getValeurSelectionnee().getNom().equals("")) {
                        modelRDV = daordv.getIdRDV(ControllerListerRDV.getValeurSelectionnee().getNom());
                    }
                    long idcontact = modelContact.getId();
                    int insert = daordVxContacts.insertRDVxC(modelRDV.getId(), idcontact);
                    if(count == values.size()){
                        Intent intent = new Intent(ControllerContact.this, ControllerListerRDV.class);
                        startActivity(intent);
                    }
                } else {
                    Intent intent = new Intent(ControllerContact.this, ControllerListerRDV.class);
                    startActivity(intent);
                }

            } else if (!ControllerCreerMsgProg.getTitreMsgProgStatic().equals("") || !ControllerListerMsgProg.getValeurSelectionnee().equals("")) { //sil s'agit de la classe msg prog
                if (hash.getKey().isChecked()) {
                    count++;
                    String[] split = hash.getValue().getText().toString().split(" - ");
                    this.daoContact = new DAOContact(this);
                    ModelContact modelContact = this.daoContact.getContact(split[1]);

                    DAOMsgProgxContacts daoMsgProgxContacts = new DAOMsgProgxContacts(this);
                    DAOMsgProg daoMsgProg = new DAOMsgProg(this);
                    ModelMsgProg modelMsgProg = new ModelMsgProg();

                    if (!ControllerCreerMsgProg.getTitreMsgProgStatic().equals("")) {
                        modelMsgProg = daoMsgProg.getIdMsgProg(ControllerCreerMsgProg.getTitreMsgProgStatic());
                    } else if (!ControllerListerRDV.getValeurSelectionnee().equals("")) {
                        modelMsgProg = daoMsgProg.getIdMsgProg(ControllerListerMsgProg.getValeurSelectionnee().getTitre());
                    }

                    long idcontact = modelContact.getId();
                    int insert = daoMsgProgxContacts.insertMsgProgxC(modelMsgProg.getId(), idcontact);
                    if(count == values.size()){
                        Intent intent = new Intent(ControllerContact.this, ControllerListerMsgProg.class);
                        startActivity(intent);
                    }
                } else {
                    Intent intent = new Intent(ControllerContact.this, ControllerListerMsgProg.class);
                    startActivity(intent);
                }
            }
        }
    }


    /*public void listerContact(){
        // create a cursor to query the Contacts on the device to start populating a listview
        cursor1 = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " ASC");
        startManagingCursor(cursor1);

        String[] from = {ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME, ContactsContract.CommonDataKinds.Phone.NUMBER, ContactsContract.CommonDataKinds.Phone._ID}; // get the list items for the listadapter could be TITLE or URI


        int[] to = {android.R.id.text1, android.R.id.text2}; // sets the items from above string to listview

        // new listadapter, created to use android checked template
        SimpleCursorAdapter listadapter = new SimpleCursorAdapter(this, android.R.layout.simple_list_item_2, cursor1, from, to);

        setListAdapter(listadapter);

        // adds listview so I can get data from it
        lv = getListView();
        lv.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
    }*/


}
