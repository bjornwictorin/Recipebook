package com.example.recipebook;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;

/**
 * Created by Björn on 2016-12-01.
 */

public class RecipeContentProvider extends ContentProvider {

    private DBHelper dbHelper;
    private static final UriMatcher uriMatcher;

    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(RecipeProviderContract.AUTHORITY, "recipes", 1);
    }

    @Override
    public boolean onCreate() {
        Log.d("G53MDP", "com.example.recipebook.RecipeContentProvider onCreate");
        this.dbHelper = new DBHelper(this.getContext(), "recipeDB", null, 1);
        return true;
    }

    @Override
    public String getType(Uri uri) {
        if (uri.getLastPathSegment() == null) {
            return "vnd.android.cursor.dir/RecipeContentProvider.data.text";
        } else {
            return "vnd.android.cursor.item/RecipeContentProvider.data.text";
        }
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String tableName;
        switch (uriMatcher.match(uri)) {
            case 1:
                tableName = "recipes";
                break;
            default:
                tableName = "recipes";
                break;
        }
        long id = db.insert(tableName, null, values);
        Uri nu = ContentUris.withAppendedId(uri, id);
        getContext().getContentResolver().notifyChange(nu, null);
        return nu;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs,
                        String sortOrder) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String tableName;
        switch (uriMatcher.match(uri)) {
            case 1:
                tableName = "recipes";
                break;
            default:
                tableName = "recipes";
                break;
        }
        return db.query(tableName, projection, selection, selectionArgs, null, null, sortOrder);
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        throw new UnsupportedOperationException("Update is not implemented.");
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String tableName;
        switch (uriMatcher.match(uri)) {
            case 1:
                tableName = "recipes";
                break;
            default:
                tableName = "recipes";
                break;
        }
        return db.delete(tableName, selection, selectionArgs);
    }
}
