package com.example.bakingapp.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.bakingapp.R;
import com.example.bakingapp.fragments.Listeners;
import com.example.bakingapp.holders.RecipeViewHolder;
import com.example.bakingapp.models.Recipe;
import com.example.bakingapp.widget.Constants;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.squareup.picasso.Picasso;


import java.util.List;

public class RecipesAdapter extends RecyclerView.Adapter<RecipeViewHolder>{

    private Context mContext;
    private List<Recipe> mRecipes;
    private Listeners.OnItemClickListener mOnItemClickListener;

    public RecipesAdapter(Context context, List<Recipe> recipes, Listeners.OnItemClickListener onItemClickListener){
        this.mContext = context;
        this.mRecipes = recipes;
        this.mOnItemClickListener = onItemClickListener;
    }


    @NonNull
    @Override
    public RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.recipe_list_item, parent, false);

        return new RecipeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeViewHolder holder, final int position) {
        holder.mTvRecipeName.setText(mRecipes.get(position).getImage());
        holder.mTvServings.setText(mContext.getString(R.string.servings, mRecipes.get(position).getServings()));

        String recipeImage = mRecipes.get(position).getImage();
        if (!recipeImage.isEmpty()){
            Picasso.get().load(recipeImage)
                    .placeholder(R.drawable.ic_dinner)
                    .into(holder.mIvRecipe);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mOnItemClickListener != null){
                    mOnItemClickListener.onItemClick(position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
       return mRecipes.size();
    }
}
