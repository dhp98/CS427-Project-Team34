package edu.uiuc.cs427app.activity;

import android.os.Bundle;
import edu.uiuc.cs427app.R;

public class MainActivity extends BaseMenuActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
  }
}
