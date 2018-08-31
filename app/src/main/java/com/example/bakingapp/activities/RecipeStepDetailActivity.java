package com.example.bakingapp.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.support.v7.widget.Toolbar;

import com.example.bakingapp.R;
import com.example.bakingapp.adapters.StepsFragmentPagerAdapter;
import com.example.bakingapp.models.Recipe;
import com.example.bakingapp.utils.Misc;
import com.orhanobut.logger.Logger;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeStepDetailActivity extends AppCompatActivity{
    public static final String RECIPE_KEY = "recipe_k";
    public static final String STEP_SELECTED_KEY = "step_k";

    @BindView(R.id.recipe_step_tab_layout)
    TabLayout mTlRecipeStep;

    @BindView(R.id.recipe_step_viewpager)
    ViewPager mVpRecipeStep;

    @BindView(android.R.id.content)
    View mParentLayout;

    private Recipe mRecipe;
    private int mStepSelectedPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_step_detail);
        ButterKnife.bind(this);

        Toolbar toolbar = findViewById(R.id.detail_toolbar);
        setSupportActionBar(toolbar);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null && bundle.containsKey(RECIPE_KEY) &&bundle.containsKey(STEP_SELECTED_KEY)){
            mRecipe = bundle.getParcelable(RECIPE_KEY);
            mStepSelectedPosition = bundle.getInt(STEP_SELECTED_KEY);
        }else {
            Misc.makeSnackBar(this, mParentLayout, getString(R.string.load_data_fail_recipe), true);
            finish();
        }
        //Show up button in action bar
        final ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.setTitle(mRecipe.getName());
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        StepsFragmentPagerAdapter adapter = new StepsFragmentPagerAdapter(getApplicationContext(), mRecipe.getSteps(), getSupportFragmentManager());
        mVpRecipeStep.setAdapter(adapter);
        mTlRecipeStep.setupWithViewPager(mVpRecipeStep);
        mVpRecipeStep.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (actionBar != null){
                    actionBar.setTitle(mRecipe.getSteps().get(position).getShortDescription());
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        mVpRecipeStep.setCurrentItem(mStepSelectedPosition);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Logger.d("onDestroy");
    }
}
