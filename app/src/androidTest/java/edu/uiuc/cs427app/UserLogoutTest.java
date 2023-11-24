package edu.uiuc.cs427app;

import android.content.Context;
import android.content.Intent;

import androidx.room.Room;
import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Date;
import java.util.concurrent.Executors;

import edu.uiuc.cs427app.activity.MainActivity;
import edu.uiuc.cs427app.data.entity.User;
import edu.uiuc.cs427app.data.repository.UserRepository;
import edu.uiuc.cs427app.database.AppDatabase;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class UserLogoutTest {
  static Intent intent;
  static final Context targetContext =
      InstrumentationRegistry.getInstrumentation().getTargetContext();
  AppDatabase appDatabase;
  UserRepository userRepository;
  User espressoUser;

  static {
    intent = new Intent(targetContext, MainActivity.class);
    intent.putExtra("userEmail", "espresso_user@illinois.edu");
  }

  /** Create test user and related cities before each test. */
  @Before
  public void setUp() {
    initializeDatabase();
    userRepository = new UserRepository(targetContext);
    Date now = new Date();
    espressoUser = new User();
    espressoUser.setEmailId("espresso_user@illinois.edu");
    espressoUser.setPassword("123456");
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
  public void checkUserLogoutUpdatesDBAndRedirectsToFrontActivity() throws InterruptedException {
    try (ActivityScenario<MainActivity> scenario = ActivityScenario.launch(intent)) {
      // Check Logout button is displayed
      Thread.sleep(1000);
      Espresso.onView(ViewMatchers.withId(R.id.action_logout))
          .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
      Thread.sleep(1000);
      // Click on logout button
      Espresso.onView(ViewMatchers.withId(R.id.action_logout)).perform(ViewActions.click());
      Thread.sleep(1000);
      User user = userRepository.getByEmailSync(espressoUser.getEmailId());
      Assert.assertFalse(user.getLoggedIn());
      // Check MainActivityFrontPage signup and sign-in buttons are displayed
      Espresso.onView(ViewMatchers.withId(R.id.gotosignupbutton))
          .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
      Espresso.onView(ViewMatchers.withId(R.id.loginbutton))
          .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }
  }

  /** Delete user and related cities after test completion */
  @After
  public void tearDown() {
    userRepository.delete(espressoUser);
    appDatabase.close();
  }
}
