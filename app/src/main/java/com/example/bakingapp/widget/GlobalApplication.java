package com.example.bakingapp.widget;

import android.app.Application;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.support.test.espresso.IdlingResource;

import com.example.bakingapp.BuildConfig;
import com.example.bakingapp.IdlingResource.RecipesIdlingResource;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;

public class GlobalApplication extends Application{
    //Idling Resource will be null in production
    @Nullable
    private RecipesIdlingResource mIdlingResource;

    @VisibleForTesting
    @NonNull
    private IdlingResource initializeIdlingResource(){
        if (this.mIdlingResource == null){
            mIdlingResource = new RecipesIdlingResource();
        }
        return mIdlingResource;
    }

    public GlobalApplication(){
        if (BuildConfig.DEBUG){
            initializeIdlingResource();
        }
        Logger.addLogAdapter(new AndroidLogAdapter(){
            @Override
            public boolean isLoggable(int priority, String tag) {
                return BuildConfig.DEBUG;
            }
        });
    }

    public void setIdleState(boolean state){
        if (mIdlingResource != null){
            mIdlingResource.setIdleState(state);
        }
    }

    @Nullable
    public RecipesIdlingResource getIdlingResource() {
        return mIdlingResource;
    }
}
