package com.remindus.projetcommun.remindus.controller;

/**
 * Created by bahia on 26/02/2015.
 */

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.remindus.projetcommun.remindus.R;
import com.remindus.projetcommun.remindus.dao.DAOContact;
import com.remindus.projetcommun.remindus.dao.DAOModelMsg;
import com.remindus.projetcommun.remindus.model.ModelContact;
import com.remindus.projetcommun.remindus.model.ModelModelMsg;
import com.remindus.projetcommun.remindus.model.ModelRDV;

import java.util.HashMap;
import java.util.List;

public class ControllerContact extends ControllerHeader {

    private ListView lv;
    private Cursor cursor1;
    private DAOContact daoContact;
    private CustomAdapterContact adapter =null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vue_afficher_contact);

        this.listerContact();
        this.checkButtonClick();
    }

    public void listerContact(){
        daoContact = new DAOContact(this);
        daoContact.getCrud().open();

        final List<ModelContact> values = daoContact.getAllContacts();
        lv = (ListView) findViewById(R.id.sampleList);

         adapter= new CustomAdapterContact(this,R.layout.vue_afficher_contact,values);
        lv.setAdapter(adapter);


        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.i("Valeur sélectionnée", ""+lv.getId());
            }
        });

    }





    public void choisirCheckBox(View view){

        CheckBox cb = (CheckBox) view;
        int position = Integer.parseInt(cb.getTag().toString());
        Log.i("POSITION: ", ""+position);
    }

    public void refreshContact(View view){
        daoContact = new DAOContact(this);
        cursor1 = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " ASC");

        while (cursor1.moveToNext()){
            String contactId = cursor1.getString(cursor1.getColumnIndex(ContactsContract.Contacts._ID));
            String name = cursor1.getString(cursor1.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
            String number = cursor1.getString(cursor1.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
            daoContact.insertContact(name, number);
        }
    }

    private void checkButtonClick() {

        Button myButton = (Button) findViewById(R.id.validerContact);

        myButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                StringBuffer responseText = new StringBuffer();
                responseText.append("Contacts selectionnés ...\n");


                final HashMap<CheckBox, TextView> values = adapter.getListChecked();

                for (HashMap.Entry<CheckBox, TextView> hash : values.entrySet()) {
                    if (hash.getKey().isChecked()) {
                        responseText.append("\n - " + hash.getValue().getText());
                        String[] split = hash.getValue().getText().toString().split("\n");
                        daoContact = new DAOContact(getBaseContext());
                        ModelContact modelContact = daoContact.getContact(split[1]);
                        Log.i("contact", "" + modelContact.getId() + " " + modelContact.getContact());

                    }
                }

                Toast.makeText(getApplicationContext(),
                        responseText, Toast.LENGTH_LONG).show();
            }
        });

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
                Intent intent = new Intent(ControllerContact.this, ControllerParametre.class);
                startActivity(intent);
                break;

        }

        return false;
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
