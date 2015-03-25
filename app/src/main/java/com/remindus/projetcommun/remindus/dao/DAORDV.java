package com.remindus.projetcommun.remindus.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.remindus.projetcommun.remindus.basededonnees.MySQLiteHelper;
import com.remindus.projetcommun.remindus.basededonnees.utilities.CRUD;
import com.remindus.projetcommun.remindus.controller.UtilitaireDate;
import com.remindus.projetcommun.remindus.model.ModelRDV;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ilanmalka on 11/03/15.
 */
public class DAORDV extends IDAO {

    public DAORDV(Context context) {
        super(context);
        //rempli le allColumn avec le attributs de la table rdv
        String[] allColumns = {
                MySQLiteHelper.COLUMN_ID_RDV,
                MySQLiteHelper.COLUMN_NOM_RDV,
                MySQLiteHelper.COLUMN_DATE_DEBUT_RDV,
                MySQLiteHelper.COLUMN_DATE_FIN_RDV,
                MySQLiteHelper.COLUMN_DATESTRING_RDV,
                MySQLiteHelper.COLUMN_LIEU_RDV,
                MySQLiteHelper.COLUMN_MODE_TEL_RDV
        };
        setAllColumns(allColumns);

    }

    public int insertRDV(String nom, long datedebut, long datefin, String datestring, String lieu, long mode) {
        ContentValues values = new ContentValues();
        values.put(MySQLiteHelper.COLUMN_NOM_RDV, nom);
        values.put(MySQLiteHelper.COLUMN_DATE_DEBUT_RDV, datedebut);
        values.put(MySQLiteHelper.COLUMN_DATE_FIN_RDV, datefin);
        values.put(MySQLiteHelper.COLUMN_DATESTRING_RDV, datestring);
        values.put(MySQLiteHelper.COLUMN_LIEU_RDV, lieu);
        values.put(MySQLiteHelper.COLUMN_MODE_TEL_RDV, mode);
        getCrud().open();
        boolean insert = getCrud().insert(MySQLiteHelper.TABLE_RDV, values);
        getCrud().close();
        if (insert) {
            return 0; // insertion ok
        }
        return 1; // pb d'insertion

    }


    public boolean deleteRDV(ModelRDV modelRDV) {
        long id = modelRDV.getId();
        getCrud().open();
        boolean delete = getCrud().delete(MySQLiteHelper.TABLE_RDV, MySQLiteHelper.COLUMN_ID_RDV
                + " = " + id);
        // this.crud.close();
        if (delete) {
            Log.i("DELETE", "effectué");
            return true;
        }
        Log.i("DELETE", "merde");
        return false;

    }

    public int updateRDV(ModelRDV modelRDV, String nom, long datedebut, long datefin, String datestring, String lieu, long mode) {
        String id = "" + modelRDV.getId();
        Log.i("ID", id);
        getCrud().open();
        ContentValues values = new ContentValues();
        values.put(MySQLiteHelper.COLUMN_NOM_RDV, nom);
        values.put(MySQLiteHelper.COLUMN_DATE_DEBUT_RDV, datedebut);
        values.put(MySQLiteHelper.COLUMN_DATE_FIN_RDV, datefin);
        values.put(MySQLiteHelper.COLUMN_DATESTRING_RDV, datestring);
        values.put(MySQLiteHelper.COLUMN_LIEU_RDV, lieu);
        values.put(MySQLiteHelper.COLUMN_MODE_TEL_RDV, mode);
        boolean update = getCrud().update(MySQLiteHelper.TABLE_RDV, values, MySQLiteHelper.COLUMN_ID_RDV, new String[]{id});
        if (update) {
            Log.i("UPDATE", "BON");
            return 0; //si l'update fonctionne
        }
        return 1; // pb update
    }

    public List<ModelRDV> getAllRDV() {
        List<ModelRDV> rdvs = new ArrayList<ModelRDV>();

        Cursor cursor = getCrud().getDatabase().query(MySQLiteHelper.TABLE_RDV,
                getAllColumns(), null, null, null, null, null);

        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            ModelRDV rdv = cursorToRDV(cursor);
            rdvs.add(rdv);
            cursor.moveToNext();

        }
        cursor.close();
        return rdvs;
    }

    public ModelRDV getRDV(long id) {
        getCrud().open();
        ModelRDV modelRDV = new ModelRDV();
        String sql = "SELECT * FROM " + MySQLiteHelper.TABLE_RDV + " WHERE " + MySQLiteHelper.COLUMN_ID_RDV + " = " + id;
        Cursor cursor = getCrud().getDatabase().rawQuery(sql, null);
        while (cursor.moveToNext()) {
            modelRDV = cursorToRDV(cursor);
        }
        cursor.close();
        return modelRDV;
    }

    public ModelRDV getRDV(String date) {
        getCrud().open();
        String sql = "SELECT * FROM " + MySQLiteHelper.TABLE_RDV + " WHERE " + MySQLiteHelper.COLUMN_DATESTRING_RDV + " = ?";
        Cursor cursor = getCrud().getDatabase().rawQuery(sql, new String[]{date});
        ModelRDV rdv = this.cursorToRDV(cursor);
        getCrud().close();
        return rdv;
    }

    public ModelRDV getIdRDV(String nom) {
        getCrud().open();
        String sql = "SELECT * FROM " + MySQLiteHelper.TABLE_RDV + " WHERE " + MySQLiteHelper.COLUMN_NOM_RDV + " = ?";
        Cursor cursor = getCrud().getDatabase().rawQuery(sql, new String[]{nom});
        ModelRDV rdv = new ModelRDV();
        while (cursor.moveToNext()) {
            rdv = cursorToRDV(cursor);
        }
        cursor.close();
        return rdv;
    }


    public ModelRDV cursorToRDV(Cursor cursor) {
        ModelRDV rdv = new ModelRDV();
        rdv.setId(cursor.getLong(0));
        rdv.setNom(cursor.getString(1));
        rdv.setDatedebut(cursor.getLong(2));
        rdv.setDatefin(cursor.getLong(3));
        rdv.setDateString(cursor.getString(4));
        rdv.setLieu(cursor.getString(5));
        rdv.setMode(cursor.getLong(6));
        return rdv;
    }

    public ModelRDV prochainRDV(long time) {
        String sql = "SELECT * FROM " + MySQLiteHelper.TABLE_RDV + " WHERE " + MySQLiteHelper.COLUMN_DATE_DEBUT_RDV + " >= "
                + ((getUtilitaireDate().dateActuelle())-time) + " ORDER BY " + MySQLiteHelper.COLUMN_DATE_DEBUT_RDV + " ASC";
        getCrud().open();
        Cursor cursor = getCrud().requeteGeneral(sql, null);
        ModelRDV modelRDV = new ModelRDV();
        while (cursor.moveToNext()) {
            modelRDV = this.cursorToRDV(cursor);
            break;
        }
        return modelRDV;
    }



}
