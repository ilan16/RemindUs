package com.remindus.projetcommun.remindus.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.remindus.projetcommun.remindus.basededonnees.MySQLiteHelper;
import com.remindus.projetcommun.remindus.basededonnees.utilities.CRUD;
import com.remindus.projetcommun.remindus.model.ModelContact;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ilan on 24/02/2015.
 */
public class DAOContact extends IDAO{
    /**
     *
     * @param context
     */
    public DAOContact(Context context) {
        super(context);
        //rempli le allColumn avec le attributs de la table contact
        String[] allColumns = {
                MySQLiteHelper.COLUMN_ID_CONTACT,
                MySQLiteHelper.COLUMN_NOM_CONTACT,
                MySQLiteHelper.COLUMN_TELEPHONE
        };
        setAllColumns(allColumns);
    }

    /**
     * permet d'insérer un contact avec son numéro de tel
     * @param contact
     * @param telephone
     * @return
     */
    public boolean insertContact(String contact, String telephone) {
        if (!isExist(contact, telephone)) {
            ContentValues values = new ContentValues();
            values.put(MySQLiteHelper.COLUMN_NOM_CONTACT, contact);
            values.put(MySQLiteHelper.COLUMN_TELEPHONE, telephone);
            getCrud().open();
            boolean insert = getCrud().insert(MySQLiteHelper.TABLE_CONTACTS, values);
            getCrud().close();
            if (insert) {
                return true;
            }
            return false;
        }
        return false;
    }

    /**
     *permet de supprimer un contact
     * @param contact
     */
    public void deleteContact(ModelContact contact) {
        long id = contact.getId();
        System.out.println("Contact deleted with id: " + id);
        getCrud().getDatabase().delete(MySQLiteHelper.TABLE_CONTACTS, MySQLiteHelper.COLUMN_ID_CONTACT
                + " = " + id, null);
    }

    /**
     * permet de récupérer l'ensemble des lignes de la tables contact
     * @return List<ModelContact>
     */
    public List<ModelContact> getAllContacts() {
        List<ModelContact> contacts = new ArrayList<ModelContact>();

        Cursor cursor = getCrud().getDatabase().query(MySQLiteHelper.TABLE_CONTACTS,
                getAllColumns(), null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            ModelContact contact = cursorToContact(cursor);
            contacts.add(contact);
            cursor.moveToNext();
        }
        cursor.close();
        return contacts;
    }

    /**
     * permet de récupérer le contact correspondant au numero de télphnoe renseigné
     * @param telephone
     * @return
     */
    public ModelContact getContact(String telephone) {
        ModelContact contact = new ModelContact();
        getCrud().open();
        String sql = "SELECT * FROM " + MySQLiteHelper.TABLE_CONTACTS + " WHERE " + MySQLiteHelper.COLUMN_TELEPHONE + " = ?";
        Cursor cursor = getCrud().getDatabase().rawQuery(sql, new String[]{telephone});
        while (cursor.moveToNext()) {
            contact = cursorToContact(cursor);
        }
        cursor.close();
        return contact;
    }

    /**
     * permet de récupérer le contact correspondant à l'id renseigné
     * @param id
     * @return ModelContact
     */
    public ModelContact getContact(long id) {
        ModelContact contact = new ModelContact();
        getCrud().open();
        String sql = "SELECT * FROM " + MySQLiteHelper.TABLE_CONTACTS + " WHERE " + MySQLiteHelper.COLUMN_ID_CONTACT + " = " + id;
        Cursor cursor = getCrud().getDatabase().rawQuery(sql, null);
        while (cursor.moveToNext()) {
            contact = cursorToContact(cursor);
        }
        cursor.close();
        return contact;
    }

    /**
     * permet de renvoyer les données d'une seule ligne
     * @param cursor
     * @return ModelContact
     */
    private ModelContact cursorToContact(Cursor cursor) {
        ModelContact contact = new ModelContact();
        contact.setId(cursor.getLong(0));
        contact.setContact(cursor.getString(1));
        contact.setTelephone(cursor.getString(2));
        return contact;
    }

    /**
     * vérifie si la paire nom et tel existe dans la bdd
     * @param nom
     * @param tel
     * @return boolean : true si ca existe false sinon
     */
    public boolean isExist(String nom, String tel) {
        getCrud().open();
        String sql = "SELECT * FROM " + MySQLiteHelper.TABLE_CONTACTS + " WHERE " + MySQLiteHelper.COLUMN_NOM_CONTACT + " = \"" + nom + "\"" +
                " AND " + MySQLiteHelper.COLUMN_TELEPHONE + " = \"" + tel + "\"";
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

