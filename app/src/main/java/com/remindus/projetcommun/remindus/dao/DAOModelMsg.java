package com.remindus.projetcommun.remindus.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.remindus.projetcommun.remindus.basededonnees.MySQLiteHelper;
import com.remindus.projetcommun.remindus.basededonnees.utilities.CRUD;
import com.remindus.projetcommun.remindus.model.ModelGroupe;
import com.remindus.projetcommun.remindus.model.ModelModelMsg;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by ilanmalka on 11/03/15.
 */
public class DAOModelMsg {

    private MySQLiteHelper dbHelper;
    private String[] allColumns = {
            MySQLiteHelper.COLUMN_ID_MSG_PROG,
            MySQLiteHelper.COLUMN_TITRE_MSG_PROG,
            MySQLiteHelper.COLUMN_CONTENU_MODEL_MSG,
            MySQLiteHelper.COLUMN_DATE_CREATION_MODEL_MSG
    };
    private CRUD crud;

    public DAOModelMsg(Context context) {
        crud = new CRUD(context);
    }

    public CRUD getCrud() {
        return crud;
    }

    public void setCrud(CRUD crud) {
        this.crud = crud;
    }

    public int insertModelMsg(String titre) {
        if (!this.isExist(titre)) {
            ContentValues values = new ContentValues();
            values.put(MySQLiteHelper.COLUMN_TITRE_MODEL_MSG, titre);
            //convertion date en long pour la bdd
            Date actuelle = new Date();
            long dateLong = actuelle.getTime();
            values.put(MySQLiteHelper.COLUMN_DATE_CREATION_MODEL_MSG, dateLong);
            this.crud.open();
            boolean insert = crud.insert(MySQLiteHelper.TABLE_MODEL_MSG, values);
            this.crud.close();
            if (insert) {
                return 0; // insertion ok
            }
            return 1; // pb d'insertion
        } else {
            return 2; // groupe existe déjà
        }
    }


    public boolean deleteModelMsg(ModelModelMsg modelModelMsg) {
        long id = modelModelMsg.getId();
        this.crud.open();
        boolean delete = crud.delete(MySQLiteHelper.TABLE_MODEL_MSG, MySQLiteHelper.COLUMN_ID_MODEL_MSG
                + " = " + id);
        // this.crud.close();
        if (delete){
            Log.i("DELETE", "effectué");
            return true;
        }
        Log.i("DELETE", "merde");
        return false;

    }

    public int updateModelMsg(ModelModelMsg modelModelMsg, String titre){
        String id = ""+modelModelMsg.getId();
        Log.i("ID",id);
        if(!this.isExist(titre)) {
            this.crud.open();
            ContentValues values = new ContentValues();
            values.put(MySQLiteHelper.COLUMN_TITRE_MODEL_MSG, titre);
            boolean update = crud.update(MySQLiteHelper.TABLE_MODEL_MSG, values, MySQLiteHelper.COLUMN_ID_MODEL_MSG, new String[]{id});
            if(update){
                Log.i("UPDATE","BON");
                return 0; //si l'update fonctionne
            }
            return 1; // pb update
        }
        return 2; // le nom existe déjà donc pas possible de maj avec ce nom
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
