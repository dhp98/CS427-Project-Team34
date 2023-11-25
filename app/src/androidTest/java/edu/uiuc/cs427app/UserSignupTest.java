package edu.uiuc.cs427app;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
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

import java.util.concurrent.Executors;

import edu.uiuc.cs427app.activity.SignUpActivity;
import edu.uiuc.cs427app.data.entity.User;
import edu.uiuc.cs427app.data.repository.UserRepository;
import edu.uiuc.cs427app.database.AppDatabase;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class UserSignupTest {
    static final Context targetContext =
            InstrumentationRegistry.getInstrumentation().getTargetContext();
    AppDatabase appDatabase;
    UserRepository userRepository;

    private View decorView;

    private static final String SIGNUP_EMAIL_TO_USE = "espresso_user@illinois.edu";
    private static final String SIGNUP_PASSWORD_TO_USE = "123456";

    /**
     * Initialize DB and repository before each test.
     */
    @Before
    public void setUp() {
        initializeDatabase();
        userRepository = new UserRepository(targetContext);
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
    public void checkSignupSuccess() throws InterruptedException {
        performSignup(SIGNUP_EMAIL_TO_USE, SIGNUP_PASSWORD_TO_USE, SIGNUP_PASSWORD_TO_USE);

        // Assert new user has been added to database
        User user = userRepository.getByEmailSync(SIGNUP_EMAIL_TO_USE);
        Assert.assertNotNull(user);
        Assert.assertEquals(SIGNUP_EMAIL_TO_USE, user.getEmailId());
        Assert.assertEquals(SIGNUP_PASSWORD_TO_USE, user.getPassword());

        Thread.sleep(1000);
    }

    @Test
    public void checkSignupShouldFailWhenEmailIsEmpty() throws InterruptedException {
        performSignup("", SIGNUP_PASSWORD_TO_USE, SIGNUP_PASSWORD_TO_USE);

        // Assert new user was not added to database
        User user = userRepository.getByEmailSync(SIGNUP_EMAIL_TO_USE);
        Assert.assertNull(user);

        Thread.sleep(1000);
    }

    @Test
    public void checkSignupShouldFailWhenPasswordsMismatch() throws InterruptedException {
        performSignup(SIGNUP_EMAIL_TO_USE, SIGNUP_PASSWORD_TO_USE, SIGNUP_PASSWORD_TO_USE + "abc");

        // Assert new user was not added to database
        User user = userRepository.getByEmailSync(SIGNUP_EMAIL_TO_USE);
        Assert.assertNull(user);

        Thread.sleep(1000);
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
        User espressoUser = userRepository.getByEmailSync(SIGNUP_EMAIL_TO_USE);
        if (espressoUser != null) {
            userRepository.delete(espressoUser);
        }
        appDatabase.close();
    }

    private void performSignup(String email, String pw, String confirmPw) throws InterruptedException {
        try (ActivityScenario<SignUpActivity> activityScenario =
                     ActivityScenario.launch(SignUpActivity.class)) {
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
            emailTextField.perform(replaceText(email), closeSoftKeyboard());

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
            passwordField.perform(replaceText(pw), closeSoftKeyboard());

            ViewInteraction confirmPasswordField =
                    onView(
                            allOf(
                                    withId(R.id.confirmPassword),
                                    childAtPosition(
                                            childAtPosition(
                                                    withClassName(is("androidx.constraintlayout.widget.ConstraintLayout")),
                                                    0),
                                            3),
                                    isDisplayed()));
            confirmPasswordField.perform(replaceText(confirmPw), closeSoftKeyboard());

            Thread.sleep(1000);

            ViewInteraction signupButton =
                    onView(allOf(withId(R.id.signUp), withText("Sign up"), isDisplayed()));
            signupButton.perform(click());
        }
    }
}