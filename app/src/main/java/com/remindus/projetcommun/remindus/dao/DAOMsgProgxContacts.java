package com.remindus.projetcommun.remindus.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.remindus.projetcommun.remindus.basededonnees.MySQLiteHelper;
import com.remindus.projetcommun.remindus.basededonnees.utilities.CRUD;
import com.remindus.projetcommun.remindus.model.ModelMsgProg;
import com.remindus.projetcommun.remindus.model.ModelMsgProgxContacts;
import com.remindus.projetcommun.remindus.model.ModelRDVxContacts;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ilanmalka on 22/03/15.
 */
public class DAOMsgProgxContacts {
    private MySQLiteHelper dbHelper;
    private String[] allColumns = {
            MySQLiteHelper.COLUMN_ID_MSG_PROG_MPxC,
            MySQLiteHelper.COLUMN_ID_CONTACT_MPxC
    };
    private CRUD crud;

    public DAOMsgProgxContacts(Context context) {
        crud = new CRUD(context);
    }

    public CRUD getCrud() {
        return crud;
    }

    public void setCrud(CRUD crud) {
        this.crud = crud;
    }

    public int insertMsgProgxC(long idmsg, long idcontact) {
        ContentValues values = new ContentValues();
        values.put(MySQLiteHelper.COLUMN_ID_MSG_PROG_MPxC, idmsg);
        values.put(MySQLiteHelper.COLUMN_ID_CONTACT_MPxC, idcontact);

        this.crud.open();
        boolean insert = crud.insert(MySQLiteHelper.TABLE_MSG_PROGxCONTACTS, values);

        if (insert) {
            Log.i("INSERER", "" + idmsg + " " + idcontact);
            return 0; // insertion ok
        }
        return 1; // pb d'insertion
    }

    public boolean deleteMsgProgxC(ModelMsgProgxContacts modelMsgProgxContacts) {
        long idcontact = modelMsgProgxContacts.getIdContact();
        long idMsgProg = modelMsgProgxContacts.getIdMsgProg();
        this.crud.open();
        boolean delete = crud.delete(MySQLiteHelper.TABLE_MSG_PROGxCONTACTS, MySQLiteHelper.COLUMN_ID_CONTACT_MPxC
                + " = " + idcontact + " AND " + MySQLiteHelper.COLUMN_ID_MSG_PROG_MPxC + " = " + idMsgProg);
        if (delete) {
            Log.i("DELETE", "effectué");
            return true;
        }
        Log.i("DELETE", "merde");
        return false;
    }

    public List<ModelMsgProgxContacts> getAllMsgProgxC() {
        List<ModelMsgProgxContacts> modelMsgProgxContactses = new ArrayList<ModelMsgProgxContacts>();

        Cursor cursor = crud.getDatabase().query(MySQLiteHelper.TABLE_MSG_PROGxCONTACTS,
                allColumns, null, null, null, null, null);

        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            ModelMsgProgxContacts modelMsgProgxContacts = cursorToMsgProgxC(cursor);
            modelMsgProgxContactses.add(modelMsgProgxContacts);
            cursor.moveToNext();

        }
        cursor.close();
        return modelMsgProgxContactses;
    }

    public List<ModelMsgProgxContacts> getAllMsgProgxC(long idmsgprog) {
        List<ModelMsgProgxContacts> modelMsgProgxContactses = new ArrayList<ModelMsgProgxContacts>();
        crud.open();
        String sql = "SELECT * FROM " + MySQLiteHelper.TABLE_MSG_PROGxCONTACTS + " WHERE " + MySQLiteHelper.COLUMN_ID_MSG_PROG_MPxC + " = " + idmsgprog;
        Cursor cursor = crud.getDatabase().rawQuery(sql, null);
        while (cursor.moveToNext()) {
            ModelMsgProgxContacts modelMsgProgxContacts = cursorToMsgProgxC(cursor);
           modelMsgProgxContactses.add(modelMsgProgxContacts);
        }
        cursor.close();
        return modelMsgProgxContactses;
    }

    public boolean deleteMsgProgxC(long idc) {
        this.crud.open();
        boolean delete = crud.delete(MySQLiteHelper.TABLE_MSG_PROGxCONTACTS, MySQLiteHelper.COLUMN_ID_CONTACT_MPxC + " = " + idc);
        if (delete) {
            Log.i("DELETE", "effectué");
            return true;
        }
        Log.i("DELETE", "merde");
        return false;
    }

    private ModelMsgProgxContacts cursorToMsgProgxC(Cursor cursor) {
        ModelMsgProgxContacts modelMsgProgxContacts = new ModelMsgProgxContacts();
        modelMsgProgxContacts.setIdMsgProg(cursor.getLong(0));
        modelMsgProgxContacts.setIdContact(cursor.getLong(1));
        return modelMsgProgxContacts;
    }
}
