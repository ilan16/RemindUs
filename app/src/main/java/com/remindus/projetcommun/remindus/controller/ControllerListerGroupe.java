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
import com.remindus.projetcommun.remindus.dao.DAOGroupe;
import com.remindus.projetcommun.remindus.model.ModelContact;
import com.remindus.projetcommun.remindus.model.ModelGroupe;

import java.util.List;

/**
 * Created by ilanmalka on 09/03/15.
 */
public class ControllerListerGroupe extends ActionBarActivity {

    private DAOGroupe daoGroupe;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vue_liste_groupe);

        this.listerGroupe();
    }

    public void listerGroupe(){
        daoGroupe = new DAOGroupe(this);
        daoGroupe.getCrud().open();

        final List<ModelGroupe> values = daoGroupe.getAllGroupe();
        ListView l = (ListView) findViewById(R.id.sampleList);

        ArrayAdapter<ModelGroupe> adapter = new ArrayAdapter<ModelGroupe>(this,
                android.R.layout.simple_list_item_1, values);
        l.setAdapter(adapter);

        l.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
               // daoGroupe.deleteGroupe(values.get(position).toString());
                Log.i("GROUPE A DELETE", ""+values.get(position).toString()+"");
                Toast.makeText(getApplicationContext(), "Longggggg", Toast.LENGTH_SHORT).show();
                return false;
            }
        });

    }

    public void redirectionCreerGroupe(View view){
        Intent intent = new Intent(ControllerListerGroupe.this, ControllerCreerGroupe.class);
        startActivity(intent);
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
                Intent intent = new Intent(ControllerListerGroupe.this, ControllerParametre.class);
                startActivity(intent);
                break;

        }

        return false;
    }

}
