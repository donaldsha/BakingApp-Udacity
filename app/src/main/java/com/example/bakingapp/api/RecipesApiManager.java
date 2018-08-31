package com.example.bakingapp.api;


import android.provider.SyncStateContract;

import com.example.bakingapp.fragments.Listeners;
import com.example.bakingapp.models.Recipe;
import com.example.bakingapp.widget.Constants;

import java.io.Serializable;
import java.util.List;
import com.orhanobut.logger.Logger;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

public final class RecipesApiManager implements Serializable{

    private RecipesApiService recipesApiService;
    private static volatile RecipesApiManager sharedInstance = new RecipesApiManager();

    private RecipesApiManager(){
        //prevent from reflection Api
        if (sharedInstance != null){
            throw new RuntimeException("Use getInstance() method to get the single instance of this class");
        }
        Retrofit retrofit = new Retrofit.Builder().baseUrl(Constants.RECIPES_API_URL)
                                        .addConverterFactory(JacksonConverterFactory.create())
                                        .build();
        recipesApiService =retrofit.create(RecipesApiService.class);
    }

    public static RecipesApiManager getInstance(){
        if (sharedInstance == null){
            synchronized (RecipesApiManager.class){
                if (sharedInstance == null){
                    sharedInstance = new RecipesApiManager();
                }
            }
        }
        return sharedInstance;
    }

    public void getRecipes(final RecipesApiCallback<List<Recipe>> recipesApiCallback){
        recipesApiService.getRecipes().enqueue(new Callback<List<Recipe>>() {
            @Override
            public void onResponse(Call<List<Recipe>> call, Response<List<Recipe>> response) {
                recipesApiCallback.onResponse(response.body());
            }

            @Override
            public void onFailure(Call<List<Recipe>> call, Throwable t) {
                if (call.isCanceled()){
                    Logger.e("Request was cancelled");
                    recipesApiCallback.onCancel();
                }else {
                    Logger.e(t.getMessage());
                    recipesApiCallback.onResponse(null);
                }
            }
        });
    }
}
