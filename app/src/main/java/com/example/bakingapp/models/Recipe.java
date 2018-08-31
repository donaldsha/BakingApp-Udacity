package com.example.bakingapp.models;


import android.os.Parcel;
import android.os.Parcelable;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import com.orhanobut.logger.Logger; //got this from github  https://github.com/orhanobut/logger

public class Recipe implements Parcelable {
    @JsonProperty("image")
    private String image;

    @JsonProperty("servings")
    private int servings;

    @JsonProperty("name")
    private String name;

    @JsonProperty("ingredients")
    private List<Ingredients> ingredients;

    @JsonProperty("id")
    private int id;

    @JsonProperty("steps")
    private List<Step> steps;

    public Recipe(){
        this.image = "";
        this.servings = 0;
        this.name = "";
        this.ingredients = new ArrayList<>();
        this.id = 0;
        this.steps = new ArrayList<>();
    }

    protected Recipe(Parcel in) {
        this.image = in.readString();
        this.servings = in.readInt();
        this.name = in.readString();
        this.ingredients = new ArrayList<>();
        in.readList(this.ingredients, Ingredients.class.getClassLoader());
        this.id = in.readInt();
        this.steps = new ArrayList<>();
        in.readList(this.steps, Step.class.getClassLoader());
    }

    public static final Parcelable.Creator<Recipe> CREATOR = new Parcelable.Creator<Recipe>() {
        @Override
        public Recipe createFromParcel(Parcel in) {
            return new Recipe(in);
        }

        @Override
        public Recipe[] newArray(int size) {
            return new Recipe[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.image);
        parcel.writeInt(this.servings);
        parcel.writeString(this.name);
        parcel.writeList(this.ingredients);
        parcel.writeInt(this.id);
        parcel.writeList(this.steps);
    }

    //getters and setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public List<Ingredients> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<Ingredients> ingredients) {
        this.ingredients = ingredients;
    }

    public List<Step> getSteps() {
        return steps;
    }

    public void setSteps(List<Step> steps) {
        this.steps = steps;
    }

    public int getServings() {
        return servings;
    }

    public void setServings(int servings) {
        this.servings = servings;
    }

    public static String toBase64String(Recipe recipe){
        ObjectMapper mapper = new ObjectMapper();
        try {
            return Base64.encodeToString(mapper.writeValueAsBytes(recipe), 0);
        }catch (JsonProcessingException e){
            Logger.e(e.getMessage());
        }
        return null;
    }

    public static Recipe fromBase64(String encoded){
        if (!"".equals(encoded)){
            ObjectMapper mapper = new ObjectMapper();
            try {
                return mapper.readValue(Base64.decode(encoded, 0), Recipe.class);
            }catch (IOException e){
                Logger.e(e.getMessage());
            }
        }
        return null;
    }

    @Override
    public String toString(){
        return "Recipe{" +
                "image='" + image + '\'' +
                ", servings=" + servings +
                ", name='" + name + '\'' +
                ", ingredients=" + ingredients +
                ", id=" + id +
                ", steps=" + steps +
                '}';
    }
}
