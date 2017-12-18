package com.xmen.quakereport;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import com.xmen.quakereport.Adapters.EarthquakeAdapter;
import com.xmen.quakereport.NetworkAPI.APIClient;
import com.xmen.quakereport.NetworkAPI.EarthquakeClient;
import com.xmen.quakereport.models.EarthquakeList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ListView listView = findViewById(R.id.list);

        EarthquakeClient apiInterface = APIClient.getClient().create(EarthquakeClient.class);

        // create map
        Map<String, Object> map = new HashMap<>();
        map.put("format", "geojson");
        map.put("starttime", "2017-01-01");
        map.put("endtime", "2018-01-02");
        map.put("limit", "20");

        // query param call
        Call<EarthquakeList> call = apiInterface.getEarthquakeList(map);

        call.enqueue(new Callback<EarthquakeList>() {
            @Override
            public void onResponse(Call<EarthquakeList> call, Response<EarthquakeList> response) {

                ArrayList<EarthquakeList.Feature> earthquakes = response.body().getFeatures();
                EarthquakeAdapter adapter = new EarthquakeAdapter(MainActivity.this, earthquakes);
                listView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<EarthquakeList> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Error :(", Toast.LENGTH_SHORT).show();
                call.cancel();
            }
        });

        /*
        Simple call
        Call<EarthquakeList> call = apiInterface.getEarthQuakes();

        // Log the url
        Log.v("MainActivity", "End point: " + call.request().url().toString());


        call.enqueue(new Callback<EarthquakeList>() {
            @Override
            public void onResponse(Call<EarthquakeList> call, Response<EarthquakeList> response) {

                ArrayList<EarthquakeList.Feature> earthquakes = response.body().getFeatures();

                EarthquakeAdapter adapter = new EarthquakeAdapter(MainActivity.this, earthquakes);
                listView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<EarthquakeList> call, Throwable t) {

                Toast.makeText(MainActivity.this, "Error :(", Toast.LENGTH_SHORT).show();
                call.cancel();
            }
        });
*/



    }
}
