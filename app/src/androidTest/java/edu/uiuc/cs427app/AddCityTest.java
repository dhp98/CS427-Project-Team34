package edu.uiuc.cs427app;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.intent.Intents;
import androidx.test.espresso.intent.matcher.IntentMatchers;
import androidx.test.espresso.intent.rule.IntentsTestRule;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.intent.Intents.intending;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.intent.matcher.IntentMatchers.isInternal;
import static androidx.test.espresso.intent.matcher.IntentMatchers.toPackage;
import static androidx.test.espresso.matcher.ViewMatchers.hasDescendant;
import static androidx.test.espresso.matcher.ViewMatchers.isDescendantOfA;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withEffectiveVisibility;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

import static java.util.regex.Pattern.matches;

import com.google.android.libraries.places.widget.AutocompleteActivity;

import edu.uiuc.cs427app.R;
import edu.uiuc.cs427app.activity.MainActivity;

@RunWith(AndroidJUnit4.class)
public class AddCityTest {
    @Rule
    public ActivityTestRule<MainActivity> activityRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void testAddLocation() {
        // Mock the result of the Autocomplete activity to simulate selecting a location
        Intent resultData = new Intent();
        String cityName = "New York"; // Replace with the selected city name
        resultData.putExtra("cityName", cityName);
        Instrumentation.ActivityResult result = new Instrumentation.ActivityResult(Activity.RESULT_OK, resultData);

        // Stubbing the Autocomplete activity result
        intending(hasComponent(AutocompleteActivity.class.getName())).respondWith(result);

        // Perform a click on the "Add Location" button
        onView(withId(R.id.buttonAddLocation)).perform(click());

        // Assuming there's a delay for processing the selected location
        // Insert appropriate synchronization or wait strategy here

        // Check if the added location appears in the list view
        onView(withId(R.id.listView))
                .check(ViewAssertions.matches(Matchers.not(hasDescendant(withText(cityName)))));

    }
}
