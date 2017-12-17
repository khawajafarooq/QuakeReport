package com.xmen.quakereport.NetworkAPI;

import com.xmen.quakereport.models.EarthquakeList;

import retrofit2.Call;
import retrofit2.http.GET;

public interface EarthquakeClient {

    @GET("query?format=geojson&starttime=2017-01-01&endtime=2018-01-02&limit=20")
    Call<EarthquakeList> getEarthQuakes();
}
