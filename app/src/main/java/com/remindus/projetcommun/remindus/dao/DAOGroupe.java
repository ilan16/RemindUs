package com.remindus.projetcommun.remindus.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.remindus.projetcommun.remindus.basededonnees.MySQLiteHelper;
import com.remindus.projetcommun.remindus.model.ModelGroupe;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by ilanmalka on 08/03/15.
 */
public class DAOGroupe extends IDAO {


    public DAOGroupe(Context context) {
        super(context);
        String[] allColumns = {
                MySQLiteHelper.COLUMN_ID_GROUPE,
                MySQLiteHelper.COLUMN_NOM_GROUPE,
                MySQLiteHelper.COLUMN_DATE_CREATION
        };
        setAllColumns(allColumns);
    }

    public int insertGroupe(String nom) {
        if (!this.isExist(nom)) {
            ContentValues values = new ContentValues();
            values.put(MySQLiteHelper.COLUMN_NOM_GROUPE, nom);
            //convertion date en long pour la bdd
            Date actuelle = new Date();
            long dateLong = actuelle.getTime();
            values.put(MySQLiteHelper.COLUMN_DATE_CREATION, dateLong);
            getCrud().open();
            boolean insert = getCrud().insert(MySQLiteHelper.TABLE_GROUPES, values);
            getCrud().close();
            if (insert) {
                return 0; // insertion ok
            }
            return 1; // pb d'insertion
        } else {
            return 2; // groupe existe déjà
        }
    }


    public boolean deleteGroupe(ModelGroupe modelGroupe, Context context) {
        long id = modelGroupe.getIdGroupe();
        getCrud().open();
        DAOGroupexContact daoGroupexContact = new DAOGroupexContact(context);
        daoGroupexContact.deleteGxC((int) id);
        boolean deletegroupe = getCrud().delete(MySQLiteHelper.TABLE_GROUPES, MySQLiteHelper.COLUMN_ID_GROUPE
                + " = " + id);

        if (deletegroupe) {
            Log.i("DELETE", "effectué");
            return true;
        }
        Log.i("DELETE", "merde");
        return false;

    }

    public int updateGroupe(ModelGroupe modelGroupe, String nom) {
        String id = "" + modelGroupe.getIdGroupe();
        Log.i("ID", id);
        if (!this.isExist(nom)) {
            getCrud().open();
            ContentValues values = new ContentValues();
            values.put(MySQLiteHelper.COLUMN_NOM_GROUPE, nom);
            boolean update = getCrud().update(MySQLiteHelper.TABLE_GROUPES, values, MySQLiteHelper.COLUMN_ID_GROUPE, new String[]{id});
            if (update) {
                Log.i("UPDATE", "BON");
                return 0; //si l'update fonctionne
            }
            return 1;
        }
        return 2; // le nom existe déjà donc pas possible de maj avec ce nom
    }

    public List<ModelGroupe> getAllGroupe() {
        List<ModelGroupe> groupes = new ArrayList<ModelGroupe>();

        Cursor cursor = getCrud().getDatabase().query(MySQLiteHelper.TABLE_GROUPES,
                getAllColumns(), null, null, null, null, null);

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
        getCrud().open();
        String sql = "SELECT * FROM " + MySQLiteHelper.TABLE_GROUPES + " WHERE " + MySQLiteHelper.COLUMN_ID_GROUPE + " = " + id;
        Cursor cursor = getCrud().getDatabase().rawQuery(sql, null);
        ModelGroupe modelGroupe = new ModelGroupe();
        while (cursor.moveToNext()) {
            modelGroupe = cursorToGroupe(cursor);
        }
        cursor.close();
        return modelGroupe;
    }

    public ModelGroupe getGroupe(String nom) {
        ModelGroupe modelGroupe = new ModelGroupe();
        getCrud().open();
        String sql = "SELECT * FROM " + MySQLiteHelper.TABLE_GROUPES + " WHERE " + MySQLiteHelper.COLUMN_NOM_GROUPE + " = ? ";
        Cursor cursor = getCrud().getDatabase().rawQuery(sql, new String[]{nom});
        while (cursor.moveToNext()) {
            modelGroupe = cursorToGroupe(cursor);
        }
        cursor.close();
        return modelGroupe;
    }

    private ModelGroupe cursorToGroupe(Cursor cursor) {
        ModelGroupe groupe = new ModelGroupe();
        groupe.setIdGroupe(cursor.getLong(0));
        groupe.setNomGroupe(cursor.getString(1));
        groupe.setDateCreation(cursor.getLong(2));
        return groupe;
    }

    private boolean isExist(String nom) {
        getCrud().open();
        String sql = "SELECT * FROM " + MySQLiteHelper.TABLE_GROUPES + " WHERE " + MySQLiteHelper.COLUMN_NOM_GROUPE + " = '" + nom + "'";
        Cursor cursor = getCrud().getDatabase().rawQuery(sql, null);

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
