package com.example.recipebook;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class BrowseRecipesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browse_recipes);
    }

    @Override
    protected void onStart() {
        super.onStart();
        //Query the database. This is placed in the onStart method in order to make the
        //list update whenever this activity is started. This is especially important when the user
        //has deleted a recipe, and is taken back to the browse activity.
        //Choose which type of data from the content provider that is desired.
        String[] projection = new String[] {RecipeProviderContract._ID,
                RecipeProviderContract.RECIPE_TITLE};
        //Makes the titles sorted in alphabetical order (case insensitive).
        String sortOrder = RecipeProviderContract.RECIPE_TITLE + " COLLATE NOCASE ASC";
        //Actual query.
        Cursor cursor = getContentResolver().query(RecipeProviderContract.RECIPE_URI, projection,
                null, null, sortOrder);
        //List to hold ids.
        final ArrayList<Integer> idList = new ArrayList<Integer>();
        if (cursor.moveToFirst()) {
            do {
                idList.add(cursor.getInt(0));
            } while(cursor.moveToNext());
        }
        //Create an adapter to view the titles in a list.
        String[] fromColumns = {RecipeProviderContract.RECIPE_TITLE, RecipeProviderContract._ID};
        int[] toViews = {R.id.title_layout};
        SimpleCursorAdapter adapter = new SimpleCursorAdapter(this, R.layout.title_list_elements,
                cursor, fromColumns, toViews, 0);
        //Populate the list.
        ListView lv = (ListView) findViewById(R.id.recipe_title_list);
        lv.setAdapter(adapter);

        //Handle click events in the list.
        AdapterView.OnItemClickListener clickHandler = new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView parent, View v, int position, long id) {
                //Launch the ViewRecipeActivity, and pass it the id of the recipe to view.
                Intent intent = new Intent(BrowseRecipesActivity.this, ViewRecipeActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt("recipe_id", idList.get(position));
                intent.putExtras(bundle);
                startActivity(intent);
            }
        };
        lv.setOnItemClickListener(clickHandler);
    }
}
