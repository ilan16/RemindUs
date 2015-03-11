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

    /*
            CONTACTS
     */

    public static final String TABLE_CONTACTS = "contacts";
    public static final String COLUMN_ID_CONTACT = "id_contact";
    public static final String COLUMN_NOM_CONTACT = "nom_contact";
    public static final String COLUMN_TELEPHONE = "telephone";


    private static final String DATABASE_CREATE_CONTACTS = "create table "
            + TABLE_CONTACTS + "("
            + COLUMN_ID_CONTACT + " integer primary key autoincrement, "
            + COLUMN_NOM_CONTACT + " text not null,"
            + COLUMN_TELEPHONE + " text not null);";

    /*
            GROUPES
     */

    public static final String TABLE_GROUPES = "groupes";
    public static final String COLUMN_ID_GROUPE = "id_groupe";
    public static final String COLUMN_NOM_GROUPE = "nom_groupe";
    public static final String COLUMN_DATE_CREATION = "date_creation";


    private static final String DATABASE_CREATE_GROUPES = "create table "
            + TABLE_GROUPES + "("
            + COLUMN_ID_GROUPE + " integer primary key autoincrement, "
            + COLUMN_NOM_GROUPE + " text not null, "
            + COLUMN_DATE_CREATION + " integer not null);";

    /*
            GROUPESxCONTACTS
    */

    public static final String TABLE_GROUPESxCONTACTS = "groupesXcontacts";
    public static final String COLUMN_DATE_AJOUT = "date_ajout";

    private static final String DATABASE_CREATE_GROUPESxCONTACTS = "create table "
            + TABLE_GROUPESxCONTACTS + "("
            + COLUMN_ID_CONTACT + " INTEGER NOT NULL CONSTRAINT fk_contacts_id REFERENCES "+ TABLE_CONTACTS
            + "("+COLUMN_ID_CONTACT+"),"
            + COLUMN_ID_GROUPE + " INTEGER NOT NULL CONSTRAINT fk_groupes_id REFERENCES "+ TABLE_GROUPES
            + "("+COLUMN_ID_GROUPE+"),"
            + COLUMN_DATE_AJOUT + " INTEGER,"
            + "PRIMARY KEY (" + COLUMN_ID_CONTACT + "," + COLUMN_ID_GROUPE + ");";

    /*
            MESSAGES PROGRAMMES
    */

    public static final String TABLE_MSG_PROG = "messages_programmes";
    public static final String COLUMN_ID_MSG_PROG = "id_message_programme";
    public static final String COLUMN_TITRE_MSG_PROG = "titre_message_programme";
    public static final String COLUMN_HEURE_MSG_PROG = "heure_message_programme";
    public static final String COLUMN_DATE_MSG_PROG = "date_message_programme";
    public static final String COLUMN_MSG_PROG = "message_programme";

    private static final String DATABASE_CREATE_MSG_PROG = "create table "
            + TABLE_MSG_PROG + "("
            + COLUMN_ID_MSG_PROG + " integer primary key autoincrement, "
            + COLUMN_TITRE_MSG_PROG + " text not null,"
            + COLUMN_DATE_MSG_PROG + " integer not null,"
            + COLUMN_HEURE_MSG_PROG + " integer not null,"
            + COLUMN_MSG_PROG + " text not null );";

    /*
            MSG PRG x GROUPES
    */

    public static final String TABLE_MSG_PROGxGROUPES = "messages_programmesXgroupes";

    private static final String DATABASE_CREATE_MSG_PROGxGROUPES = "create table "
            + TABLE_MSG_PROGxGROUPES + "("
            + COLUMN_ID_MSG_PROG + " INTEGER NOT NULL CONSTRAINT fk_msg_prog_id REFERENCES "+ TABLE_MSG_PROG
            + "("+COLUMN_ID_MSG_PROG+"),"
            + COLUMN_ID_GROUPE + " INTEGER NOT NULL CONSTRAINT fk_groupes_id_msg_prog REFERENCES "+ TABLE_GROUPES
            + "("+COLUMN_ID_GROUPE+"),"
            + COLUMN_DATE_AJOUT + " INTEGER,"
            + "PRIMARY KEY (" + COLUMN_ID_MSG_PROG + "," + COLUMN_ID_GROUPE + ");";

    /*
            MODEL MESSAGE
     */

    public static final String TABLE_MODEL_MSG = "model_messages";
    public static final String COLUMN_ID_MODEL_MSG = "_id";
    public static final String COLUMN_TITRE_MODEL_MSG = "titre";
    public static final String COLUMN_CONTENU_MODEL_MSG = "contenu";
    public static final String COLUMN_DATE_CREATION_MODEL_MSG = "date_creation";

    private static final String DATABASE_CREATE_MODEL_MSG = "create table "
            + TABLE_MODEL_MSG + "("
            + COLUMN_ID_MODEL_MSG + " integer primary key autoincrement, "
            + COLUMN_TITRE_MODEL_MSG + " text not null,"
            + COLUMN_CONTENU_MODEL_MSG + " text not null, "
            + COLUMN_DATE_CREATION_MODEL_MSG +" integer not null);";

    /*
            RDV
     */

    public static final String TABLE_RDV = "rdv";
    public static final String COLUMN_ID_RDV = "_id";
    public static final String COLUMN_NOM_RDV = "nom_rdv";
    public static final String COLUMN_DATE_RDV = "date_rdv";
    public static final String COLUMN_HEURE_RDV = "heure_rdv";
    public static final String COLUMN_LIEU_RDV = "lieu";
    public static final String COLUMN_MODE_TEL_RDV = "mode";


    private static final String DATABASE_CREATE_RDV = "create table "
            + TABLE_RDV + "("
            + COLUMN_ID_RDV + " integer primary key autoincrement, "
            + COLUMN_NOM_RDV+ " text not null,"
            + COLUMN_DATE_RDV+ " integer not null,"
            + COLUMN_HEURE_RDV+ " integer not null,"
            + COLUMN_LIEU_RDV + " text not null, "
            + COLUMN_MODE_TEL_RDV +" integer not null);";

    /*
        METHODES BDD
     */

    public MySQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(DATABASE_CREATE_CONTACTS);
        database.execSQL(DATABASE_CREATE_GROUPES);
        database.execSQL(DATABASE_CREATE_GROUPESxCONTACTS);
        database.execSQL(DATABASE_CREATE_MSG_PROG);
        database.execSQL(DATABASE_CREATE_MSG_PROGxGROUPES);
        database.execSQL(DATABASE_CREATE_MODEL_MSG);
        database.execSQL(DATABASE_CREATE_RDV);
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
        database.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACTS);
        database.execSQL("DROP TABLE IF EXISTS " + TABLE_GROUPES);
        database.execSQL("DROP TABLE IF EXISTS " + TABLE_GROUPESxCONTACTS);
        database.execSQL("DROP TABLE IF EXISTS " + TABLE_MSG_PROG);
        database.execSQL("DROP TABLE IF EXISTS " + TABLE_MSG_PROGxGROUPES);
        database.execSQL("DROP TABLE IF EXISTS " + TABLE_MODEL_MSG);
        database.execSQL("DROP TABLE IF EXISTS " + TABLE_RDV);
        onCreate(database);
    }
}