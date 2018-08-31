package com.example.bakingapp.holders;


import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.bakingapp.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.recipe_name_text)
    public TextView mTvRecipeName;

    @BindView(R.id.servings_text)
    public TextView mTvServings;

    @BindView(R.id.recipe_image)
    public AppCompatImageView mIvRecipe;

    public RecipeViewHolder(View itemView) {
        super(itemView);

        ButterKnife.bind(this, itemView);
    }
}
