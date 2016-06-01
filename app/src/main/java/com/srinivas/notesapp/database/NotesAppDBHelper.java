package com.srinivas.notesapp.database;

import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Mudavath Srinivas on 29-05-2016.
 */
public class NotesAppDBHelper implements DBHelper {

    private static final String DATABASE_NAME = "notesapp.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_NOTES_TABLE_CREATE =
            "CREATE TABLE IF NOT EXISTS " + NotesTable.TABLE_NAME + " (" +
                    NotesTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    NotesTable.NOTE_ID + " INTEGER UNIQUE, " +
                    NotesTable.NOTE_TAG_ID + " INTEGER, " +
                    NotesTable.NOTE_DATA + " TEXT, " +
                    NotesTable.NOTE_CREATED_DATE + " DATETIME, " +
                    NotesTable.NOTE_MODIFIED_DATE + " DATETIME" +
                    ")";

    private static final String TABLE_NOTE_TAG_TABLE_CREATE =
            "CREATE TABLE IF NOT EXISTS " + NotesTagTable.TABLE_NAME + " (" +
                    NotesTagTable.NOTE_TAG_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    NotesTagTable.NOTE_TAG_TYPE + " TEXT UNIQUE " +
                    ")";

    private static final String TABLE_NOTE_TAG_INSERT_PERSONAL=
            "INSERT INTO "+NotesTagTable.TABLE_NAME+" ("+NotesTagTable.NOTE_TAG_TYPE+") VALUES ('Personal')";

    private static final String TABLE_NOTE_TAG_INSERT_CALLS=
            "INSERT INTO "+NotesTagTable.TABLE_NAME+" ("+NotesTagTable.NOTE_TAG_TYPE+") VALUES ('Calls')";

    private static final String TABLE_NOTE_TAG_INSERT_OFFICE=
            "INSERT INTO "+NotesTagTable.TABLE_NAME+" ("+NotesTagTable.NOTE_TAG_TYPE+") VALUES ('Office')";

    @Override
    public int getDBVersion() {
        return DATABASE_VERSION;
    }

    @Override
    public String getDBName() {
        return DATABASE_NAME;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_NOTES_TABLE_CREATE);
        db.execSQL(TABLE_NOTE_TAG_TABLE_CREATE);

        db.execSQL(TABLE_NOTE_TAG_INSERT_PERSONAL);
        db.execSQL(TABLE_NOTE_TAG_INSERT_CALLS);
        db.execSQL(TABLE_NOTE_TAG_INSERT_OFFICE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
