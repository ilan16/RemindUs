package com.remindus.projetcommun.remindus.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.remindus.projetcommun.remindus.basededonnees.MySQLiteHelper;
import com.remindus.projetcommun.remindus.model.ModelContact;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ilan on 24/02/2015.
 */
public class DAOContact {
    private SQLiteDatabase database;
    private MySQLiteHelper dbHelper;
    private String[] allColumns = {
            MySQLiteHelper.COLUMN_ID,
            MySQLiteHelper.COLUMN_CONTACT,
            MySQLiteHelper.COLUMN_TELEPHONE
    };

    public DAOContact(Context context) {
        dbHelper = new MySQLiteHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public ModelContact createContact(String contact, String telephone) {
        ContentValues values = new ContentValues();
        values.put(MySQLiteHelper.COLUMN_CONTACT, contact);
        values.put(MySQLiteHelper.COLUMN_TELEPHONE, telephone);
        long insertId = database.insert(MySQLiteHelper.TABLE_CONTACTS, null,
                values);
        Cursor cursor = database.query(MySQLiteHelper.TABLE_CONTACTS,
                allColumns, MySQLiteHelper.COLUMN_ID + " = " + insertId, null,
                null, null, null);
        cursor.moveToFirst();
        ModelContact newContact = cursorToContact(cursor);
        cursor.close();
        return newContact;
    }

    public void deleteContact(ModelContact contact) {
        long id = contact.getId();
        System.out.println("Contact deleted with id: " + id);
        database.delete(MySQLiteHelper.TABLE_CONTACTS, MySQLiteHelper.COLUMN_ID
                + " = " + id, null);
    }

    public List<ModelContact> getAllContacts() {
        List<ModelContact> contacts = new ArrayList<ModelContact>();

        Cursor cursor = database.query(MySQLiteHelper.TABLE_CONTACTS,
                allColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            ModelContact contact = cursorToContact(cursor);
            contacts.add(contact);
            cursor.moveToNext();
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

