package com.example.bakingapp.adapters;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.bakingapp.R;
import com.example.bakingapp.fragments.RecipeStepDetailFragment;
import com.example.bakingapp.models.Step;

import java.util.List;

public class StepsFragmentPagerAdapter extends FragmentPagerAdapter{
    private Context mContext;
    private List<Step> mSteps;

    public StepsFragmentPagerAdapter(Context context, List<Step> steps, FragmentManager fragmentManager){
        super(fragmentManager);
        this.mContext = context;
        this.mSteps = steps;
    }

    @Override
    public Fragment getItem(int position) {
        Bundle args = new Bundle();
        args.putParcelable(RecipeStepDetailFragment.STEP_KEY, mSteps.get(position));
        RecipeStepDetailFragment fragment = new RecipeStepDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Nullable
    @Override
    public CharSequence getPageTitle(int position){
        return String.format(mContext.getString(R.string.step), position);
    }

    @Override
    public int getCount() {
        return mSteps.size();
    }
}
