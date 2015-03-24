package com.remindus.projetcommun.remindus.dao;

import android.content.Context;

import com.remindus.projetcommun.remindus.basededonnees.MySQLiteHelper;
import com.remindus.projetcommun.remindus.basededonnees.utilities.CRUD;
import com.remindus.projetcommun.remindus.controller.UtilitaireDate;

/**
 * Created by ilanmalka on 22/03/15.
 */
public abstract class IDAO {
    // les classes du DAO hériteront de cette classe
    // cette classe ne contient pas les classes telles que insert, update, getAll car les signatures de chacune de ces methodes
    // dans les différents DAO sont différentes

    private MySQLiteHelper dbHelper; //variable qui instancie la classe qui génère la bdd
    private String[] allColumns; // variable qui contiendra l'ensemble des attributs d'une table de la bdd
    private UtilitaireDate utilitaireDate; //varaible de l'utlitaire de date
    private CRUD crud; //var qui instancie la classe qui permet de faire le CRUD sur la bdd

    public IDAO(Context context){ //controleur de la classe
        crud = new CRUD(context);
        utilitaireDate = new UtilitaireDate();
    }

    // dans la suite il y a seulement le getter et setter des variables précèdentes

    /**
     *
     * @return MySQLiteHelper
     */
    public MySQLiteHelper getDbHelper() {
        return dbHelper;
    }

    /**
     *
     * @param dbHelper
     */
    public void setDbHelper(MySQLiteHelper dbHelper) {
        this.dbHelper = dbHelper;
    }


    /**
     *
     * @return CRUD
     */
    public CRUD getCrud() {
        return crud;
    }

    /**
     *
     * @param crud
     */
    public void setCrud(CRUD crud) {
        this.crud = crud;
    }

    /**
     *
     * @return String[]
     */
    public String[] getAllColumns() {
        return allColumns;
    }

    /**
     *
     * @param allColumns
     */
    public void setAllColumns(String[] allColumns) {
        this.allColumns = allColumns;
    }

    /**
     *
     * @return UtilitaireDate
     */
    public UtilitaireDate getUtilitaireDate() {
        return utilitaireDate;
    }

    /**
     *
     * @param utilitaireDate
     */
    public void setUtilitaireDate(UtilitaireDate utilitaireDate) {
        this.utilitaireDate = utilitaireDate;
    }
}
