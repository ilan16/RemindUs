package com.remindus.projetcommun.remindus.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.remindus.projetcommun.remindus.basededonnees.MySQLiteHelper;
import com.remindus.projetcommun.remindus.basededonnees.utilities.CRUD;
import com.remindus.projetcommun.remindus.controller.UtilitaireDate;
import com.remindus.projetcommun.remindus.model.ModelModelMsg;
import com.remindus.projetcommun.remindus.model.ModelMsgProg;
import com.remindus.projetcommun.remindus.model.ModelRDV;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ilanmalka on 08/03/15.
 */
public class DAOMsgProg extends IDAO{

    /**
     *
     * @param context
     */
    public DAOMsgProg(Context context) {
        super(context);
        //rempli le allColumn avec le attributs de la table msgprog
        String[] allColumns = {
                MySQLiteHelper.COLUMN_ID_MSG_PROG,
                MySQLiteHelper.COLUMN_TITRE_MSG_PROG,
                MySQLiteHelper.COLUMN_DATE_MSG_PROG,
                MySQLiteHelper.COLUMN_DATESTRING_MSG_PROG,
                MySQLiteHelper.COLUMN_MSG_PROG
        };
        setAllColumns(allColumns);
    }

    /**
     * permet d'insérer un msg programmé
     * @param titre
     * @param date
     * @param datestring
     * @param contenu
     * @return
     */
    public int insertMsgProg(String titre, long date, String datestring, String contenu) {
        //if(!this.isExist(titre)) {

        ContentValues values = new ContentValues();
        values.put(MySQLiteHelper.COLUMN_TITRE_MSG_PROG, titre);
        values.put(MySQLiteHelper.COLUMN_DATE_MSG_PROG, date);
        values.put(MySQLiteHelper.COLUMN_DATESTRING_MSG_PROG, datestring);
        values.put(MySQLiteHelper.COLUMN_MSG_PROG, contenu);

        getCrud().open();
        boolean insert = getCrud().insert(MySQLiteHelper.TABLE_MSG_PROG, values);
        getCrud().close();

        if (insert) {
            return 0; // insertion ok
        }
        return 1; // pb d'insertion
        //} else {
        //    return 2; // msg existe déjà
        //}
    }

    /**
     * permet de modifier tous les champs d'un message programmé
     * @param modelMsgProg
     * @param titre
     * @param date
     * @param dateString
     * @param contenu
     * @return
     */
    public int updateMsgProg(ModelMsgProg modelMsgProg, String titre, long date, String dateString, String contenu) {
        String id = "" + modelMsgProg.getId();
        Log.i("ID", id);
        if(!titre.equals(modelMsgProg.getTitre())) {
            if (!this.isExist(titre)) {
                getCrud().open();
                ContentValues values = new ContentValues();
                values.put(MySQLiteHelper.COLUMN_TITRE_MSG_PROG, titre);
                values.put(MySQLiteHelper.COLUMN_DATE_MSG_PROG, date);
                values.put(MySQLiteHelper.COLUMN_DATESTRING_MSG_PROG, dateString);
                values.put(MySQLiteHelper.COLUMN_MSG_PROG, contenu);
                boolean update = getCrud().update(MySQLiteHelper.TABLE_MSG_PROG, values, MySQLiteHelper.COLUMN_ID_MSG_PROG, new String[]{id});
                if (update) {
                    Log.i("UPDATE", "BON");
                    return 0; //si l'update fonctionne
                }
                return 1; // pb update
            }
            return 2; // le nom existe déjà donc pas possible de maj avec ce nom
        }else {
            getCrud().open();

            ContentValues values = new ContentValues();
            values.put(MySQLiteHelper.COLUMN_TITRE_MSG_PROG, titre);
            values.put(MySQLiteHelper.COLUMN_DATE_MSG_PROG, date);
            values.put(MySQLiteHelper.COLUMN_DATESTRING_MSG_PROG, dateString);
            values.put(MySQLiteHelper.COLUMN_MSG_PROG, contenu);

            boolean update = getCrud().update(MySQLiteHelper.TABLE_MSG_PROG, values, MySQLiteHelper.COLUMN_ID_MSG_PROG, new String[]{id});
            if (update) {
                Log.i("UPDATE", "BON");
                return 0; //si l'update fonctionne
            }
            return 1; // pb update
        }
    }

    /**
     *
     * @param modelMsgProg
     * @return boolean : true si supp false sinon
     */
    public boolean deleteMsgProg(ModelMsgProg modelMsgProg) {
        long id = modelMsgProg.getId();
        getCrud().open();
        boolean delete = getCrud().delete(MySQLiteHelper.TABLE_MSG_PROG, MySQLiteHelper.COLUMN_ID_MSG_PROG
                + " = " + id);
        // this.crud.close();
        if (delete) {
            return true;
        }
        return false;
    }

    /**
     * permet de retourner l'ensmble des lignes de la table msg programmé
     * @return
     */
    public List<ModelMsgProg> getAllMsgProg() {
        List<ModelMsgProg> msgProgs = new ArrayList<ModelMsgProg>();

        Cursor cursor = getCrud().getDatabase().query(MySQLiteHelper.TABLE_MSG_PROG,
                getAllColumns(), null, null, null, null, null);

        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            ModelMsgProg msgProg = cursorToMsgProg(cursor);
            msgProgs.add(msgProg);
            cursor.moveToNext();

        }
        cursor.close();
        return msgProgs;
    }

    /**
     * permet de récupérer l'id du msg prog ayant ce titre
     * @param titre
     * @return
     */
    public ModelMsgProg getIdMsgProg(String titre) {
        getCrud().open();
        String sql = "SELECT * FROM " + MySQLiteHelper.TABLE_MSG_PROG + " WHERE " + MySQLiteHelper.COLUMN_TITRE_MSG_PROG + " = ?";
        Cursor cursor = getCrud().getDatabase().rawQuery(sql, new String[]{titre});
        ModelMsgProg modelMsgProg = new ModelMsgProg();
        while (cursor.moveToNext()) {
            modelMsgProg = cursorToMsgProg(cursor);
        }
        cursor.close();
        return modelMsgProg;
    }

    /**
     * permet de récupérer le msg prog ayant cet id
     * @param id
     * @return
     */
    public ModelMsgProg getMsgProg(int id) {
        getCrud().open();
        String sql = "SELECT * FROM " + MySQLiteHelper.TABLE_MSG_PROG + " WHERE " + MySQLiteHelper.COLUMN_ID_MSG_PROG + " = " + id;
        Cursor cursor = getCrud().getDatabase().rawQuery(sql, null);
        ModelMsgProg modelMsgProg = new ModelMsgProg();
        while (cursor.moveToNext()) {
            modelMsgProg = cursorToMsgProg(cursor);
        }
        cursor.close();
        return modelMsgProg;
    }

    /**
     * permet de récupérer le msg prog ayant ce titre
     * @param titre
     * @return
     */
    public ModelMsgProg getMsgProg(String titre) {
        getCrud().open();
        ModelMsgProg modelMsgProg = new ModelMsgProg();
        String sql = "SELECT * FROM " + MySQLiteHelper.TABLE_MSG_PROG + " WHERE " + MySQLiteHelper.COLUMN_TITRE_MSG_PROG + " = '" + titre+"'";
        Cursor cursor = getCrud().getDatabase().rawQuery(sql, null);
        while (cursor.moveToNext()) {
            modelMsgProg = cursorToMsgProg(cursor);
        }
        cursor.close();
        return modelMsgProg;
    }


    /**
     * permet de renvoyer les données d'une seule ligne
     * @param cursor
     * @return ModelMsgProg
     */
    private ModelMsgProg cursorToMsgProg(Cursor cursor) {
        ModelMsgProg msgProg = new ModelMsgProg();
        msgProg.setId(cursor.getLong(0));
        msgProg.setTitre(cursor.getString(1));
        msgProg.setDate(cursor.getLong(2));
        msgProg.setDatestring(cursor.getString(3));
        msgProg.setMsgProg(cursor.getString(4));
        return msgProg;
    }

    /**
     * permet de voir si le titre de ce msg prog existe
     * @param titre
     * @return true si oui false sinon
     */
    private boolean isExist(String titre) {
        String sql = "SELECT * FROM " + MySQLiteHelper.TABLE_MSG_PROG + " WHERE " + MySQLiteHelper.COLUMN_TITRE_MSG_PROG + " = ?";

        Cursor cursor = getCrud().getDatabase().rawQuery(sql, new String[]{titre});

        if (cursor != null) {
            if (cursor.getCount() > 0) {
                cursor.close();
                return true;
            }
            cursor.close();
        }
        return false;
    }

    /**
     * permet de récupérer le prochain msg prog
     * @param time: 0 pour déclancher le service d'envoie et 10000 (10sec) pour récupérer les numéros de tel
     * @return
     */
    public ModelMsgProg prochainMsgProg(long time) {
        String sql = "SELECT * FROM " + MySQLiteHelper.TABLE_MSG_PROG + " WHERE " + MySQLiteHelper.COLUMN_DATE_MSG_PROG + " >= "
                + ((getUtilitaireDate().dateActuelle())-time) + " ORDER BY " + MySQLiteHelper.COLUMN_DATE_MSG_PROG + " ASC";
        getCrud().open();
        Cursor cursor = getCrud().requeteGeneral(sql, null);
        ModelMsgProg modelMsgProg = new ModelMsgProg();
        while (cursor.moveToNext()) {
            modelMsgProg = this.cursorToMsgProg(cursor);
            break;
        }
        return modelMsgProg;
    }



}


