package com.remindus.projetcommun.remindus.basededonnees.utilities;

import android.content.ContentValues;
import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.remindus.projetcommun.remindus.basededonnees.MySQLiteHelper;

import java.util.List;
import java.util.Objects;


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

    public boolean insert(String table, ContentValues values) {
        long insert = this.database.insert(table, null, values);
        if (insert == -1) {
            Log.i("INSERT", "false");
            System.out.println(insert);
            return false;
        }
        Log.i("INSERT", "true");
        return true;
    }

    public boolean delete(String table, String whereClause) {
        int delete = this.database.delete(table, whereClause, null);
        if (delete == 0) {
            return false;
        }
        return true;
    }

    public boolean update(String table, ContentValues values, String valeurAupdate, String[] contenuValeurUpdate){
        int update = this.database.update(table, values, valeurAupdate + " = ?", contenuValeurUpdate);
        if(update == -1){
            return false;
        }
        return true;
    }

    public void requeteGeneral(String sql) {
        this.database.execSQL(sql);
    }

}
