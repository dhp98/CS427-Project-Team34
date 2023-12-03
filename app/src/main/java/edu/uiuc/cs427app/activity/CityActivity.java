package edu.uiuc.cs427app.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.libraries.places.api.Places;

import edu.uiuc.cs427app.R;

public class CityActivity extends AppCompatActivity implements View.OnClickListener {
    String cityName, state, country;
  
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city);
        
        cityName = getIntent().getStringExtra("cityName");
        state = getIntent().getStringExtra("state");
        country = getIntent().getStringExtra("country");

        TextView cityNameTextView = findViewById(R.id.textViewCityTitle);
        cityNameTextView.setText(cityName);
      
        Button buttonMap = findViewById(R.id.buttonMap);
        buttonMap.setOnClickListener(this);
      
        Button weather = findViewById(R.id.buttonWeather);
        weather.setOnClickListener(this);
    }



    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.buttonMap) {
            Intent mapIntent = new Intent(this, MapActivity.class);
            mapIntent.putExtra("cityName", cityName);
            startActivity(mapIntent);
        }
        else if (view.getId() == R.id.buttonWeather) {
            Intent intent = new Intent(this, WeatherActivity.class).putExtra("cityName",cityName);
            //intent.putExtra("state", state);
            //intent.putExtra("country", country);
            startActivity(intent);
        }

    }
}

