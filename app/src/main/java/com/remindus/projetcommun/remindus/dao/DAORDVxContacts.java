package com.remindus.projetcommun.remindus.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.remindus.projetcommun.remindus.basededonnees.MySQLiteHelper;
import com.remindus.projetcommun.remindus.basededonnees.utilities.CRUD;
import com.remindus.projetcommun.remindus.model.ModelRDVxContacts;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ilanmalka on 18/03/15.
 */
public class DAORDVxContacts extends IDAO {


    public DAORDVxContacts(Context context) {
        super(context);
        //rempli le allColumn avec le attributs de la table rdvxcontacts
        String[] allColumns = {
                MySQLiteHelper.COLUMN_ID_RDV_RDVxC,
                MySQLiteHelper.COLUMN_ID_CONTACT_RDVxC
        };
        setAllColumns(allColumns);
    }

    //permet d'insérer une ligne dans la table rdvxcontacts
    // on insère idrdv et idcontact obligatoirement car les deux attributs ne peuvent pas etre null
    /**
     *
     * @param idrdv: id rdv à ajouter
     * @param idcontact : id contact à ajouter
     * @return:  retourne un int qui correspond à l'état de l'insertion, ie si ca a été inséré ou pas
     */
    public int insertRDVxC(long idrdv, long idcontact) {
        if (!isExist(idrdv, idcontact)) { //si idrdv intersection idcontact existe déjà dans la bdd on n'insère pas
            ContentValues values = new ContentValues();
            values.put(MySQLiteHelper.COLUMN_ID_RDV_RDVxC, idrdv);
            values.put(MySQLiteHelper.COLUMN_ID_CONTACT_RDVxC, idcontact);

            getCrud().open();
            boolean insert = getCrud().insert(MySQLiteHelper.TABLE_RDVxCONTACTS, values);

            if (insert) {
                Log.i("INSERER", "" + idrdv + " " + idcontact);
                return 0; // insertion ok
            }
            return 1; // pb d'insertion
        }
        return 2;
    }

    //cette méthode permet de savoir si la paire idrdv et idcontact a déjà été insérée dans la bdd
    // --> permet d'éviter d'avoir une exception car les deux id sont en clés primaires et donc doivent être uniques
    /**
     *
     * @param idrv id rdv à ajouter
     * @param idcontact id contact à ajouter
     * @return: retourne soit true si la paire existe et false sinon
     */
    private boolean isExist(long idrv, long idcontact) {
        String sql = "SELECT * FROM " + MySQLiteHelper.TABLE_RDVxCONTACTS + " WHERE " + MySQLiteHelper.COLUMN_ID_RDV_RDVxC + " = ?"
                + " AND " + MySQLiteHelper.COLUMN_ID_CONTACT_RDVxC + " = ?";

        getCrud().open();
        Cursor cursor = getCrud().requeteGeneral(sql, new String[]{""+idrv,""+idcontact});

        if (cursor != null) {
            if (cursor.getCount() > 0) {
                cursor.close();
                return true;
            }
            cursor.close();
        }
        return false;
    }

    //cette méthode permet de supprimer une ligne de la table rdvxcontact

    /**
     *
     * @param modelRDVxContacts : de ce param on peut récuperer l'idcontact et l'idrdv a supprimer
     * @return boolean:  si true --> supprimé sinon non
     */
    public boolean deleteRDVxC(ModelRDVxContacts modelRDVxContacts) {
        long idcontact = modelRDVxContacts.getIdcontact();
        long idrdv = modelRDVxContacts.getIdrdv();
        getCrud().open();
        boolean delete = getCrud().delete(MySQLiteHelper.TABLE_RDVxCONTACTS, MySQLiteHelper.COLUMN_ID_CONTACT_RDVxC
                + " = " + idcontact + " AND " + MySQLiteHelper.COLUMN_ID_RDV_RDVxC + " = " + idrdv);
        if (delete) {
            Log.i("DELETE", "effectué");
            return true;
        }
        Log.i("DELETE", "merde");
        return false;
    }

    //permet de récupérer l'ensemble des lignes de la table rdvxcontact

    /**
     *
     * @return la liste des rdvxcontact
     */
    public List<ModelRDVxContacts> getAllRDVxC() {
        List<ModelRDVxContacts> modelRDVxContacts = new ArrayList<ModelRDVxContacts>();

        Cursor cursor = getCrud().getDatabase().query(MySQLiteHelper.TABLE_RDVxCONTACTS,
                getAllColumns(), null, null, null, null, null);

        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            ModelRDVxContacts modelRDVxContact = cursorToRDVxC(cursor);
            modelRDVxContacts.add(modelRDVxContact);
            cursor.moveToNext();

        }
        cursor.close();
        return modelRDVxContacts;
    }

    //permet de récupérer l'ensmble des lignes de la table rdvxcontact ayant le mm id rdv

    /**
     *
     * @param idrdv
     * @return: list de l'ensemble des lignes avec le mm id rdv
     */
    public List<ModelRDVxContacts> getAllRDVxC(long idrdv) {
        List<ModelRDVxContacts> modelRDVxContactses = new ArrayList<ModelRDVxContacts>();
        getCrud().open();
        String sql = "SELECT * FROM " + MySQLiteHelper.TABLE_RDVxCONTACTS + " WHERE " + MySQLiteHelper.COLUMN_ID_RDV_RDVxC + " = " + idrdv;
        Cursor cursor = getCrud().getDatabase().rawQuery(sql, null);
        while (cursor.moveToNext()) {
            ModelRDVxContacts modelRDVxContacts = cursorToRDVxC(cursor);
            modelRDVxContactses.add(modelRDVxContacts);
        }
        cursor.close();
        return modelRDVxContactses;
    }

    /**
     *
     * @param idrdv correspond à l'id a supprimer
     * @return boolean : true si supp false sinon
     */
    public boolean deleteRDVxC(long idrdv) {
        getCrud().open();
        boolean delete = getCrud().delete(MySQLiteHelper.TABLE_RDVxCONTACTS, MySQLiteHelper.COLUMN_ID_CONTACT_RDVxC + " = " + idrdv);
        if (delete) {
            return true;
        }
        return false;
    }

    /**
     * cette classe permet de renvoyer les données d'une seule ligne
     * @param cursor
     * @return ModelRDVxContacts
     */
    private ModelRDVxContacts cursorToRDVxC(Cursor cursor) {
        ModelRDVxContacts modelRDVxContacts = new ModelRDVxContacts();
        modelRDVxContacts.setIdrdv(cursor.getLong(0));
        modelRDVxContacts.setIdcontact(cursor.getLong(1));
        return modelRDVxContacts;
    }


}
