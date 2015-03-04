package com.remindus.projetcommun.remindus.basededonnees.utilities;

import android.content.ContentValues;
import android.content.Context;
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

    private long insert;
    private int delete;

    public CRUD(Context context) {
        dbHelper = new MySQLiteHelper(context);
        this.insert = 0;
        this.delete = 0;
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

    public long getInsert() {
        return insert;
    }

    public void setInsert(long insert) {
        this.insert = insert;
    }

    public int getDelete() {
        return delete;
    }

    public void setDelete(int delete) {
        this.delete = delete;
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public boolean insert(String table, ContentValues values){
        this.insert = this.database.insert(table, null, values);
        if (this.insert == -1){
            this.setInsert(this.insert);
            Log.i("INSERT", "false");
            System.out.println(insert);
            return false;
        }
        Log.i("INSERT", "true");
        return true;
    }

    public boolean delete(String table, String whereClause){
        this.delete = this.database.delete(table,whereClause,null);
        if(this.delete==0){
            this.setDelete(this.delete);
            return false;
        }
        return true;
    }

    public void select(String sql){
        this.database.execSQL(sql);
    }

}
