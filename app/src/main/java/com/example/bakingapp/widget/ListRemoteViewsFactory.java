package com.example.bakingapp.widget;

import android.content.Context;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.example.bakingapp.R;
import com.example.bakingapp.models.Recipe;

public class ListRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory{

    private Context mContext;
    private Recipe recipe;

    public ListRemoteViewsFactory(Context context){
        this.mContext = context;
    }
    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {
        recipe = Prefs.loadRecipe(mContext);
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return recipe.getIngredients().size();
    }

    @Override
    public RemoteViews getViewAt(int i) {
        RemoteViews row = new RemoteViews(mContext.getPackageName(), R.layout.baking_recipe_app_widget_list_item);
        row.setTextViewText(R.id.ingredient_item_text, recipe.getIngredients().get(i).getIngredient());

        return row;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }
}
