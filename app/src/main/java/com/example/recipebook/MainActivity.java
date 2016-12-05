package com.example.recipebook;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void addRecipeOnClick(View v) {
        Intent intent = new Intent(this, AddRecipeActivity.class);
        startActivity(intent);
    }

    public void browseRecipesOnClick(View v) {
        Intent intent = new Intent(this, BrowseRecipesActivity.class);
        startActivity(intent);
    }

    public void searchRecipesOnClick(View v) {
        Intent intent = new Intent(this, SearchRecipesActivity.class);
        startActivity(intent);
    }
}
