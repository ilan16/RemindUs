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
public class DAOMsgProgxContacts extends IDAO {

    private CRUD crud;

    public DAOMsgProgxContacts(Context context) {
        super(context);
        //rempli le allColumn avec le attributs de la table msgxcontacts
        String[] allColumns = {
                MySQLiteHelper.COLUMN_ID_MSG_PROG_MPxC,
                MySQLiteHelper.COLUMN_ID_CONTACT_MPxC
        };
        setAllColumns(allColumns);
    }

    public int insertMsgProgxC(long idmsg, long idcontact) {
        ContentValues values = new ContentValues();
        values.put(MySQLiteHelper.COLUMN_ID_MSG_PROG_MPxC, idmsg);
        values.put(MySQLiteHelper.COLUMN_ID_CONTACT_MPxC, idcontact);

        getCrud().open();
        boolean insert = getCrud().insert(MySQLiteHelper.TABLE_MSG_PROGxCONTACTS, values);

        if (insert) {
            Log.i("INSERER", "" + idmsg + " " + idcontact);
            return 0; // insertion ok
        }
        return 1; // pb d'insertion
    }

    public boolean deleteMsgProgxC(ModelMsgProgxContacts modelMsgProgxContacts) {
        long idcontact = modelMsgProgxContacts.getIdContact();
        long idMsgProg = modelMsgProgxContacts.getIdMsgProg();
        getCrud().open();
        boolean delete = getCrud().delete(MySQLiteHelper.TABLE_MSG_PROGxCONTACTS, MySQLiteHelper.COLUMN_ID_CONTACT_MPxC
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

        Cursor cursor = getCrud().getDatabase().query(MySQLiteHelper.TABLE_MSG_PROGxCONTACTS,
                getAllColumns(), null, null, null, null, null);

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
        getCrud().open();
        String sql = "SELECT * FROM " + MySQLiteHelper.TABLE_MSG_PROGxCONTACTS + " WHERE " + MySQLiteHelper.COLUMN_ID_MSG_PROG_MPxC + " = " + idmsgprog;
        Cursor cursor = getCrud().getDatabase().rawQuery(sql, null);
        while (cursor.moveToNext()) {
            ModelMsgProgxContacts modelMsgProgxContacts = cursorToMsgProgxC(cursor);
           modelMsgProgxContactses.add(modelMsgProgxContacts);
        }
        cursor.close();
        return modelMsgProgxContactses;
    }

    public String getAllNumero(long idmsgprog, Context context){
        List<ModelMsgProgxContacts> maList = this.getAllMsgProgxC(idmsgprog);
        DAOContact daoContact = new DAOContact(context);
        String numeros = "";
        for (int i = 0; i < maList.size(); i++){
            long idContact = maList.get(i).getIdMsgProg();
            numeros += daoContact.getContact(idContact)+";";
        }
        return numeros;
    }

    public boolean deleteMsgProgxC(long idc) {
        getCrud().open();
        boolean delete = getCrud().delete(MySQLiteHelper.TABLE_MSG_PROGxCONTACTS, MySQLiteHelper.COLUMN_ID_CONTACT_MPxC + " = " + idc);
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
