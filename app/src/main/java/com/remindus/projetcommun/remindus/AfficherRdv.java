package com.remindus.projetcommun.remindus;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by bahia on 23/02/2015.
 */
public class AfficherRdv extends Fragment {

    View rootview;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootview = inflater.inflate(R.layout.vue_afficher_rdv, container, false);
        return rootview;
    }
}
