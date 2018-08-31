package com.example.bakingapp.widget;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.content.ContextCompat;

import com.example.bakingapp.R;
import com.example.bakingapp.models.Recipe;

public class Prefs {
    public static final String PREFS_NAME = "prefs";

    public static void saveRecipe(Context context, Recipe recipe){
        SharedPreferences.Editor prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE).edit();
        prefs.putString(context.getString(R.string.widget_recipe_key), Recipe.toBase64String(recipe));
        prefs.apply();
    }

    public static Recipe loadRecipe(Context context){
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        String recipeBase64 = prefs.getString(context.getString(R.string.widget_recipe_key), "");
        return "".equals(recipeBase64) ? null : Recipe.fromBase64(prefs.getString(context.getString(R.string.widget_recipe_key), ""));
    }
}
