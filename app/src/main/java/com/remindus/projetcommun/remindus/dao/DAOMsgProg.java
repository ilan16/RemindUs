package com.remindus.projetcommun.remindus.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.remindus.projetcommun.remindus.basededonnees.MySQLiteHelper;
import com.remindus.projetcommun.remindus.basededonnees.utilities.CRUD;
import com.remindus.projetcommun.remindus.model.ModelModelMsg;
import com.remindus.projetcommun.remindus.model.ModelMsgProg;

import java.util.ArrayList;
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

    public int updateMsgProg(ModelMsgProg modelMsgProg, String titre, long date, String dateString, String contenu) {
        String id = "" + modelMsgProg.getIdMsgProg();
        Log.i("ID", id);
        if(!titre.equals(modelMsgProg.getTitre())) {
            if (!this.isExist(titre)) {
                this.crud.open();
                ContentValues values = new ContentValues();
                values.put(MySQLiteHelper.COLUMN_TITRE_MSG_PROG, titre);
                values.put(MySQLiteHelper.COLUMN_DATE_MSG_PROG, date);
                values.put(MySQLiteHelper.COLUMN_DATESTRING_MSG_PROG, dateString);
                values.put(MySQLiteHelper.COLUMN_MSG_PROG, contenu);
                boolean update = crud.update(MySQLiteHelper.TABLE_MSG_PROG, values, MySQLiteHelper.COLUMN_ID_MSG_PROG, new String[]{id});
                if (update) {
                    Log.i("UPDATE", "BON");
                    return 0; //si l'update fonctionne
                }
                return 1; // pb update
            }
            return 2; // le nom existe déjà donc pas possible de maj avec ce nom
        }else {
            this.crud.open();

            ContentValues values = new ContentValues();
            values.put(MySQLiteHelper.COLUMN_TITRE_MSG_PROG, titre);
            values.put(MySQLiteHelper.COLUMN_DATE_MSG_PROG, date);
            values.put(MySQLiteHelper.COLUMN_DATESTRING_MSG_PROG, dateString);
            values.put(MySQLiteHelper.COLUMN_MSG_PROG, contenu);

            boolean update = crud.update(MySQLiteHelper.TABLE_MSG_PROG, values, MySQLiteHelper.COLUMN_ID_MSG_PROG, new String[]{id});
            if (update) {
                Log.i("UPDATE", "BON");
                return 0; //si l'update fonctionne
            }
            return 1; // pb update
        }
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
            ModelMsgProg msgProg = cursorToMsgProg(cursor);
            msgProgs.add(msgProg);
            cursor.moveToNext();

        }
        cursor.close();
        return msgProgs;
    }

    public ModelMsgProg getMsgProg(int id) {
        crud.open();
        String sql = "SELECT * FROM " + MySQLiteHelper.TABLE_MSG_PROG + " WHERE " + MySQLiteHelper.COLUMN_ID_MSG_PROG + " = " + id;
        Cursor cursor = crud.getDatabase().rawQuery(sql, null);
        crud.close();
        return this.cursorToMsgProg(cursor);
    }

    public ModelMsgProg getMsgProg(String titre) {
        crud.open();
        ModelMsgProg modelMsgProg = new ModelMsgProg();
        String sql = "SELECT * FROM " + MySQLiteHelper.TABLE_MSG_PROG + " WHERE " + MySQLiteHelper.COLUMN_TITRE_MSG_PROG + " = '" + titre+"'";
        Cursor cursor = crud.getDatabase().rawQuery(sql, null);
        while (cursor.moveToNext()) {
            modelMsgProg = cursorToMsgProg(cursor);
        }
        cursor.close();
        return modelMsgProg;
    }

    private ModelMsgProg cursorToMsgProg(Cursor cursor) {
        ModelMsgProg msgProg = new ModelMsgProg();
        msgProg.setIdMsgProg(cursor.getLong(0));
        msgProg.setTitre(cursor.getString(1));
        msgProg.setDate(cursor.getLong(2));
        msgProg.setDatestring(cursor.getString(3));
        msgProg.setMsgProg(cursor.getString(4));
        return msgProg;
    }

    private boolean isExist(String titre) {
        String sql = "SELECT * FROM " + MySQLiteHelper.TABLE_MSG_PROG + " WHERE " + MySQLiteHelper.COLUMN_TITRE_MSG_PROG + " = ?";

        Cursor cursor = this.crud.getDatabase().rawQuery(sql, new String[]{titre});

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


