package com.remindus.projetcommun.remindus.basededonnees.utilities;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.remindus.projetcommun.remindus.basededonnees.MySQLiteHelper;


/**
 * Created by ilanmalka on 04/03/15.
 */
public class CRUD {

    private SQLiteDatabase database;
    private MySQLiteHelper dbHelper;


    public CRUD(Context context) {
        dbHelper = new MySQLiteHelper(context);
    }

    public SQLiteDatabase getDatabase() {
        return database;
    }

    public void setDatabase(SQLiteDatabase database) {
        this.database = database;
    }

    public MySQLiteHelper getDbHelper() {
        return dbHelper;
    }

    public void setDbHelper(MySQLiteHelper dbHelper) {
        this.dbHelper = dbHelper;
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    /**
     * permet d'insérer une valeur dans une table
     * @param table
     * @param values
     * @return boolean
     */
    public boolean insert(String table, ContentValues values) {
        long insert = this.database.insert(table, null, values);
        if (insert == -1) {
            Log.i("INSERT", "false");
            return false;
        }
        Log.i("INSERT", "true");
        return true;
    }

    /**
     * permet de supprimer une ligne d'une table
     * @param table : table sur laquelle effetuer le delete
     * @param whereClause: contient le contenu de la variable qui faut supp
     * @return boolean
     */
    public boolean delete(String table, String whereClause) {
        int delete = this.database.delete(table, whereClause, null);
        if (delete == 0) {
            return false;
        }
        return true;
    }

    /**
     * permet de faire un update
     * @param table: table sur laquelle effetuer l'update
     * @param values: contient les attributs avec leurs nouvelles valeurs
     * @param valeurAupdate: valeur a updater
     * @param contenuValeurUpdate
     * @return boolean
     */
    public boolean update(String table, ContentValues values, String valeurAupdate, String[] contenuValeurUpdate) {
        int update = this.database.update(table, values, valeurAupdate + " = ?", contenuValeurUpdate);
        if (update == -1) {
            return false;
        }
        return true;
    }

    /**
     * permet de faire tout type de requete mais en particulier une requete de type SELECT
     * @param sql :  contenu de la requête; ex: SELECT blabl FROM blabla WHERE .....
     * @param contenu tableau ou se trouve le contenu des de la requete
     * @return
     */
    public Cursor requeteGeneral(String sql, String[] contenu) {
        return this.database.rawQuery(sql, contenu);
    }

}
