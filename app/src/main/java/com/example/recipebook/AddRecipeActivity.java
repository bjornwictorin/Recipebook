package com.example.recipebook;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

public class AddRecipeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_recipe);
    }

    public void createRecipeOnClick(View v) {
        Log.d("G53MDP", "createRecipeOnClick");
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
        //Store URI of the new recipe as the activity's result and finish the activity.
        Bundle bundle = new Bundle();
        bundle.putParcelable("new_recipe_uri", newRecipeUri);
        Intent result = new Intent();
        result.putExtras(bundle);
        setResult(Activity.RESULT_OK, result);
        finish();
    }
}
