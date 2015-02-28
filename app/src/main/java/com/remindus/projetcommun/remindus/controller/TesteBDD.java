package com.remindus.projetcommun.remindus.controller;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.remindus.projetcommun.remindus.R;
import com.remindus.projetcommun.remindus.dao.DAOContact;
import com.remindus.projetcommun.remindus.model.ModelContact;

import java.util.List;
import java.util.Random;

public class TesteBDD extends ActionBarActivity {

    private DAOContact datasource;
    ListView l = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teste_bdd);

        datasource = new DAOContact(this);
        datasource.open();

        List<ModelContact> values = datasource.getAllContacts();
        l = (ListView) findViewById(R.id.list);

        // utilisez SimpleCursorAdapter pour afficher les
        // éléments dans une ListView
        ArrayAdapter<ModelContact> adapter = new ArrayAdapter<ModelContact>(this,
                android.R.layout.simple_list_item_1, values);
        l.setAdapter(adapter);
    }

    // Sera appelée par l'attribut onClick
    // des boutons déclarés dans main.xml
    public void onClick(View view) {
        @SuppressWarnings("unchecked")
        ArrayAdapter<ModelContact> adapter = (ArrayAdapter<ModelContact>) l.getAdapter();
        ModelContact comment = null;
        switch (view.getId()) {
            case R.id.add:
                String[] contacts = new String[] { "Cool", "Very nice", "Hate it" };
                String[] telephone = new String[] { "0123456789", "01478523369", "796541230" };
                int nextInt = new Random().nextInt(3);
                // enregistrer le nouveau commentaire dans la base de données
                comment = datasource.createContact(contacts[nextInt], telephone[nextInt]);
                adapter.add(comment);
                break;
            case R.id.delete:
                if (l.getAdapter().getCount() > 4) {
                    comment = (ModelContact) l.getAdapter().getItem(3);
                    datasource.deleteContact(comment);
                    adapter.remove(comment);
                }
                break;
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onResume() {
        datasource.open();
        super.onResume();
    }

    @Override
    protected void onPause() {
        datasource.close();
        super.onPause();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_teste_bdd, menu);
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
}
