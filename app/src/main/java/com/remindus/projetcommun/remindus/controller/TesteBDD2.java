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
import com.remindus.projetcommun.remindus.dao.DAOContact;
import com.remindus.projetcommun.remindus.model.ModelContact;

import java.util.List;

/**
 * Created by ilanmalka on 04/03/15.
 */
public class TesteBDD2 extends ControllerHeader {

    private EditText contactEdit;
    private EditText telephoneEdit;
    private DAOContact datasource;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teste_bdd_2);
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
                Intent intent = new Intent(TesteBDD2.this, ControllerParametre.class);
                startActivity(intent);
                break;

        }

        return false;
    }

    public void onClick (View v) {
        Log.i("Test:", "bouton ajouté appuié");
        contactEdit = (EditText) findViewById(R.id.name);
        telephoneEdit = (EditText) findViewById(R.id.phone);

        String contact =  contactEdit.getText().toString();
        String telephone = telephoneEdit.getText().toString();

        datasource = new DAOContact(this);
        if(telephone.length() < 8){
            Toast.makeText(getApplicationContext(), R.string.erreur_telephone_contact, Toast.LENGTH_LONG).show();
        }else {
            datasource.insertContact(contact, telephone);
        }

        Log.i("Name:", " " + contactEdit.getText().toString() + " ");
        Log.i("Phone:", " "+ telephoneEdit.getText().toString()+" ");
    }


    public void lister(View view){

        Log.i("Test:", "bouton lister appuié");
        datasource = new DAOContact(this);
        datasource.getCrud().open();
        final List<ModelContact> values = datasource.getAllContacts();
        ListView l = (ListView) findViewById(R.id.sampleList);

        // utilisez SimpleCursorAdapter pour afficher les
        // éléments dans une ListView
        ArrayAdapter<ModelContact> adapter = new ArrayAdapter<ModelContact>(this,
                android.R.layout.simple_list_item_1, values);
        l.setAdapter(adapter);

        l.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Log.i("Tag", "Position=" + values.get(position));
                        Toast.makeText(getApplicationContext(), "Test", Toast.LENGTH_SHORT).show();
                    }
                });

        l.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getApplicationContext(), "Longggggg", Toast.LENGTH_SHORT).show();
                return false;
            }
        });
        datasource.getCrud().close();
    }


}
