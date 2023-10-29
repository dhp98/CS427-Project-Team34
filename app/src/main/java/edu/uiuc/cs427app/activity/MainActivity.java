package edu.uiuc.cs427app.activity;

import android.os.Bundle;
import edu.uiuc.cs427app.R;

public class MainActivity extends BaseMenuActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    // Retrieve the user's theme preference from the intent
    int userTheme = getIntent().getIntExtra("userTheme", 0); // Default to 0 if not provided

    // Set the theme based on the user's preference before calling setContentView
    if (userTheme == 1) {
      setTheme(R.style.Theme_MyFirstApp);
    }
    else if (userTheme == 2) {
      setTheme(R.style.Theme_SkyBlue);
    }
    else {
      setTheme(R.style.Theme_TealBlue);
    }

    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
  }
}
