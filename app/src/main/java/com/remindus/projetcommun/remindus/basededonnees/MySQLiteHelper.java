package com.remindus.projetcommun.remindus.basededonnees;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Ilan on 24/02/2015.
 */
public class MySQLiteHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "remindus.db";
    private static final int DATABASE_VERSION = 1;

    public static final String TABLE_CONTACTS = "contacts";
    public static final String COLUMN_ID_CONTACT = "id_contact";
    public static final String COLUMN_NOM_CONTACT = "nom_contact";
    public static final String COLUMN_TELEPHONE = "telephone";


    private static final String DATABASE_CREATE_CONTACTS = "create table "
            + TABLE_CONTACTS + "("
            + COLUMN_ID_CONTACT + " integer primary key autoincrement, "
            + COLUMN_NOM_CONTACT + " text not null,"
            + COLUMN_TELEPHONE + " text not null);";

    public static final String TABLE_GROUPES = "groupes";
    public static final String COLUMN_ID_GROUPE = "id_groupe";
    public static final String COLUMN_NOM_GROUPE = "nom_groupe";
    public static final String COLUMN_FK_ID_CONTACT = "id_contact";


    private static final String DATABASE_CREATE_GROUPES = "create table "
            + TABLE_GROUPES + "("
            + COLUMN_ID_GROUPE + " integer primary key autoincrement, "
            + COLUMN_NOM_GROUPE + " text not null,"
            + COLUMN_FK_ID_CONTACT + " text not null CONSTRAINT fk_contact_id REFERENCES " + TABLE_CONTACTS + "(" + COLUMN_ID_CONTACT + "));";

    public MySQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(DATABASE_CREATE_CONTACTS);
        database.execSQL(DATABASE_CREATE_GROUPES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
        database.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACTS);
        database.execSQL("DROP TABLE IF EXISTS " + TABLE_GROUPES);
        onCreate(database);
    }
}