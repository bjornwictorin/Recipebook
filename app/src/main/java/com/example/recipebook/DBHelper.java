package com.example.recipebook;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Björn on 2016-12-01.
 */

public class DBHelper extends SQLiteOpenHelper {
    public DBHelper (Context context, String name, SQLiteDatabase.CursorFactory cf, int nbr) {
        super(context, name, cf, nbr);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //Sets up the database table.
        db.execSQL("CREATE TABLE recipes (_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "recipetitle TEXT, recipeinstructions TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS recipes");
        onCreate(db);
    }
}
