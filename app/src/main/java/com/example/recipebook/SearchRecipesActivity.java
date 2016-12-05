package com.example.recipebook;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import java.util.ArrayList;

public class SearchRecipesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_recipes);
    }

    @Override
    protected void onStart() {
        super.onStart();
        //onSearchClick is called here to make sure that the result list is updated every time the
        //activity starts. This is important since otherwise the list will show recipes that have
        //been deleted if you delete a recipe that you have found through the search function.
        onSearchClick(null);
    }

    public void onSearchClick(View v) {
        //Fetch the text from the search field.
        EditText searchBox = (EditText) findViewById(R.id.search_box);
        String searchTerm = searchBox.getText().toString();
        //Create and make a db query based on the search term.
        String[] projection = {RecipeProviderContract._ID, RecipeProviderContract.RECIPE_TITLE};
        //This search query will make the database return all recipes that contain the search term
        //anywhere in the title or the instruction.
        String selection = RecipeProviderContract.RECIPE_TITLE + " LIKE ? OR " +
                RecipeProviderContract.RECIPE_INSTRUCTIONS + " LIKE ?";
        //The % signs are here to let the search term be a substring present anywhere in the title
        //or instruction.
        String[] selectionArgs = {"%" + searchTerm + "%", "%" + searchTerm + "%"};
        //Makes the recipes ordered alphabetically without regard to upper or lower case.
        String sortOrder = RecipeProviderContract.RECIPE_TITLE + " COLLATE NOCASE ASC";
        Cursor cursor = getContentResolver().query(RecipeProviderContract.RECIPE_URI,
                projection, selection, selectionArgs, sortOrder);
        if (cursor.getCount() < 1) {
            //No search results.
            Toast toast = Toast.makeText(getApplicationContext(),
                    "No recipes matched your search term.", Toast.LENGTH_SHORT);
            toast.show();
            //Clear all elements in the list.
            ListView lv = (ListView) findViewById(R.id.search_results_list);
            lv.setAdapter(null);
        } else {
            //Recipes found that match the search term.
            //List to hold the ids of the recipes in the search result.
            final ArrayList<Integer> idList = new ArrayList<Integer>();
            cursor.moveToFirst();
            do {
                idList.add(cursor.getInt(0));
            } while (cursor.moveToNext());
            //Create an adapter to view the titles in a list.
            String[] fromColumns = {RecipeProviderContract.RECIPE_TITLE, RecipeProviderContract._ID};
            int[] toViews = {R.id.title_layout};
            SimpleCursorAdapter adapter = new SimpleCursorAdapter(this, R.layout.title_list_elements,
                    cursor, fromColumns, toViews, 0);
            //Populate the list.
            ListView lv = (ListView) findViewById(R.id.search_results_list);
            lv.setAdapter(adapter);

            //Handle click events in the list.
            AdapterView.OnItemClickListener clickHandler = new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView parent, View v, int position, long id) {
                    //Launch the ViewRecipeActivity, and pass it the id of the recipe to view.
                    Intent intent = new Intent(SearchRecipesActivity.this, ViewRecipeActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putInt("recipe_id", idList.get(position));
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            };
            lv.setOnItemClickListener(clickHandler);
        }
    }
}
