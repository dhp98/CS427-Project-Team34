package edu.uiuc.cs427app.activity;

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

public class CityActivity extends AppCompatActivity implements View.OnClickListener{

    private String cityName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city);

        cityName = getIntent().getStringExtra("cityName");
        TextView cityNameTextView = findViewById(R.id.textViewCityTitle);
        cityNameTextView.setText(cityName);

        Button buttonMap = findViewById(R.id.buttonMap);
        buttonMap.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.buttonMap) {
            Intent mapIntent = new Intent(this, MapActivity.class);
            mapIntent.putExtra("cityName", cityName);
            startActivity(mapIntent);
        }

    }
}
