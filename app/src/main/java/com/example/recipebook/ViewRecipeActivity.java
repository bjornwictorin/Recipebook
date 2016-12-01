package com.example.recipebook;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

public class ViewRecipeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_recipe);

        //Query the database.
        Bundle bundle = getIntent().getExtras();
        int recipeID = bundle.getInt("recipe_id");
        Log.d("G53MDP", "id: " + recipeID);
        String[] projection = {RecipeProviderContract._ID, RecipeProviderContract.RECIPE_TITLE,
                RecipeProviderContract.RECIPE_INSTRUCTIONS};
        String selection = RecipeProviderContract._ID + " = ?";
        String[] selectionArgs = {"" + recipeID};
        Cursor cursor = getContentResolver().query(RecipeProviderContract.RECIPE_URI, projection,
                selection, selectionArgs, null);
        cursor.moveToFirst();
        String title = cursor.getString(1);
        String description = cursor.getString(2);

        //Fill the title textview.
        TextView titleView = (TextView) findViewById(R.id.title);
        TextView descriptionView = (TextView) findViewById(R.id.description);
        titleView.setText(title);
        descriptionView.setText(description);
    }
}
