package com.remindus.projetcommun.remindus.controller;

import android.app.ActionBar;
import android.app.ListActivity;
import android.content.ContentValues;
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
import com.remindus.projetcommun.remindus.basededonnees.MySQLiteHelper;
import com.remindus.projetcommun.remindus.dao.DAOContact;
import com.remindus.projetcommun.remindus.model.ModelContact;

import java.util.List;

/**
 * Created by ilanmalka on 04/03/15.
 */
public class TesteBDD2 extends ActionBarActivity {

    private EditText nom;
    private EditText phone;
    private DAOContact datasource;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teste_bdd_2);
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_controller_groupe, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onClick (View v) {
        Log.i("Test:", "bouton ajouté appuié");
        nom = (EditText) findViewById(R.id.name);
        phone = (EditText) findViewById(R.id.phone);

        datasource = new DAOContact(this);
        datasource.getCrud().open();
        ContentValues c = new ContentValues();
        c.put(MySQLiteHelper.COLUMN_NOM_CONTACT, nom.getText().toString());
        c.put(MySQLiteHelper.COLUMN_TELEPHONE, phone.getText().toString());
        datasource.getCrud().insert(MySQLiteHelper.TABLE_CONTACTS, c);
        datasource.getCrud().close();
        Log.i("Name:", " "+ nom.getText().toString()+" ");
        Log.i("Phone:", " "+ phone.getText().toString()+" ");
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
