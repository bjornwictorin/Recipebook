package com.example.recipebook;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static final int ADD_REQUEST_CODE = 1;
    private static final int BROWSE_REQUEST_CODE = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void addRecipeOnClick(View v) {
        Intent intent = new Intent(this, AddRecipeActivity.class);
        startActivityForResult(intent, ADD_REQUEST_CODE);
    }

    public void browseRecipesOnClick(View v) {
        Intent intent = new Intent(this, BrowseRecipesActivity.class);
        startActivityForResult(intent, BROWSE_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            Bundle bundle = data.getExtras();
            if (requestCode == ADD_REQUEST_CODE) {
                //Display toaster with uri of newly created recipe db entry.
                String uriString = bundle.getParcelable("new_recipe_uri").toString();
                Log.d("G53MDP", uriString);
                String messageString = "Recipe added with URI " + uriString;
                Toast toast = Toast.makeText(getApplicationContext(), messageString,
                        Toast.LENGTH_SHORT);
                toast.show();
            }
        }


    }
}
