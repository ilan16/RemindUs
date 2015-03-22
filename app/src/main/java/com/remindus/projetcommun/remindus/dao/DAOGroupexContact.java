package com.remindus.projetcommun.remindus.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.remindus.projetcommun.remindus.basededonnees.MySQLiteHelper;
import com.remindus.projetcommun.remindus.basededonnees.utilities.CRUD;
import com.remindus.projetcommun.remindus.model.ModelGroupe;
import com.remindus.projetcommun.remindus.model.ModelGroupexContact;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ilanmalka on 08/03/15.
 */
public class DAOGroupexContact extends IDAO {


    public DAOGroupexContact(Context context) {
        super(context);
        String[] allColumns = {
                MySQLiteHelper.COLUMN_ID_GROUPE_GC,
                MySQLiteHelper.COLUMN_ID_CONTACT_GC,
                MySQLiteHelper.COLUMN_DATE_AJOUT
        };
        setAllColumns(allColumns);
    }


    public int insertGxC(long idgroupe, long idcontact) {

        ContentValues values = new ContentValues();
        values.put(MySQLiteHelper.COLUMN_ID_GROUPE_GC, idgroupe);
        values.put(MySQLiteHelper.COLUMN_ID_CONTACT_GC, idcontact);

        getCrud().open();
        boolean insert = getCrud().insert(MySQLiteHelper.TABLE_GROUPESxCONTACTS, values);
        getCrud().close();

        if (insert) {
            return 0; // insertion ok
        }
        return 1; // pb d'insertion
    }

    public boolean deleteGxC(ModelGroupexContact modelGroupexContact) {
        long idcontact = modelGroupexContact.getIdcontact();
        long idgroupe = modelGroupexContact.getIdgroupe();
        getCrud().open();
        boolean delete = getCrud().delete(MySQLiteHelper.TABLE_GROUPESxCONTACTS, MySQLiteHelper.COLUMN_ID_CONTACT_GC
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

        Cursor cursor = getCrud().getDatabase().query(MySQLiteHelper.TABLE_GROUPESxCONTACTS,
                getAllColumns(), null, null, null, null, null);

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
        getCrud().open();
        String sql = "SELECT * FROM " + MySQLiteHelper.TABLE_GROUPESxCONTACTS + " WHERE " + MySQLiteHelper.COLUMN_ID_CONTACT_GC + " = " + idc + " AND " + MySQLiteHelper.COLUMN_ID_GROUPE_GC + " = " + idg;
        Cursor cursor = getCrud().getDatabase().rawQuery(sql, null);
        ModelGroupexContact modelGroupexContact = new ModelGroupexContact();
        while (cursor.moveToNext()) {
            modelGroupexContact = cursorToGxC(cursor);
        }
        cursor.close();
        return modelGroupexContact;
    }

    //permet de récupérer toutes les lignes faisant parties du mm groupe
    public ModelGroupexContact getGxC(int idg) {
        getCrud().open();
        String sql = "SELECT * FROM " + MySQLiteHelper.TABLE_GROUPESxCONTACTS + " WHERE " + MySQLiteHelper.COLUMN_ID_GROUPE_GC + " = " + idg;
        Cursor cursor = getCrud().getDatabase().rawQuery(sql, null);
        ModelGroupexContact modelGroupexContact = new ModelGroupexContact();
        while (cursor.moveToNext()) {
            modelGroupexContact = cursorToGxC(cursor);
        }
        cursor.close();
        return modelGroupexContact;
    }

    public boolean deleteGxC(int idgroupe) {
        getCrud().open();
        boolean delete = getCrud().delete(MySQLiteHelper.TABLE_GROUPESxCONTACTS, MySQLiteHelper.COLUMN_ID_GROUPE_GC + " = " + idgroupe);
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
