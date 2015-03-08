package com.remindus.projetcommun.remindus.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.remindus.projetcommun.remindus.basededonnees.MySQLiteHelper;
import com.remindus.projetcommun.remindus.basededonnees.utilities.CRUD;
import com.remindus.projetcommun.remindus.model.ModelGroupe;
import com.remindus.projetcommun.remindus.model.ModelGroupexContact;
import com.remindus.projetcommun.remindus.model.ModelMsgProg;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ilanmalka on 08/03/15.
 */
public class DAOGroupexContact {
    private MySQLiteHelper dbHelper;
    private String[] allColumns = {
            MySQLiteHelper.COLUMN_ID_GROUPE,
            MySQLiteHelper.COLUMN_ID_CONTACT
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
            values.put(MySQLiteHelper.COLUMN_ID_GROUPE, idgroupe);
            values.put(MySQLiteHelper.COLUMN_ID_CONTACT, idcontact);

            this.crud.open();
            boolean insert = crud.insert(MySQLiteHelper.TABLE_GROUPESxCONTACTS, values);
            this.crud.close();

            if (insert) {
                return 0; // insertion ok
            }
            return 1; // pb d'insertion
    }

    public void deleteGxC(ModelMsgProg modelMsgProg) {
        long id = modelMsgProg.getIdMsgProg();
        System.out.println("Contact deleted with id: " + id);
        crud.getDatabase().delete(MySQLiteHelper.TABLE_MSG_PROG, MySQLiteHelper.COLUMN_ID_MSG_PROG
                + " = " + id, null);
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

    public ModelGroupexContact getGxC(int idg, int idc){
        crud.open();
        String sql = "SELECT * FROM " + MySQLiteHelper.TABLE_GROUPESxCONTACTS + " WHERE "+ MySQLiteHelper.COLUMN_ID_CONTACT + " = " + idc + " AND " + MySQLiteHelper.COLUMN_ID_GROUPE + " = " + idg;
        Cursor cursor = crud.getDatabase().rawQuery(sql, null);
        crud.close();
        return this.cursorToGxC(cursor);
    }


    private ModelGroupexContact cursorToGxC(Cursor cursor) {
        ModelGroupexContact modelGroupexContact = new ModelGroupexContact();
        modelGroupexContact.setIdgroupe(cursor.getLong(0));
        modelGroupexContact.getIdcontact(cursor.getLong(1));
        return modelGroupexContact;
    }


}
