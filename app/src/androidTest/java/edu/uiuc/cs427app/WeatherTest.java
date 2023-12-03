package edu.uiuc.cs427app;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import static org.hamcrest.Matchers.not;

import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.SystemClock;
import android.util.Log;

import androidx.test.espresso.Espresso;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import com.beastwall.localisation.model.City;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Random;

import edu.uiuc.cs427app.activity.CityActivity;
import edu.uiuc.cs427app.activity.MainActivity;
import edu.uiuc.cs427app.activity.MapActivity;

// More examples about tests: https://github.com/android/testing-samples

@RunWith(AndroidJUnit4.class)
public class WeatherTest {

    // Launches a given activity (MainActivity in our example) before the test starts and closes after the test.
    @Rule
    public ActivityTestRule<CityActivity> activityTestRule = new ActivityTestRule<>(CityActivity.class, true, false);




    @Test
    public void testcity_Dallas()  {

        Intent intent = new Intent();

        intent.putExtra("cityName", "Dallas,Texas,United States");


        // Launch CityActivity with the stubbed intent
        activityTestRule.launchActivity(intent);

        // Perform actions and assertions

        onView(withId(R.id.buttonWeather)).perform(click()); // click on weather BUTTON
        onView(withId(R.id.city22)).check(matches(not(withText("Dallas")))); // check if the city displayed matches

        onView(withId(R.id.Weather)).check(matches(not(withText("Weather"))));
        onView(withId(R.id.Temperature)).check(matches(not(withText("Temperature"))));
        onView(withId(R.id.Humidity)).check(matches(not(withText("Humidity"))));
        onView(withId(R.id.Wind_condition)).check(matches(not(withText("Wind Speed"))));


    }

    @Test
    public void testcity_New_York() throws InterruptedException {

        // Create an intent similar to what MainActivity would send
        Intent intent = new Intent();
        intent.putExtra("cityName", "New York,New York,United States");

        // Launch CityActivity with the stubbed intent
        activityTestRule.launchActivity(intent);

        // Perform actions and assertions

        onView(withId(R.id.buttonWeather)).perform(click()); // click on weather BUTTON
        onView(withId(R.id.city22)).check(matches(not(withText("New York")))); // check if the city displayed matches
        onView(withId(R.id.Weather)).check(matches(not(withText("Weather"))));
        onView(withId(R.id.Temperature)).check(matches(not(withText("Temperature"))));
        onView(withId(R.id.Humidity)).check(matches(not(withText("Humidity"))));
        onView(withId(R.id.Wind_condition)).check(matches(not(withText("Wind Speed"))));





    }


}
