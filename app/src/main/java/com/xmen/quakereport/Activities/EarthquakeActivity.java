package com.xmen.quakereport.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.xmen.quakereport.Adapters.EarthquakeAdapter;
import com.xmen.quakereport.NetworkAPI.APIClient;
import com.xmen.quakereport.NetworkAPI.EarthquakeClient;
import com.xmen.quakereport.R;
import com.xmen.quakereport.models.EarthquakeList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EarthquakeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ConnectivityManager cm =
                (ConnectivityManager)this.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();


        final ListView listView = findViewById(R.id.list);
        final TextView emptyTextView = findViewById(R.id.empty_view);
        final View loadingIndicator = findViewById(R.id.loading_indicator);
        listView.setEmptyView(emptyTextView);


        if (isConnected) {

            EarthquakeClient apiInterface = APIClient.getClient().create(EarthquakeClient.class);


            SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
            String minMagnitude = sharedPrefs.getString(
                    getString(R.string.settings_min_magnitude_key),
                    getString(R.string.settings_min_magnitude_default));

            String orderBy = sharedPrefs.getString(
                    getString(R.string.settings_order_by_key),
                    getString(R.string.settings_order_by_default)
            );


            // create map
            Map<String, Object> map = new HashMap<>();
            map.put("format", "geojson");
            map.put("limit", "20");
            map.put("minmag", minMagnitude);
            map.put("orderby", orderBy);


            // query param call
            Call<EarthquakeList> call = apiInterface.getEarthquakeList(map);

            call.enqueue(new Callback<EarthquakeList>() {
                @Override
                public void onResponse(Call<EarthquakeList> call, Response<EarthquakeList> response) {

                    loadingIndicator.setVisibility(View.GONE);

                    ArrayList<EarthquakeList.Feature> earthquakes = response.body().getFeatures();

                    if (earthquakes != null && !earthquakes.isEmpty()) {
                        EarthquakeAdapter adapter = new EarthquakeAdapter(EarthquakeActivity.this, earthquakes);
                        listView.setAdapter(adapter);

                    } else {
                        emptyTextView.setText(R.string.no_earthquakes);
                    }
                }

                @Override
                public void onFailure(Call<EarthquakeList> call, Throwable t) {

                    loadingIndicator.setVisibility(View.GONE);
                    emptyTextView.setText(R.string.failure_error);
                }
            });

        } else {
            loadingIndicator.setVisibility(View.GONE);
            emptyTextView.setText(R.string.no_internet_error);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Intent settingsIntent = new Intent(this, SettingsActivity.class);
            startActivity(settingsIntent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
