package com.remindus.projetcommun.remindus.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.remindus.projetcommun.remindus.basededonnees.MySQLiteHelper;
import com.remindus.projetcommun.remindus.basededonnees.utilities.CRUD;

import com.remindus.projetcommun.remindus.model.ModelRDV;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ilanmalka on 11/03/15.
 */
public class DAORDV {

    private MySQLiteHelper dbHelper;
    private String[] allColumns = {
            MySQLiteHelper.COLUMN_ID_RDV,
            MySQLiteHelper.COLUMN_NOM_RDV,
            MySQLiteHelper.COLUMN_DATE_RDV,
            MySQLiteHelper.COLUMN_DATESTRING_RDV,
            MySQLiteHelper.COLUMN_LIEU_RDV,
            MySQLiteHelper.COLUMN_MODE_TEL_RDV
    };
    private CRUD crud;

    public DAORDV(Context context) {
        crud = new CRUD(context);
    }

    public CRUD getCrud() {
        return crud;
    }

    public void setCrud(CRUD crud) {
        this.crud = crud;
    }

    public int insertRDV(String nom, long date, String datestring,String lieu, long mode) {
        ContentValues values = new ContentValues();
        values.put(MySQLiteHelper.COLUMN_NOM_RDV, nom);
        values.put(MySQLiteHelper.COLUMN_DATE_RDV, date);
        values.put(MySQLiteHelper.COLUMN_DATESTRING_RDV, datestring);
        values.put(MySQLiteHelper.COLUMN_LIEU_RDV, lieu);
        values.put(MySQLiteHelper.COLUMN_MODE_TEL_RDV, mode);
        this.crud.open();
        boolean insert = crud.insert(MySQLiteHelper.TABLE_RDV, values);
        this.crud.close();
        if (insert) {
            return 0; // insertion ok
        }
        return 1; // pb d'insertion

    }


    public boolean deleteRDV(ModelRDV modelRDV) {
        long id = modelRDV.getId();
        this.crud.open();
        boolean delete = crud.delete(MySQLiteHelper.TABLE_RDV, MySQLiteHelper.COLUMN_ID_RDV
                + " = " + id);
        // this.crud.close();
        if (delete) {
            Log.i("DELETE", "effectué");
            return true;
        }
        Log.i("DELETE", "merde");
        return false;

    }

    public int updateRDV(ModelRDV modelRDV, String nom, long date, String lieu, long mode) {
        String id = "" + modelRDV.getId();
        Log.i("ID", id);
        this.crud.open();
        ContentValues values = new ContentValues();
        values.put(MySQLiteHelper.COLUMN_NOM_RDV, nom);
        values.put(MySQLiteHelper.COLUMN_DATE_RDV, date);
        values.put(MySQLiteHelper.COLUMN_LIEU_RDV, lieu);
        values.put(MySQLiteHelper.COLUMN_MODE_TEL_RDV, mode);
        boolean update = crud.update(MySQLiteHelper.TABLE_RDV, values, MySQLiteHelper.COLUMN_ID_RDV, new String[]{id});
        if (update) {
            Log.i("UPDATE", "BON");
            return 0; //si l'update fonctionne
        }
        return 1; // pb update
    }

    public List<ModelRDV> getAllRDV() {
        List<ModelRDV> rdvs = new ArrayList<ModelRDV>();

        Cursor cursor = crud.getDatabase().query(MySQLiteHelper.TABLE_RDV,
                allColumns, null, null, null, null, null);

        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            ModelRDV rdv = cursorToRDV(cursor);
            rdvs.add(rdv);
            cursor.moveToNext();

        }
        cursor.close();
        return rdvs;
    }

    public ModelRDV getRDV(int id) {
        crud.open();
        String sql = "SELECT * FROM " + MySQLiteHelper.TABLE_RDV + " WHERE " + MySQLiteHelper.COLUMN_ID_RDV + " = " + id;
        Cursor cursor = crud.getDatabase().rawQuery(sql, null);
        crud.close();
        return this.cursorToRDV(cursor);
    }

    public ModelRDV getRDV(long date) {
        crud.open();
        String sql = "SELECT * FROM " + MySQLiteHelper.TABLE_RDV + " WHERE " + MySQLiteHelper.COLUMN_DATE_RDV + " = " + date;
        Cursor cursor = crud.getDatabase().rawQuery(sql, null);
        crud.close();
        return this.cursorToRDV(cursor);
    }

    public ModelRDV getRDV(String nom) {
        crud.open();
        String sql = "SELECT * FROM " + MySQLiteHelper.TABLE_RDV + " WHERE " + MySQLiteHelper.COLUMN_NOM_RDV + " = " + nom;
        Cursor cursor = crud.getDatabase().rawQuery(sql, null);
        crud.close();
        return this.cursorToRDV(cursor);
    }

    private ModelRDV cursorToRDV(Cursor cursor) {
        ModelRDV rdv = new ModelRDV();
        rdv.setId(cursor.getLong(0));
        rdv.setNom(cursor.getString(1));
        rdv.setDate(cursor.getLong(2));
        rdv.setLieu(cursor.getString(3));
        rdv.setMode(cursor.getLong(4));
        return rdv;
    }


}
