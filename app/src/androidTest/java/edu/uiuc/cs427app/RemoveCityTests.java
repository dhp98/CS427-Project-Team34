package edu.uiuc.cs427app;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.hasDescendant;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.SystemClock;
import android.util.Log;

import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.hamcrest.Matchers;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import edu.uiuc.cs427app.activity.MainActivity;

// More examples about tests: https://github.com/android/testing-samples

@RunWith(AndroidJUnit4.class)
public class RemoveCityTests {

    // Launches a given activity (MainActivity in our example) before the test starts and closes after the test.
    @Rule
    public ActivityScenarioRule<MainActivity> activityScenarioRule
            = new ActivityScenarioRule<>(MainActivity.class);

    @Test
    public void testRemoveLocation() {
        // Input for location removal
        String locationToRemove = "New York";

        // Simulate click on the remove location button
        onView(ViewMatchers.withId(R.id.buttonRemoveLocation)).perform(ViewActions.click());

        // Verify if the AlertDialog is displayed
        onView(withText("Enter Location to remove")).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));

        // Input text into the EditText in the AlertDialog
        onView(ViewMatchers.withId(android.R.id.edit)).perform(ViewActions.typeText(locationToRemove));

        // Click on the positive button "Remove" in the AlertDialog
        onView(withText("Remove")).perform(ViewActions.click());

        // Here you can add verification steps based on the expected behavior after removal,
        // such as verifying the updated list view contents or other UI changes.
        // For example, assuming the list view has an ID of R.id.listView:

        // Verify that the list view has been updated after removal
        onView(withId(R.id.listView))
                .check(ViewAssertions.matches(Matchers.not(hasDescendant(withText(locationToRemove)))));
    }
}
