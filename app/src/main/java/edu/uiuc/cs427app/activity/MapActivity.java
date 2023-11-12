package edu.uiuc.cs427app.activity;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.net.PlacesClient;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

import edu.uiuc.cs427app.R;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private String cityName;
    private static final String API_KEY = "7oQcQJEG21DD11l4l8PZT8P1VdNlcn3X";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_view);

        cityName = getIntent().getStringExtra("cityName");
        TextView cityNameTextView = findViewById(R.id.textViewCityTitle);
        cityNameTextView.setText(cityName);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        new FetchLocationTask().execute(cityName);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
    }

    private class FetchLocationTask extends AsyncTask<String, Void, LatLng> {

        protected LatLng doInBackground(String... cities) {
            String locationQueryUrl = "https://dataservice.accuweather.com/locations/v1/cities/search?apikey=" + API_KEY + "&q=" + cities[0];
            try {
                URL url = new URL(locationQueryUrl);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.connect();

                int responseCode = connection.getResponseCode();
                if (responseCode != 200) {
                    throw new RuntimeException("HttpResponseCode: " + responseCode);
                } else {
                    StringBuilder inline = new StringBuilder();
                    Scanner scanner = new Scanner(url.openStream());

                    while (scanner.hasNext()) {
                        inline.append(scanner.nextLine());
                    }
                    scanner.close();

                    JSONArray jsonArray = new JSONArray(inline.toString());
                    if (jsonArray.length() > 0) {
                        JSONObject location = jsonArray.getJSONObject(0);
                        JSONObject geoPosition = location.getJSONObject("GeoPosition");
                        double latitude = geoPosition.getDouble("Latitude");
                        double longitude = geoPosition.getDouble("Longitude");
                        return new LatLng(latitude, longitude);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(LatLng latLng) {
            if (latLng != null) {

                TextView textViewLongitude = findViewById(R.id.textViewLongitude);
                textViewLongitude.setText("Longitude: " + String.format("%.3f", latLng.longitude));

                TextView textViewLatitude = findViewById(R.id.textViewLatitude);
                textViewLatitude.setText("Latitude: " + String.format("%.3f", latLng.latitude));

                mMap.addMarker(new MarkerOptions().position(latLng).title(cityName));
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10));
            }

        }

    }
}
