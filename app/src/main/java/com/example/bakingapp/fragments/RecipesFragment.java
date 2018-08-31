package com.example.bakingapp.fragments;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.bakingapp.R;
import com.example.bakingapp.adapters.RecipesAdapter;
import com.example.bakingapp.api.RecipesApiCallback;
import com.example.bakingapp.api.RecipesApiManager;
import com.example.bakingapp.models.Recipe;
import com.example.bakingapp.utils.Misc;
import com.example.bakingapp.utils.SpacingItemDecoration;
import com.example.bakingapp.widget.AppWidgetService;
import com.example.bakingapp.widget.GlobalApplication;
import com.example.bakingapp.widget.Prefs;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class RecipesFragment extends Fragment {

    @BindView(R.id.recipes_recycler_view)
    RecyclerView mRecipesRecyclerView;

    @BindView(R.id.pull_to_refresh)
    SwipeRefreshLayout mPullToRefresh;

    @BindView(R.id.noDataContainer)
    ConstraintLayout mNoDataContainer;
    private static String RECIPES_KEY = "recipes";

    private OnRecipeClickListener mListener;
    private Unbinder unbinder;
    private List<Recipe> mRecipes;
    private GlobalApplication globalApplication;

    private final BroadcastReceiver networkChangeReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (mRecipes == null){
                loadRecipes();
            }
        }
    };

    //Empty Constructor
    public RecipesFragment(){

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //Inflate The layout for this fragment
        View viewRoot = inflater.inflate(R.layout.fragment_recipes, container, false);
        unbinder = ButterKnife.bind(this, viewRoot);
        mPullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadRecipes();
            }
        });

        mNoDataContainer.setVisibility(View.VISIBLE);
        setupRecyclerView();

        globalApplication = (GlobalApplication) getActivity().getApplicationContext();
        globalApplication.setIdleState(false);
        if (savedInstanceState != null && savedInstanceState.containsKey(RECIPES_KEY)){
            mRecipes = savedInstanceState.getParcelableArrayList(RECIPES_KEY);
            mRecipesRecyclerView.setAdapter(new RecipesAdapter(getActivity().getApplicationContext(), mRecipes, new Listeners.OnItemClickListener() {
                @Override
                public void onItemClick(int position) {
                    mListener.onRecipeSelected(mRecipes.get(position));
                }
            }));
            dataLoadedTakeCareLayout();
        }

        return viewRoot;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnRecipeClickListener){
            mListener = (OnRecipeClickListener) context;
        }else {
            throw new RuntimeException(context.toString() + " must implement OnRecipeClickListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        Logger.d("onDestroyView");
    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().registerReceiver(networkChangeReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
    }

    @Override
    public void onPause() {
        super.onPause();
        getActivity().unregisterReceiver(networkChangeReceiver);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mRecipes != null && !mRecipes.isEmpty()){
            outState.putParcelableArrayList(RECIPES_KEY, (ArrayList<? extends Parcelable>) mRecipes);
        }
    }

    private void setupRecyclerView(){
        mRecipesRecyclerView.setVisibility(View.GONE);
        mRecipesRecyclerView.setHasFixedSize(true);
        boolean twoPaneMode = getResources().getBoolean(R.bool.twoPaneMode);

        if (twoPaneMode){
            mRecipesRecyclerView.setLayoutManager(new GridLayoutManager(getActivity().getApplicationContext(), 3));
        }else {
            mRecipesRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext(), LinearLayoutManager.VERTICAL, false));
        }
        mRecipesRecyclerView.addItemDecoration(new SpacingItemDecoration((int) getResources().getDimension(R.dimen.margin_m)));
        mRecipesRecyclerView.addOnItemTouchListener(new RecyclerView.SimpleOnItemTouchListener());
    }

    private void loadRecipes(){
        if (Misc.isNetworkAvailable(getActivity().getApplicationContext())){
            mPullToRefresh.setRefreshing(true);

            RecipesApiManager.getInstance().getRecipes(new RecipesApiCallback<List<Recipe>>() {
                @Override
                public void onResponse(List<Recipe> result) {
                    if (result != null){
                        mRecipes = result;
                        mRecipesRecyclerView.setAdapter(new RecipesAdapter(getActivity().getApplicationContext(), mRecipes, new Listeners.OnItemClickListener() {
                            @Override
                            public void onItemClick(int position) {
                                mListener.onRecipeSelected(mRecipes.get(position));
                            }
                        }));
                        //set default recipe
                        if (Prefs.loadRecipe(getActivity().getApplicationContext())== null){
                            AppWidgetService.updateWidget(getActivity(), mRecipes.get(0));

                        }
                    }else {
                        Misc.makeSnackBar(getActivity(), getView(), getString(R.string.load_data_fail), true);
                    }
                    dataLoadedTakeCareLayout();
                }

                @Override
                public void onCancel() {
                    dataLoadedTakeCareLayout();
                }
            });
        }else {
            Misc.makeSnackBar(getActivity(), getView(), getString(R.string.no_internet), true);
        }
    }
    //check if data loaded
    private void dataLoadedTakeCareLayout(){
        boolean loaded = mRecipes != null && mRecipes.size() >0;
        mPullToRefresh.setRefreshing(false);

        mRecipesRecyclerView.setVisibility(loaded ? View.VISIBLE : View.GONE);
        mNoDataContainer.setVisibility(loaded ? View.GONE : View.VISIBLE);

        globalApplication.setIdleState(true);
    }

    /**
     * Interface created to allow communication between Fragments
     */
    public interface OnRecipeClickListener{
        void onRecipeSelected(Recipe recipe);
    }
}
