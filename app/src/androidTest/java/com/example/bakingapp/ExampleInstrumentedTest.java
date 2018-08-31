package com.example.bakingapp;

import android.content.Context;
import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.intent.Intents;
import android.support.test.runner.AndroidJUnit4;

import com.example.bakingapp.activities.RecipeInfoActivity;
import com.example.bakingapp.activities.RecipeStepDetailActivity;
import com.example.bakingapp.models.Recipe;
import com.example.bakingapp.widget.Prefs;

import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasExtraWithKey;
import static android.support.test.espresso.matcher.ViewMatchers.isCompletelyDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest extends BaseTest {
    @Test
    public void clickRecyclerViewItemHasIntentWithAKey() {
        // check if key present
        Intents.init();
        Navigation.getMeToRecipeInfo(0);
        intended(hasExtraWithKey(RecipeInfoActivity.RECIPE_KEY));
        Intents.release();
    }

    @Test
    public void clickOnRecyclerViewStepItem_opensRecipeStepActivity_orFragment(){
        Navigation.getMeToRecipeInfo(0);

        boolean twoPaneMode = globalApplication.getResources().getBoolean(R.bool.twoPaneMode);
        if (!twoPaneMode){
            Intents.init();;
            Navigation.selectRecipeStep(1);
            intended(hasComponent(RecipeStepDetailActivity.class.getName()));
            intended(hasExtraWithKey(RecipeStepDetailActivity.RECIPE_KEY));
            intended(hasExtraWithKey(RecipeStepDetailActivity.STEP_SELECTED_KEY));
            Intents.release();
            //check TabLayout
            onView(withId(R.id.recipe_step_tab_layout))
                    .check(matches(isCompletelyDisplayed()));
        }else {
            Navigation.selectRecipeStep(1);
            onView(withId(R.id.exo_player_view)).check(matches(isDisplayed()));
        }
    }
    @Test
    public void clickOnRecyclerViewItem_opensRecipeInfoActivity(){
        Navigation.getMeToRecipeInfo(0);
        onView(withId(R.id.ingredients_text)).check(matches(isDisplayed()));
        onView(withId(R.id.recipe_step_list)).check(matches(isDisplayed()));
    }

    @Test
    public void checkAddWidgetButtonFunctionality(){
        globalApplication.getSharedPreferences(Prefs.PREFS_NAME, Context.MODE_PRIVATE).edit()
                                                .clear().commit();

        Navigation.getMeToRecipeInfo(0);
        onView(withId(R.id.action_add_to_widget)).check(matches(isDisplayed())).perform(click());

        //get recipe base 64 string from sharedPrefs
        Recipe recipe = Prefs.loadRecipe(globalApplication);

        //assert recipe is not null
        assertNotNull(recipe);
    }
}
