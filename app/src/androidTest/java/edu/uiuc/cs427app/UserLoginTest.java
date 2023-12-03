package edu.uiuc.cs427app;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.RootMatchers.withDecorView;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;

import android.content.Context;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.room.Room;
import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.ViewInteraction;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.test.platform.app.InstrumentationRegistry;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.hamcrest.TypeSafeMatcher;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Date;
import java.util.concurrent.Executors;

import edu.uiuc.cs427app.activity.SignInActivity;
import edu.uiuc.cs427app.data.entity.User;
import edu.uiuc.cs427app.data.repository.UserRepository;
import edu.uiuc.cs427app.database.AppDatabase;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class UserLoginTest {
    static final Context targetContext =
            InstrumentationRegistry.getInstrumentation().getTargetContext();
    AppDatabase appDatabase;
    UserRepository userRepository;
    User espressoUser;

    private View decorView;

    private static final String SIGNUP_EMAIL_TO_USE = "espresso_user@illinois.edu";
    private static final String SIGNUP_PASSWORD_TO_USE = "123456";
    private static final String UNREGISTERED_EMAIL_TO_USE = "adeelas2@illinois.edu";
    private static final boolean IS_DEVICE_API_LEVEL_29 = Build.VERSION.SDK_INT == Build.VERSION_CODES.Q;

    /**
     * Initialize DB and repository before each test.
     */
    @Before
    public void setUp() {
        initializeDatabase();
        userRepository = new UserRepository(targetContext);
        Date now = new Date();
        espressoUser = new User();
        espressoUser.setEmailId(SIGNUP_EMAIL_TO_USE);
        espressoUser.setPassword(SIGNUP_PASSWORD_TO_USE);
        espressoUser.setLoginName("espresso_user");
        espressoUser.setCreatedOn(now);
        espressoUser.setLastModified(now);
        espressoUser.setCurrentTheme(0);
        espressoUser.setLoggedIn(false);
        userRepository.insert(espressoUser);
    }

    /**
     * Initialize in memory database which allows main thread queries and has only a single thread
     * task executor. This allows to test more reliably and avoid various hacks to get {@link
     * androidx.lifecycle.LiveData} to work with Espresso testing
     */
    private void initializeDatabase() {
        appDatabase =
                Room.inMemoryDatabaseBuilder(targetContext, AppDatabase.class)
                        .allowMainThreadQueries()
                        .setTransactionExecutor(Executors.newSingleThreadExecutor())
                        .build();
    }

    @Test
    public void checkLoginSuccessWhenCredentialsMatchAndUserExists() throws InterruptedException {
        try (ActivityScenario<SignInActivity> activityScenario =
                     ActivityScenario.launch(SignInActivity.class)) {
            Thread.sleep(1000);

            ViewInteraction emailTextField =
                    onView(
                            allOf(
                                    withId(R.id.userEmail),
                                    childAtPosition(
                                            childAtPosition(
                                                    withClassName(is("androidx.constraintlayout.widget.ConstraintLayout")),
                                                    0),
                                            1),
                                    isDisplayed()));
            emailTextField.perform(replaceText(SIGNUP_EMAIL_TO_USE), closeSoftKeyboard());

            ViewInteraction passwordField =
                    onView(
                            allOf(
                                    withId(R.id.userPassword),
                                    childAtPosition(
                                            childAtPosition(
                                                    withClassName(is("androidx.constraintlayout.widget.ConstraintLayout")),
                                                    0),
                                            2),
                                    isDisplayed()));
            passwordField.perform(replaceText(SIGNUP_PASSWORD_TO_USE), closeSoftKeyboard());

            Thread.sleep(1000);

            ViewInteraction loginButton =
                    onView(allOf(withId(R.id.userLogin), withText("Login"), isDisplayed()));
            loginButton.perform(click());

            // Assert user logged in flag has been set to true in database
            User user = userRepository.getByEmailSync(SIGNUP_EMAIL_TO_USE);
            Assert.assertNotNull(user);
            Assert.assertTrue(user.getLoggedIn());

            Thread.sleep(1000);
        }
    }

    @Test
    public void checkLoginShouldFailWhenCredentialsMismatch() throws InterruptedException {
        try (ActivityScenario<SignInActivity> activityScenario =
                     ActivityScenario.launch(SignInActivity.class)) {
            Thread.sleep(1000);

            ViewInteraction emailTextField =
                    onView(
                            allOf(
                                    withId(R.id.userEmail),
                                    childAtPosition(
                                            childAtPosition(
                                                    withClassName(is("androidx.constraintlayout.widget.ConstraintLayout")),
                                                    0),
                                            1),
                                    isDisplayed()));
            emailTextField.perform(replaceText(SIGNUP_EMAIL_TO_USE), closeSoftKeyboard());

            ViewInteraction passwordField =
                    onView(
                            allOf(
                                    withId(R.id.userPassword),
                                    childAtPosition(
                                            childAtPosition(
                                                    withClassName(is("androidx.constraintlayout.widget.ConstraintLayout")),
                                                    0),
                                            2),
                                    isDisplayed()));
            passwordField.perform(
                    replaceText(SIGNUP_PASSWORD_TO_USE.substring(0, SIGNUP_PASSWORD_TO_USE.length() - 1)),
                    closeSoftKeyboard());

            Thread.sleep(1000);

            ViewInteraction loginButton =
                    onView(allOf(withId(R.id.userLogin), withText("Login"), isDisplayed()));
            loginButton.perform(click());

            // Assert toast message is displayed
            // Toasts are displayed in a different view root on API levels other than 29
            if (IS_DEVICE_API_LEVEL_29) {
                onView(withText("Username and/or password don't match"))
                        .inRoot(withDecorView(Matchers.not(decorView)))
                        .check(matches(isDisplayed()));
            }

            // Assert user logged in flag is still false in database
            User user = userRepository.getByEmailSync(SIGNUP_EMAIL_TO_USE);
            Assert.assertNotNull(user);
            Assert.assertFalse(user.getLoggedIn());

            Thread.sleep(1000);
        }
    }

    @Test
    public void checkLoginShouldFailWhenCredentialsEmpty() throws InterruptedException {
        try (ActivityScenario<SignInActivity> activityScenario =
                     ActivityScenario.launch(SignInActivity.class)) {
            Thread.sleep(1000);

            ViewInteraction emailTextField =
                    onView(
                            allOf(
                                    withId(R.id.userEmail),
                                    childAtPosition(
                                            childAtPosition(
                                                    withClassName(is("androidx.constraintlayout.widget.ConstraintLayout")),
                                                    0),
                                            1),
                                    isDisplayed()));
            emailTextField.perform(replaceText(SIGNUP_EMAIL_TO_USE), closeSoftKeyboard());

            ViewInteraction passwordField =
                    onView(
                            allOf(
                                    withId(R.id.userPassword),
                                    childAtPosition(
                                            childAtPosition(
                                                    withClassName(is("androidx.constraintlayout.widget.ConstraintLayout")),
                                                    0),
                                            2),
                                    isDisplayed()));
            passwordField.perform(replaceText(""), closeSoftKeyboard());

            Thread.sleep(1000);

            ViewInteraction loginButton =
                    onView(allOf(withId(R.id.userLogin), withText("Login"), isDisplayed()));
            loginButton.perform(click());

            // Assert toast message is displayed
            // Toasts are displayed in a different view root on API levels other than 29
            if (IS_DEVICE_API_LEVEL_29) {
                onView(withText("Email and password are required"))
                        .inRoot(withDecorView(Matchers.not(decorView)))
                        .check(matches(isDisplayed()));
            }

            // Assert user logged in flag is still false in database
            User user = userRepository.getByEmailSync(SIGNUP_EMAIL_TO_USE);
            Assert.assertNotNull(user);
            Assert.assertFalse(user.getLoggedIn());

            Thread.sleep(1000);
        }
    }

    @Test
    public void checkLoginShouldFailWhenUserNotRegistered() throws InterruptedException {
        try (ActivityScenario<SignInActivity> activityScenario =
                     ActivityScenario.launch(SignInActivity.class)) {
            Thread.sleep(1000);

            ViewInteraction emailTextField =
                    onView(
                            allOf(
                                    withId(R.id.userEmail),
                                    childAtPosition(
                                            childAtPosition(
                                                    withClassName(is("androidx.constraintlayout.widget.ConstraintLayout")),
                                                    0),
                                            1),
                                    isDisplayed()));
            emailTextField.perform(replaceText(UNREGISTERED_EMAIL_TO_USE), closeSoftKeyboard());

            ViewInteraction passwordField =
                    onView(
                            allOf(
                                    withId(R.id.userPassword),
                                    childAtPosition(
                                            childAtPosition(
                                                    withClassName(is("androidx.constraintlayout.widget.ConstraintLayout")),
                                                    0),
                                            2),
                                    isDisplayed()));
            passwordField.perform(replaceText(SIGNUP_PASSWORD_TO_USE), closeSoftKeyboard());

            Thread.sleep(1000);

            ViewInteraction loginButton =
                    onView(allOf(withId(R.id.userLogin), withText("Login"), isDisplayed()));
            loginButton.perform(click());

            // Assert toast message is displayed
            // Toasts are displayed in a different view root on API levels other than 29
            if (IS_DEVICE_API_LEVEL_29) {
                onView(withText("Username and/or password don't match"))
                        .inRoot(withDecorView(Matchers.not(decorView)))
                        .check(matches(isDisplayed()));
            }

            // Assert user logged in flag is still false in database
            User user = userRepository.getByEmailSync(UNREGISTERED_EMAIL_TO_USE);
            Assert.assertNull(user);

            Thread.sleep(1000);
        }
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
                return parent instanceof ViewGroup
                        && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }

    /**
     * Delete user and related cities after test completion
     */
    @After
    public void tearDown() {
        userRepository.delete(espressoUser);
        appDatabase.close();
    }
}
