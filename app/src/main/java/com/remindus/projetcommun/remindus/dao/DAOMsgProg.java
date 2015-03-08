package com.remindus.projetcommun.remindus.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

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
public class DAOMsgProg {
    private MySQLiteHelper dbHelper;
    private String[] allColumns = {
            MySQLiteHelper.COLUMN_ID_GROUPE,
            MySQLiteHelper.COLUMN_NOM_GROUPE,
            MySQLiteHelper.COLUMN_DATE_CREATION
    };
    private CRUD crud;

    public DAOMsgProg(Context context) {
        crud = new CRUD(context);
    }

    public CRUD getCrud() {
        return crud;
    }

    public void setCrud(CRUD crud) {
        this.crud = crud;
    }

    public int insertMsgProg(String titre, long date, long heure, String contenu) {
        if(!this.isExist(titre)) {

            ContentValues values = new ContentValues();
            //values.put(MySQLiteHelper.COLUMN_TITRE_MSG_PROG, titre);
            //values.put(MySQLiteHelper.COLUMN_DATE_MSG_PROG, date);
            //values.put(MySQLiteHelper.COLUMN_HEURE_MSG_PROG, heure);
            //values.put(MySQLiteHelper.COLUMN_MSG_PROG, contenu);

            this.crud.open();
            boolean insert = crud.insert(MySQLiteHelper.TABLE_GROUPES, values);
            this.crud.close();

            if (insert) {
                return 0; // insertion ok
            }
            return 1; // pb d'insertion
        } else {
            return 2; // msg existe déjà
        }
    }

    public void deleteGroupe(ModelContact contact) {
        long id = contact.getId();
        System.out.println("Contact deleted with id: " + id);
        crud.getDatabase().delete(MySQLiteHelper.TABLE_GROUPES, MySQLiteHelper.COLUMN_ID_GROUPE
                + " = " + id, null);
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

    public ModelGroupe getGroupe(int id){
        crud.open();
        String sql = "SELECT * FROM " + MySQLiteHelper.TABLE_GROUPES + " WHERE "+ MySQLiteHelper.COLUMN_ID_GROUPE + " = " + id;
        Cursor cursor = crud.getDatabase().rawQuery(sql, null);
        crud.close();
        return this.cursorToGroupe(cursor);
    }

    public ModelGroupe getGroupe(String nom){
        crud.open();
        String sql = "SELECT * FROM " + MySQLiteHelper.TABLE_GROUPES + " WHERE "+ MySQLiteHelper.COLUMN_NOM_GROUPE + " = " + nom;
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

    private boolean isExist(String titre){
        //String sql = "SELECT * FROM " + MySQLiteHelper.TABLE_MSG_PROG + " WHERE "+ MySQLiteHelper.COLUMN_TITRE_MSG_PROG + " = '" + titre + "'";
        //Cursor cursor = this.crud.getDatabase().rawQuery(sql, null);
       // if (cursor.getCount() > 0){
         //   return true;
       // }
        return false;
    }

}
