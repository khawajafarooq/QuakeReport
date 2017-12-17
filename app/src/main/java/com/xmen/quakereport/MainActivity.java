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
        Call<EarthquakeList> call = apiInterface.getEarthQuakes();

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




    }
}
