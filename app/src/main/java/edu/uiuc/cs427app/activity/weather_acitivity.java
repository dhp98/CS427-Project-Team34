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

public class weather_acitivity extends AppCompatActivity {
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



        String city = getIntent().getStringExtra("city");






    RequestQueue queue2 = Volley.newRequestQueue(this);

    String url2 = "https://api.weatherbit.io/v2.0/current?&city="+city+"&key=0886b82915b746d69e8c893a4bd5fe61";
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


                Temperature2.setText(get_data.get("temp").toString()+" ºF");
                weather.setText(weath.get("description").toString());
                wind.setText(String.format("%.2f",get_data.get("wind_spd") ));
                Humidity.setText(get_data.get("rh").toString());
                city2.setText(city);
                date.setText(get_data.get("ob_time").toString());

                //Log.e("yaounde", ss);

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