package edu.uiuc.cs427app.activity;
import java.util.*;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import androidx.appcompat.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import edu.uiuc.cs427app.R;
import edu.uiuc.cs427app.data.repository.UserRepository;
import edu.uiuc.cs427app.data.entity.User;

public class MainActivity extends BaseMenuActivity {
  private List<String> newLocations = new ArrayList<String>(); //locations array
  ListView listView;
  private String deleteLoc = "";
  private UserRepository userRepository;
  private String userEmail;
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    // Retrieve the user's theme preference from the intent
    int userTheme = getIntent().getIntExtra("userTheme", 0); // Default to 0 if not provided
    userEmail = getIntent().getStringExtra("userEmail");
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

    listView = findViewById(R.id.listView);
    userRepository = new UserRepository(getApplicationContext()); // Initialize userRepository first

    userRepository.getByEmail(userEmail).observe(this, user -> {
      if (user != null) {
        String cityListStr = user.getCityList();
        if (cityListStr != null && !cityListStr.isEmpty()) {
          String[] cities = cityListStr.split(",");
          newLocations.clear();
          newLocations.addAll(Arrays.asList(cities));
          ArrayAdapter<String> adapter = new ArrayAdapter<>(MainActivity.this,
                  android.R.layout.simple_list_item_1, android.R.id.text1, newLocations);
          listView.setAdapter(adapter);
          listView.setOnItemClickListener((parent, view, position, id) -> {
            String selectedCity = newLocations.get(position);
            Intent intent = new Intent(MainActivity.this, CityActivity.class);
            intent.putExtra("cityName", selectedCity);
            startActivity(intent);
          });
        }
      }
    });

    Button buttonAdd = findViewById(R.id.buttonAddLocation);
    Button buttonDel = findViewById(R.id.buttonRemoveLocation);
    buttonAdd.setOnClickListener(this);
    buttonDel.setOnClickListener(this);

  }
  @Override
  public void onClick(View view) {
    Intent intent;
    switch (view.getId()) {
      //case for adding a location
      case R.id.buttonAddLocation:
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Enter new Location");
        final EditText input = new EditText(this);
        builder.setView(input);
        builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
          // send data from the AlertDialog to the Activity
          public void onClick(DialogInterface dialog, int which) {
            String newCity = input.getText().toString().trim();
            newLocations.add(newCity);
            userRepository.addCity(userEmail, newCity);
          }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
        break;
      //case for removing a location
      case R.id.buttonRemoveLocation:
        AlertDialog.Builder removeDialog = new AlertDialog.Builder(this);
        removeDialog.setMessage("Enter Location to remove");
        final EditText inputRemove = new EditText(this);
        removeDialog.setView(inputRemove);
        removeDialog.setPositiveButton("Remove", new DialogInterface.OnClickListener() {
          // send data from the AlertDialog to the Activity
          public void onClick(DialogInterface dialog, int which) {
            String cityToRemove = inputRemove.getText().toString().trim();
            newLocations.remove(cityToRemove);
            userRepository.removeCity(userEmail, cityToRemove);
          }
        });
        AlertDialog alertDialo = removeDialog.create();
        alertDialo.show();
        break;
    }
  }
}


