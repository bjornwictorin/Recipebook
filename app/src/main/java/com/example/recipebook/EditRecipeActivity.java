package com.example.recipebook;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

public class EditRecipeActivity extends AppCompatActivity {

    private int recipeID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_recipe);
        recipeID = getIntent().getExtras().getInt("recipe_id");

        //Query the database and populate the text fields.
        String[] projection = {RecipeProviderContract._ID, RecipeProviderContract.RECIPE_TITLE,
                RecipeProviderContract.RECIPE_INSTRUCTIONS};
        String selection = RecipeProviderContract._ID + " = ?";
        String[] selectionArgs = {"" + recipeID};
        Cursor cursor = getContentResolver().query(RecipeProviderContract.RECIPE_URI, projection,
                selection, selectionArgs, null);
        cursor.moveToFirst();
        String title = cursor.getString(1);
        String description = cursor.getString(2);
        EditText editTitle = (EditText) findViewById(R.id.edit_title);
        EditText editDescription = (EditText) findViewById(R.id.edit_instruction);
        editTitle.setText(title);
        editDescription.setText(description);
    }

    public void doneEditingOnClick(View v) {
        //Fetch the text from the edit text fields.
        EditText title = (EditText) findViewById(R.id.edit_title);
        EditText instruction = (EditText) findViewById(R.id.edit_instruction);
        String newTitle = title.getText().toString();
        String newInstruction = instruction.getText().toString();
        //Save the new title and description in the database.
        ContentValues newValues = new ContentValues();
        newValues.put(RecipeProviderContract.RECIPE_TITLE, newTitle);
        newValues.put(RecipeProviderContract.RECIPE_INSTRUCTIONS, newInstruction);
        String selection = RecipeProviderContract._ID + " = ?";
        String[] selectionArgs = {"" +  recipeID};
        getContentResolver().update(RecipeProviderContract.RECIPE_URI, newValues, selection,
                selectionArgs);
        setResult(Activity.RESULT_OK);
        finish();
    }
}
