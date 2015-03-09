package com.remindus.projetcommun.remindus.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.remindus.projetcommun.remindus.basededonnees.MySQLiteHelper;
import com.remindus.projetcommun.remindus.basededonnees.utilities.CRUD;
import com.remindus.projetcommun.remindus.model.ModelContact;
import com.remindus.projetcommun.remindus.model.ModelGroupe;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by ilanmalka on 08/03/15.
 */
public class DAOGroupe {

    private MySQLiteHelper dbHelper;
    private String[] allColumns = {
            MySQLiteHelper.COLUMN_ID_GROUPE,
            MySQLiteHelper.COLUMN_NOM_GROUPE,
            MySQLiteHelper.COLUMN_DATE_CREATION
    };
    private CRUD crud;

    public DAOGroupe(Context context) {
        crud = new CRUD(context);
    }

    public CRUD getCrud() {
        return crud;
    }

    public void setCrud(CRUD crud) {
        this.crud = crud;
    }

    public int insertGroupe(String nom) {
        if (!this.isExist(nom)) {
            ContentValues values = new ContentValues();
            values.put(MySQLiteHelper.COLUMN_NOM_GROUPE, nom);
            //convertion date en long pour la bdd
            Date actuelle = new Date();
            long dateLong = actuelle.getTime();
            values.put(MySQLiteHelper.COLUMN_DATE_CREATION, 0);
            this.crud.open();
            boolean insert = crud.insert(MySQLiteHelper.TABLE_GROUPES, values);
            this.crud.close();
            if (insert) {
                return 0; // insertion ok
            }
            return 1; // pb d'insertion
        } else {
            return 2; // groupe existe déjà
        }
    }

    public void deleteGroupe(String nom) {
        System.out.println("Contact deleted with name group: " + nom);
        boolean delete = crud.delete(MySQLiteHelper.TABLE_GROUPES, MySQLiteHelper.COLUMN_NOM_GROUPE
                + " = " + nom);
        if (delete){
            Log.i("DELETE", "effectué");
        }else{
            Log.i("DELETE", "merde");
        }
    }

    public List<ModelGroupe> getAllGroupe() {
        List<ModelGroupe> groupes = new ArrayList<ModelGroupe>();

        Cursor cursor = crud.getDatabase().query(MySQLiteHelper.TABLE_GROUPES,
                allColumns, null, null, null, null, null);

        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            ModelGroupe groupe = cursorToGroupe(cursor);
            groupes.add(groupe);
            cursor.moveToNext();

        }
        cursor.close();
        return groupes;
    }

    public ModelGroupe getGroupe(int id) {
        crud.open();
        String sql = "SELECT * FROM " + MySQLiteHelper.TABLE_GROUPES + " WHERE " + MySQLiteHelper.COLUMN_ID_GROUPE + " = " + id;
        Cursor cursor = crud.getDatabase().rawQuery(sql, null);
        crud.close();
        return this.cursorToGroupe(cursor);
    }

    public ModelGroupe getGroupe(String nom) {
        crud.open();
        String sql = "SELECT * FROM " + MySQLiteHelper.TABLE_GROUPES + " WHERE " + MySQLiteHelper.COLUMN_NOM_GROUPE + " = " + nom;
        Cursor cursor = crud.getDatabase().rawQuery(sql, null);
        crud.close();
        return this.cursorToGroupe(cursor);
    }

    private ModelGroupe cursorToGroupe(Cursor cursor) {
        ModelGroupe groupe = new ModelGroupe();
        groupe.setIdGroupe(cursor.getLong(0));
        groupe.setNomGroupe(cursor.getString(1));
        groupe.setDateCreation(cursor.getLong(2));
        return groupe;
    }

    private boolean isExist(String nom) {
        this.crud.open();
        String sql = "SELECT * FROM " + MySQLiteHelper.TABLE_GROUPES + " WHERE " + MySQLiteHelper.COLUMN_NOM_GROUPE + " = '" + nom + "'";
        Cursor cursor = crud.getDatabase().rawQuery(sql, null);

        if (cursor != null) {
            if (cursor.getCount() > 0) {
                cursor.close();
                return true;
            }
            cursor.close();
        }
        return false;
    }

}
