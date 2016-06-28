package com.hfad.popularmovies.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by heleneshaikh on 27/06/16.
 */
public class MovieDatabaseHelper extends SQLiteOpenHelper {
    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "HMDB";
    private static final String TAG = "app";
    SQLiteDatabase db;
    public ContentValues contentValues;

    private double vote_average;
    private int id;
    private String overview;
    private String original_title;
    private String release_date;
    private String poster_path;
    private String popularity;

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
                "RELEASE_DATE NUMERIC" +
                "POSTER_PATH TEXT");
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
