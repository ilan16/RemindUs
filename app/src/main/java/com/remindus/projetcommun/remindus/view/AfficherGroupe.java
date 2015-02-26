package com.remindus.projetcommun.remindus;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.remindus.projetcommun.remindus.dao.DAOContact;
import com.remindus.projetcommun.remindus.model.ModelContact;

import java.util.List;
import java.util.Random;

/**
 * Created by bahia on 23/02/2015.
 */
public class AfficherGroupe extends Fragment {

    View rootview;
    DAOContact datasource;
    ListView l = null;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootview = inflater.inflate(R.layout.vue_afficher_groupe, container, false);

        datasource = new DAOContact(getActivity());
        datasource.open();

        List<ModelContact> values = datasource.getAllContacts();
        l = (ListView) rootview.findViewById(R.id.listTest);

        // utilisez SimpleCursorAdapter pour afficher les
        // éléments dans une ListView
        ArrayAdapter<ModelContact> adapter = new ArrayAdapter<ModelContact>(getActivity().getApplicationContext(),
                android.R.layout.simple_list_item_1, values);
        l.setAdapter(adapter);
        return rootview;
    }

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
    public void onResume() {
        datasource.open();
        super.onResume();
    }

    @Override
    public void onPause() {
        datasource.close();
        super.onPause();
    }
}
