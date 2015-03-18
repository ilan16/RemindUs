package com.remindus.projetcommun.remindus.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.remindus.projetcommun.remindus.basededonnees.MySQLiteHelper;
import com.remindus.projetcommun.remindus.basededonnees.utilities.CRUD;
import com.remindus.projetcommun.remindus.model.ModelContact;
import com.remindus.projetcommun.remindus.model.ModelMsgProg;
import com.remindus.projetcommun.remindus.model.ModelRDV;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by ilanmalka on 08/03/15.
 */
public class DAOMsgProg {
    private MySQLiteHelper dbHelper;
    private String[] allColumns = {
            MySQLiteHelper.COLUMN_ID_MSG_PROG,
            MySQLiteHelper.COLUMN_TITRE_MSG_PROG,
            MySQLiteHelper.COLUMN_DATE_MSG_PROG,
            MySQLiteHelper.COLUMN_DATESTRING_MSG_PROG,
            MySQLiteHelper.COLUMN_MSG_PROG
    };
    private CRUD crud;

    public DAOMsgProg(Context context) {
        crud = new CRUD(context);
    }

    public CRUD getCrud() {
        return crud;
    }

    public void setCrud(CRUD crud) {
        this.crud = crud;
    }

    public int insertMsgProg(String titre, long date, String datestring, String contenu) {
        //if(!this.isExist(titre)) {

            ContentValues values = new ContentValues();
            values.put(MySQLiteHelper.COLUMN_TITRE_MSG_PROG, titre);
            values.put(MySQLiteHelper.COLUMN_DATE_MSG_PROG, date);
            values.put(MySQLiteHelper.COLUMN_DATESTRING_MSG_PROG, datestring);
            values.put(MySQLiteHelper.COLUMN_MSG_PROG, contenu);

            this.crud.open();
            boolean insert = crud.insert(MySQLiteHelper.TABLE_MSG_PROG, values);
            this.crud.close();

            if (insert) {
                return 0; // insertion ok
            }
            return 1; // pb d'insertion
        //} else {
        //    return 2; // msg existe déjà
        //}
    }

    public boolean deleteMsgProg(ModelMsgProg modelMsgProg) {
        long id = modelMsgProg.getIdMsgProg();
        this.crud.open();
        boolean delete = crud.delete(MySQLiteHelper.TABLE_MSG_PROG, MySQLiteHelper.COLUMN_ID_MSG_PROG
                + " = " + id);
        // this.crud.close();
        if (delete) {
            Log.i("DELETE", "effectué");
            return true;
        }
        Log.i("DELETE", "merde");
        return false;
    }

    public List<ModelMsgProg> getAllMsgProg() {
        List<ModelMsgProg> msgProgs = new ArrayList<ModelMsgProg>();

        Cursor cursor = crud.getDatabase().query(MySQLiteHelper.TABLE_MSG_PROG,
                allColumns, null, null, null, null, null);

        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            ModelMsgProg msgProg = cursorToMshProg(cursor);
            msgProgs.add(msgProg);
            cursor.moveToNext();

        }
        cursor.close();
        return msgProgs;
    }

    public ModelMsgProg getMsgProg(int id){
        crud.open();
        String sql = "SELECT * FROM " + MySQLiteHelper.TABLE_MSG_PROG + " WHERE "+ MySQLiteHelper.COLUMN_ID_MSG_PROG + " = " + id;
        Cursor cursor = crud.getDatabase().rawQuery(sql, null);
        crud.close();
        return this.cursorToMshProg(cursor);
    }

    public ModelMsgProg getMsgProg(String titre){
        crud.open();
        String sql = "SELECT * FROM " + MySQLiteHelper.TABLE_MSG_PROG + " WHERE "+ MySQLiteHelper.COLUMN_TITRE_MSG_PROG + " = " + titre;
        Cursor cursor = crud.getDatabase().rawQuery(sql, null);
        crud.close();
        return this.cursorToMshProg(cursor);
    }

    private ModelMsgProg cursorToMshProg(Cursor cursor) {
        ModelMsgProg msgProg = new ModelMsgProg();
        msgProg.setIdMsgProg(cursor.getLong(0));
        msgProg.setTitre(cursor.getString(1));
        msgProg.setDate(cursor.getLong(2));
        msgProg.setDatestring(cursor.getString(3));
        msgProg.setMsgProg(cursor.getString(4));
        return msgProg;
    }

    private boolean isExist(String titre){
        String sql = "SELECT * FROM " + MySQLiteHelper.TABLE_MSG_PROG + " WHERE "+ MySQLiteHelper.COLUMN_TITRE_MSG_PROG + " = \"" + titre + "\"";
        Cursor cursor = this.crud.getDatabase().rawQuery(sql, null);
        if (cursor.getCount() > 0){
            return true;
        }
        return false;
    }

}
