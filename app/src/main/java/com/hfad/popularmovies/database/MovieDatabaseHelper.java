package com.hfad.popularmovies.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class MovieDatabaseHelper extends SQLiteOpenHelper {
    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "HMDB";
    SQLiteDatabase db;


    public MovieDatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE MOVIE(" +
                "_id INTEGER PRIMARY KEY" +
                "MOVIE_ID INTEGER" +
                "VOTE_AVERAGE REAL" +
                "OVERVIEW TEXT" +
                "ORIGINAL_TITLE TEXT" +
                "RELEASE_DATE TEXT" +
                "POSTER_PATH TEXT" +
                "POPULARITY TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
