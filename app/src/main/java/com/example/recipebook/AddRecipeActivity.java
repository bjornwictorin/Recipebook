package com.example.recipebook;

import android.content.ContentValues;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class AddRecipeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_recipe);
    }

    public void createRecipeOnClick(View v) {
        //Read text input and store temporarily in strings.
        EditText editTitle = (EditText) findViewById(R.id.edit_title);
        String title = editTitle.getText().toString();
        EditText editInstruction = (EditText) findViewById(R.id.edit_instruction);
        String instruction = editInstruction.getText().toString();
        //Store text input in the database, in the recipe table.
        ContentValues recipeValues = new ContentValues();
        recipeValues.put(RecipeProviderContract.RECIPE_TITLE, title);
        recipeValues.put(RecipeProviderContract.RECIPE_INSTRUCTIONS, instruction);
        Uri newRecipeUri = getContentResolver().insert(RecipeProviderContract.RECIPE_URI,
                recipeValues);
        //Display toaster messaging that the recipe has been added to the database.
        Toast toast = Toast.makeText(getApplicationContext(), "Recipe added.", Toast.LENGTH_SHORT);
        toast.show();
        finish();
    }
}
