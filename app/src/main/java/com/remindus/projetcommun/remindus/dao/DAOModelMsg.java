package com.remindus.projetcommun.remindus.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.remindus.projetcommun.remindus.basededonnees.MySQLiteHelper;
import com.remindus.projetcommun.remindus.basededonnees.utilities.CRUD;
import com.remindus.projetcommun.remindus.model.ModelModelMsg;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ilanmalka on 11/03/15.
 */
public class DAOModelMsg extends IDAO {

    /**
     *
     * @param context
     */
    public DAOModelMsg(Context context) {
        super(context);
        //rempli le allColumn avec le attributs de la table model message
        String[] allColumns = {
                MySQLiteHelper.COLUMN_ID_MODEL_MSG,
                MySQLiteHelper.COLUMN_TITRE_MODEL_MSG,
                MySQLiteHelper.COLUMN_CONTENU_MODEL_MSG,
                MySQLiteHelper.COLUMN_DATE_CREATION_MODEL_MSG
        };
        setAllColumns(allColumns);
    }



    /**
     * @param titre, contenu
     * @return int
     */
    public int insertModelMsg(String titre, String contenu) {
        if (!this.isExist(titre)) {
            ContentValues values = new ContentValues();
            values.put(MySQLiteHelper.COLUMN_TITRE_MODEL_MSG, titre);
            values.put(MySQLiteHelper.COLUMN_DATE_CREATION_MODEL_MSG, getUtilitaireDate().dateActuelle());
            values.put(MySQLiteHelper.COLUMN_CONTENU_MODEL_MSG, contenu);
            getCrud().open();
            boolean insert = getCrud().insert(MySQLiteHelper.TABLE_MODEL_MSG, values);
            getCrud().close();
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
        getCrud().open();
        boolean delete = getCrud().delete(MySQLiteHelper.TABLE_MODEL_MSG, MySQLiteHelper.COLUMN_ID_MODEL_MSG
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
        getCrud().open();
        ContentValues values = new ContentValues();
        values.put(MySQLiteHelper.COLUMN_TITRE_MODEL_MSG, titre);
        values.put(MySQLiteHelper.COLUMN_CONTENU_MODEL_MSG, contenu);
        boolean update = getCrud().update(MySQLiteHelper.TABLE_MODEL_MSG, values, MySQLiteHelper.COLUMN_ID_MODEL_MSG, new String[]{id});
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

        Cursor cursor = getCrud().getDatabase().query(MySQLiteHelper.TABLE_MODEL_MSG,
                getAllColumns(), null, null, null, null, null);

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
        getCrud().open();
        String sql = "SELECT * FROM " + MySQLiteHelper.TABLE_MODEL_MSG + " WHERE " + MySQLiteHelper.COLUMN_ID_MODEL_MSG + " = " + id;
        Cursor cursor = getCrud().getDatabase().rawQuery(sql, null);
        ModelModelMsg modelModelMsg = new ModelModelMsg();
        while (cursor.moveToNext()) {
            modelModelMsg = cursorToModelMsg(cursor);
        }
        cursor.close();
        return modelModelMsg;
    }

    public ModelModelMsg getModelMsg(String titre) {
        getCrud().open();
        String sql = "SELECT * FROM " + MySQLiteHelper.TABLE_MODEL_MSG + " WHERE " + MySQLiteHelper.COLUMN_TITRE_MODEL_MSG + " =  ?";
        Cursor cursor = getCrud().getDatabase().rawQuery(sql, new String[]{titre});
        ModelModelMsg modelModelMsg = new ModelModelMsg();
        while (cursor.moveToNext()) {
            modelModelMsg = cursorToModelMsg(cursor);
        }
        cursor.close();
        return modelModelMsg;
    }

    /**
     * permet de renvoyer les données d'une seule ligne
     * @param cursor
     * @return ModelModelMsg
     */
    private ModelModelMsg cursorToModelMsg(Cursor cursor) {
        ModelModelMsg modelModelMsg = new ModelModelMsg();
        modelModelMsg.setId(cursor.getLong(0));
        modelModelMsg.setTitre(cursor.getString(1));
        modelModelMsg.setContenu(cursor.getString(2));
        modelModelMsg.setDateCreation(cursor.getLong(3));
        return modelModelMsg;
    }

    private boolean isExist(String titre) {
        getCrud().open();
        String sql = "SELECT * FROM " + MySQLiteHelper.TABLE_MODEL_MSG + " WHERE " + MySQLiteHelper.COLUMN_TITRE_MODEL_MSG + " = '" + titre + "'";
        Cursor cursor = getCrud().getDatabase().rawQuery(sql, null);

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
