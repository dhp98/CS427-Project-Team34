package edu.uiuc.cs427app.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import edu.uiuc.cs427app.R;

public class CityActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city);

        String cityName = getIntent().getStringExtra("cityName");
        TextView cityNameTextView = findViewById(R.id.textViewCityTitle);
        cityNameTextView.setText(cityName);
    }
}