package com.example.bakingapp.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.example.bakingapp.R;
import com.example.bakingapp.activities.MainActivity;
import com.example.bakingapp.models.Recipe;

public class AppWidget extends AppWidgetProvider{

    public static void updateAppWidget(Context context, AppWidgetManager appWidgetManager, int appWidgetId){

        Recipe recipe = Prefs.loadRecipe(context);
        if (recipe != null){
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, new Intent(context, MainActivity.class), 0);
            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.baking_recipes_app_widget);

            views.setTextViewText(R.id.recipe_widget_name_text, recipe.getName());
            views.setOnClickPendingIntent(R.id.recipe_widget_name_text, pendingIntent);
            //initialize the list view
            Intent intent = new Intent(context, AppWidgetService.class);
            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
            //bind remote adapter
            views.setRemoteAdapter(R.id.recipe_widget_listview, intent);

            appWidgetManager.updateAppWidget(appWidgetId, views);
            appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetId, R.id.recipe_widget_listview);

        }
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for (int appWidgetId: appWidgetIds){
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    public static void updateAppWidgets(Context context,  AppWidgetManager appWidgetManager, int[] appWidgetIds){
        for (int appWidgetId: appWidgetIds){
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {

    }

    @Override
    public void onDisabled(Context context) {

    }
}
