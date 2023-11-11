package edu.uiuc.cs427app.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


import edu.uiuc.cs427app.R;

public class CityActivity extends AppCompatActivity implements View.OnClickListener {
    String cityName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city);
        // Setting variables for login and signup
        Button weather = findViewById(R.id.buttonweather);
        weather.setOnClickListener(this);
        cityName = getIntent().getStringExtra("cityName");
        TextView cityNameTextView = findViewById(R.id.textViewCityTitle);
        cityNameTextView.setText(cityName);

    }



    @Override
    public void onClick(View view) {
        Intent intent;


        intent = new Intent(this, weather_acitivity.class).putExtra("city",cityName);

        startActivity(intent);



}}




