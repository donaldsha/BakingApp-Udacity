package com.example.bakingapp.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.bakingapp.R;
import com.example.bakingapp.models.Recipe;
import com.example.bakingapp.fragments.RecipesFragment;

public class MainActivity extends AppCompatActivity implements RecipesFragment.OnRecipeClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public void onRecipeSelected(Recipe recipe){
        Intent intent = new Intent(this, RecipeInfoActivity.class);
        intent.putExtra(RecipeInfoActivity.RECIPE_KEY, recipe);
        startActivity(intent);
    }
}
