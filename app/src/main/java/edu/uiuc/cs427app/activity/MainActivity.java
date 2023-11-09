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
import edu.uiuc.cs427app.data.entity.UserCity;
import edu.uiuc.cs427app.data.repository.UserRepository;
import edu.uiuc.cs427app.data.repository.UserCityRepository;
import edu.uiuc.cs427app.data.entity.User;

import com.google.android.gms.common.api.Status;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.model.AddressComponent;
import com.google.android.libraries.places.api.net.PlacesClient;


import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;

public class MainActivity extends BaseMenuActivity {
  private List<String> newLocations = new ArrayList<String>(); //locations array
  ListView listView;
  private String deleteLoc = "";
  private UserRepository userRepository;
  private UserCityRepository userCityRepository;
  private String userEmail;
  private String userId;
  private static final int AUTOCOMPLETE_REQUEST_CODE = 1;
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    // Retrieve the user's theme preference from the intent
    int userTheme = getIntent().getIntExtra("userTheme", 0); // Default to 0 if not provided
    userEmail = getIntent().getStringExtra("userEmail");
    userId = getIntent().getStringExtra("userId");

    if (!Places.isInitialized()) {
      Places.initialize(getApplicationContext(), ""); // Replace with your actual API ke
    }

    PlacesClient placesClient = Places.createClient(this);
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
    userCityRepository = new UserCityRepository(getApplicationContext());
    userRepository.getByEmail(userEmail).observe(this, user -> {
      if (user != null) {
        // Observe the LiveData for user cities from the UserCityRepository
        userCityRepository.getUserCitiesByUserId(userId).observe(this, userCityList -> {
          newLocations.clear();
          if (userCityList != null) {
            for (UserCity userCity : userCityList) {
              newLocations.add(userCity.getCityName());
            }
          }
          // Now that newLocations has been updated, notify the adapter
          ArrayAdapter<String> adapter = new ArrayAdapter<>(MainActivity.this,
                  android.R.layout.simple_list_item_1, android.R.id.text1, newLocations);
          listView.setAdapter(adapter);
          listView.setOnItemClickListener((parent, view, position, id) -> {
            String selectedCity = newLocations.get(position);
            Intent intent = new Intent(MainActivity.this, CityActivity.class);
            intent.putExtra("cityName", selectedCity);
            startActivity(intent);
          });
        });
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
//
        List<Place.Field> fields = Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.ADDRESS_COMPONENTS);
        Intent intent2 = new Autocomplete.IntentBuilder(AutocompleteActivityMode.FULLSCREEN, fields)
                .build(this);
        startActivityForResult(intent2, AUTOCOMPLETE_REQUEST_CODE);
        break;
      //case for removing a location
      case R.id.buttonRemoveLocation:
        AlertDialog.Builder removeDialog = new AlertDialog.Builder(this);
        removeDialog.setMessage("Enter Location to remove");
        final EditText inputRemove = new EditText(this);
        removeDialog.setView(inputRemove);
        removeDialog.setPositiveButton("Remove", (dialog, which) -> {
          String cityToRemove = inputRemove.getText().toString().trim();
          if (!cityToRemove.isEmpty()) {
            // Since we don't have a unique identifier for the city entry, we need to fetch the city and then delete it
            userCityRepository.getUserCitiesByUserId(userId).observe(this, userCities -> {
              for (UserCity userCity : userCities) {
                if (userCity.getCityName().equals(cityToRemove)) {
                  userCityRepository.delete(userCity); // Remove from UserCity table.
                  newLocations.remove(cityToRemove); // Update the local list
                  ((ArrayAdapter) listView.getAdapter()).notifyDataSetChanged(); // Notify the adapter to refresh the list
                  break; // Break the loop once we find the match
                }
              }
            });
          }
        });

        AlertDialog alertDialo = removeDialog.create();
        alertDialo.show();
        break;
    }
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);

    if (requestCode == AUTOCOMPLETE_REQUEST_CODE) {
      if (resultCode == RESULT_OK) {
        Place place = Autocomplete.getPlaceFromIntent(data);
        String cityName = place.getName();
        String state = "";
        String country = "";
        double latitude = 0;
        double longitude = 0;

        for (AddressComponent component : place.getAddressComponents().asList()) {
          String type = component.getTypes().get(0);
          if (type.equals("administrative_area_level_1")) {
            state = component.getName();
          } else if (type.equals("country")) {
            country = component.getName();
          }
        }

        if (place.getLatLng() != null) {
          latitude = place.getLatLng().latitude;
          longitude = place.getLatLng().longitude;
        }

        UserCity userCity = new UserCity(userId, cityName, state, country, latitude, longitude);
        userCityRepository.insert(userCity);
        newLocations.add(cityName);
        ((ArrayAdapter) listView.getAdapter()).notifyDataSetChanged();
      } else if (resultCode == AutocompleteActivity.RESULT_ERROR) {
        Status status = Autocomplete.getStatusFromIntent(data);
        // Handle the error.
      }
    }
  }
}


