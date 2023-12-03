package edu.uiuc.cs427app;


import androidx.test.espresso.DataInteraction;
import androidx.test.espresso.ViewInteraction;
import androidx.test.filters.LargeTest;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.ListView;

import static androidx.test.InstrumentationRegistry.getInstrumentation;
import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.pressBack;
import static androidx.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static androidx.test.espresso.action.ViewActions.*;
import static androidx.test.espresso.assertion.ViewAssertions.*;
import static androidx.test.espresso.matcher.ViewMatchers.*;

import edu.uiuc.cs427app.R;
import edu.uiuc.cs427app.activity.MainActivityFrontPage;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.hamcrest.core.IsInstanceOf;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.is;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class RemoveCityTest {

    @Rule
    public ActivityScenarioRule<MainActivityFrontPage> mActivityScenarioRule =
            new ActivityScenarioRule<>(MainActivityFrontPage.class);

    @Test
    public void removeCityTest() throws InterruptedException {
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
        appCompatEditText2.perform(longClick());
        Thread.sleep(1000);
        ViewInteraction appCompatEditText3 = onView(
                allOf(withId(R.id.userEmail), withText("dhyeyhp2"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("androidx.constraintlayout.widget.ConstraintLayout")),
                                        0),
                                1),
                        isDisplayed()));
        appCompatEditText3.perform(replaceText("dhyeyhp2"));
        Thread.sleep(1000);
        ViewInteraction appCompatEditText4 = onView(
                allOf(withId(R.id.userEmail), withText("dhyeyhp2"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("androidx.constraintlayout.widget.ConstraintLayout")),
                                        0),
                                1),
                        isDisplayed()));
        appCompatEditText4.perform(closeSoftKeyboard());
        Thread.sleep(1000);
        ViewInteraction appCompatEditText5 = onView(
                allOf(withId(R.id.userPassword),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("androidx.constraintlayout.widget.ConstraintLayout")),
                                        0),
                                2),
                        isDisplayed()));
        appCompatEditText5.perform(replaceText("123"), closeSoftKeyboard());
        Thread.sleep(1000);
        ViewInteraction appCompatEditText6 = onView(
                allOf(withId(R.id.confirmPassword),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("androidx.constraintlayout.widget.ConstraintLayout")),
                                        0),
                                3),
                        isDisplayed()));
        appCompatEditText6.perform(replaceText("123"), closeSoftKeyboard());
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
        Thread.sleep(1000);
        ViewInteraction appCompatEditText7 = onView(
                allOf(withId(R.id.userPassword),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("androidx.constraintlayout.widget.ConstraintLayout")),
                                        0),
                                2),
                        isDisplayed()));
        appCompatEditText7.perform(replaceText("123"), closeSoftKeyboard());
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
        ViewInteraction appCompatEditText8 = onView(
                allOf(withId(com.google.android.libraries.places.R.id.places_autocomplete_search_bar),
                        childAtPosition(
                                allOf(withId(com.google.android.libraries.places.R.id.places_autocomplete_search_bar_container),
                                        childAtPosition(
                                                withId(com.google.android.libraries.places.R.id.places_autocomplete_content),
                                                0)),
                                1),
                        isDisplayed()));
        appCompatEditText8.perform(click());
        Thread.sleep(1000);
        ViewInteraction appCompatEditText9 = onView(
                allOf(withId(com.google.android.libraries.places.R.id.places_autocomplete_search_bar),
                        childAtPosition(
                                allOf(withId(com.google.android.libraries.places.R.id.places_autocomplete_search_bar_container),
                                        childAtPosition(
                                                withId(com.google.android.libraries.places.R.id.places_autocomplete_content),
                                                0)),
                                1),
                        isDisplayed()));
        appCompatEditText9.perform(replaceText("champaign"), closeSoftKeyboard());
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
        ViewInteraction appCompatEditText10 = onView(
                allOf(withId(com.google.android.libraries.places.R.id.places_autocomplete_search_bar),
                        childAtPosition(
                                allOf(withId(com.google.android.libraries.places.R.id.places_autocomplete_search_bar_container),
                                        childAtPosition(
                                                withId(com.google.android.libraries.places.R.id.places_autocomplete_content),
                                                0)),
                                1),
                        isDisplayed()));
        appCompatEditText10.perform(click());
        Thread.sleep(1000);
        ViewInteraction appCompatEditText11 = onView(
                allOf(withId(com.google.android.libraries.places.R.id.places_autocomplete_search_bar),
                        childAtPosition(
                                allOf(withId(com.google.android.libraries.places.R.id.places_autocomplete_search_bar_container),
                                        childAtPosition(
                                                withId(com.google.android.libraries.places.R.id.places_autocomplete_content),
                                                0)),
                                1),
                        isDisplayed()));
        appCompatEditText11.perform(replaceText("London"), closeSoftKeyboard());
        Thread.sleep(1000);
        ViewInteraction recyclerView2 = onView(
                allOf(withId(com.google.android.libraries.places.R.id.places_autocomplete_list),
                        childAtPosition(
                                withId(com.google.android.libraries.places.R.id.places_autocomplete_content),
                                3)));
        recyclerView2.perform(actionOnItemAtPosition(0, click()));

        Thread.sleep(1000);
        ViewInteraction materialButton10 = onView(
                allOf(withId(R.id.buttonRemoveLocation), withText("Remove a location"),
                        childAtPosition(
                                allOf(withId(R.id.linearLayout),
                                        childAtPosition(
                                                withClassName(is("androidx.constraintlayout.widget.ConstraintLayout")),
                                                0)),
                                3)));
        materialButton10.perform(scrollTo(), click());
        Thread.sleep(1000);
        ViewInteraction editText3 = onView(
                allOf(childAtPosition(
                                allOf(withId(androidx.appcompat.R.id.custom),
                                        childAtPosition(
                                                withId(androidx.appcompat.R.id.customPanel),
                                                0)),
                                0),
                        isDisplayed()));
        editText3.perform(replaceText("London"), closeSoftKeyboard());
        Thread.sleep(1000);
        ViewInteraction materialButton11 = onView(
                allOf(withId(android.R.id.button1), withText("Remove"),
                        childAtPosition(
                                childAtPosition(
                                        withId(androidx.appcompat.R.id.buttonPanel),
                                        0),
                                3)));
        materialButton11.perform(scrollTo(), click());
        Thread.sleep(1000);
//      Asserting that after removing the city only one city remains in the listview.
        onView(withId(R.id.listView))
                .check(matches(new TypeSafeMatcher<View>() {
                    @Override
                    protected boolean matchesSafely(View item) {
                        ListView listView = (ListView) item;
                        return listView.getCount() == 1;
                    }

                    @Override
                    public void describeTo(Description description) {
                        description.appendText("ListView should have only one item");
                    }
                }));

        ViewInteraction textView = onView(
                allOf(withId(android.R.id.text1), withText("Champaign,Illinois,United States"),
                        withParent(allOf(withId(R.id.listView),
                                withParent(withId(R.id.linearLayout)))),
                        isDisplayed()));

        textView.check(matches(withText("Champaign,Illinois,United States")));
        Thread.sleep(1000);
        ViewInteraction materialButton12 = onView(
                allOf(withId(R.id.buttonRemoveLocation), withText("Remove a location"),
                        childAtPosition(
                                allOf(withId(R.id.linearLayout),
                                        childAtPosition(
                                                withClassName(is("androidx.constraintlayout.widget.ConstraintLayout")),
                                                0)),
                                3)));
        materialButton12.perform(scrollTo(), click());
        Thread.sleep(1000);
        ViewInteraction editText4 = onView(
                allOf(childAtPosition(
                                allOf(withId(androidx.appcompat.R.id.custom),
                                        childAtPosition(
                                                withId(androidx.appcompat.R.id.customPanel),
                                                0)),
                                0),
                        isDisplayed()));
        editText4.perform(replaceText("Champaign"), closeSoftKeyboard());
        Thread.sleep(1000);
        ViewInteraction materialButton13 = onView(
                allOf(withId(android.R.id.button1), withText("Remove"),
                        childAtPosition(
                                childAtPosition(
                                        withId(androidx.appcompat.R.id.buttonPanel),
                                        0),
                                3)));
        materialButton13.perform(scrollTo(), click());
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
