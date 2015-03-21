package com.remindus.projetcommun.remindus.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.remindus.projetcommun.remindus.basededonnees.MySQLiteHelper;
import com.remindus.projetcommun.remindus.basededonnees.utilities.CRUD;
import com.remindus.projetcommun.remindus.controller.UtilitaireDate;
import com.remindus.projetcommun.remindus.model.ModelModelMsg;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ilanmalka on 11/03/15.
 */
public class DAOModelMsg {

    private MySQLiteHelper dbHelper;
    private UtilitaireDate utilitaireDate;
    private String[] allColumns = {
            MySQLiteHelper.COLUMN_ID_MODEL_MSG,
            MySQLiteHelper.COLUMN_TITRE_MODEL_MSG,
            MySQLiteHelper.COLUMN_CONTENU_MODEL_MSG,
            MySQLiteHelper.COLUMN_DATE_CREATION_MODEL_MSG
    };
    private CRUD crud;

    public DAOModelMsg(Context context) {
        crud = new CRUD(context);
        utilitaireDate = new UtilitaireDate();
    }

    public CRUD getCrud() {
        return crud;
    }

    public void setCrud(CRUD crud) {
        this.crud = crud;
    }

    public int insertModelMsg(String titre, String contenu) {
        if (!this.isExist(titre)) {
            ContentValues values = new ContentValues();
            values.put(MySQLiteHelper.COLUMN_TITRE_MODEL_MSG, titre);
            values.put(MySQLiteHelper.COLUMN_DATE_CREATION_MODEL_MSG, utilitaireDate.dateActuelle());
            values.put(MySQLiteHelper.COLUMN_CONTENU_MODEL_MSG, contenu);
            this.crud.open();
            boolean insert = crud.insert(MySQLiteHelper.TABLE_MODEL_MSG, values);
            this.crud.close();
            if (insert) {
                return 0; // insertion ok
            }
            return 1; // pb d'insertion
        } else {
            return 2; // groupe existe déjà
        }
    }


    public boolean deleteModelMsg(ModelModelMsg modelModelMsg) {
        long id = modelModelMsg.getId();
        this.crud.open();
        boolean delete = crud.delete(MySQLiteHelper.TABLE_MODEL_MSG, MySQLiteHelper.COLUMN_ID_MODEL_MSG
                + " = " + id);
        // this.crud.close();
        if (delete) {
            Log.i("DELETE", "effectué");
            return true;
        }
        Log.i("DELETE", "merde");
        return false;

    }

    public int updateModelMsg(ModelModelMsg modelModelMsg, String titre, String contenu) {
        String id = "" + modelModelMsg.getId();
        Log.i("ID", id);
//        if(!this.isExist(titre)) {
        this.crud.open();
        ContentValues values = new ContentValues();
        values.put(MySQLiteHelper.COLUMN_TITRE_MODEL_MSG, titre);
        values.put(MySQLiteHelper.COLUMN_CONTENU_MODEL_MSG, contenu);
        boolean update = crud.update(MySQLiteHelper.TABLE_MODEL_MSG, values, MySQLiteHelper.COLUMN_ID_MODEL_MSG, new String[]{id});
        if (update) {
            Log.i("UPDATE", "BON");
            return 0; //si l'update fonctionne
        }
        return 1; // pb update
//        }
//        return 2; // le nom existe déjà donc pas possible de maj avec ce nom
    }

    public List<ModelModelMsg> getAllModelMsg() {
        List<ModelModelMsg> modelModelMsgs = new ArrayList<ModelModelMsg>();

        Cursor cursor = crud.getDatabase().query(MySQLiteHelper.TABLE_MODEL_MSG,
                allColumns, null, null, null, null, null);

        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            ModelModelMsg modelModelMsg = cursorToModelMsg(cursor);
            modelModelMsgs.add(modelModelMsg);
            cursor.moveToNext();

        }
        cursor.close();
        return modelModelMsgs;
    }

    public ModelModelMsg getModelMsg(int id) {
        crud.open();
        String sql = "SELECT * FROM " + MySQLiteHelper.TABLE_MODEL_MSG + " WHERE " + MySQLiteHelper.COLUMN_ID_MODEL_MSG + " = " + id;
        Cursor cursor = crud.getDatabase().rawQuery(sql, null);
        crud.close();
        return this.cursorToModelMsg(cursor);
    }

    public ModelModelMsg getGroupe(String titre) {
        crud.open();
        String sql = "SELECT * FROM " + MySQLiteHelper.TABLE_MODEL_MSG + " WHERE " + MySQLiteHelper.COLUMN_TITRE_MODEL_MSG + " = " + titre;
        Cursor cursor = crud.getDatabase().rawQuery(sql, null);
        crud.close();
        return this.cursorToModelMsg(cursor);
    }

    private ModelModelMsg cursorToModelMsg(Cursor cursor) {
        ModelModelMsg modelModelMsg = new ModelModelMsg();
        modelModelMsg.setId(cursor.getLong(0));
        modelModelMsg.setTitre(cursor.getString(1));
        modelModelMsg.setContenu(cursor.getString(2));
        modelModelMsg.setDateCreation(cursor.getLong(3));
        return modelModelMsg;
    }

    private boolean isExist(String titre) {
        this.crud.open();
        String sql = "SELECT * FROM " + MySQLiteHelper.TABLE_MODEL_MSG + " WHERE " + MySQLiteHelper.COLUMN_TITRE_MODEL_MSG + " = '" + titre + "'";
        Cursor cursor = crud.getDatabase().rawQuery(sql, null);

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
