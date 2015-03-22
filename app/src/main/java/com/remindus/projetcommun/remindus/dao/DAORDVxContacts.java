package com.remindus.projetcommun.remindus.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.remindus.projetcommun.remindus.basededonnees.MySQLiteHelper;
import com.remindus.projetcommun.remindus.basededonnees.utilities.CRUD;
import com.remindus.projetcommun.remindus.model.ModelRDVxContacts;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ilanmalka on 18/03/15.
 */
public class DAORDVxContacts extends IDAO {


    public DAORDVxContacts(Context context) {
        super(context);
        String[] allColumns = {
                MySQLiteHelper.COLUMN_ID_RDV_RDVxC,
                MySQLiteHelper.COLUMN_ID_CONTACT_RDVxC
        };
        setAllColumns(allColumns);
    }


    public int insertRDVxC(long idrdv, long idcontact) {
        ContentValues values = new ContentValues();
        values.put(MySQLiteHelper.COLUMN_ID_RDV_RDVxC, idrdv);
        values.put(MySQLiteHelper.COLUMN_ID_CONTACT_RDVxC, idcontact);

        getCrud().open();
        boolean insert = getCrud().insert(MySQLiteHelper.TABLE_RDVxCONTACTS, values);

        if (insert) {
            Log.i("INSERER", "" + idrdv + " " + idcontact);
            return 0; // insertion ok
        }
        return 1; // pb d'insertion
    }

    public boolean deleteRDVxC(ModelRDVxContacts modelRDVxContacts) {
        long idcontact = modelRDVxContacts.getIdcontact();
        long idrdv = modelRDVxContacts.getIdrdv();
        getCrud().open();
        boolean delete = getCrud().delete(MySQLiteHelper.TABLE_RDVxCONTACTS, MySQLiteHelper.COLUMN_ID_CONTACT_RDVxC
                + " = " + idcontact + " AND " + MySQLiteHelper.COLUMN_ID_RDV_RDVxC + " = " + idrdv);
        if (delete) {
            Log.i("DELETE", "effectué");
            return true;
        }
        Log.i("DELETE", "merde");
        return false;
    }

    public List<ModelRDVxContacts> getAllRDVxC() {
        List<ModelRDVxContacts> modelRDVxContacts = new ArrayList<ModelRDVxContacts>();

        Cursor cursor = getCrud().getDatabase().query(MySQLiteHelper.TABLE_RDVxCONTACTS,
                getAllColumns(), null, null, null, null, null);

        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            ModelRDVxContacts modelRDVxContact = cursorToRDVxC(cursor);
            modelRDVxContacts.add(modelRDVxContact);
            cursor.moveToNext();

        }
        cursor.close();
        return modelRDVxContacts;
    }

    public List<ModelRDVxContacts> getAllRDVxC(long idrdv) {
        List<ModelRDVxContacts> modelRDVxContactses = new ArrayList<ModelRDVxContacts>();
        getCrud().open();
        String sql = "SELECT * FROM " + MySQLiteHelper.TABLE_RDVxCONTACTS + " WHERE " + MySQLiteHelper.COLUMN_ID_RDV_RDVxC + " = " + idrdv;
        Cursor cursor = getCrud().getDatabase().rawQuery(sql, null);
        while (cursor.moveToNext()) {
            ModelRDVxContacts modelRDVxContacts = cursorToRDVxC(cursor);
            modelRDVxContactses.add(modelRDVxContacts);
        }
        cursor.close();
        return modelRDVxContactses;
    }

    public boolean deleteRDVxC(long idrdv) {
        getCrud().open();
        boolean delete = getCrud().delete(MySQLiteHelper.TABLE_RDVxCONTACTS, MySQLiteHelper.COLUMN_ID_CONTACT_RDVxC + " = " + idrdv);
        if (delete) {
            Log.i("DELETE", "effectué");
            return true;
        }
        Log.i("DELETE", "merde");
        return false;
    }

    private ModelRDVxContacts cursorToRDVxC(Cursor cursor) {
        ModelRDVxContacts modelRDVxContacts = new ModelRDVxContacts();
        modelRDVxContacts.setIdrdv(cursor.getLong(0));
        modelRDVxContacts.setIdcontact(cursor.getLong(1));
        return modelRDVxContacts;
    }


}
