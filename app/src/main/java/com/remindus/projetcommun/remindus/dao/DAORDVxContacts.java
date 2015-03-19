package com.remindus.projetcommun.remindus.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.remindus.projetcommun.remindus.basededonnees.MySQLiteHelper;
import com.remindus.projetcommun.remindus.basededonnees.utilities.CRUD;
import com.remindus.projetcommun.remindus.model.ModelGroupexContact;
import com.remindus.projetcommun.remindus.model.ModelRDV;
import com.remindus.projetcommun.remindus.model.ModelRDVxContacts;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ilanmalka on 18/03/15.
 */
public class DAORDVxContacts {

    private MySQLiteHelper dbHelper;
    private String[] allColumns = {
            MySQLiteHelper.COLUMN_ID_RDV_RDVxC,
            MySQLiteHelper.COLUMN_ID_CONTACT_RDVxC
    };
    private CRUD crud;

    public DAORDVxContacts(Context context) {
        crud = new CRUD(context);
    }

    public CRUD getCrud() {
        return crud;
    }

    public void setCrud(CRUD crud) {
        this.crud = crud;
    }

    public int insertRDVxC(long idrdv, long idcontact) {
        ContentValues values = new ContentValues();
        values.put(MySQLiteHelper.COLUMN_ID_RDV_RDVxC, idrdv);
        values.put(MySQLiteHelper.COLUMN_ID_CONTACT_RDVxC, idcontact);

        this.crud.open();
        boolean insert = crud.insert(MySQLiteHelper.TABLE_RDVxCONTACTS, values);

        if (insert) {
            Log.i("INSERER", ""+idrdv+" "+idcontact);
            return 0; // insertion ok
        }
        return 1; // pb d'insertion
    }

    public boolean deleteRDVxC(ModelRDVxContacts modelRDVxContacts) {
        long idcontact = modelRDVxContacts.getIdcontact();
        long idrdv = modelRDVxContacts.getIdrdv();
        this.crud.open();
        boolean delete = crud.delete(MySQLiteHelper.TABLE_RDVxCONTACTS, MySQLiteHelper.COLUMN_ID_CONTACT_RDVxC
                + " = " + idcontact + " AND " + MySQLiteHelper.COLUMN_ID_RDV_RDVxC + " = " + idrdv);
        if (delete){
            Log.i("DELETE", "effectué");
            return true;
        }
        Log.i("DELETE", "merde");
        return false;
    }

    public List<ModelRDVxContacts> getAllRDVxC() {
        List<ModelRDVxContacts> modelRDVxContacts = new ArrayList<ModelRDVxContacts>();

        Cursor cursor = crud.getDatabase().query(MySQLiteHelper.TABLE_RDVxCONTACTS,
                allColumns, null, null, null, null, null);

        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            ModelRDVxContacts modelRDVxContact = cursorToRDVxC(cursor);
            modelRDVxContacts.add(modelRDVxContact);
            cursor.moveToNext();

        }
        cursor.close();
        return modelRDVxContacts;
    }

    public ModelRDVxContacts getRDVxC(int idrdv, int idc){
        crud.open();
        String sql = "SELECT * FROM " + MySQLiteHelper.TABLE_RDVxCONTACTS + " WHERE "+ MySQLiteHelper.COLUMN_ID_CONTACT_RDVxC + " = " + idc + " AND " + MySQLiteHelper.COLUMN_ID_RDV_RDVxC + " = " + idrdv;
        Cursor cursor = crud.getDatabase().rawQuery(sql, null);
        crud.close();
        return this.cursorToRDVxC(cursor);
    }

    public boolean deleteRDVxC(int idrdv) {
        this.crud.open();
        boolean delete = crud.delete(MySQLiteHelper.TABLE_RDVxCONTACTS, MySQLiteHelper.COLUMN_ID_CONTACT_RDVxC + " = " + idrdv);
        if (delete){
            Log.i("DELETE", "effectué");
            return true;
        }
        Log.i("DELETE", "merde");
        return false;
    }

    private ModelRDVxContacts cursorToRDVxC(Cursor cursor) {
        ModelRDVxContacts modelRDVxContacts = new ModelRDVxContacts();
        modelRDVxContacts.setIdcontact(cursor.getLong(0));
        modelRDVxContacts.setIdrdv(cursor.getLong(1));
        return modelRDVxContacts;
    }
}
