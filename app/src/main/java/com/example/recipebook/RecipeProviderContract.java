package com.example.recipebook;

import android.net.Uri;

/**
 * Created by Björn on 2016-12-01.
 */

public class RecipeProviderContract {
    public static final String AUTHORITY = "com.example.recipebook.RecipeContentProvider";
    public static final Uri RECIPE_URI = Uri.parse("content://"+AUTHORITY+"/recipes");
    public static final String _ID = "_id";
    public static final String RECIPE_TITLE = "recipetitle";
    public static final String RECIPE_INSTRUCTIONS = "recipeinstructions";
}
