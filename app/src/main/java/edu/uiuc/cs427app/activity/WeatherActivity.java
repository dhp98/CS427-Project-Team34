package edu.uiuc.cs427app.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import edu.uiuc.cs427app.R;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class WeatherActivity extends AppCompatActivity {
    TextView weather;
    TextView Temperature2;
    TextView Humidity;
    TextView wind;
    TextView city2;
    TextView date;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_acitivity);
        weather = findViewById(R.id.Weather);
         Temperature2= findViewById(R.id.Temperature);
         wind=findViewById(R.id.Wind_condition);
         Humidity=findViewById(R.id.Humidity);
         city2=findViewById(R.id.city22);
        date=findViewById(R.id.date2);

        String[] parts = getIntent().getStringExtra("cityName").split(",");
        String city = parts[0];
        String state = parts[1];
        String country = parts[2];

    RequestQueue queue2 = Volley.newRequestQueue(this);

    String url2 = "https://api.weatherbit.io/v2.0/current?&city="+city+"&state="+state+"&country"+country+"&key=5f97b0923389467687fd6d3cb1d83bf0";
    StringRequest stringRequest2 = new StringRequest(Request.Method.GET, url2, new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {

            try {

                //JSONArray array = new JSONArray(response);
                JSONObject obj = new JSONObject(response.toString());
                String data=obj.getString("data");
                JSONArray array= new JSONArray(data);
                JSONObject get_data= new JSONObject(array.get(0).toString());
                JSONObject weath = new JSONObject(get_data.get("weather").toString());

                if (get_data.has("temp")) {
                    Temperature2.setText(get_data.get("temp").toString() + " ºC");
                } else {
                    Temperature2.setText("");
                }

                if (weath.has("description")) {
                    weather.setText(weath.get("description").toString());
                } else {
                    weather.setText("");
                }

                if (get_data.has("wind_spd")) {
                    wind.setText(String.format("%.2f", get_data.get("wind_spd")));
                } else {
                    wind.setText("");
                }

                if (get_data.has("rh")) {
                    Humidity.setText(get_data.get("rh").toString());
                } else {
                    Humidity.setText("");
                }

                city2.setText(city != null ? city : "");

                if (get_data.has("ob_time")) {
                    date.setText(get_data.get("ob_time").toString());
                } else {
                    date.setText("");
                }


            } catch (JSONException e) {
                throw new RuntimeException(e);
            }

        }
    }, new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            Log.e("API_ERROR", error.getLocalizedMessage());
        }
    });
        queue2.add(stringRequest2);


}

}