package com.remindus.projetcommun.remindus.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.EditText;

import com.remindus.projetcommun.remindus.basededonnees.MySQLiteHelper;
import com.remindus.projetcommun.remindus.basededonnees.utilities.CRUD;
import com.remindus.projetcommun.remindus.model.ModelContact;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ilan on 24/02/2015.
 */
public class DAOContact {

    private MySQLiteHelper dbHelper;
    private String[] allColumns = {
            MySQLiteHelper.COLUMN_ID_CONTACT,
            MySQLiteHelper.COLUMN_NOM_CONTACT,
            MySQLiteHelper.COLUMN_TELEPHONE
    };
    private CRUD crud;

    public DAOContact(Context context) {
        crud = new CRUD(context);
    }

    public CRUD getCrud() {
        return crud;
    }

    public void setCrud(CRUD crud) {
        this.crud = crud;
    }

    public boolean insertContact(String contact, String telephone) {
        ContentValues values = new ContentValues();
        values.put(MySQLiteHelper.COLUMN_NOM_CONTACT, contact);
        values.put(MySQLiteHelper.COLUMN_TELEPHONE, telephone);
        this.crud.open();
        boolean insert = crud.insert(MySQLiteHelper.TABLE_CONTACTS, values);
        this.crud.close();
        if(insert) {
            return true;
        }
        return false;
    }

    public void deleteContact(ModelContact contact) {
        long id = contact.getId();
        System.out.println("Contact deleted with id: " + id);
        crud.getDatabase().delete(MySQLiteHelper.TABLE_CONTACTS, MySQLiteHelper.COLUMN_ID_CONTACT
                + " = " + id, null);
    }

    public List<ModelContact> getAllContacts() {
        List<ModelContact> contacts = new ArrayList<ModelContact>();

        Cursor cursor = crud.getDatabase().query(MySQLiteHelper.TABLE_CONTACTS,
                allColumns, null, null, null, null, null);

        cursor.moveToFirst();
        int i = 0;
        while (!cursor.isAfterLast()) {
            ModelContact contact = cursorToContact(cursor);
            contacts.add(contact);
            cursor.moveToNext();
            System.out.println("i :" + i);
            i++;
        }
        cursor.close();
        return contacts;
    }

    private ModelContact cursorToContact(Cursor cursor) {
        ModelContact contact = new ModelContact();
        contact.setId(cursor.getLong(0));
        contact.setContact(cursor.getString(1));
        contact.setTelephone(cursor.getString(2));
        return contact;
    }
}

