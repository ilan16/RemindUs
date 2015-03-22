package com.remindus.projetcommun.remindus.dao;

import android.content.Context;

import com.remindus.projetcommun.remindus.basededonnees.MySQLiteHelper;
import com.remindus.projetcommun.remindus.basededonnees.utilities.CRUD;
import com.remindus.projetcommun.remindus.controller.UtilitaireDate;

/**
 * Created by ilanmalka on 22/03/15.
 */
public abstract class IDAO {

    private MySQLiteHelper dbHelper;
    private String[] allColumns;
    private UtilitaireDate utilitaireDate;
    private CRUD crud;

    public IDAO(Context context){
        crud = new CRUD(context);
        utilitaireDate = new UtilitaireDate();
    }

    public MySQLiteHelper getDbHelper() {
        return dbHelper;
    }

    public void setDbHelper(MySQLiteHelper dbHelper) {
        this.dbHelper = dbHelper;
    }

    public CRUD getCrud() {
        return crud;
    }

    public void setCrud(CRUD crud) {
        this.crud = crud;
    }

    public String[] getAllColumns() {
        return allColumns;
    }

    public void setAllColumns(String[] allColumns) {
        this.allColumns = allColumns;
    }

    public UtilitaireDate getUtilitaireDate() {
        return utilitaireDate;
    }

    public void setUtilitaireDate(UtilitaireDate utilitaireDate) {
        this.utilitaireDate = utilitaireDate;
    }
}
