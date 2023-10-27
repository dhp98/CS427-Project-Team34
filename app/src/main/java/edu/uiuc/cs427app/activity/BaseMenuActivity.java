package edu.uiuc.cs427app.activity;

import static java.util.Objects.requireNonNull;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.Transformations;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.google.android.libraries.places.api.Places;

import edu.uiuc.cs427app.R;
import edu.uiuc.cs427app.data.entity.User;
import edu.uiuc.cs427app.data.repository.UserRepository;

public class BaseMenuActivity extends AppCompatActivity implements View.OnClickListener {
  /**
   * Initialize App Bar (also called Support Action Bar) to append logged in user name to the
   * default team name and show the logout button when the user has successfully logged in.
   *
   * @param savedInstanceState If you save the state of the application in a bundle (typically
   *     non-persistent, dynamic data in onSaveInstanceState), it can be passed back to onCreate if
   *     the activity needs to be recreated (e.g., orientation change) so that you don't lose this
   *     prior information. If no data was supplied, savedInstanceState is null.
   */
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    String userEmail = getIntent().getStringExtra("userEmail");
    UserRepository userRepository = new UserRepository(getApplicationContext());
    // Method for modifying UI changes for adding a new city
    Transformations.distinctUntilChanged(userRepository.getByEmail(userEmail))
        .observe(
            BaseMenuActivity.this,
            new Observer<User>() {
              /**
               * This is an callback which will be invoked when the `User` instance has changed.
               * This typically happens when a related column is updated or the results of a
               * `SELECT` query is returned. Since the `userRepository.getByEmail` method returns a
               * `LiveData<User>`, it means Android will keep listening to the underlying query and
               * invoke this callback method whenever the data changes. We set the username in the
               * app bar, once the user has successfully logged in, inside this callback method.
               *
               * @param user User instance
               */
              @Override
              public void onChanged(User user) {
                if (getSupportActionBar() != null) {
                  String existingText = requireNonNull(getSupportActionBar().getTitle()).toString();
                  if (!existingText.contains("-")) {
                    getSupportActionBar()
                        .setTitle(
                            String.format(
                                "%s-%s",
                                getSupportActionBar().getTitle().toString(), user != null ? user.getLoginName() : ""));
                  }
                }
              }
            });
  }

  /**
   * Generic click handler to handle anything clicked inside the main activity
   *
   * @param view Current layout view
   */
  @Override
  public void onClick(View view) {
    Log.d("On View", "Resulted in " + view);
  }

  /**
   * Add buttons for user actions in app bar
   *
   * @param menu Android menu class instanceThe options menu in which you place your items
   * @return You must return true for the menu to be displayed; if you return false it will not be
   *     shown.
   */
  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    MenuInflater menuInflater = getMenuInflater();
    menuInflater.inflate(R.menu.menubuttons, menu);
    return true;
  }

  /**
   * Prepare the Screen's standard options menu to be displayed. This is called right before the
   * menu is shown, every time it is shown You can use this method to efficiently enable/disable
   * items or otherwise dynamically modify the contents
   *
   * @param menu The options menu as last shown or first initialized by onCreateOptionsMenu()
   * @return You must return true for the menu to be displayed; if you return false it will not be
   *     shown.
   */
  @Override
  public boolean onPrepareOptionsMenu(Menu menu) {
    String userEmail = getIntent().getStringExtra("userEmail");
    // Show logout button only if user is signed in
    return userEmail != null && !userEmail.isEmpty();
  }

  /**
   * This hook is called whenever an item in your options menu is selected
   *
   * @param item The menu item that was selected. This value cannot be null.
   * @return Return false to allow normal menu processing to proceed, true to consume it here.
   */
  @Override
  public boolean onOptionsItemSelected(@NonNull MenuItem item) {
    switch (item.getItemId()) {
      case R.id.action_logout:
        Intent logoutIntent = new Intent(BaseMenuActivity.this, MainActivityFrontPage.class);
        logoutIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        UserRepository userRepository = new UserRepository(getApplicationContext());
        userRepository
            .getByEmail(getIntent().getStringExtra("userEmail"))
            .observe(
                this,
                new Observer<User>() {
                  @Override
                  public void onChanged(User user) {
                    user.setLoggedIn(false);
                    userRepository
                        .update(user)
                        .addListener(
                            () -> startActivity(logoutIntent),
                            ContextCompat.getMainExecutor(getApplicationContext()));
                    if (Places.isInitialized()) {
                      Places.deinitialize();
                    }
                  }
                });
        break;
    }
    return true;
  }
}
