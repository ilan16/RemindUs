package com.remindus.projetcommun.remindus.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.remindus.projetcommun.remindus.basededonnees.MySQLiteHelper;
import com.remindus.projetcommun.remindus.basededonnees.utilities.CRUD;
import com.remindus.projetcommun.remindus.model.ModelGroupexContact;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ilanmalka on 08/03/15.
 */
public class DAOGroupexContact {
    private MySQLiteHelper dbHelper;
    private String[] allColumns = {
            MySQLiteHelper.COLUMN_ID_GROUPE_GC,
            MySQLiteHelper.COLUMN_ID_CONTACT_GC,
            MySQLiteHelper.COLUMN_DATE_AJOUT
    };
    private CRUD crud;

    public DAOGroupexContact(Context context) {
        crud = new CRUD(context);
    }

    public CRUD getCrud() {
        return crud;
    }

    public void setCrud(CRUD crud) {
        this.crud = crud;
    }

    public int insertGxC(long idgroupe, long idcontact) {

        ContentValues values = new ContentValues();
        values.put(MySQLiteHelper.COLUMN_ID_GROUPE_GC, idgroupe);
        values.put(MySQLiteHelper.COLUMN_ID_CONTACT_GC, idcontact);

        this.crud.open();
        boolean insert = crud.insert(MySQLiteHelper.TABLE_GROUPESxCONTACTS, values);
        this.crud.close();

        if (insert) {
            return 0; // insertion ok
        }
        return 1; // pb d'insertion
    }

    public boolean deleteGxC(ModelGroupexContact modelGroupexContact) {
        long idcontact = modelGroupexContact.getIdcontact();
        long idgroupe = modelGroupexContact.getIdgroupe();
        this.crud.open();
        boolean delete = crud.delete(MySQLiteHelper.TABLE_GROUPESxCONTACTS, MySQLiteHelper.COLUMN_ID_CONTACT_GC
                + " = " + idcontact + " AND " + MySQLiteHelper.COLUMN_ID_GROUPE_GC + " = " + idgroupe);
        if (delete) {
            Log.i("DELETE", "effectué");
            return true;
        }
        Log.i("DELETE", "merde");
        return false;
    }

    public List<ModelGroupexContact> getAllGxC() {
        List<ModelGroupexContact> modelGroupexContacts = new ArrayList<ModelGroupexContact>();

        Cursor cursor = crud.getDatabase().query(MySQLiteHelper.TABLE_GROUPESxCONTACTS,
                allColumns, null, null, null, null, null);

        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            ModelGroupexContact modelGroupexContact = cursorToGxC(cursor);
            modelGroupexContacts.add(modelGroupexContact);
            cursor.moveToNext();

        }
        cursor.close();
        return modelGroupexContacts;
    }

    public ModelGroupexContact getGxC(int idg, int idc) {
        crud.open();
        String sql = "SELECT * FROM " + MySQLiteHelper.TABLE_GROUPESxCONTACTS + " WHERE " + MySQLiteHelper.COLUMN_ID_CONTACT_GC + " = " + idc + " AND " + MySQLiteHelper.COLUMN_ID_GROUPE_GC + " = " + idg;
        Cursor cursor = crud.getDatabase().rawQuery(sql, null);
        crud.close();
        return this.cursorToGxC(cursor);
    }

    //permet de récupérer toutes les lignes faisant parties du mm groupe
    public ModelGroupexContact getGxC(int idg) {
        crud.open();
        String sql = "SELECT * FROM " + MySQLiteHelper.TABLE_GROUPESxCONTACTS + " WHERE " + MySQLiteHelper.COLUMN_ID_GROUPE_GC + " = " + idg;
        Cursor cursor = crud.getDatabase().rawQuery(sql, null);
        crud.close();
        return this.cursorToGxC(cursor);
    }

    public boolean deleteGxC(int idgroupe) {
        this.crud.open();
        boolean delete = crud.delete(MySQLiteHelper.TABLE_GROUPESxCONTACTS, MySQLiteHelper.COLUMN_ID_GROUPE_GC + " = " + idgroupe);
        if (delete) {
            Log.i("DELETE", "effectué");
            return true;
        }
        Log.i("DELETE", "merde");
        return false;
    }

    private ModelGroupexContact cursorToGxC(Cursor cursor) {
        ModelGroupexContact modelGroupexContact = new ModelGroupexContact();
        modelGroupexContact.setIdcontact(cursor.getLong(0));
        modelGroupexContact.setIdgroupe(cursor.getLong(1));
        modelGroupexContact.setDateAjout(cursor.getLong(2));
        return modelGroupexContact;
    }

}
