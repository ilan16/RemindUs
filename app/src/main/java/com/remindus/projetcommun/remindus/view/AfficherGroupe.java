package com.remindus.projetcommun.remindus;

import android.app.Activity;
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
public class AfficherGroupe extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vue_afficher_groupe);
    }

}
