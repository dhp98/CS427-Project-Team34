package edu.uiuc.cs427app;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import android.content.Intent;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Random;

import edu.uiuc.cs427app.activity.CityActivity;

// More examples about tests: https://github.com/android/testing-samples

@RunWith(AndroidJUnit4.class)
public class LocationTestTwoCities {

    // Launches a given activity (MainActivity in our example) before the test starts and closes after the test.
    @Rule
    public ActivityTestRule<CityActivity> activityTestRule = new ActivityTestRule<>(CityActivity.class, true, false);

    private static final String[] CITIES = {
            "New York", "Los Angeles", "Chicago", "Houston",
            "Phoenix", "Philadelphia", "San Antonio", "San Diego",
            "Dallas", "San Jose", "Austin", "Jacksonville",
            "Fort Worth", "Columbus", "Charlotte", "San Francisco",
            "Indianapolis", "Seattle", "Denver"
    };

    private String getRandomCity() {
        Random random = new Random();
        return CITIES[random.nextInt(CITIES.length)];
    }

    @Test
    public void checkMapButton_RandomCityA() throws InterruptedException {

        // Create an intent similar to what MainActivity would send
        Intent intent = new Intent();
        String RandomCityA = getRandomCity();
        intent.putExtra("cityName", RandomCityA);

        // Launch CityActivity with the stubbed intent
        activityTestRule.launchActivity(intent);

        // Perform actions and assertions
        onView(withId(R.id.buttonMap)).perform(click());
        onView(withId(R.id.textViewCityTitle)).check(matches(withText(RandomCityA)));

        Thread.sleep(3000);
    }

    @Test
    public void checkMapButton_RandomCityB() throws InterruptedException {

        // Create an intent similar to what MainActivity would send
        Intent intent = new Intent();
        String RandomCityB = getRandomCity();
        intent.putExtra("cityName", RandomCityB);

        // Launch CityActivity with the stubbed intent
        activityTestRule.launchActivity(intent);

        // Perform actions and assertions
        onView(withId(R.id.buttonMap)).perform(click());
        onView(withId(R.id.textViewCityTitle)).check(matches(withText(RandomCityB)));

        Thread.sleep(3000);
    }


}
