package com.example.bakingapp;

import android.support.test.espresso.IdlingRegistry;
import android.support.test.espresso.IdlingResource;
import android.support.test.rule.ActivityTestRule;

import com.example.bakingapp.activities.MainActivity;
import com.example.bakingapp.widget.GlobalApplication;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;

public abstract class BaseTest {
    protected GlobalApplication globalApplication;
    protected IdlingResource idlingResource;

    @Rule
    public ActivityTestRule<MainActivity> activityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Before
    public void registerIdlingResource(){
        globalApplication = (GlobalApplication) activityTestRule.getActivity().getApplicationContext();
        idlingResource = globalApplication.getIdlingResource();
        IdlingRegistry.getInstance().register(idlingResource);
    }

    @After
    public void unregisterIdlingResource(){
        if (idlingResource != null){
            IdlingRegistry.getInstance().unregister(idlingResource);
        }
    }
}
