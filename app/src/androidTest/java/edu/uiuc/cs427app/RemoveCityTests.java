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

//    @Test
//    public void checkRemoveMessage()  {
//
//        // Press the AddLocation button.
//        onView(withId(R.id.buttonRemoveLocation)).perform(click());
//        // By clicking on the button, the test navigates to a new activity
//        // Espresso takes care of this transition and will consider that into account for accessing the widgets
//
//        // Check if the text that appears in the next activity matches with the welcome message for Chicago
//        // "matches" is equivalent to assertion here, e.g., assertEqual
//        onView(withId(R.id.buttonRemoveLocation)).check(matches(withText("Enter Location to remove")));
//    }

    @Test
    public void removeCityWithInputConfirmation() {
        // Click the "Remove Location" button
        onView(withId(R.id.buttonRemoveLocation)).perform(ViewActions.click());

        // Check if the dialog message "Enter Location to remove" is displayed
        onView(withText("Enter Location to remove")).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));

        // Enter text in the input dialog
        String cityNameToRemove = "CityName"; // Replace with the city name you want to remove
        onView(withId(android.R.id.input)).perform(ViewActions.typeText(cityNameToRemove), closeSoftKeyboard());

        // Click on the positive button in the dialog (assuming the OK/Confirm button has an ID "android.R.id.button1")
        onView(withId(android.R.id.button1)).perform(ViewActions.click());

        // Verify if the city has been removed from the list
        onView(withId(R.id.listView))
                .check(ViewAssertions.matches(Matchers.not(hasDescendant(withText(cityNameToRemove)))));
    }
}
