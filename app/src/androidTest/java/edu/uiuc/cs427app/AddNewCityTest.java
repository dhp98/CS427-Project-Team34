package edu.uiuc.cs427app;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.pressBack;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.is;

import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.test.espresso.DataInteraction;
import androidx.test.espresso.ViewInteraction;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import edu.uiuc.cs427app.R;
import edu.uiuc.cs427app.activity.MainActivityFrontPage;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class AddNewCityTest {

    @Rule
    public ActivityScenarioRule<MainActivityFrontPage> mActivityScenarioRule =
            new ActivityScenarioRule<>(MainActivityFrontPage.class);

    @Test
    public void addNewCityTest() throws InterruptedException {
        ViewInteraction materialButton = onView(
                allOf(withId(R.id.gotosignupbutton), withText("Signup"),
                        childAtPosition(
                                allOf(withId(R.id.signupbutton),
                                        childAtPosition(
                                                withId(android.R.id.content),
                                                0)),
                                2),
                        isDisplayed()));
        materialButton.perform(click());
        Thread.sleep(1000);
        ViewInteraction appCompatEditText = onView(
                allOf(withId(R.id.userEmail),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("androidx.constraintlayout.widget.ConstraintLayout")),
                                        0),
                                1),
                        isDisplayed()));
        appCompatEditText.perform(replaceText("dhyeyhp2"), closeSoftKeyboard());
        Thread.sleep(1000);
        ViewInteraction appCompatEditText2 = onView(
                allOf(withId(R.id.userPassword),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("androidx.constraintlayout.widget.ConstraintLayout")),
                                        0),
                                2),
                        isDisplayed()));
        appCompatEditText2.perform(replaceText("123"), closeSoftKeyboard());
        Thread.sleep(1000);
        ViewInteraction appCompatEditText3 = onView(
                allOf(withId(R.id.confirmPassword),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("androidx.constraintlayout.widget.ConstraintLayout")),
                                        0),
                                3),
                        isDisplayed()));
        appCompatEditText3.perform(replaceText("123"), closeSoftKeyboard());
        Thread.sleep(1000);
        ViewInteraction materialButton2 = onView(
                allOf(withId(R.id.signUp), withText("Sign up"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("androidx.constraintlayout.widget.ConstraintLayout")),
                                        0),
                                4),
                        isDisplayed()));
        materialButton2.perform(click());

        ViewInteraction appCompatEditText4 = onView(
                allOf(withId(R.id.userPassword),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("androidx.constraintlayout.widget.ConstraintLayout")),
                                        0),
                                2),
                        isDisplayed()));
        appCompatEditText4.perform(replaceText("123"), closeSoftKeyboard());
        Thread.sleep(1000);
        ViewInteraction materialButton3 = onView(
                allOf(withId(R.id.userLogin), withText("Login"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("androidx.constraintlayout.widget.ConstraintLayout")),
                                        0),
                                3),
                        isDisplayed()));
        materialButton3.perform(click());
        Thread.sleep(1000);
        ViewInteraction materialButton4 = onView(
                allOf(withId(R.id.buttonAddLocation), withText("Add a location"),
                        childAtPosition(
                                allOf(withId(R.id.linearLayout),
                                        childAtPosition(
                                                withClassName(is("androidx.constraintlayout.widget.ConstraintLayout")),
                                                0)),
                                2)));
        materialButton4.perform(scrollTo(), click());
        Thread.sleep(1000);
        ViewInteraction appCompatEditText5 = onView(
                allOf(withId(com.google.android.libraries.places.R.id.places_autocomplete_search_bar),
                        childAtPosition(
                                allOf(withId(com.google.android.libraries.places.R.id.places_autocomplete_search_bar_container),
                                        childAtPosition(
                                                withId(com.google.android.libraries.places.R.id.places_autocomplete_content),
                                                0)),
                                1),
                        isDisplayed()));
        appCompatEditText5.perform(replaceText("champaign"), closeSoftKeyboard());
        Thread.sleep(1000);
        ViewInteraction recyclerView = onView(
                allOf(withId(com.google.android.libraries.places.R.id.places_autocomplete_list),
                        childAtPosition(
                                withId(com.google.android.libraries.places.R.id.places_autocomplete_content),
                                3)));
        recyclerView.perform(actionOnItemAtPosition(0, click()));
        Thread.sleep(1000);
        ViewInteraction materialButton5 = onView(
                allOf(withId(R.id.buttonAddLocation), withText("Add a location"),
                        childAtPosition(
                                allOf(withId(R.id.linearLayout),
                                        childAtPosition(
                                                withClassName(is("androidx.constraintlayout.widget.ConstraintLayout")),
                                                0)),
                                2)));
        materialButton5.perform(scrollTo(), click());
        Thread.sleep(1000);
        ViewInteraction appCompatEditText6 = onView(
                allOf(withId(com.google.android.libraries.places.R.id.places_autocomplete_search_bar),
                        childAtPosition(
                                allOf(withId(com.google.android.libraries.places.R.id.places_autocomplete_search_bar_container),
                                        childAtPosition(
                                                withId(com.google.android.libraries.places.R.id.places_autocomplete_content),
                                                0)),
                                1),
                        isDisplayed()));
        appCompatEditText6.perform(replaceText("London"), closeSoftKeyboard());
        Thread.sleep(1000);
        ViewInteraction recyclerView2 = onView(
                allOf(withId(com.google.android.libraries.places.R.id.places_autocomplete_list),
                        childAtPosition(
                                withId(com.google.android.libraries.places.R.id.places_autocomplete_content),
                                3)));
        recyclerView2.perform(actionOnItemAtPosition(0, click()));
        Thread.sleep(1000);
        ViewInteraction textView = onView(
                allOf(withId(android.R.id.text1), withText("Champaign,Illinois,United States"),
                        withParent(allOf(withId(R.id.listView),
                                withParent(withId(R.id.linearLayout)))),
                        isDisplayed()));
        textView.check(matches(withText("Champaign,Illinois,United States")));
        Thread.sleep(1000);
        DataInteraction materialTextView = onData(anything())
                .inAdapterView(allOf(withId(R.id.listView),
                        childAtPosition(
                                withId(R.id.linearLayout),
                                1)))
                .atPosition(1);
        materialTextView.perform(scrollTo(), click());
        Thread.sleep(1000);
        pressBack();
        Thread.sleep(1000);
        ViewInteraction textView3 = onView(
                allOf(withId(android.R.id.text1), withText("London,England,United Kingdom"),
                        withParent(allOf(withId(R.id.listView),
                                withParent(withId(R.id.linearLayout)))),
                        isDisplayed()));
        textView3.check(matches(withText("London,England,United Kingdom")));
    }

    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }
}
