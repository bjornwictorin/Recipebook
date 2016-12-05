package com.example.recipebook;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class ViewRecipeActivity extends AppCompatActivity {

    private int recipeID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_recipe);
    }

    @Override
    protected void onStart() {
        super.onStart();
        //This code is placed in the onStart method because the database has to be queried again
        //if the recipe is edited.
        //Query the database.
        Bundle bundle = getIntent().getExtras();
        recipeID = bundle.getInt("recipe_id");
        String[] projection = {RecipeProviderContract._ID, RecipeProviderContract.RECIPE_TITLE,
                RecipeProviderContract.RECIPE_INSTRUCTIONS};
        String selection = RecipeProviderContract._ID + " = ?";
        String[] selectionArgs = {"" + recipeID};
        Cursor cursor = getContentResolver().query(RecipeProviderContract.RECIPE_URI, projection,
                selection, selectionArgs, null);
        cursor.moveToFirst();
        String title = cursor.getString(1);
        String description = cursor.getString(2);

        //Fill the title and description text views.
        TextView titleView = (TextView) findViewById(R.id.title);
        TextView descriptionView = (TextView) findViewById(R.id.description);
        titleView.setText(title);
        descriptionView.setText(description);
    }

    public void onClickDelete(View v) {
        //Delete the recipe from the database.
        String selection = RecipeProviderContract._ID + " = ?";
        String[] selectionArgs = {"" + recipeID};
        getContentResolver().delete(RecipeProviderContract.RECIPE_URI, selection, selectionArgs);
        getContentResolver().notifyChange(RecipeProviderContract.RECIPE_URI, null);
        //Display toast message that tells the user that the recipe has been deleted.
        Toast toast = Toast.makeText(getApplicationContext(), "The recipe was deleted.",
                Toast.LENGTH_SHORT);
        toast.show();
        //Return the user to the activity that lists the activities.
        finish();
    }

    public void onClickEdit(View v) {
        //Launch an activity that allows the user to edit the recipe.
        Intent intent = new Intent(this, EditRecipeActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt("recipe_id", recipeID);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}
